(ns enthalpy.trainer
  (:require [reagent.core :as r]
            [enthalpy.words :as words]
            [enthalpy.layout :as layout]
            [enthalpy.emulation :as emulation]))

;; ---------------------------------------------------------------------------
;; State atom
;; ---------------------------------------------------------------------------

(def state
  (r/atom
   {:level          1
    :mode           :words    ; :words | :time
    :limit          15        ; word count OR seconds depending on :mode
    :words          []        ; current prompt word list
    :word-index     0         ; index of the word the user is currently typing
    :input          ""        ; live value of the text input
    :input-error?   false     ; true when current input doesn't match target prefix
    :hits           0         ; correctly completed words
    :errors         0         ; total error keystrokes
    :started?       false     ; true once the user types the first character
    :finished?      false
    :start-ms       nil       ; js/Date.now() at first keystroke
    :elapsed-ms     0         ; ms since start, updated by timer loop
    :raf-id         nil       ; requestAnimationFrame handle for the timer
    :show-cheat?    true
    :sound-stroke?  false
    :sound-error?   false
    :allow-caps?    false
    :allow-punct?   false
    :backspace-req? false
    :emulate-qwerty? false})) ; translate physical US QWERTY keys to Enthium positions

;; ---------------------------------------------------------------------------
;; Timer — runs via requestAnimationFrame while the round is active
;; ---------------------------------------------------------------------------

(defn- tick! [start-ms]
  (let [now   (js/Date.now)
        id    (js/requestAnimationFrame (fn [_] (tick! start-ms)))]
    (swap! state assoc
           :elapsed-ms (- now start-ms)
           :raf-id     id)))

(defn- start-timer! []
  (let [start (js/Date.now)]
    (swap! state assoc :start-ms start)
    (let [id (js/requestAnimationFrame (fn [_] (tick! start)))]
      (swap! state assoc :raf-id id))))

(defn- stop-timer! []
  (when-let [id (:raf-id @state)]
    (js/cancelAnimationFrame id)
    (swap! state assoc :raf-id nil)))

;; ---------------------------------------------------------------------------
;; Round management
;; ---------------------------------------------------------------------------

(defn- gen-words [s]
  (let [{:keys [level mode limit allow-caps? allow-punct?]} s]
    (cond
      (= level 8)
      (vec (words/sentences-for-round (min 10 limit)))

      (= mode :time)
      ;; for time mode, generate more words than we'll ever need
      (vec (words/words-for-round level 200 allow-caps?))

      :else
      (vec (words/words-for-round level limit allow-caps?)))))

(defn init-round! []
  (stop-timer!)
  (let [s @state
        ws (gen-words s)]
    (swap! state assoc
           :words       ws
           :word-index  0
           :input       ""
           :input-error? false
           :hits        0
           :errors      0
           :started?    false
           :finished?   false
           :start-ms    nil
           :elapsed-ms  0
           :raf-id      nil)))

(defn reset-round! []
  (init-round!))

;; ---------------------------------------------------------------------------
;; WPM / accuracy helpers
;; ---------------------------------------------------------------------------

(defn wpm
  "Standard WPM: hits per minute."
  [{:keys [hits elapsed-ms]}]
  (let [mins (/ elapsed-ms 60000)]
    (if (pos? mins)
      (js/Math.round (/ hits mins))
      0)))

(defn accuracy
  "Percentage of keystrokes that were correct."
  [{:keys [hits errors]}]
  (let [total (+ hits errors)]
    (if (pos? total)
      (-> (* 100.0 hits) (/ total) (.toFixed 1))
      "100.0")))

;; ---------------------------------------------------------------------------
;; Input / word submission logic
;; ---------------------------------------------------------------------------

(defn- finish-round! []
  (stop-timer!)
  (swap! state assoc :finished? true))

(defn- check-time-limit! [s]
  (when (and (= (:mode s) :time)
             (>= (:elapsed-ms s) (* (:limit s) 1000)))
    (finish-round!)))

(defn on-input-change!
  "Called on every keystroke in the typing input.
   `new-val` is the full current value of the <input>."
  [new-val]
  (let [s @state]
    (when (and (:started? s) (not (:finished? s)))
      (check-time-limit! s))
    (when (not (:finished? s))
      (let [target-word (nth (:words s) (:word-index s) nil)]
        (cond
          ;; ── no target word (shouldn't happen) ─────────────────────────
          (nil? target-word)
          nil

          ;; ── space pressed → attempt word submission ───────────────────
          (clojure.string/ends-with? new-val " ")
          (let [typed (clojure.string/trim new-val)]
            (if (= typed target-word)
              ;; correct word
              (let [next-idx  (inc (:word-index s))
                    new-hits  (inc (:hits s))
                    done?     (and (= (:mode s) :words)
                                   (>= new-hits (:limit s)))]
                (swap! state assoc
                       :word-index   next-idx
                       :hits         new-hits
                       :input        ""
                       :input-error? false)
                (when done? (finish-round!)))
              ;; wrong word
              (swap! state assoc
                     :errors       (inc (:errors s))
                     :input        (if (:backspace-req? s) new-val "")
                     :input-error? true)))

          ;; ── normal keypress → update input + validate prefix ──────────
          :else
          (let [error? (and (seq new-val)
                            (not (clojure.string/starts-with? target-word new-val)))]
            (when (and (not (:started? s)) (seq new-val))
              (swap! state assoc :started? true)
              (start-timer!))
            (swap! state assoc
                   :input        new-val
                   :input-error? error?
                   :errors       (if error?
                                   (inc (:errors s))
                                   (:errors s)))))))))

;; ---------------------------------------------------------------------------
;; Toolbar toggle helpers
;; ---------------------------------------------------------------------------

(defn toggle! [k]
  (swap! state update k not))

(defn set-level! [n]
  (swap! state assoc :level n)
  (init-round!))

(defn set-mode! [m]
  (swap! state assoc :mode m)
  (init-round!))

(defn set-limit! [v]
  (let [n (js/parseInt v 10)]
    (when (pos? n)
      (swap! state assoc :limit n)
      (init-round!))))

;; ---------------------------------------------------------------------------
;; Emulation-mode key handler
;; ---------------------------------------------------------------------------

(defn on-emulated-key-down!
  "Key-down handler active when :emulate-qwerty? is true.
  Intercepts every printable keystroke, translates it from its physical US
  QWERTY position to the Enthium v14 character at that position, and drives
  the trainer state directly — bypassing the browser's default text-entry so
  the <input> value always shows Enthium characters, not QWERTY characters.

  Special keys handled:
    Tab       → reset round
    Space     → submit current word (same semantics as normal mode)
    Backspace → remove last character from :input and re-validate"
  [e]
  (let [key (.-key e)
        s   @state]
    (when (not (:finished? s))
      (cond
        ;; Tab → reset round
        (= key "Tab")
        (do (.preventDefault e)
            (reset-round!))

        ;; Space → word submission (append trailing space and let on-input-change! decide)
        (= key " ")
        (do (.preventDefault e)
            (on-input-change! (str (:input s) " ")))

        ;; Backspace → remove last character, re-run validation
        (= key "Backspace")
        (do (.preventDefault e)
            (let [cur     (:input s)
                  new-val (if (pos? (count cur))
                            (subs cur 0 (dec (count cur)))
                            "")]
              (on-input-change! new-val)))

        ;; Printable single-character key → translate and append
        (= (count key) 1)
        (do (.preventDefault e)
            (let [translated (emulation/translate-key key (:allow-caps? s))
                  new-val    (str (:input s) translated)]
              (on-input-change! new-val)))))))

;; ---------------------------------------------------------------------------
;; Utility: format elapsed time as MM:SS
;; ---------------------------------------------------------------------------

(defn- fmt-time [ms]
  (let [total-s (js/Math.floor (/ ms 1000))
        m       (js/Math.floor (/ total-s 60))
        s       (mod total-s 60)]
    (str (when (< m 10) "0") m ":" (when (< s 10) "0") s)))

;; ---------------------------------------------------------------------------
;; Components
;; ---------------------------------------------------------------------------

(defn- level-btn [n current-level]
  [:button.trainer-level-btn
   {:class    (when (= n current-level) "trainer-level-btn--active")
    :on-click #(set-level! n)}
   (if (= n 8) "S" (str n))])

(defn- level-selector []
  (let [{:keys [level]} @state]
    [:div.trainer-levels
     (for [n (range 1 9)]
       ^{:key n} [level-btn n level])]))

(defn- toggle-btn [label active? on-click]
  [:button.trainer-toggle-btn
   {:class    (when active? "trainer-toggle-btn--active")
    :on-click on-click}
   label])

(defn- settings-row []
  (let [{:keys [mode limit show-cheat? sound-stroke? sound-error?
                allow-caps? allow-punct? backspace-req? emulate-qwerty?]} @state]
    [:div.trainer-settings
     ;; mode
     [toggle-btn "Words" (= mode :words) #(set-mode! :words)]
     [toggle-btn "Time"  (= mode :time)  #(set-mode! :time)]
     [:span.trainer-settings-sep]
     ;; limit input
     [:label.trainer-limit-label
      (if (= mode :time) "sec" "words")
      [:input.trainer-limit-input
       {:type      "number"
        :min       1
        :max       (if (= mode :time) 300 200)
        :value     limit
        :on-change #(set-limit! (.. % -target -value))}]]
     [:span.trainer-settings-sep]
     ;; feature toggles
     [toggle-btn "Caps"   allow-caps?      #(do (toggle! :allow-caps?) (init-round!))]
     [toggle-btn "Punct"  allow-punct?     #(do (toggle! :allow-punct?) (init-round!))]
     [toggle-btn "Bksp"   backspace-req?   #(toggle! :backspace-req?)]
     [toggle-btn "Cheat"  show-cheat?      #(toggle! :show-cheat?)]
     [:span.trainer-settings-sep]
     ;; keyboard emulation
     [toggle-btn "US KB"  emulate-qwerty?  #(toggle! :emulate-qwerty?)]]))

;; ── Prompt display ──────────────────────────────────────────────────────────

(defn- prompt-word [{:keys [text state]}]
  [:span {:class (str "trainer-word trainer-word--" (name state))} text " "])

(defn- prompt-display []
  (let [{:keys [words word-index]} @state]
    [:div.trainer-prompt
     (map-indexed
      (fn [idx w]
        (let [wstate (cond
                       (< idx word-index)  :done
                       (= idx word-index)  :current
                       :else               :pending)]
          ^{:key idx} [prompt-word {:text w :state wstate}]))
      words)]))

;; ── Input area ──────────────────────────────────────────────────────────────

(defn- input-area []
  (let [{:keys [input input-error? finished? words word-index emulate-qwerty?]} @state
        target (nth words word-index nil)]
    [:div.trainer-input-area
     [:input#trainer-input.trainer-input
      (merge
       {:type          "text"
        :auto-focus    true
        :auto-complete "off"
        :spell-check   false
        :value         input
        :disabled      finished?
        :class         (when input-error? "trainer-input--error")
        :placeholder   (when target (str "type \"" target "\"…"))}
       (if emulate-qwerty?
         ;; Emulation mode: intercept every keystroke, translate physical QWERTY
         ;; key positions to Enthium characters, drive state directly.
         ;; The no-op :on-change suppresses React's controlled-input warning.
         {:on-key-down on-emulated-key-down!
          :on-change   (fn [_])}
         ;; Normal mode: use the standard onChange path.
         {:on-change   #(on-input-change! (.. % -target -value))
          :on-key-down #(when (= (.-key %) "Tab")
                          (.preventDefault %)
                          (reset-round!))}))]
     [:div.trainer-input-hint
      (if emulate-qwerty?
        "US KB mode — type on QWERTY, trainer maps to Enthium positions \u00B7 Tab to reset"
        "Tab to reset")]]))

;; ── Stats bar ───────────────────────────────────────────────────────────────

(defn- stats-bar []
  (let [{:keys [hits errors elapsed-ms mode limit word-index started?] :as s} @state]
    [:div.trainer-stats
     [:span.trainer-stat
      [:span.trainer-stat-label "WPM"]
      [:span.trainer-stat-value (if started? (wpm s) "\u2013")]]
     [:span.trainer-stat
      [:span.trainer-stat-label "Acc"]
      [:span.trainer-stat-value (if started? (str (accuracy s) "%") "\u2013")]]
     [:span.trainer-stat
      [:span.trainer-stat-label (if (= mode :time) "Time" "Words")]
      [:span.trainer-stat-value
       (if (= mode :time)
         (str (fmt-time elapsed-ms) " / " (fmt-time (* limit 1000)))
         (str hits " / " limit))]]
     [:span.trainer-stat
      [:span.trainer-stat-label "Errors"]
      [:span.trainer-stat-value errors]]]))

;; ── Results overlay ─────────────────────────────────────────────────────────

(defn- results-overlay []
  (let [s @state]
    [:div.trainer-results
     [:div.trainer-results-card
      [:h2.trainer-results-title "Round complete!"]
      [:div.trainer-results-stats
       [:div.trainer-results-stat
        [:div.trainer-results-stat-value (wpm s)]
        [:div.trainer-results-stat-label "WPM"]]
       [:div.trainer-results-stat
        [:div.trainer-results-stat-value (str (accuracy s) "%")]
        [:div.trainer-results-stat-label "Accuracy"]]
       [:div.trainer-results-stat
        [:div.trainer-results-stat-value (:hits s)]
        [:div.trainer-results-stat-label "Words"]]
       [:div.trainer-results-stat
        [:div.trainer-results-stat-value (:errors s)]
        [:div.trainer-results-stat-label "Errors"]]
       [:div.trainer-results-stat
        [:div.trainer-results-stat-value (fmt-time (:elapsed-ms s))]
        [:div.trainer-results-stat-label "Time"]]]
      [:button.btn.btn--primary
       {:on-click #(reset-round!)}
       "Try again \u21BA"]
      [:p.trainer-results-hint "or press Tab while typing"]]]))

;; ── Cheatsheet ──────────────────────────────────────────────────────────────

(defn- cheatsheet []
  [:div.trainer-cheatsheet
   [:div.trainer-cheatsheet-inner
    [layout/keyboard-diagram]
    [:div {:style {:margin-top "0.75rem"}}
     [layout/finger-legend-component]]]])

;; ---------------------------------------------------------------------------
;; Root trainer component — initialize on first mount
;; ---------------------------------------------------------------------------

(defn trainer-page []
  (r/create-class
   {:display-name "trainer-page"

    :component-did-mount
    (fn [_] (init-round!))

    :reagent-render
    (fn []
      (let [{:keys [finished? show-cheat?]} @state]
        [:div.trainer
         [:div.trainer-header
          [:h1.trainer-title "Enthium " [:span "v14"] " Trainer"]
          [:p.trainer-subtitle "Learn the Enthium keyboard layout through progressive practice"]]

         [level-selector]
         [settings-row]

         [:div.trainer-main
          [prompt-display]
          [input-area]
          [stats-bar]

          (when finished?
            [results-overlay])]

         (when show-cheat?
           [cheatsheet])]))}))

(ns enthalpy.layout
  (:require [enthalpy.router :as router]))

;; ---------------------------------------------------------------------------
;; Key data — edit these vectors to change the layout display.
;; Each entry: [key-string finger-class lateral?]
;; finger-class: "lp" "lr" "lm" "li" "ri" "rm" "rr" "rp" "th"
;; lateral? true = dashed border (CapsLock-position or right-lateral key)
;; ---------------------------------------------------------------------------

(def keys-top
  [["q" "lp" false]
   ["y" "lr" false]
   ["o" "lm" false]
   ["u" "li" false]
   ["=" "li" false]
   ["x" "ri" false]
   ["l" "ri" false]
   ["d" "rm" false]
   ["p" "rr" false]
   ["z" "rp" false]])

(def keys-home
  [["b" "lp" true]
   ["c" "lp" false]
   ["i" "lr" false]
   ["a" "lm" false]
   ["e" "li" false]
   ["-" "li" false]
   ["k" "ri" false]
   ["h" "ri" false]
   ["t" "rm" false]
   ["n" "rr" false]
   ["s" "rp" false]
   ["w" "rp" true]])

(def keys-bot
  [["'" "lp" false]
   ["," "lr" false]
   ["." "lm" false]
   [";" "li" false]
   ["/" "li" false]
   ["j" "ri" false]
   ["m" "ri" false]
   ["g" "rm" false]
   ["f" "rr" false]
   ["v" "rp" false]])

(def key-thumb ["r" "th" false])

;; ---------------------------------------------------------------------------
;; Stat comparisons displayed on the layout page
;; ---------------------------------------------------------------------------

(def comparison-rows
  [{:layout "Enthium v14" :sfb "0.55%" :sfs "2.67%" :lsb "0.07%"
    :redirects "1.48%" :rolls "45.82%" :effort "724.5" :best? true}
   {:layout "Promethium"  :sfb "0.58%" :sfs "3.05%" :lsb "0.24%"
    :redirects "1.53%" :rolls "43.73%" :effort "732.1" :best? false}
   {:layout "Engrammer"   :sfb "0.99%" :sfs "3.47%" :lsb "0.41%"
    :redirects "2.26%" :rolls "42.17%" :effort "899.5" :best? false}
   {:layout "Colemak-DH"  :sfb "0.91%" :sfs "4.24%" :lsb "1.27%"
    :redirects "5.33%" :rolls "46.72%" :effort "1047.9" :best? false}
   {:layout "Dvorak"      :sfb "1.87%" :sfs "3.48%" :lsb "0.80%"
    :redirects "1.55%" :rolls "38.65%" :effort "1185.5" :best? false}
   {:layout "QWERTY"      :sfb "4.38%" :sfs "5.45%" :lsb "4.55%"
    :redirects "6.22%" :rolls "37.96%" :effort "2070.6" :best? false}])

;; ---------------------------------------------------------------------------
;; Finger legend data
;; ---------------------------------------------------------------------------

(def finger-legend
  [{:class "lp" :label "Left Pinky"}
   {:class "lr" :label "Left Ring"}
   {:class "lm" :label "Left Middle"}
   {:class "li" :label "Left Index"}
   {:class "ri" :label "Right Index"}
   {:class "rm" :label "Right Middle"}
   {:class "rr" :label "Right Ring"}
   {:class "rp" :label "Right Pinky"}
   {:class "th" :label "Thumb"}])

;; ---------------------------------------------------------------------------
;; Components
;; ---------------------------------------------------------------------------

(defn key-cell [[k finger lat?]]
  [:div {:key k
         :class (str "kb-key kb-" finger (when lat? " kb-lat"))}
   k])

(defn keyboard-diagram []
  [:div.keyboard
   ;; Top row — gap spacer on left and right to align with home row laterals
   [:div.kb-row
    [:div.kb-gap]
    (for [kdata keys-top]
      ^{:key (first kdata)} [key-cell kdata])
    [:div.kb-gap]]

   ;; Home row — 12 keys including laterals; visual spacer between halves
   [:div.kb-row
    (let [left  (take 6 keys-home)
          right (drop 6 keys-home)]
      [:<>
       (for [kdata left]
         ^{:key (first kdata)} [key-cell kdata])
       [:div.kb-spacer]
       (for [kdata right]
         ^{:key (first kdata)} [key-cell kdata])])]

   ;; Bottom row — same gap structure as top row
   [:div.kb-row
    [:div.kb-gap]
    (for [kdata keys-bot]
      ^{:key (first kdata)} [key-cell kdata])
    [:div.kb-gap]]

   ;; Thumb row
   [:div.kb-row
    [:div {:class (str "kb-key kb-" (second key-thumb) " kb-th")}
     (first key-thumb)]]])

(defn finger-legend-component []
  [:div.kb-legend
   (for [{:keys [class label]} finger-legend]
     ^{:key class}
     [:div.kb-legend__item
      [:div {:class (str "kb-legend__dot kb-legend__dot--" class)}]
      [:span label]])])

(defn comparison-table []
  [:div.comparison-table-wrap
   [:table.comparison-table
    [:thead
     [:tr
      [:th "Layout"]
      [:th "SFB \u2193"]
      [:th "SFS \u2193"]
      [:th "LSB \u2193"]
      [:th "Redirects \u2193"]
      [:th "Rolls \u2191"]
      [:th "Effort \u2193"]]]
    [:tbody
     (for [{:keys [layout sfb sfs lsb redirects rolls effort best?]} comparison-rows]
       ^{:key layout}
       [:tr {:class (when best? "row-enthium")}
        [:td layout]
        [:td {:class (when best? "td-best")} sfb]
        [:td {:class (when best? "td-best")} sfs]
        [:td {:class (when best? "td-best")} lsb]
        [:td {:class (when best? "td-best")} redirects]
        [:td rolls]
        [:td {:class (when best? "td-best")} effort]])]]])

;; ---------------------------------------------------------------------------
;; Full layout page
;; ---------------------------------------------------------------------------

(defn page []
  [:div.layout-page
   [:div.container
    [:div.layout-page__header
     [:h1.layout-page__title "Enthium v14 Layout Reference"]
     [:p.layout-page__lead
      "Interactive key diagram, finger assignments, and full performance comparison
       table. All data sourced from Cyanophage's Layout Analyzer and "
      [:a {:href "https://github.com/sunaku/enthium" :target "_blank"} "sunaku/enthium"] "."]]

    ;; Keyboard diagram
    [:section
     [:h2 {:style {:font-size "1.25rem" :margin-bottom "1rem"}} "Key Map"]
     [keyboard-diagram]
     [:div {:style {:margin-top "1.5rem"}}
      [finger-legend-component]]]

    ;; Layout string reference
    [:section {:style {:margin-top "3rem"}}
     [:h2 {:style {:font-size "1.25rem" :margin-bottom "1rem"}} "Text Representation"]
     [:div.key-box
      [:p
       [:code "  q y o u = x l d p z"] [:br]
       [:code "b c i a e - k h t n s w"] [:br]
       [:code "  ' , . ; / j m g f v"] [:br]
       [:code "            r"]]]
     [:p {:style {:margin-top "0.75rem" :font-size "0.875rem" :color "var(--fg-subtle)"}}
      "b = CapsLock (left lateral pinky) \u00B7 w = right lateral pinky \u00B7 r = thumb key"]]

    ;; Comparison table
    [:section {:style {:margin-top "3rem"}}
     [:h2 {:style {:font-size "1.25rem" :margin-bottom "0.5rem"}} "Performance Comparison"]
     [:p {:style {:font-size "0.875rem" :color "var(--fg-muted)" :margin-bottom "1.5rem"}}
      "Cyanophage Layout Analyzer results. \u2193 = lower is better. \u2191 = higher is better."]
     [comparison-table]]

    ;; SFB mitigation callout
    [:section {:style {:margin-top "3rem"}}
     [:h2 {:style {:font-size "1.25rem" :margin-bottom "1rem"}} "Effective SFB Rate"]
     [:div.highlight-box
      [:p "0.55% nominal SFBs \u2212 0.26% rakeable \u2212 0.07% slideable = "
       [:strong "0.22% effective SFBs"]]]
     [:p {:style {:margin-top "1rem" :font-size "0.875rem" :color "var(--fg-muted)"}}
      "Many SFBs in Enthium v14 are positioned on vertically adjacent rows so the
       finger can rake downward in one continuous motion, eliminating the timing
       penalty of a true double-tap. See the "
      [:a {:href "#/sections/stats"} "Statistics"] " section for the full table."]]

    ;; Install links
    [:section {:style {:margin-top "3rem"}}
     [:h2 {:style {:font-size "1.25rem" :margin-bottom "1rem"}} "Installation"]
     [:p {:style {:color "var(--fg-muted)" :font-size "0.875rem"}}
      "System-level layout files for Linux (XKB) and macOS (Karabiner-Elements)
       are available in the official repository:"]
     [:div {:style {:margin-top "1rem" :display "flex" :gap "0.75rem" :flex-wrap "wrap"}}
      [:a.btn.btn--primary
       {:href "https://github.com/sunaku/enthium" :target "_blank"}
       "GitHub \u2192 sunaku/enthium"]
      [:a.btn.btn--outline
       {:href "https://sunaku.github.io/enthium-keyboard-layout.html" :target "_blank"}
       "Design blog post"]]]]])

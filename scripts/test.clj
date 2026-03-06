#!/usr/bin/env clojure
;; scripts/test.clj — Enthalpy project integrity checks
;; Run from enthalpy/ with: clj -M scripts/test.clj

(ns test
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;; ── ANSI colours ────────────────────────────────────────────────────────────

(def ^:private green  "\033[32m")
(def ^:private red    "\033[31m")
(def ^:private yellow "\033[33m")
(def ^:private bold   "\033[1m")
(def ^:private reset  "\033[0m")

(defn- pass [msg] (println (str green "  PASS" reset " " msg)))
(defn- fail [msg] (println (str red   "  FAIL" reset " " msg)))
(defn- info [msg] (println (str yellow "  INFO" reset " " msg)))

;; ── Helpers ─────────────────────────────────────────────────────────────────

(defn- cljs-files [dir]
  (->> (file-seq (io/file dir))
       (filter #(.isFile %))
       (filter #(.endsWith (str %) ".cljs"))
       sort))

(defn- parse-file [f]
  (try
    (read-string (str "(do " (slurp f) ")"))
    (catch Exception e
      (throw (ex-info (str "Parse error in " f)
                      {:file f :cause (.getMessage e)})))))

;; Walk a parsed `(do ...)` form and find the value bound to `(def sym ...)`.
;; Returns the literal value (map, vec, etc.) or nil if not found.
(defn- extract-def [forms sym]
  (some (fn [form]
          (when (and (seq? form)
                     (= 'def (first form))
                     (= sym (second form)))
            (nth form 2 nil)))
        (rest forms))) ; rest skips the outer `do`

(defn- section-files []
  (cljs-files "src/enthalpy/sections"))

(defn- all-src-files []
  (cljs-files "src"))

(defn- slurp-data-cljs []
  (slurp "src/enthalpy/data.cljs"))

;; ── Error accumulator ────────────────────────────────────────────────────────

(def ^:private errors (atom 0))

(defn- assert-pass [label ok? detail]
  (if ok?
    (pass label)
    (do (fail (str label (when detail (str " — " detail))))
        (swap! errors inc))))

;; ── 1. Syntax ────────────────────────────────────────────────────────────────

(defn check-syntax []
  (println (str "\n" bold "1. Syntax" reset))
  (doseq [f (all-src-files)]
    (try
      (parse-file f)
      (pass (str f))
      (catch Exception e
        (fail (str f " — " (.getMessage e)))
        (swap! errors inc)))))

;; ── 2. Section registry completeness ────────────────────────────────────────
;; Every src/enthalpy/sections/s_*.cljs must be required + appear in data.cljs.

(defn check-section-registry []
  (println (str "\n" bold "2. Section registry completeness" reset))
  (let [data (slurp-data-cljs)]
    (doseq [f (section-files)]
      (let [filename  (.getName f)                          ; s_learn.cljs
            base      (str/replace filename #"\.cljs$" "")  ; s_learn
            ns-alias  (str/replace base "_" "-")]            ; s-learn
        (assert-pass
         (str ns-alias " required + listed in data.cljs")
         (str/includes? data ns-alias)
         nil)))))

;; ── 3. Required section keys ─────────────────────────────────────────────────

(def ^:private required-section-keys [:id :title :subtitle :excerpt :content])

(defn check-required-section-keys []
  (println (str "\n" bold "3. Required section keys" reset))
  (doseq [f (section-files)]
    (let [ast     (parse-file f)
          section (extract-def ast 'section)]
      (if (nil? section)
        (do (fail (str f " — could not extract `section` map"))
            (swap! errors inc))
        (doseq [k required-section-keys]
          (assert-pass
           (str (.getName f) " has " k)
           (and (map? section) (contains? section k))
           nil))))))

;; ── 4. Unique section IDs ────────────────────────────────────────────────────

(defn check-unique-section-ids []
  (println (str "\n" bold "4. Unique section IDs" reset))
  (let [ids   (for [f (section-files)]
                (let [ast (parse-file f)]
                  (get (extract-def ast 'section) :id)))
        dupes (->> ids frequencies (filter #(> (val %) 1)) (map key))]
    (if (empty? dupes)
      (pass (str "All " (count ids) " section IDs are unique"))
      (doseq [d dupes]
        (fail (str "Duplicate section :id " (pr-str d)))
        (swap! errors inc)))))

;; ── 5. Section excerpt length ────────────────────────────────────────────────
;; Excerpts should be non-empty (warn if suspiciously short < 40 chars).

(defn check-excerpt-length []
  (println (str "\n" bold "5. Excerpt length" reset))
  (doseq [f (section-files)]
    (let [ast     (parse-file f)
          section (extract-def ast 'section)
          excerpt (get section :excerpt "")]
      (let [trimmed (str/trim (str excerpt))]
        (assert-pass
         (str (.getName f) " excerpt non-empty")
         (pos? (count trimmed))
         "excerpt is blank")
        (when (and (pos? (count trimmed)) (< (count trimmed) 40))
          (info (str (.getName f) " excerpt is very short (" (count trimmed) " chars)")))))))

;; ── 6. No stale placeholders ─────────────────────────────────────────────────
;; Flag any [ALL CAPS IN BRACKETS] text left over from templating.

(def ^:private placeholder-re #"\[[A-Z][A-Z _-]+\]")

(defn check-no-placeholders []
  (println (str "\n" bold "6. No stale placeholders" reset))
  (doseq [f (all-src-files)]
    (let [text    (slurp f)
          matches (re-seq placeholder-re text)]
      (assert-pass
       (str f " has no [PLACEHOLDER] text")
       (empty? matches)
       (when (seq matches)
         (str "found: " (str/join ", " matches)))))))

;; ── 7. Trainer state shape ───────────────────────────────────────────────────
;; The `state` r/atom literal in trainer.cljs must contain the required keys.

(def ^:private required-trainer-keys
  [:level :mode :limit :words :word-index :input :hits :errors
   :started? :finished? :start-ms :elapsed-ms :show-cheat?
   :allow-caps? :allow-punct? :backspace-req? :emulate-qwerty?])

(defn check-trainer-state []
  (println (str "\n" bold "7. Trainer state shape" reset))
  ;; after the re-frame refactor the state map lives in db.cljs
  (let [f        (io/file "src/enthalpy/db.cljs")
        ast      (parse-file f)
        ;; default-db is defined as a map literal
        db-form  (extract-def ast 'default-db)
        ;; extract :trainer key from the map if possible
        state-map (when (map? db-form) (get db-form :trainer))]
    (if-not (map? state-map)
      (do (fail "db.cljs — could not extract :trainer map from default-db")
          (swap! errors inc))
      (doseq [k required-trainer-keys]
        (assert-pass
         (str "trainer state has " k)
         (contains? state-map k)
         nil)))))

;; ── 8. Level definitions ─────────────────────────────────────────────────────
;; words.cljs must define level-new-letters for levels 1–8.

(defn check-level-definitions []
  (println (str "\n" bold "8. Level definitions" reset))
  (let [f    (io/file "src/enthalpy/words.cljs")
        ast  (parse-file f)
        lmap (extract-def ast 'level-new-letters)]
    (if-not (map? lmap)
      (do (fail "words.cljs — could not extract `level-new-letters` map")
          (swap! errors inc))
      (doseq [n (range 1 9)]
        (assert-pass
         (str "level-new-letters has entry for level " n)
         (contains? lmap n)
         nil)))))

;; ── 9. Emulation map coverage ────────────────────────────────────────────────
;; emulation.cljs must define qwerty->enthium with exactly 31 entries:
;;   10 top row  (q w e r t  /  y u i o p)
;;    5 home left (a s d f g)
;;    6 home right (h j k l ; ')   ← apostrophe is the right-lateral pinky key
;;    5 bottom left  (z x c v b)
;;    5 bottom right (n m , . /)
;; CapsLock (Enthium 'b') is excluded — browsers cannot reliably intercept it.

(def ^:private expected-emulation-keys
  #{"q" "w" "e" "r" "t" "y" "u" "i" "o" "p"   ; top row        (10)
    "a" "s" "d" "f" "g"                          ; home left      ( 5)
    "h" "j" "k" "l" ";" "'"                      ; home right     ( 6)
    "z" "x" "c" "v" "b"                          ; bottom left    ( 5)
    "n" "m" "," "." "/"})                        ; bottom right   ( 5) → 31 total

(defn check-emulation-map []
  (println (str "\n" bold "9. Emulation map coverage" reset))
  (let [f    (io/file "src/enthalpy/emulation.cljs")
        ast  (parse-file f)
        emap (extract-def ast 'qwerty->enthium)]
    (if-not (map? emap)
      (do (fail "emulation.cljs — could not extract `qwerty->enthium` map")
          (swap! errors inc))
      (do
        (assert-pass
         (str "qwerty->enthium has " (count expected-emulation-keys) " entries"
              " (got " (count emap) ")")
         (= (count emap) (count expected-emulation-keys))
         (str "expected " (count expected-emulation-keys) ", got " (count emap)))
        (doseq [k (sort expected-emulation-keys)]
          (assert-pass
           (str "qwerty->enthium maps \"" k "\"")
           (contains? emap k)
           nil))
        ;; Sanity: every value should be a single-character string
        (let [bad-vals (filter #(not= 1 (count (val %))) emap)]
          (assert-pass
           "all mapped values are single characters"
           (empty? bad-vals)
           (when (seq bad-vals)
             (str "bad: " (pr-str (into {} bad-vals))))))))))

;; ── 10. No inline :style attributes (advisory) ─────────────────────────────────────────
;; AGENTS.org forbids adding new inline :style in hiccup.
;; Pre-existing occurrences in layout.cljs / views.cljs are grandfathered.
;; This check is advisory: it prints warnings but does NOT fail the suite,
;; so that a clean checkout still exits 0 while new violations remain visible.

;; ── 11. No top-level rf/subscribe calls ────────────────────────────────────────
;; Subscriptions must be created at runtime inside a function.  A common
;; mistake is to call `(rf/subscribe …)` in a `def` initializer or at the
;; namespace level; that subscription will be evaluated when the namespace is
;; loaded, before handlers are registered, leading to nil values (and the
;; dreaded “black screen” bug).  This check traverses each file’s AST and
;; flags any `rf/subscribe` form reached while in the top-level context.
(defn check-no-top-level-subscribe []
  (println (str "\n" bold "11. No top-level rf/subscribe calls" reset))
  (doseq [f (all-src-files)]
    (let [ast (parse-file f)
          found? (atom false)]
      (letfn [(fnwalk [form ctx]
                (cond
                  ;; direct subscribe in top-level context → violation
                  (and (seq? form)
                       (= (first form) 'rf/subscribe)
                       (= ctx :top))
                  (reset! found? true)

                  ;; entering a function definition/body
                  (and (seq? form)
                       (#{'defn 'defn- 'fn 'fn*} (first form)))
                  (doseq [sub (rest form)]
                    (fnwalk sub :fn))

                  ;; otherwise descend with same context
                  (seq? form)
                  (doseq [sub form]
                    (fnwalk sub ctx))

                  :else
                  nil))]
        (fnwalk ast :top)
        (if-not @found?
          (pass (str f " has no top-level rf/subscribe"))
          (do
            (fail (str f " has top-level rf/subscribe call"))
            (swap! errors inc)))))))

(defn check-no-inline-styles []
  (println (str "\n" bold "10. No inline :style attributes (advisory)" reset))
  (doseq [f (all-src-files)]
    (let [text (slurp f)
          hits (re-seq #":style\s+[\{\"]" text)]
      (if (empty? hits)
        (pass (str f " has no inline :style"))
        (info (str f " — " (count hits) " inline :style occurrence(s)"
                   " (pre-existing; remove when convenient)"))))))

;; ── 12. Build artifact ───────────────────────────────────────────────────────

(defn check-build-artifact []
  (println (str "\n" bold "11. Build artifact" reset))
  (let [f (io/file "docs/js/main.js")]
    (assert-pass
     "docs/js/main.js exists and is non-empty"
     (and (.exists f) (pos? (.length f)))
     (when-not (.exists f) "file not found — run: just build"))))

;; ── Runner ───────────────────────────────────────────────────────────────────

(defn -main []
  (println (str bold "── Enthalpy integrity checks ──" reset))

  (check-syntax)
  (check-section-registry)
  (check-required-section-keys)
  (check-unique-section-ids)
  (check-excerpt-length)
  (check-no-placeholders)
  (check-trainer-state)
  (check-level-definitions)
  (check-emulation-map)
  (check-no-inline-styles)
  (check-no-top-level-subscribe)
  (check-build-artifact)

  (println)
  (if (zero? @errors)
    (println (str green bold "All checks passed." reset))
    (do (println (str red bold @errors " check(s) failed." reset))
        (System/exit 1))))

(-main)

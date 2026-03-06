(ns enthalpy.emulation)

;; ---------------------------------------------------------------------------
;; US QWERTY → Enthium v14 physical-position map
;;
;; When a user types on a standard US QWERTY keyboard without any OS-level
;; remapping, pressing a physical key produces the QWERTY character.  This map
;; translates that QWERTY character to the Enthium v14 character that lives at
;; the *same physical key position*, so the trainer can accept raw QWERTY input
;; and validate it against Enthium prompts transparently.
;;
;; Enthium v14 layout (physical positions, left→right):
;;
;;   Top  row  :   q  y  o  u  =  |  x  l  d  p  z
;;   Home row  : b  c  i  a  e  -  |  k  h  t  n  s  w
;;   Bot  row  :   '  ,  .  ;  /  |  j  m  g  f  v
;;   Thumb     :                r
;;
;; Standard US QWERTY (same physical positions):
;;
;;   Top  row  :   q  w  e  r  t  |  y  u  i  o  p
;;   Home row  : [Cap]  a  s  d  f  g  |  h  j  k  l  ;  '
;;   Bot  row  :   z  x  c  v  b  |  n  m  ,  .  /
;;
;; Note: the CapsLock key maps to Enthium 'b' (left-lateral pinky key).
;; Web browsers intercept CapsLock at the OS level, so it is excluded from
;; the map.  The Space bar retains its word-submission function in both modes.
;; ---------------------------------------------------------------------------

(def qwerty->enthium
  {;; ── top row, left half ──────────────────────────────────────────────────
   "q" "q"
   "w" "y"
   "e" "o"
   "r" "u"
   "t" "="
   ;; ── top row, right half ─────────────────────────────────────────────────
   "y" "x"
   "u" "l"
   "i" "d"
   "o" "p"
   "p" "z"
   ;; ── home row, left half ─────────────────────────────────────────────────
   "a" "c"
   "s" "i"
   "d" "a"
   "f" "e"
   "g" "-"
   ;; ── home row, right half ────────────────────────────────────────────────
   "h" "k"
   "j" "h"
   "k" "t"
   "l" "n"
   ";" "s"
   "'" "w"
   ;; ── bottom row, left half ───────────────────────────────────────────────
   "z" "'"
   "x" ","
   "c" "."
   "v" ";"
   "b" "/"
   ;; ── bottom row, right half ──────────────────────────────────────────────
   "n" "j"
   "m" "m"
   "," "g"
   "." "f"
   "/" "v"})

;; Reverse map: Enthium char → physical QWERTY key that occupies that position.
;; Useful for rendering keyboard hints ("press X on your QWERTY keyboard").
(def enthium->qwerty
  (reduce-kv (fn [acc k v] (assoc acc v k)) {} qwerty->enthium))

;; ---------------------------------------------------------------------------
;; Translation helpers
;; ---------------------------------------------------------------------------

(defn translate-key
  "Translate a single character produced by a physical US QWERTY key into the
  Enthium v14 character at the same physical position.

  Arguments
    ch          – the string character the browser reported (e.g. \"a\", \"A\",
                  \";\").  Must have length 1.
    allow-caps? – when true and the user held Shift, the translated character
                  is also uppercased.

  Returns the translated character string, or `ch` unchanged when no mapping
  exists (e.g. digits, function keys, or characters already handled by the
  caller such as Space, Backspace, and Tab)."
  [ch allow-caps?]
  (let [lc     (clojure.string/lower-case ch)
        mapped (get qwerty->enthium lc)]
    (if mapped
      ;; Preserve Shift when caps are allowed and the original key was uppercase
      (if (and allow-caps? (not= ch lc))
        (clojure.string/upper-case mapped)
        mapped)
      ;; No mapping — pass the character through unchanged
      ch)))

(defn translate-string
  "Translate every character in `s` using `translate-key`.
  Useful for converting a whole QWERTY-typed string into its Enthium equivalent
  in one pass.  Characters not present in the map are passed through unchanged."
  [s allow-caps?]
  (apply str (map #(translate-key (str %) allow-caps?) s)))

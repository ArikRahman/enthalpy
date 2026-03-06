(ns enthalpy.sections.s-stats)

(def section
  {:id       "stats"
   :featured false
   :title    "Performance Statistics"
   :subtitle "Full Cyanophage analyzer data for Enthium v14"
   :excerpt  "Detailed bigram, trigram, and effort statistics from Cyanophage's Layout
              Analyzer, including the complete SFB mitigation table showing which
              same-finger bigrams can be raked or slid to reduce effective strain."
   :content
   [[:p "The following statistics were computed using Cyanophage's Layout Analyzer
        against a large English corpus. Where a metric has a directional preference,
        it is noted with \u2193 (lower is better) or \u2191 (higher is better)."]

    [:h2 "Effort Scores"]

    [:p "Effort scores are composite measures that weight each keystroke by its
        physical cost \u2014 finger travel distance, row penalties, and lateral stretch \u2014
        multiplied by the letter or bigram's frequency in English text."]

    [:ul
     [:li [:strong "Total Word Effort \u2193:"] " 724.5 \u2014 best among conventional layouts after Promethium (732.1)"]
     [:li [:strong "Base Effort \u2193:"] " 415.46 \u2014 raw positional strain ignoring bigram interactions"]]

    [:div.highlight-box
     [:p [:strong "Interpretation:"] " A Total Word Effort of 724.5 vs QWERTY's 2070.6
      means that typing the same text on Enthium v14 imposes roughly "
      [:strong "2.85\u00D7 less cumulative finger strain"] " than QWERTY. Against
      Colemak-DH (1047.9) the reduction is approximately 1.45\u00D7."]]

    [:h2 "Bigram Statistics"]

    [:p "Bigrams are two-key sequences. The following rates are measured as a
        percentage of all consecutive key pairs in a representative English corpus."]

    [:ul
     [:li [:strong "Same Finger Bigrams (SFBs) \u2193:"] " 0.55%"
      [:ul
       [:li "Promethium: 0.58%"]
       [:li "Colemak-DH: 0.91%"]
       [:li "Engrammer: 0.99%"]
       [:li "Dvorak: 1.87%"]
       [:li "QWERTY: 4.38%"]]]
     [:li [:strong "Skip Bigrams 2u \u2193:"] " 0.33%"
      [:ul
       [:li "Promethium: 0.36%"]
       [:li "Engrammer: 0.39%"]
       [:li "Dvorak: 0.45%"]
       [:li "Colemak-DH: 0.49%"]
       [:li "QWERTY: 1.43%"]]]
     [:li [:strong "Skip Bigrams SFS \u2193:"] " 2.67% \u2014 " [:strong "1st place, beats entire table"]]
     [:li [:strong "Lateral Stretch Bigrams (LSBs) \u2193:"] " 0.07% \u2014 " [:strong "1st place, beats entire table"]]
     [:li [:strong "Scissors \u2193:"] " 0.16% \u2014 sixth place"]]

    [:div.highlight-box
     [:p [:strong "First-place results:"] " Enthium v14 holds first place in both SFS
      (skip bigrams) and LSBs (lateral stretches) across the full Cyanophage
      conventional layout comparison table as of late 2025."]]

    [:h2 "Trigram Statistics"]

    [:p "Trigrams are three-key sequences. These statistics capture hand interaction
        patterns that bigrams cannot \u2014 specifically whether the hand alternates,
        rolls, or redirects."]

    [:ul
     [:li [:strong "Alternation \u2191:"] " 38.09%"
      [:ul
       [:li "Dvorak: 39.08% (slightly higher)"]
       [:li "Promethium: 36.98%"]
       [:li "Engrammer: 35.62%"]
       [:li "Colemak-DH: 25.43%"]
       [:li "QWERTY: 21.38%"]]]
     [:li [:strong "Alt SFS \u2193:"] " 4.46%"]
     [:li [:strong "Roll In \u2191:"] " 1.55%"]
     [:li [:strong "Roll Out \u2191:"] " 0.40%"]
     [:li [:strong "Bigram Roll In \u2191:"] " 26.99%"]
     [:li [:strong "Bigram Roll Out \u2191:"] " 16.88%"]
     [:li [:strong "Redirect \u2193:"] " 1.48% \u2014 second place after HandsDownNeu"
      [:ul
       [:li "Promethium: 1.53%"]
       [:li "Dvorak: 1.55%"]
       [:li "Engrammer: 2.26%"]
       [:li "Colemak-DH: 5.33%"]
       [:li "QWERTY: 6.22%"]]]
     [:li [:strong "Weak Redirect \u2193:"] " 0.79%"]
     [:li [:strong "Other \u2193:"] " 9.36%"]]

    [:h2 "Computed Finger Statistics"]

    [:ul
     [:li [:strong "Pinky Off \u2193:"] " 2.91% \u2014 beats Canary, Gallium, APTv3, Semimak, MTGAP, Dvorak, Recurva"
      [:ul
       [:li "Promethium: 4.08%"]
       [:li "Engrammer: 5.70%"]
       [:li "Dvorak: 4.13%"]
       [:li "Colemak-DH: 0.78% (low but overloads other fingers)"]
       [:li "QWERTY: 2.47%"]]]]

    [:h2 "SFB Mitigation \u2014 Rake and Slide Table"]

    [:p "Several of Enthium v14's same-finger bigrams can be executed with a single
        continuous finger motion rather than two discrete taps. "
     [:strong "Raking"] " means the finger slides down through adjacent rows in one
     stroke. " [:strong "Sliding"] " means the finger moves horizontally across
     adjacent keys. Both techniques allow the nominal SFB to be typed without the
     timing penalty of a true double-tap."]

    [:div.highlight-box
     [:p "0.55% SFBs \u2212 0.26% rakeable \u2212 0.07% slideable = "
      [:strong "0.22% effective SFBs"]]]

    [:p "Full SFB mitigation table:"]

    [:ul
     [:li [:code "ue"] " \u2014 0.08% \u2014 rake down ("
      [:code "u"] " top row \u2192 " [:code "e"] " home row, same index column)"]
     [:li [:code "oa"] " \u2014 0.05% \u2014 rake down ("
      [:code "o"] " top row \u2192 " [:code "a"] " home row, same middle column)"]
     [:li [:code "nf"] " \u2014 0.04% \u2014 rake down ("
      [:code "n"] " home row \u2192 " [:code "f"] " bottom row, same ring column)"]
     [:li [:code "ws"] " \u2014 0.03% \u2014 slide in (lateral \u2192 pinky home)"]
     [:li [:code "yi"] " \u2014 0.03% \u2014 rake down ("
      [:code "y"] " top row \u2192 " [:code "i"] " home row, same ring column)"]
     [:li [:code "e-"] " \u2014 0.02% \u2014 slide out (index home \u2192 index stretch)"]
     [:li [:code "sw"] " \u2014 0.02% \u2014 slide out (pinky home \u2192 lateral)"]
     [:li [:code "a."] " \u2014 0.02% \u2014 rake down ("
      [:code "a"] " home row \u2192 " [:code "."] " bottom row, same middle column)"]
     [:li [:code "lk"] " \u2014 0.02% \u2014 rake down ("
      [:code "l"] " top row \u2192 " [:code "k"] " home row, same index column)"]
     [:li [:code "e/"] " \u2014 0.01% \u2014 rake down (index home \u2192 index stretch bottom)"]
     [:li [:code "hm"] " \u2014 0.01% \u2014 rake down ("
      [:code "h"] " home row \u2192 " [:code "m"] " bottom row, same index column)"]]

    [:h2 "Total Rolls Breakdown"]

    [:p "Enthium v14's 45.82% total roll rate breaks down as:"]

    [:ul
     [:li [:strong "Inward bigram rolls:"] " 26.99% \u2014 fingers rolling toward the index"]
     [:li [:strong "Outward bigram rolls:"] " 16.88% \u2014 fingers rolling toward the pinky"]
     [:li [:strong "Inward trigram rolls:"] " 1.55%"]
     [:li [:strong "Outward trigram rolls:"] " 0.40%"]]

    [:p "The inward/outward ratio of roughly 1.6:1 matches the preference most typists
        report for inward rolls, which are generally perceived as more comfortable and
        faster than outward rolls due to the natural curling motion of the fingers."]

    [:h2 "Thumb Key Note"]

    [:p "The " [:code "r"] " thumb key is a feature of split and columnar ergonomic
        keyboards (such as the Glove80, Kyria, or Ferris). On standard row-staggered
        keyboards " [:code "r"] " occupies a conventional key position. The statistics
        above are computed with " [:code "r"] " as a thumb key; results on standard
        keyboards will vary slightly due to the different physical cost of reaching
        " [:code "r"] " with the index finger versus the thumb."]

    [:div.highlight-box
     [:p [:strong "Source:"] " All statistics sourced from "
      [:a {:href "https://github.com/sunaku/enthium" :target "_blank"} "sunaku/enthium"]
      " README and "
      [:a {:href "https://sunaku.github.io/enthium-keyboard-layout.html"
           :target "_blank"} "Sunaku's blog post"]
      ", computed with Cyanophage's Layout Analyzer against English text corpora."]]

    [:h2 "Ranking Summary"]

    [:p "Pascal Getreuer's widely-cited layout ranking table (as of 2025-12-28):"]

    [:ul
     [:li "SFBs: " [:strong "2nd place"] " (conventional layouts) \u2014 surpassed only by Focal"]
     [:li "SFSs: " [:strong "1st place"] " \u2014 beats the entire conventional table"]
     [:li "LSBs: " [:strong "1st place"] " \u2014 beats the entire conventional table"]
     [:li "Redirects: " [:strong "2nd place"] " \u2014 after HandsDownNeu"]
     [:li "Scissors: 6th place \u2014 after Dvorak, Anymak:END, APTv3, MTGAP, Colemak-DH"]
     [:li "Rolls: beats Semimak, Engram, HandsDownNeu, Anymak:END, Halmak, Dvorak"]
     [:li "Pinky Off: beats Canary, Gallium, APTv3, Semimak, MTGAP, Dvorak, Recurva"]]

    [:p "In thumb-key layout rankings, Enthium holds 2nd place in SFBs, SFS, and
        LSBs \u2014 placing it among the best-performing layouts of any category."]]})

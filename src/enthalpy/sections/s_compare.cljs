(ns enthalpy.sections.s-compare)

(def section
  {:id       "compare"
   :featured true
   :title    "Layout Comparisons"
   :subtitle "How Enthium v14 stacks up against QWERTY, Colemak-DH, Dvorak, Engrammer, and Promethium"
   :excerpt  "Across every major ergonomic metric \u2014 same-finger bigrams, lateral
              stretches, redirects, and rolls \u2014 Enthium v14 ranks first or second
              in the Cyanophage analyzer's conventional layout table. This section
              breaks down the numbers and what they mean in practice."
   :content
   [[:p "Raw layout analyzer statistics only tell part of the story. This section
        presents the Cyanophage comparison data alongside plain-language explanations
        of what each metric means and why Enthium\u2019s numbers translate to a
        genuinely more comfortable daily-use experience."]

    [:h2 "The Numbers at a Glance"]

    [:div.highlight-box
     [:p [:strong "Enthium v14 vs QWERTY:"] " 0.55% vs 4.38% SFBs \u2014 an 8\u00D7
      reduction in same-finger bigrams. 724.5 vs 2070.6 total word effort \u2014 nearly
      3\u00D7 less cumulative finger strain per word typed."]]

    [:h2 "Summary Metrics"]

    [:p "Total Word Effort is a composite score that weights every bigram and trigram
        by frequency and the physical cost of producing it. Lower is better."]

    [:ul
     [:li [:strong "Enthium v14:"] " 724.5 \u2014 best in the comparison set after Promethium"]
     [:li [:strong "Promethium:"] " 732.1"]
     [:li [:strong "Engrammer:"] " 899.5"]
     [:li [:strong "Colemak-DH:"] " 1047.9"]
     [:li [:strong "Dvorak:"] " 1185.5"]
     [:li [:strong "QWERTY:"] " 2070.6 \u2014 nearly 3\u00D7 worse than Enthium"]]

    [:h2 "Same-Finger Bigrams (SFBs)"]

    [:p "A same-finger bigram occurs when two consecutive letters are typed by the
        same finger. These are the primary source of typing errors and speed plateaus
        at high WPM because the finger must move to a new position while still
        completing its previous stroke."]

    [:ul
     [:li [:strong "Enthium v14:"] " 0.55% \u2014 second place overall, surpassed only by Focal"]
     [:li [:strong "Promethium:"] " 0.58%"]
     [:li [:strong "Engrammer:"] " 0.99%"]
     [:li [:strong "Colemak-DH:"] " 0.91%"]
     [:li [:strong "Dvorak:"] " 1.87%"]
     [:li [:strong "QWERTY:"] " 4.38% \u2014 nearly 8\u00D7 worse"]]

    [:div.highlight-box
     [:p [:strong "Effective SFB rate:"] " After accounting for bigrams that can be
      raked down vertically or slid horizontally, Enthium\u2019s effective SFB rate
      drops to " [:strong "0.22%"] " \u2014 roughly one-twentieth of QWERTY."]]

    [:h2 "Skip Bigrams \u2014 SFS (Same-Finger Skipgrams)"]

    [:p "SFS measures how often the same finger must be used for two keys separated
        by exactly one other keystroke \u2014 e.g. the first and third letter of a
        trigram. High SFS causes a \u201Cfloating\u201D feeling where one finger is
        constantly repositioning while the others type around it."]

    [:ul
     [:li [:strong "Enthium v14:"] " 2.67% \u2014 " [:strong "first place"] ", beats the entire Cyanophage table"]
     [:li [:strong "Promethium:"] " 3.05%"]
     [:li [:strong "Engrammer:"] " 3.47%"]
     [:li [:strong "Dvorak:"] " 3.48%"]
     [:li [:strong "Colemak-DH:"] " 4.24%"]
     [:li [:strong "QWERTY:"] " 5.45%"]]

    [:h2 "Lateral Stretch Bigrams (LSBs)"]

    [:p "LSBs are bigrams where two consecutive keystrokes require adjacent fingers
        to reach across the keyboard in opposite directions. They cause the most
        physical tension of any bigram type because multiple fingers are pulled apart
        simultaneously."]

    [:ul
     [:li [:strong "Enthium v14:"] " 0.07% \u2014 " [:strong "first place"] ", beats the entire Cyanophage table"]
     [:li [:strong "Promethium:"] " 0.24%"]
     [:li [:strong "Engrammer:"] " 0.41%"]
     [:li [:strong "Dvorak:"] " 0.80%"]
     [:li [:strong "Colemak-DH:"] " 1.27%"]
     [:li [:strong "QWERTY:"] " 4.55% \u2014 65\u00D7 worse than Enthium"]]

    [:div.highlight-box
     [:p [:strong "Why LSBs matter:"] " At 100+ WPM, lateral stretches are one of
      the leading causes of RSI symptoms. Enthium\u2019s 0.07% LSB rate means that
      out of every 1000 bigrams you type, fewer than 1 requires a lateral stretch."]]

    [:h2 "Rolls"]

    [:p "A roll is a sequence of consecutive keystrokes that travel in one direction
        across the hand \u2014 either inward (toward the index finger) or outward
        (toward the pinky). Rolls feel fluid and fast. High roll rates are one of
        the most important contributors to the subjective \u201Cflow\u201D feeling of
        a layout."]

    [:ul
     [:li [:strong "Enthium v14:"] " 45.82% total rolls"]
     [:li [:strong "Promethium:"] " 43.73%"]
     [:li [:strong "Colemak-DH:"] " 46.72% (higher, but at the cost of more redirects)"]
     [:li [:strong "Engrammer:"] " 42.17%"]
     [:li [:strong "Dvorak:"] " 38.65%"]
     [:li [:strong "QWERTY:"] " 37.96%"]]

    [:p "Enthium\u2019s rolls split 26.99% inward and 16.88% outward for bigrams,
        favouring the generally more comfortable inward direction while maintaining
        a healthy outward component for variety."]

    [:h2 "Redirects"]

    [:p "A redirect is a trigram where the direction of travel reverses mid-sequence \u2014
        e.g. index \u2192 middle \u2192 index on the same hand. Redirects interrupt
        flow and are associated with hesitation at speed. Very low redirect rates
        are characteristic of highly alternating or highly rolling layouts."]

    [:ul
     [:li [:strong "Enthium v14:"] " 1.48% \u2014 second place after HandsDownNeu"]
     [:li [:strong "Promethium:"] " 1.53%"]
     [:li [:strong "Dvorak:"] " 1.55%"]
     [:li [:strong "Engrammer:"] " 2.26%"]
     [:li [:strong "Colemak-DH:"] " 5.33%"]
     [:li [:strong "QWERTY:"] " 6.22%"]]

    [:div.highlight-box
     [:p [:strong "The Colemak redirect problem:"] " Colemak-DH\u2019s 5.33% redirect
      rate is one of the most common criticisms of that layout from long-term users.
      Enthium\u2019s 1.48% \u2014 a 72% reduction \u2014 is the most practically
      meaningful difference for typists who have already moved past QWERTY."]]

    [:h2 "Pinky Off (Pinky Load Beyond Home Column)"]

    [:p "Pinky Off measures what fraction of keystrokes require the pinky finger to
        leave its home column entirely. This is distinct from lateral key usage
        (which is a short horizontal movement) and refers to diagonal reaches to
        upper and lower rows."]

    [:ul
     [:li [:strong "Enthium v14:"] " 2.91% \u2014 beats Canary, Gallium, APTv3, Semimak, MTGAP, Dvorak"]
     [:li [:strong "Promethium:"] " 4.08%"]
     [:li [:strong "Engrammer:"] " 5.70%"]
     [:li [:strong "Dvorak:"] " 4.13%"]
     [:li [:strong "QWERTY:"] " 2.47% (low, but achieved by overloading other fingers)"]]

    [:h2 "Alternation Rate"]

    [:p "Alternation measures what fraction of trigrams alternate hands for every
        keystroke. Pure alternation (like Dvorak aims for) reduces same-hand
        fatigue but can feel mechanical. Enthium balances alternation with rolling:"]

    [:ul
     [:li [:strong "Enthium v14:"] " 38.09% alternation \u2014 high, driven by vowel/consonant separation"]
     [:li [:strong "Dvorak:"] " 39.08% (slightly higher alternation but lower rolls)"]
     [:li [:strong "Promethium:"] " 36.98%"]
     [:li [:strong "Engrammer:"] " 35.62%"]
     [:li [:strong "Colemak-DH:"] " 25.43%"]
     [:li [:strong "QWERTY:"] " 21.38%"]]

    [:h2 "Scissors"]

    [:p "A scissors bigram occurs when two adjacent fingers move in opposite vertical
        directions simultaneously \u2014 one reaching up while the other reaches down.
        This is one of the most physically uncomfortable motions in typing."]

    [:ul
     [:li [:strong "Enthium v14:"] " 0.16% \u2014 sixth place (after Dvorak, Anymak:END, APTv3, MTGAP, Colemak-DH)"]
     [:li [:strong "Promethium:"] " 0.11% (lower)"]
     [:li [:strong "Colemak-DH:"] " 0.15%"]
     [:li [:strong "Dvorak:"] " 0.08%"]
     [:li [:strong "QWERTY:"] " 1.46%"]]

    [:p "Scissors is the one metric where Enthium does not rank in the top tier.
        The 0.16% figure is a deliberate tradeoff \u2014 the specific key placements
        that would reduce scissors further would increase SFBs or redirects by a
        larger margin. At 0.16%, scissors bigrams are rare enough that most typists
        never consciously notice them."]

    [:h2 "Overall Assessment"]

    [:p "No layout wins every metric simultaneously \u2014 the optimizer\u2019s
        dilemma is that improving one dimension often costs another. Enthium v14\u2019s
        achievement is that it wins or places second in the metrics that matter most
        for sustained ergonomic use \u2014 SFBs, SFS, LSBs, and redirects \u2014
        while maintaining competitive rolls and alternation."]

    [:div.highlight-box
     [:p [:strong "The practical summary:"] " If you type English prose and/or
      programming code for hours each day and your goal is to minimise repetitive
      strain while maximising typing speed, Enthium v14 is currently the most
      well-rounded conventional keyboard layout backed by public analyzer data."]]

    [:h2 "Comparison with Thumb-Key Layouts"]

    [:p "When compared against layouts that use the thumb for a letter key (a
        technique used in split ergonomic boards), Enthium still ranks first or
        second in SFBs, SFS, and LSBs \u2014 placing it among the best performing
        layouts of any category, not just conventional ones."]

    [:ul
     [:li "SFBs: 0.55% \u2014 second after Night"]
     [:li "SFS: 2.67% \u2014 second after Night"]
     [:li "LSBs: 0.07% \u2014 second after Caster"]
     [:li "Redirects: 1.48% \u2014 third after Nordrassil, Vibranium"]]]})

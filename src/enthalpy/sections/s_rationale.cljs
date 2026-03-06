(ns enthalpy.sections.s-rationale)

(def section
  {:id       "rationale"
   :featured true
   :title    "Design Rationale"
   :subtitle "Why every key in Enthium v14 sits where it does"
   :excerpt  "Enthium v14 is not an arbitrary rearrangement \u2014 every placement
              decision is documented and justified by frequency data, ergonomic
              analysis, and real-world usage feedback across many months of iteration
              by Sunaku and the alternative layout community."
   :content
   [[:p "Enthium v14 is the fourteenth iteration of a layout that began as a horizontal
        mirror of Hands Down Promethium with apostrophe moved off the vowel hand. Each
        subsequent version has been driven by measurable ergonomic data from Cyanophage,
        Oxey, and AKL analyzers, combined with subjective reports from daily real-world
        use. This page documents the reasoning behind the final design."]

    [:h2 "The Layout at a Glance"]

    [:div.key-box
     [:p "Top:  "
      [:code "q"] " " [:code "y"] " " [:code "o"] " " [:code "u"] " " [:code "="]
      " \u00B7 "
      [:code "x"] " " [:code "l"] " " [:code "d"] " " [:code "p"] " " [:code "z"]]]

    [:div.key-box
     [:p "Home: "
      [:code "b"] " " [:code "c"] " " [:code "i"] " " [:code "a"] " " [:code "e"] " " [:code "-"]
      " \u00B7 "
      [:code "k"] " " [:code "h"] " " [:code "t"] " " [:code "n"] " " [:code "s"] " " [:code "w"]]]

    [:div.key-box
     [:p "Bot:  "
      [:code "'"] " " [:code ","] " " [:code "."] " " [:code ";"] " " [:code "/"]
      " \u00B7 "
      [:code "j"] " " [:code "m"] " " [:code "g"] " " [:code "f"] " " [:code "v"]
      " \u00B7 thumb: " [:code "r"]]]

    [:p "The left hand carries the complete vowel cluster \u2014 "
     [:code "y o u i a e"] " \u2014 across the top and home rows. The right hand
     carries the high-frequency consonant cluster " [:code "h t n s"] " on the home
     row, mirroring the Dvorak right-hand arrangement. This separation of vowels and
     consonants is the single most important source of Enthium\u2019s low redirect
     and high alternation scores."]

    [:h2 "Pinky Load Stratification"]

    [:p "Short or weakened pinky fingers are one of the most common ergonomic complaints
        among typists who use conventional layouts. Enthium addresses this with a
        deliberate frequency gradient on the pinky columns:"]

    [:ul
     [:li [:strong "Upper row (lightest load):"] " "
      [:code "q"] " (0.10%) left and " [:code "z"] " (0.07%) right. These are the
      rarest letters in English and are only reached by the pinky when already
      extended upward."]
     [:li [:strong "Home row lateral (medium load):"] " "
      [:code "b"] " (1.44%) left and " [:code "w"] " (1.77%) right. Both are close
      to the resting home position on the lateral pinky keys (CapsLock and the
      traditional apostrophe key). The lateral reach is short and horizontal."]
     [:li [:strong "Lower row (light-medium load):"] " "
      [:code "'"] " (0.47%) left and " [:code "v"] " (1.09%) right. Apostrophe is
      placed here specifically to keep it off the vowel fingers (see below)."]]

    [:div.highlight-box
     [:p [:strong "Ergonomic principle:"] " By reserving the most demanding pinky
      positions (upper row) for the least frequent letters, Enthium ensures that
      pinky fatigue scales inversely with letter frequency \u2014 you reach the
      farthest only for the rarest keys."]]

    [:h2 "Apostrophe Independence from Vowels"]

    [:p "In many layouts, apostrophe shares a finger with one or more vowels. This
        creates same-finger bigrams in extremely common English contractions:
        " [:em "you'd"] ", " [:em "I'd"] ", " [:em "he'd"] ", " [:em "they'd"] ",
        " [:em "who's"] ", " [:em "via's"] ". Because contractions are among the most
        frequent multi-character sequences in natural English text, these SFBs compound
        quickly at speed."]

    [:p "Enthium places " [:code "'"] " on the left pinky lower row \u2014 a finger
        that touches no vowel in the layout. The result: every common English
        contraction is typed with full hand alternation or a cross-finger roll, never
        a same-finger sequence."]

    [:div.highlight-box
     [:p [:strong "Measured impact:"] " Moving apostrophe off the vowel hand reduced
      Cyanophage\u2019s SFB measurement from 0.58% (Promethium) to 0.55% (Enthium),
      which was the original motivation for the entire Enthium project."]]

    [:h2 "Vertical SFB Mitigation: Raking"]

    [:p "Several unavoidable same-finger bigrams in Enthium are positioned so that the
        two keys occupy " [:strong "vertically adjacent rows on the same column"] ".
        This allows the finger to execute both keystrokes in a single downward raking
        motion rather than two discrete taps, dramatically reducing the perceived cost."]

    [:ul
     [:li [:code "ue"] " (0.08%) \u2014 rake down: "
      [:code "u"] " top row, " [:code "e"] " home row, same index column."]
     [:li [:code "oa"] " (0.05%) \u2014 rake down: "
      [:code "o"] " top row, " [:code "a"] " home row, same middle column."]
     [:li [:code "yi"] " (0.03%) \u2014 rake down: "
      [:code "y"] " top row, " [:code "i"] " home row, same ring column."]
     [:li [:code "a."] " (0.02%) \u2014 rake down: "
      [:code "a"] " home row, " [:code "."] " bottom row, same middle column."]]

    [:p "After subtracting rakeable and slideable SFBs, the "
     [:strong "effective SFB rate is 0.22%"] " \u2014 less than half the nominal
     0.55% figure that already ranks second in Pascal Getreuer\u2019s conventional
     layout table."]

    [:h2 "Vim and Programmer Coherence"]

    [:p "Enthium v14 is designed by and for a Vim and terminal user. Several placement
        decisions are explicitly motivated by editor and shell ergonomics:"]

    [:ul
     [:li [:code "h"] " " [:code "j"] " " [:code "k"] " " [:code "l"] " \u2014 the
      Vim arrow cluster. " [:code "h"] " and " [:code "l"] " are on the right home
      row and top row respectively, both reachable from the index finger without
      repositioning. " [:code "j"] " and " [:code "k"] " sit directly below on the
      right bottom row, making the full HJKL diamond a compact right-hand shape."]
     [:li [:code ","] " and " [:code ";"] " \u2014 Vim\u2019s character-jump repeat
      and reverse-repeat keys \u2014 are on adjacent fingers of the left bottom row,
      easy to twiddle."]
     [:li [:code "b"] " and " [:code "w"] " are diametrically opposite on the keyboard
      (left lateral home and right lateral home), matching Vim\u2019s word-backward and
      word-forward motions with a satisfying symmetry."]
     [:li [:code "-"] " and " [:code "="] " cluster adjacent on the index stretch
      columns for Ctrl-zoom shortcuts and "
      [:code "->"] " / " [:code "=>"] " arrow operators in code."]
     [:li [:code "."] " and " [:code "/"] " are adjacent for relative filesystem paths:
      " [:code "./"] " and " [:code "../"] " both roll inward from right to left."]]

    [:div.highlight-box
     [:p [:strong "Ctrl shortcuts:"] " Ctrl-B/I/U (bold, italic, underline) are all
      on the left hand. Ctrl-P/N (previous/next in many tools) are directionally
      coherent: " [:code "p"] " is above " [:code "n"] " on the right ring column,
      matching up/down semantics."]]

    [:h2 "The Vowel Cluster: YOU on the Left Hand"]

    [:p "Enthium inherits the vowel arrangement from Arno\u2019s Engram 2.0: all five
        primary vowels are on the left hand, with " [:code "y"] " (which functions as
        a vowel in many common words) also on the left. This mirrors the Dvorak
        philosophy of vowel/consonant separation and is the foundation of Enthium\u2019s
        38.09% alternation rate."]

    [:p "The specific arrangement " [:code "y o u"] " on the top row and "
     [:code "i a e"] " on the home row was refined over many versions. Key decisions:"]

    [:ul
     [:li [:code "e"] " is on the left index home position because it is the most
      frequent letter in English (12.7%) and the index finger is the strongest."]
     [:li [:code "a"] " is on the left middle home position (8.2% frequency) for
      the same reason \u2014 strong finger, home row, no reach required."]
     [:li [:code "i"] " sits to the left of " [:code "a"] " on the ring finger; the "
      [:code "ia"] " and " [:code "ai"] " bigrams are comfortable inward rolls."]
     [:li [:code "o"] " and " [:code "u"] " are on the top row, positioned to minimise "
      [:code "oa"] " and " [:code "ue"] " SFBs while keeping "
      [:code "you"] " as a comfortable roll."]]

    [:h2 "Right Hand: Dvorak Consonant Continuity"]

    [:p "The right home row " [:code "h t n s"] " is identical to Dvorak\u2019s right
        home row. This is not accidental: Dvorak\u2019s arrangement was itself optimised
        for English consonant frequency and roll patterns, and those properties carry
        over directly to Enthium. Users switching from Dvorak will find the right hand
        immediately familiar."]

    [:h2 "B and W on Lateral Pinky Keys"]

    [:p [:code "b"] " (1.44%) and " [:code "w"] " (1.77%) are common enough that
        placing them on the upper row would create noticeable pinky-up reach overhead.
        The lateral CapsLock and traditional-apostrophe key positions are close to home
        row height, making these reaches short and horizontal rather than diagonal."]

    [:p "This placement reduces the Cyanophage " [:em "Pinky Off"] " score to 2.91%
        \u2014 meaning only 2.91% of keystrokes require the pinky to leave its home
        column entirely."]

    [:h2 "V14 vs V13: The ; / Swap"]

    [:div.highlight-box
     [:p [:strong "The only change between v13 and v14"] " is a swap of "
      [:code ";"] " and " [:code "/"] " on the bottom row. In v14, "
      [:code ";"] " is adjacent to " [:code ","] " for easier Vim f/F/t/T twiddles,
      and " [:code ":/"] " flows left-to-right when typing URLs. Cyanophage Effort
      fell by 0.96 points as a result."]]

    [:p "The design philosophy throughout has been that a layout optimised only for
        analyzer numbers will not necessarily feel good in daily use. Every version of
        Enthium was tested with hours of real typing before being released."]]})

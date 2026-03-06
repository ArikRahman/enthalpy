(ns enthalpy.sections.s-learn)

(def section
  {:id       "learn"
   :featured true
   :title    "Learning Enthium v14"
   :subtitle "A structured onramp for switching from QWERTY or Colemak"
   :excerpt  "Enthium v14 can be unlocked in KeyBr in roughly 6 hours of focused
              practice spread over a weekend. This guide covers the optimal training
              sequence, what to expect during each phase, and how to handle the
              trickiest key transitions."
   :content
   [[:p "Switching keyboard layouts is a deliberate act of short-term sacrifice for
         long-term ergonomic gain. Enthium v14 is designed to minimise that sacrifice:
         its derivation from Engrammer and Dvorak means many key positions carry over,
         and its home-row density means your fingers travel less from the very first
         session."]

    [:h2 "Before You Start"]

    [:p "Install the layout first \u2014 see the "
     [:a {:href "https://github.com/sunaku/enthium" :target "_blank"} "sunaku/enthium"]
     " repository for Linux and macOS instructions \u2014 then open "
     [:a {:href "https://keybr.com" :target "_blank"} "KeyBr"]
     " and create a fresh profile. KeyBr introduces one letter at a time, which is the
     correct way to build muscle memory without overwhelming your working memory."]

    [:div.highlight-box
     [:p [:strong "Key insight:"] " Sunaku unlocked all letters in approximately "
      [:strong "6 hours of practice"] " over a few days on a Glove80. On a standard
      row-staggered keyboard expect 8\u201312 hours \u2014 still a remarkably short
      transition compared with layouts that share fewer anchors with prior experience."]]

    [:h2 "Phase 1 \u2014 Home Row First (hours 0\u20132)"]

    [:p "KeyBr will begin with the highest-frequency letters and expand outward.
         In Enthium v14 the home row is:"]

    [:div.key-box
     [:p "Left hand: "
      [:code "c"] " " [:code "i"] " " [:code "a"] " " [:code "e"]
      " \u00B7 Right hand: "
      [:code "h"] " " [:code "t"] " " [:code "n"] " " [:code "s"]
      " \u00B7 Thumb: " [:code "r"]]]

    [:p "These ten positions cover the highest-frequency letters in English. Expect to
         spend the first hour building confidence here. " [:code "e"] ", " [:code "t"] ",
         " [:code "a"] ", and " [:code "n"] " will feel natural quickly because they
         occupy strong fingers \u2014 index and middle."]

    [:p "The most common early confusion is " [:code "n"] " vs " [:code "s"] ": both
         live on the right pinky and ring columns respectively. Drilling "
     [:strong "ns"] " and " [:strong "sn"] " bigrams deliberately for five minutes
         accelerates disambiguation."]

    [:h2 "Phase 2 \u2014 Expanding the Alphabet (hours 2\u20135)"]

    [:p "KeyBr will unlock the remaining letters roughly in order of frequency. The
         transitions that take the longest are:"]

    [:ul
     [:li [:code "w"] " \u2014 lateral right pinky key (traditional apostrophe/quote key position).
           Expect this to take the most time; it appears in common words and
           requires an intentional reach outward."]
     [:li [:code "q"] " \u2014 upper-left corner, typed by the left pinky. "
      [:code "qu"] " is always a left-to-right inward roll, which becomes fluent quickly."]
     [:li [:code "b"] " \u2014 lateral left pinky (traditional CapsLock position). High
           frequency (1.44%) means you will notice it early. The lateral reach becomes
           automatic within a few hundred repetitions."]]

    [:div.highlight-box
     [:p [:strong "Training tip:"] " When KeyBr introduces " [:code "w"] ", treat that
      session as a " [:code "w"] " vs " [:code "s"] " differentiation drill. The muscle
      memory for \u201Cpinky stays home vs pinky reaches out\u201D is the key distinction."]]

    [:h2 "Phase 3 \u2014 Punctuation and Symbols"]

    [:p "Enthium v14 places punctuation with deliberate logic:"]

    [:ul
     [:li [:code "'"] " sits below " [:code "c"] " on the left pinky \u2014 it never
           shares a finger with any vowel, preventing same-finger bigrams in contractions
           like " [:em "you\u2019d"] ", " [:em "they\u2019d"] ", " [:em "I\u2019d"] "."]
     [:li [:code ","] " " [:code "."] " " [:code ";"] " " [:code "/"] " follow the
           same left-to-right sequence as QWERTY, reducing adaptation overhead for
           navigation shortcuts."]
     [:li [:code "-"] " and " [:code "="] " are clustered on adjacent columns for
           intuitive Ctrl-minus and Ctrl-equals (zoom) shortcuts."]]

    [:div.key-box
     [:p "Bottom row sequence: "
      [:code "'"] " " [:code ","] " " [:code "."] " " [:code ";"] " " [:code "/"]
      " (left) \u00B7 "
      [:code "j"] " " [:code "m"] " " [:code "g"] " " [:code "f"] " " [:code "v"]
      " (right)"]]

    [:h2 "Staying Motivated During the Plateau"]

    [:p "Between hours 4 and 8 you will hit a speed plateau where Enthium feels slower
         than your old layout. This is normal and temporary. The plateau exists because
         your fingers know the positions but haven\u2019t yet built the bigram-level
         muscle memory that makes typing feel automatic."]

    [:p "Strategies that help:"]

    [:ol
     [:li "Type real text, not just exercises. TypeLit and TypeRacer both work well."]
     [:li "Slow down to zero errors rather than typing fast with corrections."]
     [:li "Take breaks of at least 8 hours between sessions \u2014 consolidation
           happens during rest."]
     [:li "Keep your old layout available. Switching back occasionally for urgent work
           is fine; it does not reset progress."]]

    [:h2 "Realistic Timeline"]

    [:p "Based on Sunaku\u2019s own training log and community reports:"]

    [:ul
     [:li [:strong "Day 1 (2\u20133 hrs):"] " Unlock home row, reach ~20\u201330 WPM on
           trained keys."]
     [:li [:strong "Day 2 (2\u20133 hrs):"] " Expand to most common letters, reach
           ~25\u201340 WPM."]
     [:li [:strong "Day 3 (1\u20132 hrs):"] " Unlock all letters; expect ~35\u201355 WPM
           on KeyBr."]
     [:li [:strong "Week 2:"] " Real-world typing feels viable for daily use."]
     [:li [:strong "Month 1:"] " Approach or exceed previous QWERTY speed for most
           users."]]

    [:div.highlight-box
     [:p [:strong "From Sunaku\u2019s notes:"] " \u201CThe differences between
      Engram/mer and Enthium are so minimal that one might switch to it completely
      with about 6 hours of practice, spread over 2\u20133 days.\u201D For Colemak
      users the shared hand structure makes the transition even shorter."]]

    [:h2 "Coming from Colemak or Colemak-DH"]

    [:p "The right hand is the biggest change: Enthium places "
     [:code "h t n s"] " on the right home row where Colemak has "
     [:code "h n e i"] ". The vowel " [:code "e"] " moves to the left index, joining
         the rest of the vowel cluster. Most Colemak users report that the right-hand
         relearning takes 3\u20134 days of real-world use to fully internalise."]

    [:h2 "Coming from QWERTY"]

    [:p "Almost every key moves. Rather than trying to remember positions individually,
         trust the KeyBr process to build each finger\u2019s memory independently.
         The layout\u2019s high roll rate (45.82%) means that common English words develop
         a flowing, left-to-right feel quickly \u2014 which your fingers will recognise
         as distinctly better than QWERTY\u2019s erratic pattern very early in training."]]})

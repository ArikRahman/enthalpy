(ns enthalpy.sections.s-faq)

(def section
  {:id       "faq"
   :featured false
   :title    "Frequently Asked Questions"
   :subtitle "Common questions about Enthium v14, switching layouts, and ergonomic typing"
   :excerpt  "Answers to the most common questions about Enthium v14: how hard is it
              to learn, is it worth switching from Colemak, which keyboards does it
              work on, and what happens to your QWERTY speed during the transition."
   :content
   [[:p "These questions are drawn from discussions in the AKL Discord, Reddit's
        r/KeyboardLayouts, and direct feedback to Sunaku's blog post. If your question
        isn't answered here, the "
     [:a {:href "https://github.com/sunaku/enthium" :target "_blank"} "sunaku/enthium"]
     " issue tracker is the best place to ask."]

    [:h2 "Is Enthium v14 hard to learn?"]

    [:p "Harder than staying on QWERTY, easier than most people expect. The two factors
        that make Enthium faster to learn than other alternative layouts are:"]

    [:ul
     [:li "Its home row covers the highest-frequency letters in English, so early
           KeyBr sessions feel productive almost immediately."]
     [:li "Its ancestry in Engrammer and Dvorak means many key positions carry over
           for typists coming from those layouts, reducing the number of positions
           that need to be relearned from scratch."]]

    [:div.highlight-box
     [:p [:strong "Sunaku's measured result:"] " All letters unlocked in KeyBr in
      approximately 6 hours of practice on a Glove80 split keyboard. For standard
      row-staggered keyboards, budget 8\u201312 hours across 3\u20135 days."]]

    [:h2 "Will I lose my QWERTY speed while learning?"]

    [:p "Yes, temporarily. This is unavoidable with any layout switch and is not a
        reason to avoid switching. The mechanism is well understood: your existing
        QWERTY muscle memory does not disappear \u2014 it simply gets \u201Cparked\u201D
        in long-term memory while you build a new set of motor programs for Enthium."]

    [:p "Most people report that QWERTY speed returns to within 10\u201320% of its
        previous level within 2\u20133 weeks of using Enthium as their primary layout,
        and that it fully recovers or improves by the end of the first month. The
        practical strategy is:"]

    [:ol
     [:li "Keep your old layout available via a keyboard shortcut for urgent use."]
     [:li "Switch to Enthium full-time as soon as you can type basic text, even slowly."]
     [:li "Accept 2\u20134 weeks of reduced throughput as the cost of entry."]]

    [:h2 "Is Enthium worth switching to if I already use Colemak-DH?"]

    [:p "This is the most nuanced question. Colemak-DH is a genuinely good layout and
        the case for switching depends on what bothers you in your current daily use:"]

    [:ul
     [:li [:strong "If redirects bother you:"] " Yes. Colemak-DH's 5.33% redirect rate
           vs Enthium's 1.48% is the most practically significant difference. Redirects
           cause hesitation and errors at high speeds in a way that most typists
           eventually notice."]
     [:li [:strong "If SFBs bother you:"] " Marginally yes. Enthium's 0.55% vs
           Colemak-DH's 0.91% is a real improvement, though both are good."]
     [:li [:strong "If you are happy:"] " No. If your current layout feels comfortable
           and fast, the productivity cost of switching is not worth it. Layout
           switching has diminishing returns after the first switch off QWERTY."]]

    [:div.highlight-box
     [:p [:strong "The honest answer:"] " The difference between Colemak-DH and
      Enthium is much smaller than the difference between either layout and QWERTY.
      If you are still on QWERTY, switch to anything else first. If you are on
      Colemak-DH and experiencing redirect discomfort or SFB frustration, Enthium
      is worth considering."]]

    [:h2 "Does Enthium work on standard ANSI / ISO keyboards?"]

    [:p "Yes. The core 30 keys of Enthium v14 map onto any standard keyboard using
        system-level remapping tools. On Linux, the XKB configuration is provided in
        the "
     [:a {:href "https://github.com/sunaku/enthium/tree/main/linux"
          :target "_blank"} "linux/"]
     " directory. On macOS, a Karabiner-Elements complex modification is provided in
     the "
     [:a {:href "https://github.com/sunaku/enthium/tree/main/macos"
          :target "_blank"} "macos/"]
     " directory."]

    [:p "The lateral pinky keys ("
     [:code "b"] " on CapsLock and " [:code "w"] " on the traditional apostrophe key)
     are standard keys that every ANSI and ISO keyboard has. No hardware modification
     is required."]

    [:h2 "What about the thumb key R?"]

    [:p "The " [:code "r"] " thumb key is a feature of split and columnar ergonomic
        keyboards with dedicated thumb clusters, such as the Glove80, Kyria, Corne,
        or Ferris. On a standard keyboard, " [:code "r"] " occupies a conventional
        key position and is typed with the left index finger in the normal row-staggered
        manner."]

    [:p "The layout is fully functional without a thumb cluster. The thumb-key
        placement is an optional ergonomic enhancement for those who already have or
        plan to build a split keyboard. Sunaku's original training and development
        was done on a Glove80, but the layout is designed to work well on standard
        hardware too."]

    [:h2 "How does Enthium compare to Dvorak for a complete beginner?"]

    [:p "For a complete beginner starting from QWERTY with no prior layout experience,
        Enthium is measurably better than Dvorak by every major analyzer metric:"]

    [:ul
     [:li "SFBs: 0.55% vs Dvorak's 1.87%"]
     [:li "LSBs: 0.07% vs Dvorak's 0.80%"]
     [:li "Redirects: 1.48% vs Dvorak's 1.55%"]
     [:li "Total Word Effort: 724.5 vs Dvorak's 1185.5"]]

    [:p "The learning curve is comparable. Dvorak's main advantage is familiarity
        \u2014 decades of documentation, training tools, and community support.
        Enthium's main advantage is that its numbers are simply better on modern
        corpus analysis. For someone starting fresh with no sunk cost in any layout,
        Enthium is the more defensible choice."]

    [:h2 "What is the connection between Enthium and Engrammer?"]

    [:p "Engrammer is Arno's adaptation of his Engram layout for standard row-staggered
        keyboards. Enthium began as a horizontal mirror of Hands Down Promethium \u2014
        which is itself derived from Engram \u2014 with the apostrophe moved to a
        dedicated finger that doesn't touch any vowel. The left-hand vowel cluster
        (" [:code "y o u i a e"] ") is inherited directly from Engram/Engrammer."]

    [:p "The right hand is where Enthium diverges: rather than Engrammer's right-hand
        arrangement, Enthium uses the Dvorak-derived " [:code "h t n s"] " home row,
        which scores substantially better on SFBs, SFS, and redirect metrics with
        English text."]

    [:div.highlight-box
     [:p [:strong "Key difference:"] " If you come from Engrammer, your left hand
      needs almost no relearning. Your right hand needs significant relearning \u2014
      budget 3\u20134 days of deliberate practice to internalise the right-hand
      consonant cluster."]]

    [:h2 "Is there a Tarmak-style incremental transition path?"]

    [:p "Not currently. Tarmak works for Colemak because many keys stay in place and
        the progression is carefully sequenced to minimise disruption. Enthium's
        changes are more distributed across both hands, which makes an incremental
        path harder to design without creating worse intermediate layouts."]

    [:p "The recommended transition strategy is to use KeyBr's progressive unlocking
        system, which functions as a de-facto incremental transition by introducing
        one letter at a time in frequency order. This is arguably better than Tarmak
        because you are always typing a complete functional sublayout rather than a
        deliberately broken intermediate state."]

    [:h2 "Will Enthium ever change again?"]

    [:p "Sunaku has stated that v14 represents a stable, mature design. The "
     [:code ";/"] " swap in v14 was a minor final refinement of v13. The core
     structure \u2014 vowel cluster on the left, Dvorak consonants on the right,
     lateral pinky placement for B and W, apostrophe independence \u2014 has been
     stable since v12."]

    [:p "The layout is licensed under ISC and is free to fork and adapt. Future
        versions, if any, would be released in the "
     [:a {:href "https://github.com/sunaku/enthium" :target "_blank"} "sunaku/enthium"]
     " repository."]

    [:h2 "Where can I discuss Enthium with other users?"]

    [:ul
     [:li "The "
      [:a {:href "https://github.com/sunaku/enthium" :target "_blank"} "GitHub repository"]
      " issue tracker for bug reports and layout feedback."]
     [:li "The "
      [:a {:href "https://sunaku.github.io/enthium-keyboard-layout.html"
           :target "_blank"} "blog post comments"]
      " for discussion of the design rationale."]
     [:li "The AKL (Alternative Keyboard Layouts) Discord server, where Enthium
           has been discussed and tested by community members."]
     [:li "Reddit's r/KeyboardLayouts community."]]]})

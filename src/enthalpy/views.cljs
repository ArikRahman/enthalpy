(ns enthalpy.views
  (:require [enthalpy.router  :as router]
            [enthalpy.data    :as data]
            [enthalpy.layout  :as layout]
            [enthalpy.trainer :as trainer]))

;; ---------------------------------------------------------------------------
;; Stat card data — edit this vector to add/remove/change home-page stat cards
;; ---------------------------------------------------------------------------

(def stats
  [{:label       "SFB Rate"
    :value       "0.55%"
    :description "Same-finger bigrams — 2nd place overall, vs. QWERTY's 4.38%"
    :url         "https://github.com/sunaku/enthium"}
   {:label       "SFS Rate"
    :value       "2.67%"
    :description "Same-finger skipgrams — 1st place, beats the entire table"
    :url         "https://github.com/sunaku/enthium"}
   {:label       "LSB Rate"
    :value       "0.07%"
    :description "Lateral stretch bigrams — 1st place, beats the entire table"
    :url         "https://github.com/sunaku/enthium"}
   {:label       "Roll Rate"
    :value       "45.82%"
    :description "Total inward + outward rolls — fluid, flowing typing feel"
    :url         "https://github.com/sunaku/enthium"}
   {:label       "Redirects"
    :value       "1.48%"
    :description "2nd place — vs. Colemak-DH's 5.33% and QWERTY's 6.22%"
    :url         "https://github.com/sunaku/enthium"}
   {:label       "Word Effort"
    :value       "724.5"
    :description "Cyanophage total word effort \u2014 2.85\u00D7 lower than QWERTY's 2070.6"
    :url         "https://github.com/sunaku/enthium"}])

;; ---------------------------------------------------------------------------
;; Shared components
;; ---------------------------------------------------------------------------

(defn site-nav []
  [:nav.site-nav
   [:div.site-nav__inner
    [:a.site-nav__logo
     {:href "#/"
      :on-click #(do (.preventDefault %) (router/navigate-to-index!))}
     "Enth" [:span "alpy"]]
    [:ul.site-nav__links
     [:li [:a {:href "#/"
               :on-click #(do (.preventDefault %) (router/navigate-to-index!))}
           "Trainer"]]
     [:li [:a {:href "#/about"
               :on-click #(do (.preventDefault %) (router/navigate-to-about!))}
           "About"]]
     [:li [:a {:href "#/layout"
               :on-click #(do (.preventDefault %) (router/navigate-to-layout!))}
           "Layout"]]
     [:li [:a {:href "#/learn"
               :on-click #(do (.preventDefault %) (router/navigate-to-learn!))}
           "Sections"]]
     [:li [:a {:href "#/compare"
               :on-click #(do (.preventDefault %) (router/navigate-to-compare!))}
           "Compare"]]
     [:li [:a {:href "https://github.com/sunaku/enthium"
               :target "_blank"}
           "GitHub \u2197"]]]]])

(defn site-footer []
  [:footer.site-footer
   [:div.site-footer__inner
    [:p.site-footer__brand
     "Enthium v14 \u2026 by "
     [:strong
      [:a {:href "https://github.com/sunaku" :target "_blank"} "Suraj N. Kurapati"]]
     ". Documentation by Arik Rahman. Licensed ISC."]
    [:ul.site-footer__links
     [:li [:a {:href "https://github.com/sunaku/enthium" :target "_blank"} "GitHub"]]
     [:li [:a {:href "https://sunaku.github.io/enthium-keyboard-layout.html"
               :target "_blank"} "Blog Post"]]
     [:li [:a {:href "#/layout"
               :on-click #(do (.preventDefault %) (router/navigate-to-layout!))}
           "Layout"]]
     [:li [:a {:href "#/learn"
               :on-click #(do (.preventDefault %) (router/navigate-to-learn!))}
           "Sections"]]]]])

;; ---------------------------------------------------------------------------
;; Home page components
;; ---------------------------------------------------------------------------

(defn hero []
  [:section.hero
   [:div.container
    [:span.hero__eyebrow "Keyboard Layout"]
    [:h1.hero__title "Enthium v14"]
    [:p.hero__subtitle "Engrammer meets Promethium"]
    [:p.hero__description
     "A highly performant keyboard layout optimised for English prose and
      programming. 0.55% SFBs, 0.07% LSBs, 1.48% redirects \u2014 first or second
      place in every major ergonomic metric."]
    [:div.hero__actions
     [:a.btn.btn--primary
      {:href "#/layout"
       :on-click #(do (.preventDefault %) (router/navigate-to-layout!))}
      "Explore the Layout"]
     [:a.btn.btn--outline
      {:href "#/learn"
       :on-click #(do (.preventDefault %) (router/navigate-to-learn!))}
      "Learn to Type It"]]]])

(defn stats-section []
  [:section.stats-section
   [:div.container
    [:p.stats-section__heading "Performance at a glance"]
    [:div.stats-grid
     (for [{:keys [label value description url]} stats]
       ^{:key label}
       [:div.stat-card
        [:a {:href url :target "_blank"}
         [:div.stat-card__value value]
         [:div.stat-card__label label]
         [:div.stat-card__description description]]])]]])

(defn layout-preview-section []
  [:section.layout-section
   [:div.container
    [:div.layout-section__header
     [:h2.layout-section__title "The Layout"]
     [:p.layout-section__subtitle
      "Finger colour-coded key diagram \u2014 hover to lift keys"]]
    [layout/keyboard-diagram]
    [:div {:style {:margin-top "1.5rem"}}
     [layout/finger-legend-component]]
    [:div.layout-section__cta
     [:a.btn.btn--outline
      {:href "#/layout"
       :on-click #(do (.preventDefault %) (router/navigate-to-layout!))}
      "Full layout reference \u2192"]]]])

(defn section-card [{:keys [id title excerpt]}]
  [:div.section-card
   {:on-click #(router/navigate-to-section! id)
    :style {:cursor "pointer"}}
   [:h3.section-card__title title]
   [:p.section-card__excerpt excerpt]
   [:a.section-card__link
    {:href (str "#/sections/" id)
     :on-click #(do (.preventDefault %) (router/navigate-to-section! id))}
    "Read more \u2192"]])

(defn featured-sections-component []
  [:section.sections-section
   [:div.container
    [:div.sections-section__header
     [:h2.sections-section__title "Sections"]
     [:a.sections-section__more
      {:href "#/learn"
       :on-click #(do (.preventDefault %) (router/navigate-to-learn!))}
      "Explore all \u2192"]]
    [:div.sections-grid
     (for [s (data/featured-sections)]
       ^{:key (:id s)} [section-card s])]]])

;; ---------------------------------------------------------------------------
;; Index page — Typing trainer as the primary feature
;; ---------------------------------------------------------------------------

(defn index-page []
  [:<>
   [site-nav]
   [trainer/trainer-page]
   [site-footer]])

;; ---------------------------------------------------------------------------
;; About page — the old home page content (layout preview + stats + sections)
;; ---------------------------------------------------------------------------

(defn about-page []
  [:<>
   [site-nav]
   [hero]
   [:hr.section-divider]
   [stats-section]
   [:hr.section-divider]
   [layout-preview-section]
   [:hr.section-divider]
   [featured-sections-component]
   [site-footer]])

;; ---------------------------------------------------------------------------
;; Learn page — full sections archive
;; ---------------------------------------------------------------------------

(defn learn-page []
  [:<>
   [site-nav]
   [:div.learn-page
    [:div.container
     [:div.learn-page__header
      [:h1.learn-page__title "Sections"]
      [:p.learn-page__description
       "Every aspect of Enthium v14 \u2014 design rationale, performance statistics,
        comparisons, learning guide, and FAQ."]]
     [:div.learn-sections-grid
      (for [s (data/all-sections)]
        ^{:key (:id s)} [section-card s])]]]
   [site-footer]])

;; ---------------------------------------------------------------------------
;; Compare page — shows comparison table + compare section content
;; ---------------------------------------------------------------------------

(defn compare-page []
  (let [s (data/get-section "compare")]
    [:<>
     [site-nav]
     [:div.compare-page
      [:div.container
       [:div.compare-page__header
        [:h1.compare-page__title (:title s)]
        [:p.compare-page__lead (:subtitle s)]]
       [:section {:style {:margin-bottom "3rem"}}
        [:h2 {:style {:font-size "1.25rem"
                      :margin-bottom "0.5rem"
                      :color "var(--fg)"}}
         "Performance Comparison"]
        [:p {:style {:font-size "0.875rem"
                     :color "var(--fg-muted)"
                     :margin-bottom "1.5rem"}}
         "Cyanophage Layout Analyzer \u2014 \u2193 lower is better, \u2191 higher is better"]
        [layout/comparison-table]]
       [:article.section-body
        {:style {:margin-top "3rem"}}
        (for [node (:content s)]
          ^{:key (str node)} node)]]]
     [site-footer]]))

;; ---------------------------------------------------------------------------
;; Layout page wrapper
(defn layout-page []
  [:<>
   [site-nav]
   [layout/page]
   [site-footer]])

;; ---------------------------------------------------------------------------
;; Individual section page
;; ---------------------------------------------------------------------------

(defn section-content [s]
  [:div.section-page
   [:div.container--narrow
    [:a.section-page__back
     {:href "#/learn"
      :on-click #(do (.preventDefault %) (router/navigate-to-learn!))}
     "\u2190 All sections"]
    [:div.section-page__header
     [:h1.section-page__title (:title s)]
     (when (:subtitle s)
       [:p.section-page__subtitle (:subtitle s)])]
    [:article.section-body
     (for [node (:content s)]
       ^{:key (str node)} node)]]])

(defn section-not-found [topic]
  [:div.not-found
   [:div.container
    [:h2 (str "Section \u201C" topic "\u201D not found")]
    [:p {:style {:margin-top "1rem"}}
     [:a {:href "#/learn"
          :on-click #(do (.preventDefault %) (router/navigate-to-learn!))}
      "\u2190 Back to all sections"]]]])

(defn section-page [topic]
  (let [s (data/get-section topic)]
    [:<>
     [site-nav]
     (if s
       [section-content s]
       [section-not-found topic])
     [site-footer]]))

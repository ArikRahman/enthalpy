(ns enthalpy.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [enthalpy.router :as router]
            [enthalpy.views :as views]
            [enthalpy.layout :as layout]
            [enthalpy.events :as events]
            [enthalpy.subs :as subs]))

;; ---------------------------------------------------------------------------
;; Root component — dispatches on (:page @router/current-route)
;; ---------------------------------------------------------------------------

(defn app []
  (let [route @(rf/subscribe [:route])]
    (case (:page route)
      :index   [views/index-page]
      :about   [views/about-page]
      :learn   [views/learn-page]
      :compare [views/compare-page]
      :layout  [views/layout-page]
      :section [views/section-page (:topic route)]
      ;; fallback
      [views/index-page])))

;; ---------------------------------------------------------------------------
;; Entry point — called once by shadow-cljs
;; ---------------------------------------------------------------------------

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (router/init!)
  (rdom/render [app] (.getElementById js/document "app")))

;; Hot-reload hook — shadow-cljs calls this after each recompile
(defn ^:dev/after-load reload []
  (rdom/render [app] (.getElementById js/document "app")))

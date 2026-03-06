(ns enthalpy.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [enthalpy.router :as router]
            [enthalpy.views :as views]
            [enthalpy.layout :as layout]))

;; ---------------------------------------------------------------------------
;; Root component — dispatches on (:page @router/current-route)
;; ---------------------------------------------------------------------------

(defn app []
  (let [route @router/current-route]
    (case (:page route)
      :index   [views/index-page]
      :learn   [views/learn-page]
      :compare [views/compare-page]
      :layout  [layout/page]
      :section [views/section-page (:topic route)]
      ;; fallback
      [views/index-page])))

;; ---------------------------------------------------------------------------
;; Entry point — called once by shadow-cljs
;; ---------------------------------------------------------------------------

(defn init []
  (router/init!)
  (rdom/render [app] (.getElementById js/document "app")))

;; Hot-reload hook — shadow-cljs calls this after each recompile
(defn ^:dev/after-load reload []
  (rdom/render [app] (.getElementById js/document "app")))

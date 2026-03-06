(ns enthalpy.router
  (:require [re-frame.core :as rf]))

(defn parse-hash
  "Convert a location.hash string (including leading #) into a route map."
  [hash]
  (cond
    (or (= hash "") (= hash "#") (= hash "#/"))
    {:page :index}

    (= hash "#/about")
    {:page :about}

    (= hash "#/learn")
    {:page :learn}

    (= hash "#/compare")
    {:page :compare}

    (= hash "#/layout")
    {:page :layout}

    (re-find #"^#/sections/(.+)$" hash)
    (let [[_ topic] (re-find #"^#/sections/(.+)$" hash)]
      {:page :section :topic topic})

    :else
    {:page :index}))

;; ---------------------------------------------------------------------------
;; Navigation helpers — call these instead of manipulating location directly.
;; ---------------------------------------------------------------------------

(defn navigate-to-index! []
  (set! (.-hash js/location) "/"))

(defn navigate-to-about! []
  (set! (.-hash js/location) "/about"))

(defn navigate-to-learn! []
  (set! (.-hash js/location) "/learn"))

(defn navigate-to-compare! []
  (set! (.-hash js/location) "/compare"))

(defn navigate-to-layout! []
  (set! (.-hash js/location) "/layout"))

(defn navigate-to-section! [topic]
  (set! (.-hash js/location) (str "/sections/" topic)))

;; ---------------------------------------------------------------------------
;; Initialisation — call once from core/init
;; ---------------------------------------------------------------------------

(defn init!
  "Attach hashchange listener and sync current-route with the current URL."
  []
  (let [sync! #(rf/dispatch [:set-route (parse-hash (.-hash js/location))])]
    (.addEventListener js/window "hashchange" sync!)
    (sync!)))

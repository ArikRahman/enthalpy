(ns enthalpy.data
  (:require [enthalpy.sections.s-learn     :as s-learn]
            [enthalpy.sections.s-rationale :as s-rationale]
            [enthalpy.sections.s-compare   :as s-compare]
            [enthalpy.sections.s-stats     :as s-stats]
            [enthalpy.sections.s-faq       :as s-faq]))

;; ---------------------------------------------------------------------------
;; Registry — list every section here in any order.
;; all-sections sorts by :id alphabetically.
;; ---------------------------------------------------------------------------

(def registry
  [s-learn/section
   s-rationale/section
   s-compare/section
   s-stats/section
   s-faq/section])

;; ---------------------------------------------------------------------------
;; Public helpers
;; ---------------------------------------------------------------------------

(defn all-sections
  "Returns all sections sorted alphabetically by :id."
  []
  (sort-by :id registry))

(defn featured-sections
  "Returns only sections with :featured true, sorted by :id."
  []
  (sort-by :id (filter :featured registry)))

(defn get-section
  "Look up a section by :id string. Returns nil if not found."
  [id]
  (first (filter #(= (:id %) id) registry)))

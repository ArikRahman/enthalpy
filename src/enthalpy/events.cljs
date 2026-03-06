(ns enthalpy.events
  (:require
   [re-frame.core :as rf]
   [enthalpy.db :as db]
   [enthalpy.words :as words]))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 :set-route
 (fn [db [_ route]]
   (assoc db :route route)))

;; ---------------------------------------------------------------------------
;; Trainer Events
;; ---------------------------------------------------------------------------

(rf/reg-event-db
 :trainer/set-level
 (fn [db [_ level]]
   (assoc-in db [:trainer :level] level)))

(rf/reg-event-db
 :trainer/set-mode
 (fn [db [_ mode]]
   (assoc-in db [:trainer :mode] mode)))

(rf/reg-event-db
 :trainer/set-limit
 (fn [db [_ limit]]
   (assoc-in db [:trainer :limit] limit)))

(rf/reg-event-db
 :trainer/toggle
 (fn [db [_ k]]
   (update-in db [:trainer k] not)))

(defn- gen-words [s]
  (let [{:keys [level mode limit allow-caps? allow-punct?]} s]
    (cond
      (= level 8)
      (vec (words/sentences-for-round (min 10 limit)))

      (= mode :time)
      (vec (words/words-for-round level 200 allow-caps?))

      :else
      (vec (words/words-for-round level limit allow-caps?)))))

(rf/reg-event-db
 :trainer/reset-round
 (fn [db _]
   (let [s (:trainer db)
         ws (gen-words s)]
     (-> db
         (assoc-in [:trainer :words] ws)
         (assoc-in [:trainer :word-index] 0)
         (assoc-in [:trainer :input] "")
         (assoc-in [:trainer :input-error?] false)
         (assoc-in [:trainer :hits] 0)
         (assoc-in [:trainer :errors] 0)
         (assoc-in [:trainer :started?] false)
         (assoc-in [:trainer :finished?] false)
         (assoc-in [:trainer :start-ms] nil)
         (assoc-in [:trainer :elapsed-ms] 0)
         (assoc-in [:trainer :raf-id] nil)))))

(rf/reg-event-db
 :trainer/tick
 (fn [db [_ start-ms raf-id]]
   (let [now (js/Date.now)]
     (-> db
         (assoc-in [:trainer :elapsed-ms] (- now start-ms))
         (assoc-in [:trainer :raf-id] raf-id)))))

(rf/reg-event-db
 :trainer/start-timer
 (fn [db [_ start-ms raf-id]]
   (-> db
       (assoc-in [:trainer :start-ms] start-ms)
       (assoc-in [:trainer :raf-id] raf-id))))

(rf/reg-event-db
 :trainer/stop-timer
 (fn [db _]
   (assoc-in db [:trainer :raf-id] nil)))

(rf/reg-event-db
 :trainer/finish-round
 (fn [db _]
   (assoc-in db [:trainer :finished?] true)))

(rf/reg-event-db
 :trainer/input-change
 (fn [db [_ new-val]]
   (let [s (:trainer db)
         target-word (nth (:words s) (:word-index s) nil)]
     (cond
       (nil? target-word)
       db

       (clojure.string/ends-with? new-val " ")
       (let [typed (clojure.string/trim new-val)]
         (if (= typed target-word)
           (let [next-idx (inc (:word-index s))
                 new-hits (inc (:hits s))
                 done? (and (= (:mode s) :words) (>= new-hits (:limit s)))]
             (cond-> db
               true (assoc-in [:trainer :word-index] next-idx)
               true (assoc-in [:trainer :hits] new-hits)
               true (assoc-in [:trainer :input] "")
               true (assoc-in [:trainer :input-error?] false)
               done? (assoc-in [:trainer :finished?] true)))
           (-> db
               (assoc-in [:trainer :errors] (inc (:errors s)))
               (assoc-in [:trainer :input] (if (:backspace-req? s) new-val ""))
               (assoc-in [:trainer :input-error?] true))))

       :else
       (let [error? (and (seq new-val)
                         (not (clojure.string/starts-with? target-word new-val)))]
         (-> db
             (assoc-in [:trainer :input] new-val)
             (assoc-in [:trainer :input-error?] error?)
             (assoc-in [:trainer :errors] (if error? (inc (:errors s)) (:errors s)))
             (assoc-in [:trainer :started?] true)))))))

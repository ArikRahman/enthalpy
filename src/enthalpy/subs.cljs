(ns enthalpy.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :route
 (fn [db _]
   (:route db)))

(rf/reg-sub
 :trainer
 (fn [db _]
   (:trainer db)))

(rf/reg-sub
 :trainer/level
 :<- [:trainer]
 (fn [trainer _]
   (:level trainer)))

(rf/reg-sub
 :trainer/mode
 :<- [:trainer]
 (fn [trainer _]
   (:mode trainer)))

(rf/reg-sub
 :trainer/limit
 :<- [:trainer]
 (fn [trainer _]
   (:limit trainer)))

(rf/reg-sub
 :trainer/words
 :<- [:trainer]
 (fn [trainer _]
   (:words trainer)))

(rf/reg-sub
 :trainer/word-index
 :<- [:trainer]
 (fn [trainer _]
   (:word-index trainer)))

(rf/reg-sub
 :trainer/input
 :<- [:trainer]
 (fn [trainer _]
   (:input trainer)))

(rf/reg-sub
 :trainer/input-error?
 :<- [:trainer]
 (fn [trainer _]
   (:input-error? trainer)))

(rf/reg-sub
 :trainer/hits
 :<- [:trainer]
 (fn [trainer _]
   (:hits trainer)))

(rf/reg-sub
 :trainer/errors
 :<- [:trainer]
 (fn [trainer _]
   (:errors trainer)))

(rf/reg-sub
 :trainer/started?
 :<- [:trainer]
 (fn [trainer _]
   (:started? trainer)))

(rf/reg-sub
 :trainer/finished?
 :<- [:trainer]
 (fn [trainer _]
   (:finished? trainer)))

(rf/reg-sub
 :trainer/start-ms
 :<- [:trainer]
 (fn [trainer _]
   (:start-ms trainer)))

(rf/reg-sub
 :trainer/elapsed-ms
 :<- [:trainer]
 (fn [trainer _]
   (:elapsed-ms trainer)))

(rf/reg-sub
 :trainer/raf-id
 :<- [:trainer]
 (fn [trainer _]
   (:raf-id trainer)))

(rf/reg-sub
 :trainer/show-cheat?
 :<- [:trainer]
 (fn [trainer _]
   (:show-cheat? trainer)))

(rf/reg-sub
 :trainer/sound-stroke?
 :<- [:trainer]
 (fn [trainer _]
   (:sound-stroke? trainer)))

(rf/reg-sub
 :trainer/sound-error?
 :<- [:trainer]
 (fn [trainer _]
   (:sound-error? trainer)))

(rf/reg-sub
 :trainer/allow-caps?
 :<- [:trainer]
 (fn [trainer _]
   (:allow-caps? trainer)))

(rf/reg-sub
 :trainer/allow-punct?
 :<- [:trainer]
 (fn [trainer _]
   (:allow-punct? trainer)))

(rf/reg-sub
 :trainer/backspace-req?
 :<- [:trainer]
 (fn [trainer _]
   (:backspace-req? trainer)))

(rf/reg-sub
 :trainer/emulate-qwerty?
 :<- [:trainer]
 (fn [trainer _]
   (:emulate-qwerty? trainer)))

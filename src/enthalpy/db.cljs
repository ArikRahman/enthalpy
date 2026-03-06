(ns enthalpy.db)

(def default-db
  {:route {:page :index}
   :trainer {:level           1
             :mode            :words
             :limit           15
             :words           []
             :word-index      0
             :input           ""
             :input-error?    false
             :hits            0
             :errors          0
             :started?        false
             :finished?       false
             :start-ms        nil
             :elapsed-ms      0
             :raf-id          nil
             :show-cheat?     true
             :sound-stroke?   false
             :sound-error?    false
             :allow-caps?     false
             :allow-punct?    false
             :backspace-req?  false
             :emulate-qwerty? false}})

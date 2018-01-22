(ns todof.core
  (:require [domina :as dom]
            [domina.events :as ev]))

(console.log "Hello World")
(console.log (dom/by-id "hello"))

(defn add-exclamation [evt]
  (let [curr-text (dom/value (dom/by-id "hello"))]
    (console.log curr-text)
    (dom/set-text! (dom/by-id "hello") (str curr-text "!"))))

(ev/listen! (dom/by-id "hello") "click" add-exclamation)

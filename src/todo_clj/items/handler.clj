(ns todo-clj.items.handler
  (:require [todo-clj.items.modelt :as t]))

(defn item-names [items]
  (into [] (for [[k v] items] (:name v))))

(defn handle-index-items [req]
  (let [items (t/read-items)]
    {:status 200
     :headers {}
     :body (str "<html><head></head><body><div>"
                (item-names  items)
                "</div></body></html>")}))

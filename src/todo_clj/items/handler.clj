(ns todo-clj.items.handler
  (:require [todo-clj.items.modelt :as t]
            [todo-clj.items.view :refer [items-page]]))

(defn item-names [items]
  (into [] (for [[k v] items] (:name v))))

(defn handle-index-items [req]
  (let [items (t/read-items)]
    {:status 200
     :headers {}
     :body (items-page items)}))

(defn handle-create-item [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        item-id (t/create-item name description)]
    {:status 302
     :headers {"Location" "/items"}
     :body ""}))

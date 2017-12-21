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
                "</div><form method=\"POST\" action=\"/items\">"
                "<input type=\"text\" name=\"name\" placeholder=\"name\">"
                "<input type=\"text\" name=\"description\" placeholder=\"description\">"
                "<input type=\"submit\">"
                "</div></body></html>")}))

(defn handle-create-item [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        item-id (t/create-item name description)]
    {:status 302
     :headers {"Location" "/items"}
     :body ""}))

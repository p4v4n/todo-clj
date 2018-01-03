(ns todo-clj.items.handler
  (:require [todo-clj.items.modelt :as t]
            [todo-clj.items.view :refer [login-page items-page]]))

(defn item-vec [items]
  (into [] (for [[k v] items] v)))

(defn handle-login-page [req]
  {:status 200
   :headers {}
   :body (login-page)})

(defn handle-login [req]
  (let [name (get-in req [:params "username"])
        pass (get-in req [:params "password"])]
    {:status 302
     :headers {"Location" "/items"}
     :body ""}))

(defn handle-index-items [req]
  (let [items (item-vec  (t/read-items))]
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

(defn handle-delete-item [req]
  (let [item-id  (:item-id (:route-params req))
        exists? (t/delete-item item-id)]
    (if exists?
      {:status 302
       :headers {"Location" "/items"}
       :body ""}
      {:status 404
       :body "List not found."
       :headers {}})))

(defn handle-update-item [req]
  (let [item-id  (:item-id (:route-params req))
        exists? (t/update-item item-id)]
    (if exists?
      {:status 302
       :headers {"Location" "/items"}
       :body ""}
      {:status 404
       :body "List not found."
       :headers {}})))

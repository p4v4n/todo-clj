(ns todo-clj.items.handler
  (:require [todo-clj.items.modelt :as t]
            [todo-clj.items.view :refer [login-page signup-page items-page]]))

(defn item-vec [items]
  (into [] (for [[k v] items] v)))

(defn handle-redirect [path]
  {:status 302
   :headers {"Location" path}
   :body ""})

(defn handle-page [body]
  {:status 200
   :headers {}
   :body body})

(defn handle-error [error]
  {:status 404
   :headers {}
   :body error})

(defn handle-login-page [req]
  (handle-page (login-page)))

(defn handle-login [req]
  (let [name (get-in req [:params "username"])
        pass (get-in req [:params "password"])]
    (handle-redirect "/items")))

(defn handle-join [req]
  (handle-page (signup-page)))

(defn handle-signup [req]
  (let [uname (get-in req [:params "username"])
        upass (get-in req [:params "password"])]
    (handle-redirect "/")))

(defn handle-logout [req]
  (redirect "/"))

(defn handle-index-items [req]
  (let [items (item-vec  (t/read-items))]
    (handle-page (items-page items))))

(defn handle-create-item [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        item-id (t/create-item name description)]
    (handle-redirect "/items")))

(defn handle-delete-item [req]
  (let [item-id  (:item-id (:route-params req))
        exists? (t/delete-item item-id)]
    (if exists?
      (handle-redirect "/items")
      (handle-error "List not found"))))

(defn handle-update-item [req]
  (let [item-id  (:item-id (:route-params req))
        exists? (t/update-item item-id)]
    (if exists?
      (handle-redirect "/items")
      (handle-error "List not found"))))

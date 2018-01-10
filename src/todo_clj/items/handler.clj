(ns todo-clj.items.handler
  (:require [todo-clj.items.modelt :as t]
            [todo-clj.items.view :refer [login-page signup-page items-page]]))

(defn item-vec [items]
  (vec (for [[k v] items] v)))

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

;;-------------------

(defn handle-login-page [req]
  (let [identity (get-in req [:session :identity])]
    (if identity
      (handle-redirect "/items")
      (handle-page (login-page)))))

(defn handle-login [req]
  (let [name (get-in req [:params "username"])
        pass (get-in req [:params "password"])
        session (:session req)]
    (if-let [user (t/get-user-by-username-and-password name pass)]
      (assoc (handle-redirect "/items")
             :session (assoc session :identity (:id user)))
      (handle-redirect "/"))))

(defn handle-join [req]
  (handle-page (signup-page)))

(defn handle-signup [req]
  (let [uname (get-in req [:params "username"])
        upass (get-in req [:params "password"])]
    (if-not (or (empty? uname) (empty? upass) (t/username-exists? uname))
      (do (t/create-user {:username uname :password upass})
          (handle-redirect "/"))
      (handle-redirect "/join"))))

(defn handle-logout [req]
  (let [session (:session req)]
    (assoc (handle-redirect "/")
           :session (dissoc session :identity))))

(defn handle-index-items [req]
  (if (get-in req [:session :identity])
    (let [user-id (get-in req [:session :identity])
          items (item-vec  (t/read-items user-id))]
      (handle-page (items-page items)))
    (handle-redirect "/")))

(defn handle-create-item [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        user-id (get-in req [:session :identity])
        item-id (t/create-item name description user-id)]
    (handle-redirect "/items")))

(defn handle-delete-item [req]
  (let [item-id  (:item-id (:route-params req))
        user-id (get-in req [:session :identity])
        exists? (t/delete-item item-id user-id)]
    (if exists?
      (handle-redirect "/items")
      (handle-error "List not found"))))

(defn handle-update-item [req]
  (let [item-id  (:item-id (:route-params req))
        user-id (get-in req [:session :identity])
        exists? (t/update-item item-id user-id)]
    (if exists?
      (handle-redirect "/items")
      (handle-error "List not found"))))

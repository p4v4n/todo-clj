(ns todo-clj.items.modelt
  (:require [buddy.hashers :as hashers]))

(def t-db (atom {}))

(def user-db (atom {}))

                                        ;----------------------

(defn name-valid? [name]
  ((comp not empty?) name))

(defn description-valid? [description]
  ((comp not empty?) description))

(defn generate-uuid []
  (str (java.util.UUID/randomUUID)))

(defn date-now []
  (.format (java.text.SimpleDateFormat. "MM/dd/yyyy") (new java.util.Date)))

;;------------------Users db

(defn create-user [user]
  (let [password (:password user)
        user-id (generate-uuid)]
    (swap! user-db assoc user-id (-> user
                                     (dissoc :password)
                                     (assoc :id user-id
                                            :password-hash (hashers/encrypt password))))))

(defn get-user [user-id]
  (get @user-db user-id))

(defn get-user-by-username-and-password [username password]
  (->> (vals @user-db)
       (filter #(and (= username (:username %))
                     (hashers/check password (:password-hash %))))
       first))

(defn username-exists? [username]
  (->> (vals @user-db)
       (filter #(= username (:username %)))
       not-empty))
;;-------------------Items db

(defn create-item [name description user-id]
  (if (and (name-valid? name)
           (description-valid? description))
    (let [uid (generate-uuid)]
      (swap! t-db update user-id assoc uid
             {:name name
              :description description
              :checked false
              :date (date-now)
              :id uid}))))

(defn update-item [id user-id]
  (swap! t-db update-in [user-id id :checked] not))

(defn delete-item [id user-id]
  (swap! t-db update user-id dissoc id))

(defn read-items [user-id]
  (get (deref t-db) user-id))

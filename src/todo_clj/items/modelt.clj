(ns todo-clj.items.modelt)

(def t-db (atom {}))

                                        ;----------------------

(defn name-valid? [name]
  ((comp not empty?) name))

(defn description-valid? [description]
  ((comp not empty?) description))

(defn generate-uuid []
  (str (java.util.UUID/randomUUID)))

(defn date-now []
  (.format (java.text.SimpleDateFormat. "MM/dd/yyyy") (new java.util.Date)))

;;-------------------

(defn create-item [name description]
  (if (and (name-valid? name) (description-valid? description))
    (swap! t-db assoc (generate-uuid)
           {:name name
            :description description
            :checked false
            :date (date-now)})))

(defn update-item [id]
  (swap! t-db update-in [id :checked] not))

(defn delete-item [id]
  (swap! t-db dissoc id))

(defn read-items []
  (deref t-db))

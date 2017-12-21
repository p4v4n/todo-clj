(ns todo-clj.core
  (:require [todo-clj.items.modelt :as items]
            [todo-clj.items.handler :refer [handle-index-items
                                            handle-create-item]])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]])
  (:gen-class))

(defn hello [req]
  {:status 200
   :body "Hello!!"
   :headers {}})

(defroutes routes
  (GET "/" [] hello)
  (ANY "/request" [] handle-dump)
  (GET "/items" [] handle-index-items)
  (POST "/items" [] handle-create-item)
  (not-found "Page not found."))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "Server"] "todo-001")))

(def app
  (wrap-server
    (wrap-file-info
      (wrap-resource
        (wrap-params
          routes)
        "static"))))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))

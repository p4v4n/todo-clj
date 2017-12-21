(ns todo-clj.core
  (:require [todo-clj.items.modelt :as items])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
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
  (GET "/request" [] handle-dump)
  (not-found "Page not found."))

(def app
  (wrap-params  routes))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))

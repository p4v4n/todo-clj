(ns todo-clj.core
  (:require [todo-clj.items.modelt :as items]
            [todo-clj.items.handler :refer [handle-index-items
                                            handle-create-item
                                            handle-delete-item
                                            handle-update-item
                                            handle-login-page
                                            handle-login
                                            handle-logout
                                            handle-join
                                            handle-signup]])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]])
  (:gen-class))

(defroutes routes
  (GET "/" [] handle-login-page)
  (POST "/login" [] handle-login)
  (GET "/join" [] handle-join)
  (POST "/signup" [] handle-signup)
  (GET "/logout" [] handle-logout)
  (ANY "/request" [] handle-dump)
  (GET "/items" [] handle-index-items)
  (POST "/items" [] handle-create-item)
  (POST "/update/:item-id" [] handle-update-item)
  (DELETE "/items/:item-id" []  handle-delete-item)
  (not-found "Page not found."))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "Server"] "todo-001")))

(def sim-methods {"PUT" :put
                  "DELETE" :delete})

(defn wrap-simulated-methods [hdlr]
  (fn [req]
    (if-let [method (and (= :post (:request-method req))
                         (sim-methods (get-in req [:params "_method"])))]
      (hdlr (assoc req :request-method method))
      (hdlr req))))

(def app
  (wrap-server
    (wrap-file-info
      (wrap-resource
        (wrap-params
          (wrap-simulated-methods
            routes))
        "static"))))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))

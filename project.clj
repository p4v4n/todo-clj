(defproject todo-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.3"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.7.4"]
                 [com.h2database/h2 "1.4.196"]
                 [buddy "2.0.0"]]
  :main todo-clj.core
  :profiles {:dev
             {:main todo-clj.core/-dev-main}})

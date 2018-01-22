(defproject todo-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.3"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [buddy "2.0.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [domina "1.0.3"]]
  :main todo-clj.core
  :plugins [[lein-kibit "0.1.5"]
            [lein-bikeshed "0.5.0"]
            [lein-cljsbuild "1.1.7"]]
  :cljsbuild {:builds
              [{:id "app"
                :source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/app.js"
                           :output-dir "resources/public/js/out"
                           :source-map true
                           :optimizations :none
                           :asset-path "/js/out"
                           :main "todof.core"
                           :pretty-print true}}]}
  :profiles {:dev
             {:main todo-clj.core/-dev-main}})

(defproject zpn-stats-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.zenboxapp/taika "0.1.1"]
                 [clj-time "0.6.0"]
                 [tentacles "0.2.5"]
                 [org.clojure/core.memoize "0.5.6"]
                 [incanter/incanter-core "1.5.2"]
                 [compojure "1.1.5"]
                 [ring/ring-json "0.2.0"]]
  :plugins [[lein-ring "0.8.6"]]
  :ring {:handler zpn-stats-server.server/app})


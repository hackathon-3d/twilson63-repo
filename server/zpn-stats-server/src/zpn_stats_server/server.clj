(ns zpn-stats-server.server
  (:require [compojure.route :as route]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.json :refer :all]
            [zpn-stats-server.core :refer :all]))

(defroutes main-routes
  (GET "/" [] "<h1>Hello World</h1>")
  (POST "/checkin" {body :body}
        (println body)
        (process-checkin-event body))
  (route/not-found "<h1>Page not found</h1>"))

(def app (-> main-routes
             handler/api
             wrap-json-body))




(ns zpn-stats-server.logic
  (:require [tentacles.users :as github.users]
            [tentacles.repos :as github.repos]
            [zpn-stats-server.firebase :as firebase]
            [zpn-stats-server.score :as score]
            [clojure.core.memoize :as memo]))

(defn get-github-user
  [user-id]
  (-> user-id
      firebase/user
      (get "username")
      score/user-from-github))

(defn process-checkin-event
  [user-id]
  
  )

(ns zpn-stats-server.firebase
  (:require [taika.core :as taika]
            [clojure.core.memoize :as memo]))

(def db-name "zpn")

(defn check-ins
  []
  (taika/read db-name "/checkins"))

(defn users
  []
  (taika/read db-name "/users"))

(defn- user
  [user-id]
  (taika/read db-name (str "/users/" user-id)))

(def user 
  (memo/ttl user :ttl/threshold (* 60 60 60)))

(defn update-ranked-cohort
  [user-id ranked-cohorts]
  (taika/update! db-name (str "/cohorts/" user-id)  
                 {:ranked_cohorts ranked-cohorts} ))

(defn update-ranked-cohorts
  [ranked-cohorts]
  (let [user-ids (pmap :user_id ranked-cohorts)]
    (pmap #(update-ranked-cohort % ranked-cohorts) user-ids)))

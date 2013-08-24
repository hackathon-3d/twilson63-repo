(ns zpn-stats-server.core
  (:require [zpn-stats-server.cohort :as cohort]
            [zpn-stats-server.score :as score]
            [zpn-stats-server.firebase :as firebase]))

(defn process-checkin-event
  "Calculates the Zombie-Pirate-Ninja-Robot Score 
   for a Github User."
  [check-in]
  (let [cohorts (cohort/cohorts check-in)
        raw-scored-cohorts (map score/raw cohorts)
        ranked-cohorts (score/rank raw-scored-cohorts)]
    (firebase/update-ranked-cohorts ranked-cohorts)))


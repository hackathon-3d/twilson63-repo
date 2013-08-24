(ns zpn-stats-server.score
  (:require [tentacles.users :as github.users]
            [tentacles.repos :as github.repos]
            [zpn-stats-server.firebase :as firebase]
            [clojure.core.memoize :as memo]
            [incanter.stats :as stats]))

(def a-day (* 60 60 60 24))
(def user-from-github (memo/ttl github.users/user :ttl/threshold (* 60 60 60 24)))
(def repos-from-github (memo/ttl github.repos/user-repos :ttl/threshold a-day))

(defn pirate-score
  "Social Aspect"
  [github-user]
  (let [repos (repos-from-github (:login github-user))
        watchers (map :watchers repos)]
    (+ (:followers github-user)
       (reduce + (remove nil? watchers)))))

(defn zombie-score
  "Breadth of Knowledge"
  [github-user]
  (let [repos (repos-from-github (:login github-user))]
    (->> (map :language repos)
         (remove nil?)
         set)))

(defn robot-score
  [github-user]
  (let [repos (repos-from-github (:login github-user))]
    (count repos)))

(defn extract-forks
  [repos]
  (remove nil? (map :forks repos)))

(defn extract-and-sum-watchers
  [repos]
  (reduce + (remove nil? (map :watchers repos))))

(defn extract-and-sum-forks
  [repos]
  (reduce + (remove nil? (map :forks repos))))

(defn ninja-score
  "Output and Quality thereof"
  [github-user]
  (let [repos (repos-from-github (:login github-user))]
    (+ (extract-and-sum-forks repos)
     (extract-and-sum-watchers repos))))

(defn raw
  [user-id]
  (let [firebase-user (firebase/user user-id)
        _ (println firebase-user)
        _ (println "got firebase-user")
        github-user (user-from-github (get firebase-user "username"))
        _ (println github-user)
        _ (println "got firebase-user")]
    {:user_id user-id
     :ninja_raw_score (ninja-score github-user)
     :robot_raw_score (robot-score github-user)
     :zombie_languages (zombie-score github-user)
     :zombie_raw_score (count (zombie-score github-user)) 
     :pirate_raw_score (pirate-score github-user)}))

(defn determine-quartile-score
  [quantile value]
  (let [[min twenty-five fifty seventy-five max] quantile]
    (println min twenty-five fifty seventy-five max)
    (cond (and (>= value min) (< value twenty-five)) 1
          (and (>= value twenty-five) (< value fifty)) 2
          (and (>= value fifty) (< value seventy-five)) 3
          (and (>= value seventy-five) (<= value max)) 4)))

(defn assign-quartile-score
  [quantile input-k output-k user]
  (assoc user output-k (determine-quartile-score quantile
                                                 (input-k user))))

(defn quartile-scores
  [users input-k output-k]
  (println users input-k output-k)
  (println "about to get quartile scores")
  (let [raw-scores (map input-k users)
        _ (println "raw-scores: " raw-scores)
        quantile (stats/quantile raw-scores)
        _ (println "raw-scores: " quantile)]
    (map #(assign-quartile-score quantile input-k output-k %) users)))

(defn rank
  [cohorts]
  (println cohorts)
  (-> cohorts
      (quartile-scores :ninja_raw_score :ninja_rank)
      (quartile-scores :robot_raw_score :robot_rank)
      (quartile-scores :zombie_raw_score :zombie_rank)
      (quartile-scores :pirate_raw_score :pirate_rank)))






(ns zpn-stats-server.cohort-test
  (:require [clojure.test :refer :all]
            [zpn-stats-server.cohort :refer :all]
            [clj-time.coerce :as time.coerce]
            [clj-time.format :as time.format]
            [clj-time.core :as time]))

(def basic-date-time-formatter (time.format/formatters :basic-date-time))

(def entry {"21292" {"lat" 32.9002784 "lng" -79.9162466 "stmp" "2013-08-24T04:51:37.644Z"}})

(deftest transform-entry-test
  (is (= {"user-id" "21292" "lat" 32.9002784 "lng" -79.9162466 "stmp" "2013-08-24T04:51:37.644Z"}
         (transform-entry entry))))

(deftest within-four-hours?-test
  (let [now (time.format/unparse basic-date-time-formatter
                                 (time/now))]
    (is (within-four-hours? {:stmp "2013-08-24T04:51:37.644Z"}))))

(def family-circle-cup {:latitude 32.861961
                        :longitude -79.901658})
(def daniel-island-publix {:latitude 32.863313, 
                           :longitude -79.903804})
(def longpoint-harris-teeter {:latitude 32.843251, 
                              :longitude -79.8568})

(deftest haversine-test
  (is (= 4.675361943093953
         (haversine family-circle-cup longpoint-harris-teeter))))

(deftest near?-test
  (is (= false
         (-> (near? family-circle-cup longpoint-harris-teeter))))
  (is (= true
       (-> (near? family-circle-cup daniel-island-publix)))))







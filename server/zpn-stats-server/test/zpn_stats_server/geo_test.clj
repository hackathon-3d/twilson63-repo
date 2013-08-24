(ns zpn-stats-server.geo-test
  (:require [zpn-stats-server.geo :refer :all]
            [clojure.test :refer :all]))


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



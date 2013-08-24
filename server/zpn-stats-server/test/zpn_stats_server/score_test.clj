(ns zpn-stats-server.score-test
  (:require [clojure.test :refer :all]
            [zpn-stats-server.score :refer :all]))

(def entry {"21292" {"lat" 32.9002784 "lng" -79.9162466 "stmp" "2013-08-24T04:51:37.644Z"}})

(def u (user-from-github "markgunnels"))
(def repos (repos-from-github  "markgunnels"))

;; worse tests ever written
(deftest pirate-score-test
  (is (= 45
         (pirate-score u)))) 

(deftest zombie-score-test
  (is (= 2 (count (zombie-score u)))))

(deftest ninja-score-test
  (is (= 38 (ninja-score u))))


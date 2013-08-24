(ns zpn-stats-server.firebase-test
  (:require [zpn-stats-server.firebase :refer :all]
            [clojure.test :refer :all]))

(deftest check-ins-test
  (let [check-ins (check-ins)]
    (println check-ins)
    (is (not (empty? check-ins)))))

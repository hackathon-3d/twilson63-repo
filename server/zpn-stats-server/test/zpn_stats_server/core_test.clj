(ns zpn-stats-server.core-test
  (:require [clojure.test :refer :all]
            [zpn-stats-server.core :refer :all]
            [clj-time.coerce :as time.coerce]
            [clj-time.core :as time]))

(deftest within-four-hours?-test
  []
  (let [now (time/now)
        5-hours-ago (time/minus now (hours 5))]
    (is (within-four-hours? now 5-hours-ago))))

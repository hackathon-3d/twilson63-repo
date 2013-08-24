(ns zpn-stats-server.cohort
  (:require [zpn-stats-server.firebase :as db]
            [clj-time.coerce :as time.coerce]
            [clj-time.core :as time]))

;; WHAT IS NEAR?
(def earth-radius 6367)
(def near 0.5)

(defn rad [x] 
  (* x  (/ Math/PI 180)))

;; https://gist.github.com/frankvilhelmsen/1787462
;; Thankfully someone else had done the work.
;; Full credit to them.
(defn haversine 
  [position destination]
  "Calculate the distance between two coordinates, with the haversine formula"
  (let [square_half_chord 
        (+ (Math/pow (Math/sin (/ (rad (- (destination :latitude) (position :latitude))) 2)) 2) 
           (* (Math/cos (rad (position :latitude))) 
              (Math/cos (rad (destination :latitude))) 
              (Math/pow (Math/sin (/ (rad (- (destination :longitude) (position :longitude))) 2)) 2)))
        angular_distance 
        (* (Math/asin (Math/sqrt square_half_chord)) 2)]
    (* angular_distance earth-radius)))

(defn near?
  [position destination]
  (< (haversine position destination) near))

(defn transform-entry
  [checkin]
  (let [k (-> checkin keys first)
        v (get checkin k)]
    (assoc v "user-id" k)))

(defn timestamp-as-date-time
  [checkin]
  (-> checkin
      (get "stmp")
      time.coerce/to-date-time))

(defn within-four-hours?
  [check-in]
  (println check-in)
  (let [check-in-date (time.coerce/to-date-time (:stmp check-in))
        four-hours-ago (time/ago (time/hours 4))]
    (println four-hours-ago)
    (time/before? four-hours-ago check-in-date)))

(defn find-recent-checkins
  [check-ins]
  (filter #(within-four-hours? %) check-ins))

(defn is-close-check-in?
  [check-in past-check-in]
  (println check-in)
  (println "LAT: " (get check-in "lat") )
  (near? {:latitude (get check-in "lat")
          :longitude (get check-in "lng")}
         {:latitude (get past-check-in "lat")
          :longitude (get past-check-in "lng")}))

(defn find-close-check-ins
  [check-in check-ins]
  (filter #(is-close-check-in? (-> check-in vals first) 
                               (-> % second)) check-ins))

(defn cohorts
  [check-in]
  (let [check-ins (db/check-ins)
        _ (println check-ins)
        _ (println "got check-ins")
        recent-check-ins (find-recent-checkins check-ins)
        _ (println recent-check-ins)
        _ (println "got recent-check-ins")
        cohorts (find-close-check-ins check-in check-ins)
        _ (println cohorts)
        _ (println "got cohorts")]
    (map first cohorts)))


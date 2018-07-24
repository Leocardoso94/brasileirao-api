(ns brasileirao.core-test
  (:require [clojure.test :refer :all]
            [brasileirao.core :refer :all]))

(deftest get-all-teams
  (is (= 20 (count (get-table "brasileirao-serie-a")))))

(deftest correct-goals-difference
  (let [stats (:stats (first  (get-table "brasileirao-serie-b")))]
  (is (= (:goal-difference stats) (-  (:goals-for stats)  (:goals-against stats))))))

(deftest correct-points 
  (let [stats (:stats (first  (get-table "brasileirao-serie-b")))]
  (is (= (:points stats) (+ (:drawed stats) (* 3 (:won stats)))))))
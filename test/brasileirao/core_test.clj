(ns brasileirao.core-test
  (:require [clojure.test :refer :all]
            [brasileirao.core :refer :all]))

(deftest get-all-teams
  (is (= 20 (count (get-table "brasileirao-serie-a")))))

(deftest correct-goals-difference
  (let [stats (:stats (first  (get-table "brasileirao-serie-b")))]
  (is (= (Integer/parseInt (:goal-difference stats)) (- (Integer/parseInt (:goals-for stats)) (Integer/parseInt (:goals-against stats)))))))
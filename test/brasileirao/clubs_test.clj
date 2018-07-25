(ns brasileirao.clubs-test
  (:require [clojure.test :refer :all]
            [brasileirao.clubs :refer :all]))

(deftest get-all-teams-from-2-series
  (is (= 20 (count (:serie-a (get-clubs)))))
  (is (= 20 (count (:serie-b (get-clubs))))))
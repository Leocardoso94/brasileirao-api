(ns brasileirao.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [brasileirao.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"brasileirao-serie-a\":\"http://localhost:3000/table/brasileirao-serie-a\",\"brasileirao-serie-b\":\"http://localhost:3000/table/brasileirao-serie-b\"}"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
(ns brasileirao.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.response :refer [response]]
            [brasileirao.core :refer [get-table]]))

(defn endpoints []
  (let [uri "http://localhost:3000"
        generate-uri #(str uri %)]
    {:brasileirao-serie-a (generate-uri "/table/brasileirao-serie-a")
     :brasileirao-serie-b (generate-uri "/table/brasileirao-serie-b")}))


(defroutes app-routes
  (GET "/" [a]
    (println a)
    (response  (endpoints)))
  (GET "/table/:tournament" [tournament] (get-table tournament))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])
      wrap-json-response
      wrap-json-body))
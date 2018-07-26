(ns brasileirao.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.response :refer [response]]
            [brasileirao.table :refer [get-table]]
            [brasileirao.clubs :refer [get-clubs]]
            [brasileirao.rodada :refer [get-rodada]]))

(defn endpoints []
  (let [uri "https://campeonato-brasileiro-api.herokuapp.com"
        generate-uri #(str uri %)]
    {:brasileirao-serie-a (generate-uri "/table/brasileirao-serie-a")
     :brasileirao-serie-b (generate-uri "/table/brasileirao-serie-b")
     :clubs (generate-uri "/clubs")
     :round (generate-uri "/round/{round-number}")}))


(defroutes app-routes
  (GET "/" []
    (response  (endpoints)))
  (GET "/clubs" [] (response (get-clubs)))
  (GET "/round/:round" [round serie] (get-rodada round ))
  (GET "/table/:tournament" [tournament] (get-table tournament))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])
      wrap-json-response
      wrap-json-body))
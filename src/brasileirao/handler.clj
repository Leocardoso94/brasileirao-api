(ns brasileirao.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [camel-snake-kebab.core :refer :all]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.util.response :refer [response]]
            [brasileirao.table :refer [get-table]]
            [brasileirao.clubs :refer [get-clubs]]
            [brasileirao.rodada :refer [get-rodada]]))

(defn response-camel-case [data]
  (response  (transform-keys ->camelCaseString data)))

(defn endpoints []
  (let [uri "https://campeonato-brasileiro-api.herokuapp.com"
        generate-uri #(str uri %)]
    {:brasileirao-serie-a (generate-uri "/table/brasileirao-serie-a")
     :brasileirao-serie-b (generate-uri "/table/brasileirao-serie-b")
     :clubs (generate-uri "/clubs")
     :round (generate-uri "/round/{round-number}")}))


(defroutes app-routes
  (GET "/" []
    (response-camel-case (endpoints)))
  (GET "/clubs" [] (response-camel-case (get-clubs)))
  (GET "/round/:round" [round serie] (response-camel-case (get-rodada round)))
  (GET "/table/:tournament" [tournament] (response-camel-case  (get-table tournament)))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])
      wrap-json-response
      wrap-json-body))
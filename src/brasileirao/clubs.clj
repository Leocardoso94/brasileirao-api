(ns brasileirao.clubs
  (:require [net.cgrand.enlive-html :as html]
            [brasileirao.utils :refer [get-dom get-content]]))

(defn get-team [table-row]
  (->
   (html/select table-row [:strong.tabela-times-time-nome])
   first
   get-content))

(defn get-dom-from-tournament [tournament]
  (->
   (str "https://globoesporte.globo.com/futebol/" tournament)
   get-dom
   (html/select   [:table.tabela-times :tbody :tr])
   (->> (map get-team))))


(defn get-clubs  []
  {:serie-a (get-dom-from-tournament "brasileirao-serie-a")
   :serie-b (get-dom-from-tournament "brasileirao-serie-b")})
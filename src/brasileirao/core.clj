(ns brasileirao.core
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]))

(defn get-dom
  [url]
  (html/html-snippet
   (:body @(http/get url {:insecure? true}))))


(defn get-content [html-tag]
  (apply str (html-tag :content)))

(defn get-team [table-row]
  (->
   (html/select table-row [:strong.tabela-times-time-nome])
   first
   get-content))

(defn get-stats [table-row selector]
  (let [html-tag (html/select table-row selector)
        get-stat-by-index #(get-content (nth html-tag %))]
    {:points (get-stat-by-index 0)
     :played (get-stat-by-index 1)
     :won (get-stat-by-index 2)
     :drawed (get-stat-by-index 3)
     :lost (get-stat-by-index 4)
     :goals-for (get-stat-by-index 5)
     :goals-against (get-stat-by-index 6)
     :goal-difference (get-stat-by-index 7)
     :exploitation (get-stat-by-index 8)}))

(defn get-table  []
  (let [dom (get-dom "https://globoesporte.globo.com/futebol/brasileirao-serie-a/")
        teams (map get-team  (html/select dom [:table.tabela-times :tbody :tr]))]
    (map-indexed (fn [index item]
                   {:team (nth teams index)
                    :stats (get-stats item  [:td])}) (html/select dom [:table.tabela-pontos :tbody :tr]))))

(defn -main
  []
  (get-table))

(ns brasileirao.rodada
  (:require [net.cgrand.enlive-html :as html]
            [brasileirao.utils :refer [get-dom get-content]]))

(defn normalize-games [arr normalized-arr]
  (if (empty? arr)
    normalized-arr
    (->> (take 2 arr)
         (map-indexed
          #(if (zero? %1)
             {:hometeam_name (get-content %2)}
             {:awayteam_name (get-content %2)}))
         (reduce merge)
         (conj normalized-arr)
         (normalize-games (drop 2 arr)))))

(defn get-players [dom]
  (normalize-games  (reverse (html/select dom [:span.placar-jogo-equipes-nome])) ()))


(defn get-placar  [dom selector]
  (get-content (first (html/select (:content dom) selector))))

(defn normalize-placar [dom]
  {:hometeam_score (get-placar dom [:span.placar-jogo-equipes-placar-mandante])
   :awayteam_score (get-placar dom [:span.placar-jogo-equipes-placar-visitante])})

(defn  get-mandante-placar [dom]
  (map normalize-placar

       (html/select dom [:span.placar-jogo-equipes-item.placar-jogo-equipes-placar])))

(defn merge-properties [arr1 arr2 arr3]
  (map-indexed (fn [index item]
                 (conj item (nth  arr2 index) (nth  arr3 index))) arr1))

(defn get-other-infos [dom]
  (map (fn [item]
         {:place (get-content (first (html/select item [:span.placar-jogo-informacoes-local])))
          :date (subs  (get-content item) 4 14)
          :schedule (subs (get-content item) (- (count (get-content item)) 5) (count (get-content item)))})
       (html/select dom [:div.placar-jogo-informacoes])))

(defn get-rodada  [rodada]
  (let [rodada-url (str "https://globoesporte.globo.com/servico/backstage/esportes_campeonato/esporte/futebol/modalidade/futebol_de_campo/categoria/profissional/campeonato/campeonato-brasileiro/edicao/campeonato-brasileiro-2018/fases/fase-unica-seriea-2018/rodada/" rodada "/jogos.html")

        dom (get-dom  rodada-url)]
    (merge-properties (get-players dom)
                      (get-mandante-placar dom)
                      (get-other-infos dom))))
(ns brasileirao.rodada
  (:require [net.cgrand.enlive-html :as html]
            [brasileirao.utils :refer [get-dom get-content]]))


(def rodada-url "https://globoesporte.globo.com/servico/backstage/esportes_campeonato/esporte/futebol/modalidade/futebol_de_campo/categoria/profissional/campeonato/campeonato-brasileiro/edicao/campeonato-brasileiro-2018/fases/fase-unica-seriea-2018/rodada/1/jogos.html")

(defn extract-data-from-rodada [html-tag]
  (get-dom  rodada-url))

(defn normalize-games [foo arr]
  (if (empty? arr)
    foo
    (normalize-games  (conj foo (map #(get-content %) (take 2 arr))) (drop 2 arr))))

(defn get-players [dom]
  (normalize-games () dom))

(defn get-rodada  []
  (let [dom (get-dom  rodada-url)]

    (get-players (html/select dom [:span.placar-jogo-equipes-nome]))
  ; (get-dom  rodada-url)
    ))
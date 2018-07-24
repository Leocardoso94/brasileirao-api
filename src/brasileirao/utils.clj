(ns brasileirao.utils
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]))

(defn get-dom
  [url]
  (html/html-snippet
   (:body @(http/get url {:insecure? true}))))

(defn get-content [html-tag]
  (apply str (html-tag :content)))
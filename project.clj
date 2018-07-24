(defproject brasileirao "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.8.1"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [enlive "1.1.6"]
                 [http-kit "2.1.18"]
                 [compojure "1.5.1"]
                 [ring/ring-json "0.4.0"]
                 [ring-cors "0.1.12"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler brasileirao.handler/app}
  :jvm-opts ["--add-modules" "java.xml.bind"]
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.0"]]}})

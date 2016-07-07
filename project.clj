(defproject zap "1.0.0"
  :description "Zap your bugs!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                           [ring/ring-core "1.4.0"]
			   [ring/ring-jetty-adapter "1.4.0"]
                           ;[kerodon "0.7.0"]
                           [compojure "1.4.0"]
                           [hiccup "1.0.5"]
                           [korma "0.4.0"]
                           [org.xerial/sqlite-jdbc "3.7.15-M1"]
                           [org.clojure/data.json "0.2.6"]]

;:profiles {:dev {:dependencies [[ring-serve "0.1.2"]]}}

  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler zap.core/app}
  :main zap.core
  :aot [zap.core])

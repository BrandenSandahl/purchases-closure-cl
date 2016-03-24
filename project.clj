(defproject purchases-clojure-cl "0.0.1-SNAPSHOT"
  :description "reading from a .csv and putputting to a webpage"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [compojure "1.5.0"]
                 [hiccup "1.0.5"]]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [purchases-clojure-cl.core]
  :main purchases-clojure-cl.core)

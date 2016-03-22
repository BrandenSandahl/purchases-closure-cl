(ns purchases-clojure-cl.core

  (:require [clojure.string :as str]
            [clojure.walk :as walk])
  (:gen-class))


(defn -main []
  
  
      (println "Enter a category to sort by.")

  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line]
                         (str/split line #","))
                       purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line]
                         (apply hash-map (interleave header line)))
                       purchases)
        purchases (walk/keywordize-keys purchases)
        text (read-line)
        purchases (filter (fn [line]
                           (= (:category line) text))
                   purchases)]
        

   (spit (format "Purchases_By_%s.edn" text) (pr-str purchases))))
            

         
        
  
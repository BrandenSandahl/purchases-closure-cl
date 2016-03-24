(ns purchases-clojure-cl.core
  (:require [clojure.string :as str] ;this brings in a new lib
            [clojure.walk :as walk] ;brings in walk lib 
            [compojure.core :as c]
            [ring.adapter.jetty :as j]
            [ring.middleware.params :as p]
            [hiccup.core :as h])
  (:gen-class))

(defn read-purchases []
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
          purchases (walk/keywordize-keys purchases)]
      purchases))


(defn categories [purchases]
  (let [all-categories (map :category purchases)
        categories-set (sort (set all-categories))]
    [:div 
     (map (fn [category]
            [:span
             [:a {:href (str "/?category=" category)} category]
             " "])
      categories-set)
     [:span
      [:a {:href "/?category=All"} "All"]]]))


(defn header-html [purchases]
  [:tr 
   (let [header-values (keys (first purchases))]
       (map (fn[item]
              [:th (name item)])
         header-values))])


(defn purchases-html [filtered-purchases]
   (map (fn [purchase]
          [:tr
           (map (fn [item]
                  [:td (val item)])
            purchase)]) 
    filtered-purchases))

(c/defroutes app    ;define routes here. The app part names the routes for the server to use
  (c/GET "/" request
    (let [params (:params request)
          category (get params "category")
          purchases (read-purchases)
          filtered-purchases (filter (fn [purchase]
                                       (= (:category purchase) category))
                               purchases)]
      (h/html 
             [:head
              [:link {:rel "stylesheet" :href "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"}
               [:html
                [:body
                 [:div {:class "container"}
                  (categories purchases)
                  [:table {:class "table-condensed"}               
                   [:thead
                    (header-html purchases)
                     [:tbody                   
                      (purchases-html (if (not= category "All") filtered-purchases purchases))]]]]]]]]))))
                

(defn -main []
  (j/run-jetty (p/wrap-params app) {:port 3000}))   ;params for the web server
(ns churn.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [csv-map.core :refer :all]))

(def churn "resources/public/Churn-Modelling.csv")

(defn pcs [csv]
  (parse-csv (slurp csv) :key :keyword))

(defn pchrn []
  (pcs churn))

;; {:estimatedsalary "112542.58", :numofproducts "1", :age "41", 
;; :surname "Hill", :creditscore "608", :balance "83807.86", 
;; :geography "Spain", :exited "0", :hascrcard "0", :rownumber "2", 
;; :gender "Female", :isactivemember "1", :tenure "1", :customerid "15647311"}

(defn countr [row val]
  (count (filter #(= (row %) val) (pcs churn))))

(defn ctrand [func]
  (count (filter (func) (pchrn))))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


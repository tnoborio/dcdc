(ns dcdc.service
  (:require [io.pedestal.http :as http]))

(def counter (atom 0))

(swap! counter inc)

(defn respond-hello
  [_]
  {:status 200 :body (str "Hello, world! " (swap! counter inc))})

(def routes
  #{["/greet" :get respond-hello :route-name :greet]})

(def service
  {:env :prod
   ::http/routes routes
   ::http/resource-path "/public"
   ::http/type   :jetty
   ::http/host   "0.0.0.0"
   ::http/port   8080})

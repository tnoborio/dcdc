(ns hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  {:status 200 :body "Hello, world! 7"})

(def routes
  #(route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]}))

(defn create-server []
  (-> {:env :dev
       ::http/routes routes
       ::http/type   :jetty
       ::http/host   "0.0.0.0"
       ::http/port   8080
       ::http/join? false}
      http/default-interceptors
      http/dev-interceptors
      http/create-server))

(defn run [_]
  (http/start (create-server)))

(run 3)



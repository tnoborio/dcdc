(ns hello
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]))

(def counter (atom 0))

(swap! counter inc)

(defn respond-hello [_]
  {:status 200 :body (str "Hello, world! " (swap! counter inc))})

(def routes
  #(route/expand-routes
    #{["/greet" :get respond-hello :route-name :greet]}))

(defn create-server []
  (-> {:env :dev
       ::route/routes routes
       ::server/type   :jetty
       ::server/host   "0.0.0.0"
       ::server/port   8899
       ::server/join? false}
      server/default-interceptors
      server/dev-interceptors
      server/create-server))

(defn run [& _]
  (server/start (create-server)))

(defn -main [& _] (run))
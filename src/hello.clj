(ns hello
  (:require [io.pedestal.http :as http]
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
       ::http/routes routes
       ::http/type   :jetty
       ::http/host   "0.0.0.0"
       ::http/port   8899
       ::http/join? false}
      http/default-interceptors
      http/dev-interceptors
      http/create-server))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn run [_]
  (http/start (create-server)))

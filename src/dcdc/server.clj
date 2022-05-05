(ns dcdc.server
  (:gen-class)
  (:require [io.pedestal.http :as server]
            [dcdc.service :as service]))

(defonce runnable-service
  (server/create-server service/service))

(defn run-dev
  [& args]
  (-> service/service
      (merge
       {:env :dev
        :http/routes #(deref #'dcdc.service/routes)
        ::server/join? false})
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start))

(defn -main [& args]
  (server/start runnable-service))
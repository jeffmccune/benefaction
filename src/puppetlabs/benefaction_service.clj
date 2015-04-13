(ns puppetlabs.benefaction-service
  (:require [clojure.tools.logging :as log]
            [puppetlabs.benefaction-core :as core]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]))

(defprotocol HelloService
  (hello [this caller]))

(defn env-settings
  "If the PORT environment variable is defined return a map with the key :port
  bound to the Integer value of the environment variable.  Otherwise, return
  nil. This function overrides the TCP port when running in a PaaS process
  model, e.g. in Heroku."
  []
  (if-let [port-env (System/getenv "PORT")]
    (let [port (Integer. port-env)
          _ (log/info (format "Found PORT=%s environment variable" port))]
      {:port port})))

(trapperkeeper/defservice hello-service
  HelloService
  [[:WebserverService override-webserver-settings!]]
  (init [this context]
    (log/info "Initializing hello service")
    ; use $PORT if defined, e.g. in Heroku
    (if-let [settings (env-settings)] (override-webserver-settings! settings))
    context)
  (start [this context]
    (log/info "Starting hello service")
    context)
  (stop [this context]
    (log/info "Shutting down hello service")
    context)
  (hello [this caller]
         (core/hello caller)))

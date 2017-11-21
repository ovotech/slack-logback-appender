(ns user
  (:require [environ.core :refer [env]]
            [slack-logback-appender.core :as sla])
  (:import (ch.qos.logback.core.encoder Encoder)
           (ch.qos.logback.classic.spi ILoggingEvent)
           (ch.qos.logback.classic Level)))

; NOTE: You'll need the slack webhook defined in your SLACK_WEBHOOK environment variable.

(defn try-it
  []
  (let [enc (reify Encoder
              (encode [_ event] (.getBytes "a test")))
        event (reify ILoggingEvent
                (getLevel [_] Level/INFO))]
    (sla/send-to-slack {:channel "#energy-contracts-test" :webhook (env :slack-webhook) :username "test" :encoder enc} event)))


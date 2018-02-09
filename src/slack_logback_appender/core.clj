(ns slack-logback-appender.core
  (:gen-class
    :extends ch.qos.logback.core.UnsynchronizedAppenderBase
    :name ovotech.SlackLogbackAppender
    :init init
    :state state
    :methods [[setChannel [String] void]
              [setEmoji [String] void]
              [setWebhook [String] void]
              [setUsername [String] void]
              [setMention [String] void]
              [setEncoder [ch.qos.logback.core.encoder.Encoder] void]])
  (:require [cheshire.core :as json]
            [clj-http.lite.client :as http]))

(defn send-to-slack
  [{:keys [emoji channel webhook username mention encoder]} event]
  (when (and (not (clojure.string/blank? channel))
             (not (clojure.string/blank? webhook)))
    (let [event-level      (-> event .getLevel str keyword)
          attachment-color (case event-level
                             :ERROR "danger"
                             :WARN "warning"
                             :INFO "good"
                             "#439FE0")
          text             (String. (.encode encoder event))]
      (http/post webhook
                 {:body (json/generate-string
                          {:channel     channel
                           :username    username
                           :icon_emoji  emoji
                           :attachments [{:title     event-level
                                          :color     attachment-color
                                          :mrkdwn_in ["text"]
                                          :text      (if (not (empty? mention))
                                                       (str "<!" (clojure.string/replace mention "@" "") ">\n" text)
                                                       text)}]})}))))

(defn -init []
  [[] (atom {})])

(defn -append [this e]
  (send-to-slack @(.state this) e))

(defn -setEncoder [this encoder]
  (swap! (.state this) assoc :encoder encoder))

(defn -setChannel [this channel]
  (swap! (.state this) assoc :channel channel))

(defn -setWebhook [this webhook]
  (swap! (.state this) assoc :webhook webhook))

(defn -setUsername [this username]
  (swap! (.state this) assoc :username username))

(defn -setEmoji [this emoji]
  (swap! (.state this) assoc :emoji emoji))

(defn -setMention [this mention]
  (swap! (.state this) assoc :mention mention))


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
              [setLayout [ch.qos.logback.core.Layout] void]])
  (:require [cheshire.core :as json]
            [clj-http.client :as http]))

(defn -init []
  [[] (atom {})])

(defn -append [this e]
  (let [{:keys [emoji channel webhook username mention layout]} @(.state this)
        event-level      (-> e .getLevel str keyword)
        attachment-color (case event-level
                           :ERROR "danger"
                           :WARN "warning"
                           :INFO "good"
                           "#439FE0")
        text             (.doLayout layout e)]
    (http/post webhook
      {:body (json/generate-string
               {:channel     channel
                :username    username
                :icon_emoji  emoji
                :attachments [{:title     event-level
                               :color     attachment-color
                               :mrkdwn_in ["text"]
                               :text      (if mention
                                            (str "<!" (clojure.string/replace mention "@" "") ">\n" text)
                                            text)}]})})))

(defn -setLayout [this layout]
  (swap! (.state this) assoc :layout layout))

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


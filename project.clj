(defproject ovotech/slack-logback-appender "0.0.1"
  :description "Simple logback appender for pushing to a given Slack channel"
  :url "https://github.com/ovotech/slack-logback-appender"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :aot :all

  :dependencies [[ch.qos.logback/logback-core "1.2.3"]
                 [cheshire "5.8.0"]
                 [clj-http "3.7.0"]
                 [org.clojure/clojure "1.8.0"]])

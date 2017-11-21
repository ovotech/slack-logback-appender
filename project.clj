(defproject ovotech/slack-logback-appender "0.0.3"
  :description "Simple logback appender for pushing to a given Slack channel"
  :url "https://github.com/ovotech/slack-logback-appender"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :aot :all

  :dependencies [[ch.qos.logback/logback-core "1.2.3"]
                 [cheshire "5.8.0"]
                 [clj-http-lite "0.3.0"]
                 [org.clojure/clojure "1.8.0"]]

  :profiles {:dev {:dependencies [[ch.qos.logback/logback-classic "1.2.3"]
                                  [environ "1.1.0"]]
                   :source-paths ["dev"]
                   :repl-options {:init-ns user}}})

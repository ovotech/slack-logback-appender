# slack-logback-appender

A simple logback appender for pushing messages to slack.

E.g.

```xml
    <appender name="SLACK" class="ovotech.SlackLogbackAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>%date{ISO8601} [%thread] %-5level %logger{36} - %msg%n</Pattern>
        </layout>

        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>${SLACK_LOG_LEVEL:-INFO}</level>
        </filter>
        <channel>#my-channel</channel>
        <username>my-username</username>
        <webhook>https://hooks.slack.com/my/slack/webhook</webhook>
        <emoji>:rain_cloud:</emoji>
        <mention>@channel</mention>
    </appender>
```
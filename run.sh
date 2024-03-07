#!/bin/zsh

mvn clean install && \
cp /run/media/lucifer/kiki/Programming/Java/code/project-i/target/project-i.war /usr/share/apache-tomcat-9.0.85/webapps && \
zsh /usr/share/apache-tomcat-9.0.85/bin/shutdown.sh && \
zsh /usr/share/apache-tomcat-9.0.85/bin/startup.sh

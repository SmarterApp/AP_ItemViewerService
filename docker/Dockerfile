FROM tomcat:8.0.45-jre7

ARG source=content/
ARG contentStore=/home/tomcat7/content/
COPY $source $contentStore

WORKDIR /usr/local/tomcat/

COPY context.xml ./conf
COPY server.xml ./conf
RUN  rm -rf webapps/*

COPY itemviewerservice.war ./webapps

ENTRYPOINT ["catalina.sh", "run"]
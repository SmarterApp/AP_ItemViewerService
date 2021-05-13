FROM tomcat:7-jre8

ARG source=content/
ARG contentStore=/home/tomcat7/content/
ARG contextName=ROOT

COPY $source $contentStore

WORKDIR /usr/local/tomcat/

COPY context.xml ./conf


RUN rm ./conf/server.xml
COPY server.xml ./conf
COPY .keystore ./conf

RUN  rm -rf webapps/*

COPY itemviewerservice.war ./webapps
RUN mv ./webapps/itemviewerservice.war ./webapps/$contextName.war

EXPOSE 8080

ENTRYPOINT ["catalina.sh", "run"]

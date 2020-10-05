FROM ubuntu:20.10
USER root
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && \
	apt-get --no-install-recommends -y install default-jre openjdk-11-jdk
	

ARG VERSION
LABEL VERSION=$VERSION
RUN mkdir /usr/src/municipal-permit-chabot

ARG MUNICIPALPERMITCHABOT_APP
COPY $MUNICIPALPERMITCHABOT_APP /usr/src/municipal-permit-chabot/app.jar

EXPOSE 5000

# configure the entrypoint
ADD ./docker/docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh


ENTRYPOINT ["/docker-entrypoint.sh"]

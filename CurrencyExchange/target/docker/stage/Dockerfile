FROM openjdk
WORKDIR /opt/docker
ADD opt /opt
RUN ["chown", "-R", "daemon:daemon", "."]
EXPOSE 7070
USER daemon
ENTRYPOINT ["sh", "-c", "/"]
CMD []

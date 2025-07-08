FROM ubuntu:latest
LABEL authors="seni"

ENTRYPOINT ["top", "-b"]
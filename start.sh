#!/bin/sh
 sudo docker build -t fieldwire/image-app . &&
 sudo docker run -d -p 8080:8080 fieldwire/image-app

#!/bin/bash

service nginx start
java -jar /admin-panel/app.jar --server.port=8090
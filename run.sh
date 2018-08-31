#!/bin/bash
./gradlew clean deployment -x test
cd dist
docker-compose up -d --build
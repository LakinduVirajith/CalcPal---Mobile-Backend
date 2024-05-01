#!/bin/bash

# REPLACE PLACEHOLDERS IN USER SERVICE YAML
sed -i "s|\${JWT_SECRET}|$JWT_SECRET|g" src/main/resources/config/user-service.yaml
sed -i "s|\${MAIL_ADDRESS}|$MAIL_ADDRESS|g" src/main/resources/config/user-service.yaml
sed -i "s|\${MAIL_APP_PASSWORD}|$MAIL_APP_PASSWORD|g" src/main/resources/config/user-service.yaml
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" src/main/resources/config/user-service.yaml

# REPLACE PLACEHOLDERS IN VERBAL DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" src/main/resources/config/user-service.yaml

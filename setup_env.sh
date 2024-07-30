#!/bin/bash

# REPLACE PLACEHOLDERS IN USER SERVICE YAML
sed -i "s|\${JWT_SECRET}|$JWT_SECRET|g" user-service/src/main/resources/application.yaml
sed -i "s|\${MAIL_ADDRESS}|$MAIL_ADDRESS|g" user-service/src/main/resources/application.yaml
sed -i "s|\${MAIL_APP_PASSWORD}|$MAIL_APP_PASSWORD|g" user-service/src/main/resources/application.yaml
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/user-service.yaml

# REPLACE PLACEHOLDERS IN VERBAL DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/verbal-diagnosis-service.yaml

# REPLACE PLACEHOLDERS IN LEXICAL DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/lexical-diagnosis-service.yaml

# REPLACE PLACEHOLDERS IN OPERATIONAL DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/operational-diagnosis-service.yaml

# REPLACE PLACEHOLDERS IN IDEOGNOSTIC DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/ideognostic-diagnosis-service.yaml

# REPLACE PLACEHOLDERS IN SEQUENTIAL DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/sequential-diagnosis-service.yaml

# REPLACE PLACEHOLDERS IN VISUAL DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/visual-diagnosis-service.yaml

# REPLACE PLACEHOLDERS IN PRACTOGNOSTIC DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/practognostic-diagnosis-service.yaml

# REPLACE PLACEHOLDERS IN GRAPHICAL DIAGNOSIS YAML
sed -i "s|\${MONGODB_URI}|$MONGODB_URI|g" config-server/src/main/resources/config/graphical-diagnosis-service.yaml

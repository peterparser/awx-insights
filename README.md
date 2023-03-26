# AWX insights

This project implements the endpoint that can retrieve all the data pushed from your AWX instance and push this data to a postgreSQL database.

## Implementation

This endpoint is implemented in Quarkus, because I was curious about this new Java framework.

## Techonology Stack

For running and properly exploit this application you need:
* A host that could span from a simple vm to a k8s cluster
* A PostgreSQL database
* A grafana instance


## What I have already prepared for you
* A grafana dashboard that can show you some insightful data about your automations and how much time you are saving by automating that task.
* Ansible automations to update the database with some informations (like how much time you spend for task in a manual manner).
* Tekton pipelines that used to build the container image for this applciations

## What you have to do
All you need to do is to configure the connection to the PostgreSQL database by passing the following environment variables to the application:
* QUARKUS_DATASOURCE_DB-KIND: postgresql 
* QUARKUS_DATASOURCE_JDBC_URL: jdbc:postgresql://\<dbhost\>:\<dbport\>/\<dbname\>
* QUARKUS_DATASOURCE_USERNAME: \<dbusername\>
* QUARKUS_DATASOURCE_PASSWORD: \<dbpassword\>

## Dashboards examples

Enjoy

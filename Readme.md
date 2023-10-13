# Car Store App

## Requirements

- Docker

### Building the project

Run the command in your project root:

- `docker-compose build`

### Starting the application

Run the command in your project root: 

- `docker-compose up`

(Make sure your port 5432 is free since it will try to use it for the postgres DB.
Stop any PostgreSQL service from task manager if it's running to make sure)

- If you want to run it locally then you will need your own postgreSQL server setup, jdk-17 and Maven.

(Adjust the application.properties accordingly. All you need is an empty DB, the flyway scripts will build the schema and pre-populate the pre-requisite data)

### Notes:

- In order to activate the cron job uncomment line 26 in CronjobServiceImpl

- Import the CarStoreAPI.postman_collection.json in Postman to get ready requests for all end-points.

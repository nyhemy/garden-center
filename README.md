# Garden Center API Project
The Garden Center project simulates the curation of a web server for a company called Garden Center LLC. Trainees are instructed to create a REST API that will allow contractors to place orders online.

## Pre-requisites
Both the **Intellij** and **Postman** applications are needed to run this project.

## Running the App
1. Open project in **Intellij**

2. In order to set up postgres database make sure the username, password, and port in the application.yml file are as follows:
POSTGRES_USER=**user**
POSTGRES_PASSWORD=**root**
PORT=**5432**

3. Run ApplicationRunner.
The Dataloader will load examples of each entity (**User, Product, Customer, Order, Address, Item**) into the database when the app starts up.


## Usage
1. Open up **Postman**.

2. Go to the following link to access this project's Postman collections:
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/f6dc56bb43127ac47e45)

3. The *Garden Center API Project* collection folder contains the **GET, POST, PUT, and DELETE** requests for the User, Product, Customer, and Order entities. Each entity's respective requests are put into their own folders, navigate to these folders and select which request you want to perform based off of their contents.

4. In order to **reset the database** to its default state (maybe you add, modified, or deleted data that was part of the default Database payload) go to Intellij and hit the U-Turn arrow at the top right of the project. **Please note this will delete changes you have made!**

## Testing

***Unit Testing***
1. Open the project in **Intellij** if it isn't already.

2. Navigate to the unit test package under *src\test\java\io\catalyte\training\services*.

3. **Right click** on the tests and run them to check the services of the project, or right click and run with coverage to check if their coverage is accurate as well. All services in the project are covered by these tests.

***Integration Testing***
1. Open the project in **Intellij** if it isn't already.

2. Navigate to the integration testing package under *src\test\java\io\catalyte\training\controllers*.

3. **Right click** on the tests and run them to check the project controllers, or right click and run with coverage to check if their coverage is accurate as well. All controllers in the project are covered by these tests.


## Linting
To lint the project press **CTRL + ALT + l**. This project uses Google's Java coding standards.
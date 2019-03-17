# beer-delivery
Sample solution

# How to run
## Java Configuration
* Import project as Maven
* Inside ```hibernate.cfg.xml``` check that ```connection.url``` attribute matches your new database or docker image, also check ```connection.username``` and ```connection.password``` to match your mySql server (if not using docker).
* Open ```App.java``` file, here you can specify running coordinates by using ```lat``` and ```lon``` variables and you can turn on optimization by setting ```optimize``` flag to true
* Create new maven run configuration with command line ```compile exec:java```
* Simply run configuration if using docker or check seperate MySql configuration

## MySql Configuration
### Using seperate MySql server
* On ```MySql```, create empty database named ```beer_delivery```
* Run Java app one time, this will generate tables inside database
* Import csv files, that are provided in ```resource\DatabaseDump``` folder into matching tables
* Run app again, this time it will do calculations

### Using Docker
* Open command shell in ```beer-delivery\docker_sql```
* Execute ```docker build -t beer_delivery .```
* Execute ```docker run -d -p 3306:3306 --name beer_delivery beer_delivery```. Make sure to match port with the one specified in java  ```hibernate.cfg.xml``` attribute ```connection.url```
* Run java application

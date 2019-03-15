# beer-delivery
Sample solution

# How to run
* On ```MySql```, create empty database named ```beer_delivery```
* Import project as Maven
* Inside ```hibernate.cfg.xml``` check that ```connection.url``` attribute matches your newly created database
* Open ```App.java``` file, here you can specify running coordinates by using ```lat``` and ```lon``` variables and you can turn on optimization by setting ```optimize``` flag to true
* Create new maven run configuration with command line ```compile exec:java```
* Run app one time, this will generate tables inside database
* Import csv files, that are provided in ```resource\DatabaseDump``` folder into matching tables
* Run app again, this time it will do calculations

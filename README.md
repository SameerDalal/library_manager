# Library Manger
This program is made to replicate a library that manages users checking out books and books in the library.
The library manager uses MySQL with phpMyAdmin to store user and book data.

## Get Started
Install [XAMPP Control Panel](https://www.apachefriends.org/index.html) with the `Apache` and `MySQL` modules to use phpMyAdmin.


In the method `connectToSQL()` you many need to change the port connection. For example my port would be `3306`

```java
connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_database", "root", "");
```
On this line change `3360` to your port number. To find your port number look in `Usage`

## Usage
Run the XAMPP Control Panel and the Apache and MySQL Modules. Get the MySQL Port number under the `Port(s)` column.


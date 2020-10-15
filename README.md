# Internet shop

## Project purpose
This is web-project which have common functions of internet shop. 

## Project structure
In this project I use N-tier architecture with DB layer, DAO layer, Service layer, Controllers layer and View layer. 
Project was developed according to SOLID principles with authorization and authentication by RBAC filter strategy.
<br><br> Technologies in project
* Java 8
* Maven
* MySQL
* JDBC
* Servlets
* Filters
* Tomcat
* Bootstrap

## Project guide

### To run this project you need do several steps

* Download, install and configure JDK https://www.oracle.com/java/technologies/javase-downloads.html
* Download and install IDEA Ultimate Edition
* Download, install and configure Tomcat https://tomcat.apache.org/download-90.cgi
    * Configure Tomcat in IDEA
        * Application server > add
        * Deployment > add war_exploded
        * Deployment > Application context: \
* Download, install and configure MySQL WorkBench https://dev.mysql.com/downloads/installer/
    * Setup new connection with 
        * Username: root
        * Password: 1234
        * Port: 3306
        * Hostname: 127.0.0.1
    * Create schema internet_shop
    * Create tables using command from init_db.sql file
    
### In this shop there are following function buttons: 
<br>
#### Available for all

<br>**HOMEPAGE** - return to starting page
<br>**REGISTRATION** - register new user
<br>**LOGIN** - start session for already registered user or admin
<br>**LOGOUT** - close current session
<br>**INJECT** - inject dummy products for testing
<br><br>
#### Available for user

<br>**PRODUCTS** - list of products with adding to shopping cart function
<br>**ORDERS** - list of completed orders of current user with detail function
<br>**SHOPPING CART** - list of products with complete order function
<br><br>
#### Available for admin (login: admin, password: admin)

<br>**USERS** - list of users with delete function (don't delete the admin!)
<br>**ADD PRODUCT** - create a new product
<br>**PRODUCT ADMIN** - list of products with delete function
<br>**ORDERS ADMIN** - list of orders of all users with delete function    
    
### Enjoy!     

![HOMEPAGE](https://i.ibb.co/JCLQCXb/home.png "HOMEPAGE")

## Author 
[Khomiak Pavlo](https://github.com/pavlokhomiak)


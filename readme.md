# Local Environment

## Setting up mySql locally
```$xslt
$ brew install mysql
$ brew services start mysql
$ mysql -u root
> create database db_test;
> create user 'db_test'@'localhost' identified by 'password1';
> grant all on db_test.* to 'db_test'@'localhost';
```

## Setting up postgres locally
````$xslt
$ brew install postgresql
$ brew services start postgresql
$ psql postgres
postgres=# CREATE ROLE db_test WITH LOGIN PASSWORD 'password1';
postgres=# ALTER ROLE db_test CREATEDB;
postgres=# CREATE DATABASE db_test;
postgres=# GRANT ALL PRIVILEGES ON DATABASE db_test TO db_test;
````

## Running the app
### Build the app
````$xslt
$ ./gradlew clean assemble
$ java -jar build/libs/db-test-0.0.1-SNAPSHOT.jar
````
### To populate data
````
$ curl -d '{"name":"Product 1", "description":"An awesome product"}' -H "Content-Type: application/json" -X POST http://{{your-api-endpoint}}/api/products
````

### To view product data
Go to http://localhost:8080/api/products

Or you can view data using db console:
````$xslt
postgres=# \c db_test;
postgres=# select * from product;
````

# PCF Environment

## Crunchy service on PCF
### To create a crunchy database service instance
````
$ cf create-service postgresql-9.5-odb small crunchService -c '
{
    "db_name": "crunchy1",
    "db_username": "crunchyuser",
    "owner_name": "Yosep Kim",
    "owner_email": "yokim@pivotal.io"
}'
```` 

### To bind the service instance to your app
````$xslt
$ cf bind-service db-test crunchService
````

### Target mySql or Crunchy
Change ``SPRING_PROFILES_ACTIVE`` in ``manifest.yml`` to either ``mysql`` or ``crunchy``

Then do the following:
````$xslt
$ ./gradlew clean assemble
$ cf push
$ cf unbind-service db-test mysql1/crunchService
$ cf restart db-test 

````
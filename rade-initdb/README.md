# Rade-initdb

The initdb project is used to build an initial database (Oracle or PostgreSQL)
for the application, and populate it with data from INSEE.
The database can then easily be deployed to the Production (or other)
environment.

The scripts were written and tested on a Linux Server (RHEL or CentOS 7).
For the Oracle script, an Oracle 11G database is required on the server,
and it expects to be run from the 'oracle' user account, or an account with
sufficient rights to create a User/Schema.

To build the database, run: ./build-oracle.sh

The database dump can be found here:
/$DUMP_DIR/rade-$DATE/expdp.RADE_DEV.$DATE.dmp.gz
The variable $DATE is the current date in the format YYYYMMDD.
The variable $DUMP_DIR is defined in src/main/bin/rade-db-init-parameters.sh
(by default DUMP_DIR = /u01/dump)
ex:
/u01/dump/rade-20180904/expdp.RADE_DEV.20180904.dmp.gz

The project Structure is standard and rather simple:
* src/main/sql/ contains SQL scripts to build and fill the database
* src/main/resources/ contains additional resources (if necessary)
* src/main/bin/ contains the shell scripts to build the database:
  * src/main/bin/rade-db-init-parameters.sh Définit les variables d'environnements qui seront utilisées par les autres scripts
  * src/main/bin/rade-oracle-0-dropdb.sh Delete the current User/Schema and tablespace
  * src/main/bin/rade-oracle-1-createdb.sh Create a new tablespace and User/Schema
  * src/main/bin/rade-oracle-2-initdb.sh Execute initialisation script that creates the tables, sequences, ...
  * src/main/bin/rade-oracle-3-importdata.sh Execute the various scripts that fill the tables with INSEE data
  * src/main/bin/rade-oracle-4-exportdb.sh Export (expdp) the newly created database

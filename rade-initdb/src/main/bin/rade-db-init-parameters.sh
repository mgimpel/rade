# This file is part of the Rade project (https://github.com/mgimpel/rade).
# Copyright (C) 2018 Marc Gimpel
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#
# $Id$

# Sets the various environment variables for the Rade Database building scripts

#export DATE=`date +%y%m%d%H%M%S`
export DATE=`date +%Y%m%d`

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
# PostgreSQL
# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

export PG_DATABASE=radedb
export PG_USER=radeuser
export PG_PASSWORD=password
export PG_DUMP_DIR=/tmp

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
# Oracle
# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

#export ORACLE_HOSTNAME=localhost
#export ORACLE_SID=AESNDEV
#export ORACLE_BASE=/u01/app/oracle
#export ORACLE_HOME=$ORACLE_BASE/product/11.2.0/db_1
export LANG=fr_FR.iso885915@euro
export NLS_LANG=FRENCH_FRANCE.WE8ISO8859P15
export DUMP_DIR=/u01/dump
export DB_USER=RADE_DEV
export DB_PASSWORD=password

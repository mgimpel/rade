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

# Create a new Rade Tablespace and Schema.

. rade-db-init-parameters.sh
sqlplus / as sysdba <<EOF
CREATE TABLESPACE "TS_RADE_DEV" DATAFILE 'TS_RADE_DEV_1.DBF' SIZE 50M AUTOEXTEND ON NEXT 10M MAXSIZE 5G AUTOALLOCATE SEGMENT SPACE MANAGEMENT AUTO;
CREATE USER "RADE_DEV" IDENTIFIED BY "password" DEFAULT TABLESPACE "TS_RADE_DEV" ACCOUNT UNLOCK;
GRANT "CONNECT" TO "RADE_DEV";
GRANT "RESOURCE" TO "RADE_DEV";
GRANT "DBA" TO "RADE_DEV";
EXIT;
EOF

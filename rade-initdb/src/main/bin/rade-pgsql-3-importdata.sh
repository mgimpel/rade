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

# Creates and fills the various Rade tables with data.

. rade-db-init-parameters.sh
cp ../sql/insert-*.sql $PG_DUMP_DIR/rade-$DATE/
echo "localhost:5432:$PG_DATABASE:$PG_USER:$PG_PASSWORD" > ~/.pgpass
chmod 600 ~/.pgpass
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-TypeEntiteAdmin.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-TypeNomClair.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-StatutModification.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-TypeGenealogieEntiteAdmin.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-Audit.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-CirconscriptionBassin.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-Region.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-Departement.sql
psql -h localhost -d $PG_DATABASE -U $PG_USER -c "\\i $PG_DUMP_DIR/rade-$DATE/create-tables.sql" &> $PG_DUMP_DIR/rade-$DATE/insert-Commune.sql
rm -f ~/.pgpass

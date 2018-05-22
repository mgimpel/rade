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
cp ../sql/insert-*.sql $DUMP_DIR/rade-$DATE/
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-TypeEntiteAdmin-iso885915.sql $DUMP_DIR/rade-$DATE/insert-TypeEntiteAdmin.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-TypeNomClair-iso885915.sql $DUMP_DIR/rade-$DATE/insert-TypeNomClair.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-StatutModification-iso885915.sql $DUMP_DIR/rade-$DATE/insert-StatutModification.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-TypeGenealogieEntiteAdmin-iso885915.sql $DUMP_DIR/rade-$DATE/insert-TypeGenealogieEntiteAdmin.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-Audit-iso885915.sql $DUMP_DIR/rade-$DATE/insert-Audit.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-CirconscriptionBassin-iso885915.sql $DUMP_DIR/rade-$DATE/insert-CirconscriptionBassin.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-Region-iso885915.sql $DUMP_DIR/rade-$DATE/insert-Region.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-Departement-iso885915.sql $DUMP_DIR/rade-$DATE/insert-Departement.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-Commune-iso885915.sql $DUMP_DIR/rade-$DATE/insert-Commune.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-RegionGenealogie-iso885915.sql $DUMP_DIR/rade-$DATE/insert-RegionGenealogie.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-DepartementGenealogie-iso885915.sql $DUMP_DIR/rade-$DATE/insert-DepartementGenealogie.sql
iconv -f UTF-8 -t ISO_8859-15 -o $DUMP_DIR/rade-$DATE/insert-Delegation-iso885915.sql $DUMP_DIR/rade-$DATE/insert-Delegation.sql

sed -i -e "s/'2018-04-01 00:00:00'/TO_TIMESTAMP\('2018-04-01 00:00:00', 'yyyy-mm-dd HH24:MI:SS'\)/" $DUMP_DIR/rade-$DATE/insert-Audit-iso885915.sql
sed -i -e "s/'[0-9]\{4\}-[0-9]\{2\}-[0-9]\{2\}'/TO_DATE\(&, 'yyyy-mm-dd'\)/g" $DUMP_DIR/rade-$DATE/insert-Region-iso885915.sql
sed -i -e "s/'[0-9]\{4\}-[0-9]\{2\}-[0-9]\{2\}'/TO_DATE\(&, 'yyyy-mm-dd'\)/g" $DUMP_DIR/rade-$DATE/insert-Departement-iso885915.sql
sed -i -e "s/'[0-9]\{4\}-[0-9]\{2\}-[0-9]\{2\}'/TO_DATE\(&, 'yyyy-mm-dd'\)/g" $DUMP_DIR/rade-$DATE/insert-Commune-iso885915.sql
sed -i -e "s/'',/'-',/" $DUMP_DIR/rade-$DATE/insert-Region-iso885915.sql
sed -i -e "s/'',/'-',/" $DUMP_DIR/rade-$DATE/insert-Departement-iso885915.sql
sed -i -e "s/'',/'-',/" $DUMP_DIR/rade-$DATE/insert-Commune-iso885915.sql
sed -i -e "/^ALTER TABLE/d" $DUMP_DIR/rade-$DATE/insert-Region-iso885915.sql
sed -i -e "/^ALTER TABLE/d" $DUMP_DIR/rade-$DATE/insert-Departement-iso885915.sql
sed -i -e "/^ALTER TABLE/d" $DUMP_DIR/rade-$DATE/insert-Commune-iso885915.sql
cat >> $DUMP_DIR/rade-$DATE/insert-Commune-iso885915.sql << EOF
ALTER SEQUENCE entiteadmin_seq INCREMENT BY 135500;
SELECT entiteadmin_seq.NEXTVAL FROM dual;
ALTER SEQUENCE entiteadmin_seq INCREMENT BY 1;
EOF
sqlplus RADE_DEV/password << EOF
SPOOL $DUMP_DIR/rade-$DATE/import-data.log
START $DUMP_DIR/rade-$DATE/insert-TypeEntiteAdmin-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-TypeNomClair-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-StatutModification-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-TypeGenealogieEntiteAdmin-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-Audit-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-CirconscriptionBassin-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-Region-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-Departement-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-Commune-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-RegionGenealogie-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-DepartementGenealogie-iso885915.sql
START $DUMP_DIR/rade-$DATE/insert-Delegation-iso885915.sql
SPOOL OFF
EXIT;

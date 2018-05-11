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

# Sets up the initial tables for the Rade Database.

. rade-db-init-parameters.sh
rm -rf $DUMP_DIR/rade-$DATE
mkdir -p $DUMP_DIR/rade-$DATE
cp ../sql/create-tables.sql $DUMP_DIR/rade-$DATE/
sed -i -e '1,18d' $DUMP_DIR/rade-$DATE/create-tables.sql                  # Delete copyright
sed -i -e 's/^\/\*$//g' $DUMP_DIR/rade-$DATE/create-tables.sql            # Uncomment COMMENT ON TABLE
sed -i -e 's/^\*\/$//g' $DUMP_DIR/rade-$DATE/create-tables.sql            # Uncomment COMMENT ON TABLE
sed -i -e 's/--.*//g' $DUMP_DIR/rade-$DATE/create-tables.sql              # Delete SQL comments
sed -i -e '/^\s*$/d' $DUMP_DIR/rade-$DATE/create-tables.sql               # Delete Empty lines
sed -i -e 's/integer/number(10)/g' $DUMP_DIR/rade-$DATE/create-tables.sql # Change Integer to Number(10)
sed -i -e 's/varchar/varchar2/g' $DUMP_DIR/rade-$DATE/create-tables.sql   # Change Varchar to Varchar2
sed -i -e 's/GENERATED BY DEFAULT AS IDENTITY//g' $DUMP_DIR/rade-$DATE/create-tables.sql # IDENTITY only valid on Oracle 12
# Create Sequence and Trigger to replace IDENTITY for ZR_AUDIT, ZR_ENTITEADMIN and ZR_EVENEMENT
sed -i -e '0,/^COMMENT ON TABLE ZR_AUDIT.*/s/^COMMENT ON TABLE ZR_AUDIT.*/CREATE SEQUENCE audit_seq;\nCREATE OR REPLACE TRIGGER audit_bir BEFORE INSERT ON zr_audit FOR EACH ROW WHEN \(new.audit_id IS NULL\)\nBEGIN\n  :new.audit_id := audit_seq.NEXTVAL;\nEND;\n\/\n&/' $DUMP_DIR/rade-$DATE/create-tables.sql
sed -i -e '0,/^COMMENT ON TABLE ZR_ENTITEADMIN.*/s/^COMMENT ON TABLE ZR_ENTITEADMIN.*/CREATE SEQUENCE entiteadmin_seq;\nCREATE OR REPLACE TRIGGER entiteadmin_bir BEFORE INSERT ON zr_entiteadmin FOR EACH ROW WHEN \(new.id IS NULL\)\nBEGIN\n  :new.id := entiteadmin_seq.NEXTVAL;\nEND;\n\/\n&/' $DUMP_DIR/rade-$DATE/create-tables.sql
sed -i -e '0,/^COMMENT ON TABLE ZR_EVENEMENT.*/s/^COMMENT ON TABLE ZR_EVENEMENT.*/CREATE SEQUENCE evenement_seq;\nCREATE OR REPLACE TRIGGER evenement_bir BEFORE INSERT ON zr_evenement FOR EACH ROW WHEN \(new.id IS NULL\)\nBEGIN\n  :new.id := evenement_seq.NEXTVAL;\nEND;\n\/\n&/' $DUMP_DIR/rade-$DATE/create-tables.sql
sqlplus RADE_DEV/password << EOF
SPOOL $DUMP_DIR/rade-$DATE/create-tables.log
START $DUMP_DIR/rade-$DATE/create-tables.sql
SPOOL OFF
EXIT;
EOF

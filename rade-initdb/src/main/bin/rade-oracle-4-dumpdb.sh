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

# Creates an export (expdp) of the newly created Rade database

. rade-db-init-parameters.sh
expdp \'sys/password as sysdba\' directory=dump_dir DUMPFILE=expdp.RADE_DEV.$DATE.dmp LOGFILE=expdp.RADE_DEV.$DATE.log SCHEMAS=RADE_DEV
mv $DUMP_DIR/expdp.RADE_DEV.$DATE.dmp $DUMP_DIR/rade-$DATE/
mv $DUMP_DIR/expdp.RADE_DEV.$DATE.log $DUMP_DIR/rade-$DATE/
gzip -9 $DUMP_DIR/rade-$DATE/expdp.RADE_DEV.$DATE.dmp
chmod a+r $DUMP_DIR/rade-$DATE/expdp.RADE_DEV.$DATE.dmp.gz

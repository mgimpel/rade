/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2018 Marc Gimpel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/* $Id$ */
package fr.aesn.rade.batch.chunk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import fr.aesn.rade.persist.model.Departement;

/**
 * FieldSetMapper that Maps INSEE Departement file lines to Departement Entities.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class DepartementMapper
  extends EntiteAdminMapper
  implements FieldSetMapper<Departement> {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(DepartementMapper.class);

  /**
   * Maps INSEE Departement file lines to Departement Entities.
   * @param fieldSet parsed line from INSEE Departement file.
   * @return the Departement Entity.
   */
  @Override
  public Departement mapFieldSet(final FieldSet fieldSet)
    throws BindException {
    log.debug("Importing line: {}", fieldSet.toString());
    Departement dept = new Departement();
    dept.setTypeEntiteAdmin(getTypeEntiteAdmin("DEP"));
    dept.setRegion(fieldSet.readString(0));
    dept.setCodeInsee(fieldSet.readString(1));
    dept.setChefLieu(fieldSet.readString(2));
    dept.setTypeNomClair(getTypeNomClair(fieldSet.readString(3)));
    dept.setNomMajuscule(fieldSet.readString(4));
    dept.setNomEnrichi(fieldSet.readString(5));
    return dept;
  }
}

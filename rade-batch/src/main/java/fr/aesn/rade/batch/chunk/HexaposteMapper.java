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

import fr.aesn.rade.persist.model.Hexaposte;

/**
 * FieldSetMapper that Maps Hexaposte file lines to Hexaposte Entities.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class HexaposteMapper
  implements FieldSetMapper<Hexaposte> {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(HexaposteMapper.class);

  /**
   * Maps Hexaposte file lines to Hexaposte Entities.
   * @param fieldSet parsed line from Hexaposte file.
   * @return the Hexaposte Entity.
   */
  @Override
  public Hexaposte mapFieldSet(final FieldSet fieldSet)
    throws BindException {
    log.debug("Importing line: {}", fieldSet.toString());
    Hexaposte hexaposte = new Hexaposte();
    hexaposte.setIdentifiant(fieldSet.readString(0));
    hexaposte.setCodeInseeCommune(fieldSet.readString(1));
    hexaposte.setLibelleCommune(fieldSet.readString(2));
    hexaposte.setIndicateurPluridistribution(fieldSet.readInt(3));
    hexaposte.setTypeCodePostal(fieldSet.readString(4));
    hexaposte.setLibelleLigne5(fieldSet.readString(5));
    hexaposte.setCodePostal(fieldSet.readString(6));
    hexaposte.setLibelleAcheminement(fieldSet.readString(7));
    hexaposte.setCodeInseeAncienneCommune(fieldSet.readString(8));
    hexaposte.setCodeMaJ(fieldSet.readString(9));
    hexaposte.setCodeEtenduAdresse(fieldSet.readString(10));
    return hexaposte;
  }
}

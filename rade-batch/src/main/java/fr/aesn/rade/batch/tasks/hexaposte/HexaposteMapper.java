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
package fr.aesn.rade.batch.tasks.hexaposte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import fr.aesn.rade.persist.model.Hexaposte;

/**
 * FieldSetMapper that Maps Hexaposte file lines to Hexaposte Entities.
 * 
 * Parsed using the following Spring Batch Configuration:
 * <code>
 *   <property name="lineMapper">
 *     <bean class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
 *       <property name="lineTokenizer">
 *         <bean class="org.springframework.batch.item.file.transform.FixedLengthTokenizer"
 *               p:columns="1-6,7-11,12-49,50,51,52-89,90-94,95-126,127-131,132,133-142"/>
 *       </property>
 *       <property name="fieldSetMapper">
 *         <bean class="fr.aesn.rade.batch.task.hexaposte.HexaposteMapper"/>
 *       </property>
 *     </bean>
 *   </property>
 * </code>
 * Example file:
 * <code>
 * 19/02/2012©LA POSTE HEXAPOSTE   COMPLET 38                                                                                                    
 * 2204  01001L ABERGEMENT CLEMENCIAT               0M                                      01400L ABERGEMENT CLEMENCIAT                         
 * 2205  01002L ABERGEMENT DE VAREY                 0M                                      01640L ABERGEMENT DE VAREY                           
 * 2206  01004AMBERIEU EN BUGEY                     0M                                      01500AMBERIEU EN BUGEY                               
 * 56168201004AMBERIEU EN BUGEY                     0C                                      01501AMBERIEU EN BUGEY CEDEX                         
 * 56168301004AMBERIEU EN BUGEY                     0C                                      01502AMBERIEU EN BUGEY CEDEX                         
 * 56168401004AMBERIEU EN BUGEY                     0C                                      01503AMBERIEU EN BUGEY CEDEX                         
 * 56168501004AMBERIEU EN BUGEY                     0C                                      01504AMBERIEU EN BUGEY CEDEX                         
 * 56168601004AMBERIEU EN BUGEY                     0C                                      01505AMBERIEU EN BUGEY CEDEX                         
 * 56168701004AMBERIEU EN BUGEY                     0C                                      01506AMBERIEU EN BUGEY CEDEX                         
 * 56168801004AMBERIEU EN BUGEY                     0C                                      01508AMBERIEU EN BUGEY CEDEX                         
 * </code>
 * For more details, see:
 * https://www.fichiers-postaux.com/referentiel/Hexaposte%20NV%202011%20version%20nov%202014%20Amabis.pdf
 *
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

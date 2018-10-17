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
 * FieldSetMapper that Maps INSEE Departement file lines to Departement
 * Entities.
 *
 * Parsed using the following Spring Batch Configuration:
 * <code>
 *   <property name="lineMapper">
 *     <bean class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
 *       <property name="lineTokenizer">
 *         <bean class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer"
 *               p:delimiter="&#9;"/> <!-- &#9; for TAB (ASCII code 09) -->
 *       </property>
 *       <property name="fieldSetMapper">
 *         <bean class="fr.aesn.rade.batch.chunk.DepartementMapper"/>
 *       </property>
 *     </bean>
 *   </property>
 * </code>
 * Example file:
 * <code>
 * REGION DEP CHEFLIEU TNCC NCC                     NCCENR
 * 84     01  01053    5    AIN                     Ain
 * 32     02  02408    5    AISNE                   Aisne
 * 84     03  03190    5    ALLIER                  Allier
 * 93     04  04070    4    ALPES-DE-HAUTE-PROVENCE Alpes-de-Haute-Provence
 * 93     05  05061    4    HAUTES-ALPES            Hautes-Alpes
 * 93     06  06088    4    ALPES-MARITIMES         Alpes-Maritimes
 * 84     07  07186    5    ARDECHE                 Ardèche
 * 44     08  08105    4    ARDENNES                Ardennes
 * 76     09  09122    5    ARIEGE                  Ariège
 * 44     10  10387    5    AUBE                    Aube
 * </code>
 * Pour plus de détails, voir
 * https://www.insee.fr/fr/information/3363419
 *
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

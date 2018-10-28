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
package fr.aesn.rade.batch.tasks.insee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import fr.aesn.rade.persist.model.Region;

/**
 * FieldSetMapper that Maps INSEE Region file lines to Region Entities.
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
 *         <bean class="fr.aesn.rade.batch.task.insee.RegionMapper"/>
 *       </property>
 *     </bean>
 *   </property>
 * </code>
 * Example file:
 * <code>
 * REGION CHEFLIEU TNCC NCC                        NCCENR
 * 01     97105    3    GUADELOUPE                 Guadeloupe
 * 02     97209    3    MARTINIQUE                 Martinique
 * 03     97302    3    GUYANE                     Guyane
 * 04     97411    0    LA REUNION                 La Réunion
 * 06     97608    0    MAYOTTE                    Mayotte
 * 11     75056    1    ILE-DE-FRANCE              Île-de-France
 * 24     45234    2    CENTRE-VAL DE LOIRE        Centre-Val de Loire
 * 27     21231    0    BOURGOGNE-FRANCHE-COMTE    Bourgogne-Franche-Comté
 * 28     76540    0    NORMANDIE                  Normandie
 * 32     59350    4    HAUTS-DE-FRANCE            Hauts-de-France
 * 44     67482    2    GRAND EST                  Grand Est
 * 52     44109    4    PAYS DE LA LOIRE           Pays de la Loire
 * 53     35238    0    BRETAGNE                   Bretagne
 * 75     33063    3    NOUVELLE-AQUITAINE         Nouvelle-Aquitaine
 * 76     31555    1    OCCITANIE                  Occitanie
 * 84     69123    1    AUVERGNE-RHONE-ALPES       Auvergne-Rhône-Alpes
 * 93     13055    0    PROVENCE-ALPES-COTE D'AZUR Provence-Alpes-Côte d'Azur
 * 94     2A004    0    CORSE                      Corse
 * </code>
 * For more details, see:
 * https://www.insee.fr/fr/information/3363419
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class RegionMapper
  extends EntiteAdminMapper
  implements FieldSetMapper<Region> {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(RegionMapper.class);

  /**
   * Maps INSEE Region file lines to Region Entities.
   * @param fieldSet parsed line from INSEE Region file.
   * @return the Region Entity.
   */
  @Override
  public Region mapFieldSet(final FieldSet fieldSet)
    throws BindException {
    log.trace("Importing line: {}", fieldSet.toString());
    Region reg = new Region();
    reg.setTypeEntiteAdmin(getTypeEntiteAdmin("REG"));
    reg.setCodeInsee(fieldSet.readString(0));
    reg.setChefLieu(fieldSet.readString(1));
    reg.setTypeNomClair(getTypeNomClair(fieldSet.readString(2)));
    reg.setNomMajuscule(fieldSet.readString(3));
    reg.setNomEnrichi(fieldSet.readString(4));
    return reg;
  }
}

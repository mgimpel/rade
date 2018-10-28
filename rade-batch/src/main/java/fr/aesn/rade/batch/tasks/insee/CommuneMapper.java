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

import fr.aesn.rade.persist.model.Commune;

/**
 * FieldSetMapper that Maps INSEE Commune file lines to Commune
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
CDC CHEFLIEU REG DEP COM AR CT TNCC ARTMAJ NCC                   ARTMIN NCCENR
0   0        84  01  001 2  08 5    (L')   ABERGEMENT-CLEMENCIAT (L')   Abergement-Clémenciat
0   0        84  01  002 1  01 5    (L')   ABERGEMENT-DE-VAREY   (L')   Abergement-de-Varey
0   1        84  01  004 1  01 1           AMBERIEU-EN-BUGEY            Ambérieu-en-Bugey
0   0        84  01  005 2  22 1           AMBERIEUX-EN-DOMBES          Ambérieux-en-Dombes
0   0        84  01  006 1  04 1           AMBLEON                      Ambléon
0   0        84  01  007 1  01 1           AMBRONAY                     Ambronay
0   0        84  01  008 1  01 1           AMBUTRIX                     Ambutrix
0   0        84  01  009 1  04 1           ANDERT-ET-CONDON             Andert-et-Condon
0   0        84  01  010 1  10 1           ANGLEFORT                    Anglefort
0   0        84  01  011 4  14 1           APREMONT                     Apremont
 * </code>
 * For more details, see:
 * https://www.insee.fr/fr/information/3363419
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class CommuneMapper
  extends EntiteAdminMapper
  implements FieldSetMapper<Commune> {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(CommuneMapper.class);

  /**
   * Maps INSEE Departement file lines to Departement Entities.
   * @param fieldSet parsed line from INSEE Departement file.
   * @return the Departement Entity.
   */
  @Override
  public Commune mapFieldSet(final FieldSet fieldSet)
    throws BindException {
    log.trace("Importing line: {}", fieldSet.toString());
    Commune commune = new Commune();
    commune.setTypeEntiteAdmin(getTypeEntiteAdmin("COM"));
    commune.setDepartement(fieldSet.readString(3));
    commune.setCodeInsee(fieldSet.readString(3)+fieldSet.readString(4));
    commune.setTypeNomClair(getTypeNomClair(fieldSet.readString(7)));
    commune.setNomMajuscule(fieldSet.readString(9));
    commune.setArticleEnrichi(fieldSet.readString(10));
    commune.setNomEnrichi(fieldSet.readString(11));
    return commune;
  }
}

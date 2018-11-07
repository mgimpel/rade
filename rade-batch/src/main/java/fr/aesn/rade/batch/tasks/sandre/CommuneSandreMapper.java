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
package fr.aesn.rade.batch.tasks.sandre;

import java.util.Date;
import java.util.Map;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;

import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.service.BassinService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * FieldSetMapper that Maps CommuneSandre file lines to CommuneSandre Entities.
 *
 * Parsed using the following Spring Batch Configuration:
 * <code>
 *   <property name="lineMapper">
 *     <bean class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
 *       <property name="lineTokenizer">
 *         <bean class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer"
 *               p:delimiter=";"/>
 *       </property>
 *       <property name="fieldSetMapper">
 *         <bean class="fr.aesn.rade.batch.task.sandre.CommuneSandreMapper"/>
 *       </property>
 *     </bean>
 *   </property>
 * </code>
 * Example file:
 * <code>
 * CdCommune;LbCommune;StCommune;DateCreationCommune;DateMajCommune;CdComiteBassin;NumCircAdminBassin;CdEuDistrict;CdBassinDCE
 * Numéro de la commune;Nom de la Commune;StCommune;DateCreationCommune;DateMajCommune;Code du comité de bassin;Numéro de la circonscription de bassin;Code européen du district hydrographique;Code du bassin DCE
 * "01001";"L'ABERGEMENT-CLÉMENCIAT";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * "01002";"L'ABERGEMENT-DE-VAREY";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * "01004";"AMBÉRIEU-EN-BUGEY";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * "01005";"AMBÉRIEUX-EN-DOMBES";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * "01006";"AMBLÉON";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * "01007";"AMBRONAY";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * "01008";"AMBUTRIX";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * "01009";"ANDERT-ET-CONDON";"Validé";"2002-01-01";"2016-01-01T00:00:00";"FR000006";"06";"EU35";"D"
 * </code>
 * For more details, see:
 * <ul>
 * <li>Page Sandre: http://www.sandre.eaufrance.fr/atlas/srv/fre/catalog.search#/metadata/689a5b99-8d4e-488d-9305-c970b18ad64c</li>
 * <li>Description du Modèle de données: http://services.sandre.eaufrance.fr/telechargement/geo/COM/sandre_sc_referentiels_com_1.pdf</li>
 * <li>Web Service fournissant le CSV: https://api.sandre.eaufrance.fr/referentiels/v1/commune.csv?compress=true</li>
 * </ul>
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public class CommuneSandreMapper
  implements FieldSetMapper<CommuneSandre> {
  /** Service for Circonscription Bassin. */
  @Autowired @Setter
  private BassinService bassinService;
  /** Map to CirconscriptionBassin from database. */
  private Map<String, CirconscriptionBassin> bassinMap;

  /**
   * Maps CommuneSandre file lines to CommuneSandre Entities.
   * @param fieldSet parsed line from CommuneSandre file.
   * @return the CommuneSandre Entity.
   */
  @Override
  public CommuneSandre mapFieldSet(final FieldSet fieldSet)
    throws BindException {
    log.trace("Importing line: {}", fieldSet.toString());
    CommuneSandre commune = new CommuneSandre();
    commune.setCodeCommune(fieldSet.readString(0));
    commune.setLibelleCommune(fieldSet.readString(1));
    commune.setStatutCommune(fieldSet.readString(2));
    commune.setDateCreationCommune(fieldSet.readDate(3, (Date)null));
    commune.setDateMajCommune(fieldSet.readDate(4));
    commune.setCodeComiteBassin(fieldSet.readString(5));
    commune.setCirconscriptionBassin(getBassin(fieldSet.readString(6)));
    commune.setCodeEuDistrict(fieldSet.readString(7));
    commune.setCodeBassinDce(fieldSet.readString(8));
    return commune;
  }

  /**
   * Returns the CirconscriptionBassin for the given code.
   * @param code the bassin code
   * @return the CirconscriptionBassin.
   */
  protected CirconscriptionBassin getBassin(final String code) {
    if (bassinMap == null) {
      bassinMap = bassinService.getBassinMap();
    }
    return bassinMap.get(code);
  }
}

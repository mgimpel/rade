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

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.file.transform.RangeArrayPropertyEditor;
import org.springframework.validation.BindException;

import fr.aesn.rade.persist.model.Hexaposte;

import org.springframework.batch.item.file.transform.FieldSet;

/**
 * JUnit Test for HexaposteMapper.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestHexaposteMapper {
  /** Field size for tokenizing lines from the Hexaposte file to import. */
  public static final String COLUMNS =
    "1-6,7-11,12-49,50,51,52-89,90-94,95-126,127-131,132,133-142";
  /** Test line from the Hexaposte file to import. */
  public static final String TEST_LINE =
    "2204  01001L ABERGEMENT CLEMENCIAT               0M                                      01400L ABERGEMENT CLEMENCIAT                         ";

  /**
   * Test mapping one line from the Hexaposte file to import.
   */
  @Test
  public void testMapping() {
    FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
    RangeArrayPropertyEditor range = new RangeArrayPropertyEditor();
    range.setAsText(COLUMNS);
    tokenizer.setColumns((Range[])range.getValue());
    FieldSet fieldSet = tokenizer.tokenize(TEST_LINE);
    HexaposteMapper mapper = new HexaposteMapper();
    try {
      Hexaposte hexaposte = mapper.mapFieldSet(fieldSet);
      assertEquals("Entity doesn't match expected value",
                   "2204", hexaposte.getIdentifiant());
      assertEquals("Entity doesn't match expected value",
                   "01001", hexaposte.getCodeInseeCommune());
      assertEquals("Entity doesn't match expected value",
                   "L ABERGEMENT CLEMENCIAT", hexaposte.getLibelleCommune());
      assertEquals("Entity doesn't match expected value",
                   0, hexaposte.getIndicateurPluridistribution().intValue());
      assertEquals("Entity doesn't match expected value",
                   "M", hexaposte.getTypeCodePostal());
      assertEquals("Entity doesn't match expected value",
                   "", hexaposte.getLibelleLigne5());
      assertEquals("Entity doesn't match expected value",
                   "01400", hexaposte.getCodePostal());
      assertEquals("Entity doesn't match expected value",
                   "L ABERGEMENT CLEMENCIAT", hexaposte.getLibelleAcheminement());
      assertEquals("Entity doesn't match expected value",
                   "", hexaposte.getCodeInseeAncienneCommune());
      assertEquals("Entity doesn't match expected value",
                   "", hexaposte.getCodeMaJ());
      assertEquals("Entity doesn't match expected value",
                   "", hexaposte.getCodeEtenduAdresse());
      assertNull("Entity doesn't match expected null value",
                 hexaposte.getAudit());
    } catch (BindException e) {
      fail("Mapper failed to parse test String with BindException: "
           + e.getMessage());
    }
  }
}

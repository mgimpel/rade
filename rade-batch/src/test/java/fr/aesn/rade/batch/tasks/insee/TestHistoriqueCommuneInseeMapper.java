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

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.validation.BindException;

import org.springframework.batch.item.file.transform.FieldSet;

/**
 * JUnit Test for RegionMapper.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestHistoriqueCommuneInseeMapper {
  /** Test line from the INSEE Commune history file to import. */
  public static final String TEST_LINE =
    "01\t\t\t003\tA16-07-1973\t19-08-1973\t01-01-1974\t01-01-1974\t330\t\t\t\t\t01165\t\t\t\t\t\t1\tAmareins\t\t";

  /**
   * Test mapping one line from the Region file to import.
   */
  @Test
  public void testMapping() {
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter("\t");
    FieldSet fieldSet = tokenizer.tokenize(TEST_LINE);
    HistoriqueCommuneInseeMapper mapper = new HistoriqueCommuneInseeMapper();
    try {
      HistoriqueCommuneInseeModel historique = mapper.mapFieldSet(fieldSet);
      assertEquals("Entity doesn't match expected value",
                   "01", historique.getCodeDepartement());
      assertEquals("Entity doesn't match expected value",
                   "", historique.getCodeArrondissement());
      assertEquals("Entity doesn't match expected value",
                   "", historique.getCodeCanton());
      assertEquals("Entity doesn't match expected value",
                   "003", historique.getCodeCommune());
      assertEquals("Entity doesn't match expected value",
                   "A16-07-1973", historique.getTexteLegislative());
      assertEquals("Entity doesn't match expected value",
                   "19-08-1973", historique.getDateJO());
    } catch (BindException e) {
      fail("Mapper failed to parse test String with BindException: "
           + e.getMessage());
    }
  }
}

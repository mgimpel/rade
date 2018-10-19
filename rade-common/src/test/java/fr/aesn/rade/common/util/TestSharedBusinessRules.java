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
package fr.aesn.rade.common.util;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

/**
 * JUnit Test for SharedBusinessRules.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestSharedBusinessRules {
  /**
   * Test a given date is between the start and finish dates.
   * @throws ParseException if SimpleDateFormat cannot parse the given String.
   */
  @Test
  public void testIsBetweenDates() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // null date is always false
    assertFalse(SharedBusinessRules.isBetween(null, null, null));
    assertFalse(SharedBusinessRules.isBetween(sdf.parse("2009-01-01"), null, null));
    assertFalse(SharedBusinessRules.isBetween(null, sdf.parse("2011-01-01"), null));
    assertFalse(SharedBusinessRules.isBetween(sdf.parse("2009-01-01"), sdf.parse("2011-01-01"), null));
    // null start and end date always true
    assertTrue(SharedBusinessRules.isBetween(null, null, sdf.parse("2010-01-01")));
    // null start date - only end date counts
    assertTrue(SharedBusinessRules.isBetween(null, sdf.parse("2011-01-01"), sdf.parse("2010-01-01")));
    assertFalse(SharedBusinessRules.isBetween(null, sdf.parse("2009-01-01"), sdf.parse("2010-01-01")));
    // null end date - only start date counts
    assertTrue(SharedBusinessRules.isBetween(sdf.parse("2009-01-01"), null, sdf.parse("2010-01-01")));
    assertFalse(SharedBusinessRules.isBetween(sdf.parse("2011-01-01"), null, sdf.parse("2010-01-01")));
    // all three dates
    assertTrue(SharedBusinessRules.isBetween(sdf.parse("2009-01-01"), sdf.parse("2011-01-01"), sdf.parse("2010-01-01")));
    assertFalse(SharedBusinessRules.isBetween(sdf.parse("2009-01-01"), sdf.parse("2009-01-01"), sdf.parse("2010-01-01")));
    assertFalse(SharedBusinessRules.isBetween(sdf.parse("2011-01-01"), sdf.parse("2011-01-01"), sdf.parse("2010-01-01")));
  }
}

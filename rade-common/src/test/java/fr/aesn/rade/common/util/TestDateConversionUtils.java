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

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit Test for DateConversionUtils.
 */
public class TestDateConversionUtils {
  /**
   * Test formatStringToDateUrl
   * @throws ParseException if theString could not be parsed into a Date
   */
  @Test
  public void testFormatStringToDateUrl()
    throws ParseException {
    // 1st January at 00h 00m 00s (midnight)
    assertEquals("Error converting URL format String to Date",
                 Date.from(ZonedDateTime.of(2019, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant()),
                 DateConversionUtils.urlStringToDate("2019-01-01"));
    // 31st December at 00h 00m 00s (midnight)
    assertEquals("Error converting URL format String to Date",
                 Date.from(ZonedDateTime.of(2019, 12, 31, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant()),
                 DateConversionUtils.urlStringToDate("2019-12-31"));
  }

  /**
   * Test formatStringToDateUrl Exception
   * @throws ParseException if theString could not be parsed into a Date
   */
  @Test(expected = ParseException.class)
  public void testFormatStringToDateUrlException()
    throws ParseException {
    DateConversionUtils.urlStringToDate("01/01/2019");
  }

  /**
   * Test formatDateToStringUrl
   */
  @Test
  public void testFormatDateToStringUrl() {
    Date date;
    // 28th January at 00h 00m 00s (midnight)
    date = Date.from(ZonedDateTime.of(2019, 1, 28, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant());
    assertEquals("Error converting date to URL format String",
                 "2019-01-28",
                 DateConversionUtils.toUrlString(date));
    // 28th January at 12h 00m 00s (noon)
    date = Date.from(ZonedDateTime.of(2019, 1, 28, 12, 0, 0, 0, ZoneId.systemDefault()).toInstant());
    assertEquals("Error converting date to URL format String",
                 "2019-01-28",
                 DateConversionUtils.toUrlString(date));
    // 28th January at 23h 59m 59s 999999999ns (1 nanosecond to midnight)
    date = Date.from(ZonedDateTime.of(2019, 1, 28, 23, 59, 59, 999999999, ZoneId.systemDefault()).toInstant());
    assertEquals("Error converting date to URL format String",
                 "2019-01-28",
                 DateConversionUtils.toUrlString(date));
  }

  /**
   * Test formatDateToStringUi
   */
  @Test
  public void testFormatDateToStringUi() {
    Date date;
    // 28th January at 00h 00m 00s (midnight)
    date = Date.from(ZonedDateTime.of(2019, 1, 28, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant());
    assertEquals("Error converting date to URL format String",
                 "28/01/2019",
                 DateConversionUtils.toUiString(date));
    // 28th January at 12h 00m 00s (noon)
    date = Date.from(ZonedDateTime.of(2019, 1, 28, 12, 0, 0, 0, ZoneId.systemDefault()).toInstant());
    assertEquals("Error converting date to URL format String",
                 "28/01/2019",
                 DateConversionUtils.toUiString(date));
    // 28th January at 23h 59m 59s 999999999ns (1 nanosecond to midnight)
    date = Date.from(ZonedDateTime.of(2019, 1, 28, 23, 59, 59, 999999999, ZoneId.systemDefault()).toInstant());
    assertEquals("Error converting date to URL format String",
                 "28/01/2019",
                 DateConversionUtils.toUiString(date));
  }
}

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
import java.util.GregorianCalendar;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit Test for DateConversionUtils.
 */
public class TestDateConversionUtils { 
  /**
   * test formatDateToStringUrl
   * @throws java.text.ParseException
   */
  @Test
  public void testFormatDateToStringUrl() throws ParseException {
    String dateString = "2019-01-28";
    GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.of(2019, 1, 28, 0, 0, 0, 0, ZoneId.systemDefault()));
    assertEquals("Erreur lors de la conversion de la chaine de caractère en date", DateConversionUtils.formatDateToStringUrl(gregorianCalendar.getTime()), dateString);
  }
  
  /**
   * test formatStringToDateUrl
   * @throws java.text.ParseException
   */
  @Test
  public void testFormatStringToDateUrl() throws ParseException{
    String dateString = "2019-01-28";
    GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.of(2019, 1, 28, 0, 0, 0, 0, ZoneId.systemDefault()));
    assertEquals("Erreur lors de la conversion de la date en chaine de caractère", DateConversionUtils.formatStringToDateUrl(dateString), gregorianCalendar.getTime());
    dateString = "2019-12-31";
    gregorianCalendar = GregorianCalendar.from(ZonedDateTime.of(2019, 12, 31, 0, 0, 0, 0, ZoneId.systemDefault()));
    assertEquals("Erreur lors de la conversion de la date en chaine de caractère", DateConversionUtils.formatStringToDateUrl(dateString), gregorianCalendar.getTime());
    
  }
  
  /**
   * test formatDateToStringUi
   * @throws java.text.ParseException
   */
  @Test
  public void testFormatDateToStringUi() throws ParseException {
    GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.of(2019, 1, 28, 0, 0, 0, 0, ZoneId.systemDefault()));
    String dateString = "28/01/2019";
    assertEquals("Erreur lors de la conversion de la chaine de caractère en date", DateConversionUtils.formatDateToStringUi(gregorianCalendar.getTime()), dateString);
  }
}

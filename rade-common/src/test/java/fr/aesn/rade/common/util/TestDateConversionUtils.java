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
import java.text.SimpleDateFormat;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit Test for DateConversionUtils.
 */
public class TestDateConversionUtils {
  private static final String FORMAT_URL = "yyyy-MM-dd";
  private static final String FORMAT_UI = "dd/MM/yyyy";
  
  /**
   * test formatDateToStringUrl
   * @throws java.text.ParseException
   */
  @Test
  public void testFormatDateToStringUrl() throws ParseException {
    String dateString = "2019-01-28";
    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_URL);
    String date = DateConversionUtils.formatDateToStringUrl(sdf.parse(dateString));
    assertEquals("Erreur lors de la conversion de la chaine de caractère en date", dateString, date);
  }
  
  /**
   * test formatStringToDateUrl
   * @throws java.text.ParseException
   */
  @Test
  public void testFormatStringToDateUrl() throws ParseException{
    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_URL);
    String dateString = "2019-01-01";
    assertEquals("Erreur lors de la conversion de la date en chaine de caractère", sdf.parse(dateString), DateConversionUtils.formatStringToDateUrl(dateString));
    dateString = "2019-12-31";
    assertEquals("Erreur lors de la conversion de la date en chaine de caractère", sdf.parse(dateString), DateConversionUtils.formatStringToDateUrl(dateString));
  }
  
  /**
   * test formatDateToStringUi
   * @throws java.text.ParseException
   */
  @Test
  public void testFormatDateToStringUi() throws ParseException {
    String dateString = "28/01/2019";
    SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_UI);
    String date = DateConversionUtils.formatDateToStringUi(sdf.parse(dateString));
    assertEquals("Erreur lors de la conversion de la chaine de caractère en date", dateString, date);
  }
}

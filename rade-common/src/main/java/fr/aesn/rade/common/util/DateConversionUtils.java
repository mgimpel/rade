/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2019 Sophie Belin
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
import java.util.Date;

/**
 * Date utilities.
 * @author sophie.belin
 */
public class DateConversionUtils {
  /** Date Format for User Interface. */
  public static final String UI_DATE_FORMAT = "dd/MM/yyyy";
  /** Date Format for URLs. */
  public static final String URL_DATE_FORMAT = "yyyy-MM-dd";

  /**
   * Hidden private Constructor makes the class non-instantiable.
   * Utility classes, which are a collection of static members,
   * are not meant to be instantiated.
   */
  private DateConversionUtils() {
    throw new UnsupportedOperationException(
        "This is a utility class and cannot be instantiated");
  }

  /**
   * Convert the given date to a String that can be use into a URL.
   * @param date Date to format.
   * @return a the formatted date.
   */
  public static String toUrlString(Date date) {
    if(date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(URL_DATE_FORMAT);
    return sdf.format(date);
  }

  /**
   * Convert the given String to a Date.
   * @param date String to format.
   * @return the parsed Date.
   * @throws ParseException if problem parsing the given String.
   */
  public static Date urlStringToDate(String date)
    throws ParseException {
    if(date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(URL_DATE_FORMAT);
    return sdf.parse(date);
  }

  /**
   * Convert the given String to a Date.
   * @param date String to format.
   * @param defaultDate the date to return if the String could not be parsed.
   * @return the parsed Date.
   */
  public static Date urlStringToDate(String date, Date defaultDate) {
    if(date == null) {
      return defaultDate;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(URL_DATE_FORMAT);
    try {
      return sdf.parse(date);
    } catch (ParseException e) {
      return defaultDate;
    }
  }

  /**
   * Convert the given date to a String for use in the UI.
   * @param date Date to format.
   * @return a the formatted date.
   */
  public static String toUiString(Date date) {
    if(date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(UI_DATE_FORMAT);
    return sdf.format(date);
  }
}

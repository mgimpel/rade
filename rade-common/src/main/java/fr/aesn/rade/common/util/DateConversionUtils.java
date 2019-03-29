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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

  /**
   * Convert the given Date to a java.time.LocalDate (i.e. a date without a
   * time-zone in the ISO-8601 calendar system, such as 2007-12-03)
   * @param date Date to convert.
   * @param zoneId the time-zone to use (or null for system default)
   * @return the Date as a java.time.LocalDate
   */
  public static final LocalDate toLocalDate(final Date date,
                                            final ZoneId zoneId) {
    if(date == null) {
      return null;
    }
    if (date instanceof java.sql.Date) {
      // SQL Date has no Time component so cannot be converted to an Instant
      return ((java.sql.Date) date).toLocalDate();
    } else {
      ZoneId zone = (zoneId == null ? ZoneId.systemDefault() : zoneId);
      return date.toInstant().atZone(zone).toLocalDate();
    }
  }

  /**
   * Convert the given Date to a java.time.ZonedDateTime
   * (i.e. a date-time with a time-zone in the ISO-8601 calendar system,
   * such as 2007-12-03T10:15:30+01:00 Europe/Paris)
   * @param date Date to convert.
   * @param zoneId the time-zone to use (or null for system default)
   * @return the Date as a java.time.ZonedDateTime
   */
  public static final ZonedDateTime toZonedDateTime(final Date date,
                                                    final ZoneId zoneId) {
    if(date == null) {
      return null;
    }
    ZoneId zone = (zoneId == null ? ZoneId.systemDefault() : zoneId);
    if (date instanceof java.sql.Date) {
      // SQL Date has no Time component so cannot be converted to an Instant
      return ((java.sql.Date) date).toLocalDate().atStartOfDay(zone);
    } else {
      return date.toInstant().atZone(zone);
    }
  }
}

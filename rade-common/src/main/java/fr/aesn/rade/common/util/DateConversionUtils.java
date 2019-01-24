/*
 * Copyright (C) 2019 sophie.belin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.aesn.rade.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utilities
 * @author sophie.belin
 */
public class DateConversionUtils {
   /**
  * Hidden private Constructor makes the class non-instantiable.
  * Utility classes, which are a collection of static members,
  * are not meant to be instantiated.
  */
  private DateConversionUtils(){
    throw new UnsupportedOperationException(
        "This is a utility class and cannot be instantiated");
  }
  
  /**
   * Convert the given date to a String that can be use into a url
   * @param date Date to format
   * @return a the formatted date
   */
  public static String formatDateToStringUrl(Date date){
    if(date == null){
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  /**
   * Convert the given String to a date
   * @param date String to format
   * @return the parsed Date
   * @throws java.text.ParseException
   */
  public static Date formatStringToDateUrl(String date) throws ParseException{
    if(date == null){
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.parse(date);
  }

  /**
   * Convert the given date to a String that can be use into the IHM
   * @param date Date to format
   * @return a the formatted date
   */
  public static String formatDateToStringIHM(Date date){
    if(date == null){
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    return sdf.format(date);
  }
}

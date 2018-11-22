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

import java.text.Normalizer;

/**
 * String Utilities.
 *
 * Some code was copied from https://www.rgagnon.com/javadetails/java-0456.html
 * Many thanks.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class StringUtils {
  /** List of Unicode characters with accents and such. */
  private static final String UNICODE =
      "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9"
    + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD"
    + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177"
    + "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1"
    + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF"
    + "\u00C5\u00E5"
    + "\u00C7\u00E7"
    + "\u0150\u0151\u0170\u0171"
    + "\u0160\u0161\u017D\u017E"
    ;
  /** List of characters equivalent to UNICODE without accents. */
  private static final String PLAIN_ASCII =
      "AaEeIiOoUu"    // grave
    + "AaEeIiOoUuYy"  // acute
    + "AaEeIiOoUuYy"  // circumflex
    + "AaOoNn"        // tilde
    + "AaEeIiOoUuYy"  // umlaut
    + "Aa"            // ring
    + "Cc"            // cedilla
    + "OoUu"          // double acute
    + "SsZz"          // hacek
    ;
  /** List of Unicode upper case characters with accents and such. */
  private static final String UPPERCASE_ASCII =
      "AEIOU"  // grave
    + "AEIOUY" // acute
    + "AEIOUY" // circumflex
    + "AON"    // tilde
    + "AEIOUY" // umlaut
    + "A"      // ring
    + "C"      // cedilla
    + "OU"     // double acute
    + "SZ"     // hacek
    ;
  /** List of characters equivalent to UPPERCASE_UNICODE without accents. */
  private static final String UPPERCASE_UNICODE =
      "\u00C0\u00C8\u00CC\u00D2\u00D9"
    + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
    + "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176"
    + "\u00C3\u00D5\u00D1"
    + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178"
    + "\u00C5"
    + "\u00C7"
    + "\u0150\u0170"
    + "\u0160\u017D"
    ;

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String.
   * @param s the String to process.
   * @return the String without any accents.
   */
  public static String toAscii(String s) {
    return toAsciiWithNormalizer(s);
  }

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String and convert to upper case.
   * @param s the String to process.
   * @return the upper case String without any accents.
   */
  public static String toUpperAscii(String s) {
    return toUpperAsciiWithLookup(s);
  }

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String.
   *
   * The Normalizer decomposes the original characters into a combination of a
   * base character and a diacritic sign (just the accent). The replaceAll
   * removes all the diacritic signs.
   * @param s the String to process.
   * @return the String without any accents.
   */
  public static String toAsciiWithNormalizer(final String s) {
    return s == null ? null
            : Normalizer.normalize(s, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String and convert to upper case.
   *
   * The Normalizer decomposes the original characters into a combination of a
   * base character and a diacritic sign (just the accent). The replaceAll
   * removes all the diacritic signs.
   * @param s the String to process.
   * @return the upper case String without any accents.
   */
  public static String toUpperAsciiWithNormalizer(final String s) {
    return s == null ? null
            : Normalizer.normalize(s.toUpperCase(), Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
  }

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String.
   * @param s the String to process.
   * @return the String without any accents.
   */
  public static String toAsciiWithReplaceAll(final String s) {
    return s == null ? null
            : s.replaceAll("[áàâäãå]","a")
               .replaceAll("[éèêë]","e")
               .replaceAll("[íìîï]","i")
               .replaceAll("[óòôöőõ]","o")
               .replaceAll("[úùûüű]","u")
               .replaceAll("[ýŷÿ]","y")
               .replaceAll("[ÁÀÂÄÃÅ]","A")
               .replaceAll("[ÉÈÊË]","E")
               .replaceAll("[ÍÌÎÏ]","I")
               .replaceAll("[ÓÒÔÖŐÕ]","O")
               .replaceAll("[ÚÙÛÜŰ]","U")
               .replaceAll("[ÝŶŸ]","Y")
               .replaceAll("ç","c")
               .replaceAll("Ç","C")
               .replaceAll("ñ","n")
               .replaceAll("Ñ","N")
               .replaceAll("š","s")
               .replaceAll("Š","S")
               .replaceAll("ž","z")
               .replaceAll("Ž","Z");
  }

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String and convert to upper case.
   * @param s the String to process.
   * @return the upper case String without any accents.
   */
  public static String toUpperAsciiWithReplaceAll(final String s) {
    return s == null ? null
            : s.toUpperCase()
               .replaceAll("[ÁÀÂÄÃÅ]","A")
               .replaceAll("[ÉÈÊË]","E")
               .replaceAll("[ÍÌÎÏ]","I")
               .replaceAll("[ÓÒÔÖŐÕ]","O")
               .replaceAll("[ÚÙÛÜŰ]","U")
               .replaceAll("[ÝŶŸ]","Y")
               .replaceAll("Ç","C")
               .replaceAll("Ñ","N")
               .replaceAll("Š","S")
               .replaceAll("Ž","Z");
  }

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String.
   * @param s the String to process.
   * @return the String without any accents.
   */
  public static String toAsciiWithLookup(final String s) {
    if (s == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    int n = s.length();
    for (int i = 0; i < n; i++) {
      char c = s.charAt(i);
      int pos = UNICODE.indexOf(c);
      if (pos > -1){
        sb.append(PLAIN_ASCII.charAt(pos));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * Remove accents (acute, grave, circumflex, tilde, umlaut, ...)
   * and such (cedilla) from String and convert to upper case.
   * @param s the String to process.
   * @return the upper case String without any accents.
   */
  public static String toUpperAsciiWithLookup(final String s) {
    if (s == null) {
      return null;
    }
    String sup = s.toUpperCase();
    StringBuilder sb = new StringBuilder();
    int n = sup.length();
    for (int i = 0; i < n; i++) {
      char c = sup.charAt(i);
      int pos = UPPERCASE_UNICODE.indexOf(c);
      if (pos > -1){
        sb.append(UPPERCASE_ASCII.charAt(pos));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * Convert the given String to Unicode Hexadecimal
   * @param s the String to process.
   * @return a String of Hexadecimal digits representing the Unicode for each
   * character.
   */
  public static String toUnicodeHex(final String s) {
    StringBuilder buf = new StringBuilder(200);
    for (char ch: s.toCharArray()) {
      if (buf.length() > 0)
        buf.append(' ');
      buf.append(String.format("%04x", (int) ch));
    }
    return buf.toString();
  }
}

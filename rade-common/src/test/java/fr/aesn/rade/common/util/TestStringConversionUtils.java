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

import org.junit.Test;

/**
 * JUnit Test for StringUtils.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestStringConversionUtils {
  private static final String TEST_VOWELS_FR    = "ÀÂÉÈÊËÎÏÔÙÛàâéèêëîïôùû";
  private static final String RESULT_VOWELS_FR  = "AAEEEEIIOUUaaeeeeiiouu";
  private static final String TEST_VOWELS_ALL   = "ÁÀÂÄÃÅÉÈÊËÍÌÎÏÓÒÔÖŐÕÚÙÛÜŰÝŶŸáàâäãåéèêëíìîïóòôöőõúùûüűýŷÿ";
  private static final String RESULT_VOWELS_ALL = "AAAAAAEEEEIIIIOOOOOOUUUUUYYYaaaaaaeeeeiiiioooooouuuuuyyy";
  private static final String TEST_CONSONANTS_FR    = "Çç";
  private static final String RESULT_CONSONANTS_FR  = "Cc";
  private static final String TEST_CONSONANTS_ALL   = "ÇÑçñ";
  private static final String RESULT_CONSONANTS_ALL = "CNcn";
  private static final String TEST_ISO_8859_1       = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ";
  private static final String RESULT_ISO_8859_1     = "AAAAAAÆCEEEEIIIIÐNOOOOOØUUUUYÞßaaaaaaæceeeeiiiiðnoooooøuuuuyþy";
  private static final String TEST_ISO_8859_15      = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ€ŠšŽžŒœŸ";
  private static final String RESULT_ISO_8859_15    = "AAAAAAÆCEEEEIIIIÐNOOOOOØUUUUYÞßaaaaaaæceeeeiiiiðnoooooøuuuuyþy€SsZzŒœY";
//  private static final String TEST_LIGATURES_FR     = "ÆŒæœ";

  /**
   * Test toAsciiWithNormalizer.
   */
  @Test
  public void testToAsciiWithNormalizer() {
    assertEquals("toAsciiWithNormalizer failed", RESULT_VOWELS_FR,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_VOWELS_FR));
    assertEquals("toAsciiWithNormalizer failed", RESULT_VOWELS_ALL,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_VOWELS_ALL));
    assertEquals("toAsciiWithNormalizer failed", RESULT_CONSONANTS_FR,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_CONSONANTS_FR));
    assertEquals("toAsciiWithNormalizer failed", RESULT_CONSONANTS_ALL,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_CONSONANTS_ALL));
    assertEquals("toAsciiWithNormalizer failed", RESULT_ISO_8859_1,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_ISO_8859_1));
    assertEquals("toAsciiWithNormalizer failed", RESULT_ISO_8859_15,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_ISO_8859_15));
  }

  /**
   * Test toAsciiWithReplaceAll.
   */
  @Test
  public void testToAsciiWithReplaceAll() {
    assertEquals("toAsciiWithReplaceAll failed", RESULT_VOWELS_FR,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_VOWELS_FR));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_VOWELS_ALL,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_VOWELS_ALL));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_CONSONANTS_FR,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_CONSONANTS_FR));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_CONSONANTS_ALL,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_CONSONANTS_ALL));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_ISO_8859_1,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_ISO_8859_1));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_ISO_8859_15,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_ISO_8859_15));
  }

  /**
   * Test toAsciiWithLookup.
   */
  @Test
  public void testToAsciiWithLookup() {
    assertEquals("toAsciiWithLookup failed", RESULT_VOWELS_FR,
                 StringConversionUtils.toAsciiWithLookup(TEST_VOWELS_FR));
    assertEquals("toAsciiWithLookup failed", RESULT_VOWELS_ALL,
                 StringConversionUtils.toAsciiWithLookup(TEST_VOWELS_ALL));
    assertEquals("toAsciiWithLookup failed", RESULT_CONSONANTS_FR,
                 StringConversionUtils.toAsciiWithLookup(TEST_CONSONANTS_FR));
    assertEquals("toAsciiWithLookup failed", RESULT_CONSONANTS_ALL,
                 StringConversionUtils.toAsciiWithLookup(TEST_CONSONANTS_ALL));
    assertEquals("toAsciiWithLookup failed", RESULT_ISO_8859_1,
                 StringConversionUtils.toAsciiWithLookup(TEST_ISO_8859_1));
    assertEquals("toAsciiWithLookup failed", RESULT_ISO_8859_15,
                 StringConversionUtils.toAsciiWithLookup(TEST_ISO_8859_15));
  }

  /**
   * Test toUpperAsciiWithNormalizer.
   */
  @Test
  public void testToUpperAsciiWithNormalizer() {
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_VOWELS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_VOWELS_FR));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_VOWELS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_VOWELS_ALL));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_CONSONANTS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_CONSONANTS_FR));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_CONSONANTS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_CONSONANTS_ALL));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_ISO_8859_1.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_ISO_8859_1));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_ISO_8859_15.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_ISO_8859_15));
  }

  /**
   * Test toUpperAsciiWithReplaceAll.
   */
  @Test
  public void testToUpperAsciiWithReplaceAll() {
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_VOWELS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_VOWELS_FR));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_VOWELS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_VOWELS_ALL));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_CONSONANTS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_CONSONANTS_FR));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_CONSONANTS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_CONSONANTS_ALL));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_ISO_8859_1.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_ISO_8859_1));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_ISO_8859_15.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_ISO_8859_15));
  }

  /**
   * Test toUpperAsciiWithLookup.
   */
  @Test
  public void testToUpperAsciiWithLookup() {
    assertEquals("toUpperAsciiWithLookup failed", RESULT_VOWELS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_VOWELS_FR));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_VOWELS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_VOWELS_ALL));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_CONSONANTS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_CONSONANTS_FR));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_CONSONANTS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_CONSONANTS_ALL));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_ISO_8859_1.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_ISO_8859_1));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_ISO_8859_15.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_ISO_8859_15));
  }

  /**
   * Test toUnicodeHex.
   */
  @Test
  public void testToUnicodeHex() {
    StringBuffer sb = new StringBuffer();
    for (String c : StringConversionUtils.toUnicodeHex(TEST_ISO_8859_15).split(" ")) {
      sb.append((char)Integer.parseInt(c, 16));
    }
    assertEquals("toHex failed", TEST_ISO_8859_15, sb.toString());
  }
}

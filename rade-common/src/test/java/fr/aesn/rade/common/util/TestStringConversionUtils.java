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
  // Accented Characters (to be stripped)
  private static final String TEST_VOWELS_FR    = "ÀÂÉÈÊËÎÏÔÙÛàâéèêëîïôùû";
  private static final String RESULT_VOWELS_FR  = "AAEEEEIIOUUaaeeeeiiouu";
  private static final String TEST_VOWELS_ALL   = "ÁÀÂÄÃÅÉÈÊËÍÌÎÏÓÒÔÖŐÕÚÙÛÜŰÝŶŸáàâäãåéèêëíìîïóòôöőõúùûüűýŷÿ";
  private static final String RESULT_VOWELS_ALL = "AAAAAAEEEEIIIIOOOOOOUUUUUYYYaaaaaaeeeeiiiioooooouuuuuyyy";
  private static final String TEST_CONSONANTS_FR    = "Çç";
  private static final String RESULT_CONSONANTS_FR  = "Cc";
  private static final String TEST_CONSONANTS_ALL   = "ÇÑçñŠšŽž";
  private static final String RESULT_CONSONANTS_ALL = "CNcnSsZz";
  // Character Sets
  private static final String TEST_ISO_8859_1       = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ";
  private static final String RESULT_ISO_8859_1     = "AAAAAAÆCEEEEIIIIÐNOOOOOØUUUUYÞßaaaaaaæceeeeiiiiðnoooooøuuuuyþy";
  private static final String TEST_ISO_8859_15      = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ€ŠšŽžŒœŸ";
  private static final String RESULT_ISO_8859_15    = "AAAAAAÆCEEEEIIIIÐNOOOOOØUUUUYÞßaaaaaaæceeeeiiiiðnoooooøuuuuyþy€SsZzŒœY";
  // Unchanged Characters
  private static final String NUMBER_CHARACTERS      = "0123456789";
  private static final String UNCHANGED_CHARACTERS   = "ÆŒæœÐØÞßðøþ€£$¥@&%#¤§";
  private static final String PUNCTUATION_CHARACTERS = ".,;:!?()[]{}|_+-*/=<>'\\\"";
  private static final String LATIN_ALPHABET         = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
  private static final String GREEK_ALPHABET         = "ΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΡρΣσςΤτΥυΦφΧχΨψΩω";
  private static final String ENGLISH_PANGRAM        = "The quick brown fox jumps over the lazy dog";
  private static final String SERBIAN_PANGRAM        = "Чешће цeђење мрeжастим џаком побољшава фертилизацију генских хибрида";
  private static final String HEBREW_PANGRAM         = "דג סקרן שט בים מאוכזב ולפתע מצא חברה";
  private static final String HINDI_PANGRAM          = "ऋषियों को सताने वाले दुष्ट राक्षसों के राजा रावण का सर्वनाश करने वाले विष्णुवतार भगवान श्रीराम, अयोध्या के महाराज दशरथ के बड़े सपुत्र थे।";
  private static final String JAPANESE_PANGRAM       = "いろはにほへと ちりぬるを わかよたれそ つねならむ うゐのおくやま けふこえて あさきゆめみし ゑひもせす";
  private static final String CHINESE_EXAMPLE        = "微風迎客，軟語伴茶";

  /**
   * Test toAsciiWithNormalizer.
   */
  @Test
  public void testToAsciiWithNormalizer() {
    // Test Accents and Cedilla are removed
    assertEquals("toAsciiWithNormalizer failed", RESULT_VOWELS_FR,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_VOWELS_FR));
    assertEquals("toAsciiWithNormalizer failed", RESULT_VOWELS_ALL,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_VOWELS_ALL));
    assertEquals("toAsciiWithNormalizer failed", RESULT_CONSONANTS_FR,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_CONSONANTS_FR));
    assertEquals("toAsciiWithNormalizer failed", RESULT_CONSONANTS_ALL,
                 StringConversionUtils.toAsciiWithNormalizer(TEST_CONSONANTS_ALL));
    // Test Unchanged Characters
    assertEquals("toAsciiWithNormalizer failed", NUMBER_CHARACTERS,
                 StringConversionUtils.toAsciiWithNormalizer(NUMBER_CHARACTERS));
    assertEquals("toAsciiWithNormalizer failed", UNCHANGED_CHARACTERS,
                 StringConversionUtils.toAsciiWithNormalizer(UNCHANGED_CHARACTERS));
    assertEquals("toAsciiWithNormalizer failed", PUNCTUATION_CHARACTERS,
                 StringConversionUtils.toAsciiWithNormalizer(PUNCTUATION_CHARACTERS));
    assertEquals("toAsciiWithNormalizer failed", LATIN_ALPHABET,
                 StringConversionUtils.toAsciiWithNormalizer(LATIN_ALPHABET));
    assertEquals("toAsciiWithNormalizer failed", GREEK_ALPHABET,
                 StringConversionUtils.toAsciiWithNormalizer(GREEK_ALPHABET));
    assertEquals("toAsciiWithNormalizer failed", ENGLISH_PANGRAM,
                 StringConversionUtils.toAsciiWithNormalizer(ENGLISH_PANGRAM));
    assertEquals("toAsciiWithNormalizer failed", SERBIAN_PANGRAM,
                 StringConversionUtils.toAsciiWithNormalizer(SERBIAN_PANGRAM));
    assertEquals("toAsciiWithNormalizer failed", HEBREW_PANGRAM,
                 StringConversionUtils.toAsciiWithNormalizer(HEBREW_PANGRAM));
    assertEquals("toAsciiWithNormalizer failed", HINDI_PANGRAM,
                 StringConversionUtils.toAsciiWithNormalizer(HINDI_PANGRAM));
    assertEquals("toAsciiWithNormalizer failed", JAPANESE_PANGRAM,
                 StringConversionUtils.toAsciiWithNormalizer(JAPANESE_PANGRAM));
    assertEquals("toAsciiWithNormalizer failed", CHINESE_EXAMPLE,
                 StringConversionUtils.toAsciiWithNormalizer(CHINESE_EXAMPLE));
    // Test Specific Character Sets
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
    // Test Accents and Cedilla are removed
    assertEquals("toAsciiWithReplaceAll failed", RESULT_VOWELS_FR,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_VOWELS_FR));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_VOWELS_ALL,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_VOWELS_ALL));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_CONSONANTS_FR,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_CONSONANTS_FR));
    assertEquals("toAsciiWithReplaceAll failed", RESULT_CONSONANTS_ALL,
                 StringConversionUtils.toAsciiWithReplaceAll(TEST_CONSONANTS_ALL));
    // Test Unchanged Characters
    assertEquals("toAsciiWithReplaceAll failed", NUMBER_CHARACTERS,
                 StringConversionUtils.toAsciiWithReplaceAll(NUMBER_CHARACTERS));
    assertEquals("toAsciiWithReplaceAll failed", UNCHANGED_CHARACTERS,
                 StringConversionUtils.toAsciiWithReplaceAll(UNCHANGED_CHARACTERS));
    assertEquals("toAsciiWithReplaceAll failed", PUNCTUATION_CHARACTERS,
                 StringConversionUtils.toAsciiWithReplaceAll(PUNCTUATION_CHARACTERS));
    assertEquals("toAsciiWithReplaceAll failed", LATIN_ALPHABET,
                 StringConversionUtils.toAsciiWithReplaceAll(LATIN_ALPHABET));
    assertEquals("toAsciiWithReplaceAll failed", GREEK_ALPHABET,
                 StringConversionUtils.toAsciiWithReplaceAll(GREEK_ALPHABET));
    assertEquals("toAsciiWithReplaceAll failed", ENGLISH_PANGRAM,
                 StringConversionUtils.toAsciiWithReplaceAll(ENGLISH_PANGRAM));
    assertEquals("toAsciiWithReplaceAll failed", SERBIAN_PANGRAM,
                 StringConversionUtils.toAsciiWithReplaceAll(SERBIAN_PANGRAM));
    assertEquals("toAsciiWithReplaceAll failed", HEBREW_PANGRAM,
                 StringConversionUtils.toAsciiWithReplaceAll(HEBREW_PANGRAM));
    assertEquals("toAsciiWithReplaceAll failed", HINDI_PANGRAM,
                 StringConversionUtils.toAsciiWithReplaceAll(HINDI_PANGRAM));
    assertEquals("toAsciiWithReplaceAll failed", JAPANESE_PANGRAM,
                 StringConversionUtils.toAsciiWithReplaceAll(JAPANESE_PANGRAM));
    assertEquals("toAsciiWithReplaceAll failed", CHINESE_EXAMPLE,
                 StringConversionUtils.toAsciiWithReplaceAll(CHINESE_EXAMPLE));
    // Test Specific Character Sets
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
    // Test Accents and Cedilla are removed
    assertEquals("toAsciiWithLookup failed", RESULT_VOWELS_FR,
                 StringConversionUtils.toAsciiWithLookup(TEST_VOWELS_FR));
    assertEquals("toAsciiWithLookup failed", RESULT_VOWELS_ALL,
                 StringConversionUtils.toAsciiWithLookup(TEST_VOWELS_ALL));
    assertEquals("toAsciiWithLookup failed", RESULT_CONSONANTS_FR,
                 StringConversionUtils.toAsciiWithLookup(TEST_CONSONANTS_FR));
    assertEquals("toAsciiWithLookup failed", RESULT_CONSONANTS_ALL,
                 StringConversionUtils.toAsciiWithLookup(TEST_CONSONANTS_ALL));
    // Test Unchanged Characters
    assertEquals("toAsciiWithLookup failed", NUMBER_CHARACTERS,
                 StringConversionUtils.toAsciiWithLookup(NUMBER_CHARACTERS));
    assertEquals("toAsciiWithLookup failed", UNCHANGED_CHARACTERS,
                 StringConversionUtils.toAsciiWithLookup(UNCHANGED_CHARACTERS));
    assertEquals("toAsciiWithLookup failed", PUNCTUATION_CHARACTERS,
                 StringConversionUtils.toAsciiWithLookup(PUNCTUATION_CHARACTERS));
    assertEquals("toAsciiWithLookup failed", LATIN_ALPHABET,
                 StringConversionUtils.toAsciiWithLookup(LATIN_ALPHABET));
    assertEquals("toAsciiWithLookup failed", GREEK_ALPHABET,
                 StringConversionUtils.toAsciiWithLookup(GREEK_ALPHABET));
    assertEquals("toAsciiWithLookup failed", ENGLISH_PANGRAM,
                 StringConversionUtils.toAsciiWithLookup(ENGLISH_PANGRAM));
    assertEquals("toAsciiWithLookup failed", SERBIAN_PANGRAM,
                 StringConversionUtils.toAsciiWithLookup(SERBIAN_PANGRAM));
    assertEquals("toAsciiWithLookup failed", HEBREW_PANGRAM,
                 StringConversionUtils.toAsciiWithLookup(HEBREW_PANGRAM));
    assertEquals("toAsciiWithLookup failed", HINDI_PANGRAM,
                 StringConversionUtils.toAsciiWithLookup(HINDI_PANGRAM));
    assertEquals("toAsciiWithLookup failed", JAPANESE_PANGRAM,
                 StringConversionUtils.toAsciiWithLookup(JAPANESE_PANGRAM));
    assertEquals("toAsciiWithLookup failed", CHINESE_EXAMPLE,
                 StringConversionUtils.toAsciiWithLookup(CHINESE_EXAMPLE));
    // Test Specific Character Sets
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
    // Test Accents and Cedilla are removed
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_VOWELS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_VOWELS_FR));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_VOWELS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_VOWELS_ALL));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_CONSONANTS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_CONSONANTS_FR));
    assertEquals("toUpperAsciiWithNormalizer failed", RESULT_CONSONANTS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(TEST_CONSONANTS_ALL));
    // Test Unchanged Characters
    assertEquals("toUpperAsciiWithNormalizer failed", NUMBER_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(NUMBER_CHARACTERS));
    assertEquals("toUpperAsciiWithNormalizer failed", UNCHANGED_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(UNCHANGED_CHARACTERS));
    assertEquals("toUpperAsciiWithNormalizer failed", PUNCTUATION_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(PUNCTUATION_CHARACTERS));
    assertEquals("toUpperAsciiWithNormalizer failed", LATIN_ALPHABET.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(LATIN_ALPHABET));
    assertEquals("toUpperAsciiWithNormalizer failed", GREEK_ALPHABET.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(GREEK_ALPHABET));
    assertEquals("toUpperAsciiWithNormalizer failed", ENGLISH_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(ENGLISH_PANGRAM));
    assertEquals("toUpperAsciiWithNormalizer failed", SERBIAN_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(SERBIAN_PANGRAM));
    assertEquals("toUpperAsciiWithNormalizer failed", HEBREW_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(HEBREW_PANGRAM));
    assertEquals("toUpperAsciiWithNormalizer failed", HINDI_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(HINDI_PANGRAM));
    assertEquals("toUpperAsciiWithNormalizer failed", JAPANESE_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(JAPANESE_PANGRAM));
    assertEquals("toUpperAsciiWithNormalizer failed", CHINESE_EXAMPLE.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithNormalizer(CHINESE_EXAMPLE));
    // Test Specific Character Sets
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
    // Test Accents and Cedilla are removed
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_VOWELS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_VOWELS_FR));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_VOWELS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_VOWELS_ALL));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_CONSONANTS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_CONSONANTS_FR));
    assertEquals("toUpperAsciiWithReplaceAll failed", RESULT_CONSONANTS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(TEST_CONSONANTS_ALL));
    // Test Unchanged Characters
    assertEquals("toUpperAsciiWithReplaceAll failed", NUMBER_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(NUMBER_CHARACTERS));
    assertEquals("toUpperAsciiWithReplaceAll failed", UNCHANGED_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(UNCHANGED_CHARACTERS));
    assertEquals("toUpperAsciiWithReplaceAll failed", PUNCTUATION_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(PUNCTUATION_CHARACTERS));
    assertEquals("toUpperAsciiWithReplaceAll failed", LATIN_ALPHABET.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(LATIN_ALPHABET));
    assertEquals("toUpperAsciiWithReplaceAll failed", GREEK_ALPHABET.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(GREEK_ALPHABET));
    assertEquals("toUpperAsciiWithReplaceAll failed", ENGLISH_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(ENGLISH_PANGRAM));
    assertEquals("toUpperAsciiWithReplaceAll failed", SERBIAN_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(SERBIAN_PANGRAM));
    assertEquals("toUpperAsciiWithReplaceAll failed", HEBREW_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(HEBREW_PANGRAM));
    assertEquals("toUpperAsciiWithReplaceAll failed", HINDI_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(HINDI_PANGRAM));
    assertEquals("toUpperAsciiWithReplaceAll failed", JAPANESE_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(JAPANESE_PANGRAM));
    assertEquals("toUpperAsciiWithReplaceAll failed", CHINESE_EXAMPLE.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithReplaceAll(CHINESE_EXAMPLE));
    // Test Specific Character Sets
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
    // Test Accents and Cedilla are removed
    assertEquals("toUpperAsciiWithLookup failed", RESULT_VOWELS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_VOWELS_FR));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_VOWELS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_VOWELS_ALL));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_CONSONANTS_FR.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_CONSONANTS_FR));
    assertEquals("toUpperAsciiWithLookup failed", RESULT_CONSONANTS_ALL.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(TEST_CONSONANTS_ALL));
    // Test Unchanged Characters
    assertEquals("toUpperAsciiWithLookup failed", NUMBER_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(NUMBER_CHARACTERS));
    assertEquals("toUpperAsciiWithLookup failed", UNCHANGED_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(UNCHANGED_CHARACTERS));
    assertEquals("toUpperAsciiWithLookup failed", PUNCTUATION_CHARACTERS.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(PUNCTUATION_CHARACTERS));
    assertEquals("toUpperAsciiWithLookup failed", LATIN_ALPHABET.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(LATIN_ALPHABET));
    assertEquals("toUpperAsciiWithLookup failed", GREEK_ALPHABET.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(GREEK_ALPHABET));
    assertEquals("toUpperAsciiWithLookup failed", ENGLISH_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(ENGLISH_PANGRAM));
    assertEquals("toUpperAsciiWithLookup failed", SERBIAN_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(SERBIAN_PANGRAM));
    assertEquals("toUpperAsciiWithLookup failed", HEBREW_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(HEBREW_PANGRAM));
    assertEquals("toUpperAsciiWithLookup failed", HINDI_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(HINDI_PANGRAM));
    assertEquals("toUpperAsciiWithLookup failed", JAPANESE_PANGRAM.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(JAPANESE_PANGRAM));
    assertEquals("toUpperAsciiWithLookup failed", CHINESE_EXAMPLE.toUpperCase(),
                 StringConversionUtils.toUpperAsciiWithLookup(CHINESE_EXAMPLE));
    // Test Specific Character Sets
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
    testEncodeDecodeHex(TEST_VOWELS_ALL);
    testEncodeDecodeHex(TEST_CONSONANTS_ALL);
    testEncodeDecodeHex(NUMBER_CHARACTERS);
    testEncodeDecodeHex(UNCHANGED_CHARACTERS);
    testEncodeDecodeHex(PUNCTUATION_CHARACTERS);
    testEncodeDecodeHex(LATIN_ALPHABET);
    testEncodeDecodeHex(GREEK_ALPHABET);
    testEncodeDecodeHex(ENGLISH_PANGRAM);
    testEncodeDecodeHex(SERBIAN_PANGRAM);
    testEncodeDecodeHex(HEBREW_PANGRAM);
    testEncodeDecodeHex(HINDI_PANGRAM);
    testEncodeDecodeHex(JAPANESE_PANGRAM);
    testEncodeDecodeHex(CHINESE_EXAMPLE);
    testEncodeDecodeHex(TEST_ISO_8859_1);
    testEncodeDecodeHex(TEST_ISO_8859_15);
  }

  /**
   * Hex Encode then decode the given String and compare them. 
   * @param test the String to test.
   */
  private void testEncodeDecodeHex(final String test) {
    StringBuffer sb = new StringBuffer();
    for (String c : StringConversionUtils.toUnicodeHex(test).split(" ")) {
      sb.append((char)Integer.parseInt(c, 16));
    }
    assertEquals("toUnicodeHex failed", test, sb.toString());
  }
}

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
package fr.aesn.rade.batch.chunk;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.validation.BindException;

import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.MetadataService;

import org.springframework.batch.item.file.transform.FieldSet;

/**
 * JUnit Test for RegionMapper.
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestRegionMapper {
  /** Test line from the INSEE Region file to import. */
  public static final String TEST_LINE =
    "11\t75056\t1\tILE-DE-FRANCE\tÎle-de-France";

  /** Metadata Service. */
  private static MetadataService metadataService = mock(MetadataService.class);

  /**
   * Set up the Test Environment.
   */
  @BeforeClass
  public static void setUpClass() {
    createServiceMocks();
  }

  /**
   * Build Service Mocks for use in the tests.
   */
  private static void createServiceMocks() {
    Map<String, TypeEntiteAdmin> tea = MapUtils.putAll(new HashMap<String, TypeEntiteAdmin>(),
                                                       new Object[][] {
        {"COM", TypeEntiteAdmin.of("COM", "Commune")},
        {"ARR", TypeEntiteAdmin.of("ARR", "Arrondissement")},
        {"CAN", TypeEntiteAdmin.of("CAN", "Canton")},
        {"DEP", TypeEntiteAdmin.of("DEP", "Département")},
        {"REG", TypeEntiteAdmin.of("REG", "Région")}
    });
    when(metadataService.getTypeEntiteAdminMap()).thenReturn(tea);
    Map<String, TypeNomClair> tncc = MapUtils.putAll(new HashMap<String, TypeNomClair>(),
                                                     new Object[][] {
        {"0", TypeNomClair.of("0", null, "DE", null)},
        {"1", TypeNomClair.of("1", null, "D'", null)},
        {"2", TypeNomClair.of("2", "LE", "DU", null)},
        {"3", TypeNomClair.of("3", "LA", "DE LA", null)},
        {"4", TypeNomClair.of("4", "LES", "DES", null)},
        {"5", TypeNomClair.of("5", "L'", "DE L'", null)},
        {"6", TypeNomClair.of("6", "AUX", "DES", null)},
        {"7", TypeNomClair.of("7", "LAS", "DE LAS", null)},
        {"8", TypeNomClair.of("8", "LOS", "DE LOS", null)}
    });
    when(metadataService.getTypeNomClairMap()).thenReturn(tncc);
  }

  /**
   * Test mapping one line from the Hexaposte file to import.
   */
  @Test
  public void testMapping() {
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter("\t");
    FieldSet fieldSet = tokenizer.tokenize(TEST_LINE);
    RegionMapper mapper = new RegionMapper();
    mapper.setMetadataService(metadataService);
    try {
      Region region = mapper.mapFieldSet(fieldSet);
      assertEquals("", "REG", region.getTypeEntiteAdmin().getCode());
      assertEquals("", "11", region.getCodeInsee());
      assertEquals("", "75056", region.getChefLieu());
      assertEquals("", "1", region.getTypeNomClair().getCode());
      assertEquals("", "ILE-DE-FRANCE", region.getNomMajuscule());
      assertEquals("", "Île-de-France", region.getNomEnrichi());
      assertNull("", region.getArticleEnrichi());
      assertNull("", region.getCommentaire());
      assertNull("", region.getDebutValidite());
      assertNull("", region.getFinValidite());
      assertNull("", region.getAudit());
    } catch (BindException e) {
      fail("Mapper failed to parse test String with BindException: " + e.getMessage());
    }
  }
}

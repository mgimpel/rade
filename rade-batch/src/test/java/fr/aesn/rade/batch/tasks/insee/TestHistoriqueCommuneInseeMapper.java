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
package fr.aesn.rade.batch.tasks.insee;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.validation.BindException;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;

/**
 * JUnit Test for RegionMapper.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestHistoriqueCommuneInseeMapper {
  /** Test line from the INSEE Commune history file to import. */
  public static final String TEST_LINE =
    "01\t\t\t003\tA16-07-1973\t19-08-1973\t01-01-1974\t01-01-1974\t330\t\t\t\t\t01165\t\t\t\t\t\t1\tAmareins\t\t";

  /**
   * Test mapping one line from the Commune History file to import.
   * @throws ParseException failed to parse date.
   * @throws BindException Mapper failed to parse test String.
   */
  @Test
  public void testMapping() throws ParseException, BindException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter("\t");
    FieldSet fieldSet = tokenizer.tokenize(TEST_LINE);
    HistoriqueCommuneInseeMapper mapper = new HistoriqueCommuneInseeMapper();
    HistoriqueCommuneInseeModel historique = mapper.mapFieldSet(fieldSet);
    assertEquals("Entity doesn't match expected value",
                 "01", historique.getCodeDepartement());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getCodeArrondissement());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getCodeCanton());
    assertEquals("Entity doesn't match expected value",
                 "003", historique.getCodeCommune());
    assertEquals("Entity doesn't match expected value",
                 "A16-07-1973", historique.getTexteLegislative());
    assertEquals("Entity doesn't match expected value",
                 "19-08-1973", historique.getDateJO());
    assertEquals("Entity doesn't match expected value",
                 sdf.parse("01-01-1974"), historique.getDateEffet());
    assertEquals("Entity doesn't match expected value",
                 sdf.parse("01-01-1974"), historique.getDatePlusRecente());
    assertEquals("Entity doesn't match expected value",
                 "330", historique.getTypeModification());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getChefLieu());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getAncienChefLieu());
    assertNull("Entity doesn't match expected null value",
               historique.getNombreCommunes());
    assertNull("Entity doesn't match expected null value",
               historique.getRangCommunes());
    assertEquals("Entity doesn't match expected value",
                 "01165", historique.getCommuneEchange());
    assertNull("Entity doesn't match expected null value",
               historique.getPopulationCommuneEchange());
    assertNull("Entity doesn't match expected null value",
               historique.getSurfaceCommuneEchange());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getAncienCommuneChgmtDept());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getAncienArrondissement());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getAncienCanton());
    assertEquals("Entity doesn't match expected value",
                 "1", historique.getTypeNomClair());
    assertEquals("Entity doesn't match expected value",
                 "Amareins", historique.getNomOfficiel());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getAncienTypeNomClair());
    assertEquals("Entity doesn't match expected value",
                 "", historique.getAncienNom());
  }

  /**
   * Test mapping the whole Commune History file to import.
   * @throws Exception problem reading/mapping input file.
   */
  @Test
  public void testMappingFile() throws Exception {
    // Configure and open ItemReader (reading test input file)
    FlatFileItemReader<HistoriqueCommuneInseeModel> reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource("batchfiles/insee/historiq2018.txt"));
    reader.setLinesToSkip(1);
    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter("\t");
    DefaultLineMapper<HistoriqueCommuneInseeModel> lineMapper = new DefaultLineMapper<>();
    lineMapper.setFieldSetMapper(new HistoriqueCommuneInseeMapper());
    lineMapper.setLineTokenizer(tokenizer);
    reader.setLineMapper(lineMapper);
    reader.afterPropertiesSet();
    ExecutionContext ec = new ExecutionContext();
    reader.open(ec);
    // Configure Validator and validate (@Size, @Min, ...) each line
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    HistoriqueCommuneInseeModel record;
    List<HistoriqueCommuneInseeModel> records = new ArrayList<>();
    Set<ConstraintViolation<HistoriqueCommuneInseeModel>> violations;
    while((record = reader.read()) != null) {
      records.add(record);
      violations = validator.validate(record);
      assertEquals("Record violates constraints", 0, violations.size());
    }
    // Check all records from input file have been read
    assertNull(record);
    assertEquals("Didn't read all the file", 16290, records.size());
  }
}

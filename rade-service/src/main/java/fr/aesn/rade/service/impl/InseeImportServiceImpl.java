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
package fr.aesn.rade.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.DepartementJpaDao;
import fr.aesn.rade.persist.dao.RegionJpaDao;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.InseeImportService;
import fr.aesn.rade.service.MetadataService;
import lombok.Cleanup;

/**
 * Service Implementation for Region.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
public class InseeImportServiceImpl
  implements InseeImportService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(InseeImportServiceImpl.class);
  /** Data Access Object for Region. */
  private RegionJpaDao regionJpaDao;
  /** Data Access Object for Departement. */
  private DepartementJpaDao departementJpaDao;
  /** Data Access Object for Commune. */
  private CommuneJpaDao communeJpaDao;
  /** Service for Metadata. */
  private MetadataService metadataService;

/**
   * Empty Constructor for Bean.
   */
  public InseeImportServiceImpl() {
    // Empty Constructor for Bean.
  }

  public void setRegionJpaDao(final RegionJpaDao regionJpaDao) {
    this.regionJpaDao = regionJpaDao;
  }

  public void setDepartementJpaDao(final DepartementJpaDao departementJpaDao) {
    this.departementJpaDao = departementJpaDao;
  }

  public void setCommuneJpaDao(final CommuneJpaDao communeJpaDao) {
    this.communeJpaDao = communeJpaDao;
  }

  public void setMetadataService(final MetadataService metadataService) {
    this.metadataService = metadataService;
  }

  /**
   * 
   * @param regionList parsed list from INSEE file reg2018.txt or whatever year.
   * @param debutValidite date that the given data is valid from.
   */
  //@Override
  @Transactional(readOnly = false)
  public void importRegion(List<String[]> regionList, Date debutValidite) {
    // Verifier les intitules des colonnes
    String[] line = regionList.get(0);
    if (line.length != 5 ||
        "REGION".equals(line[0]) ||   // Code région
        "CHEFLIEU".equals(line[1]) || // Chef-lieu de région
        "TNCC".equals(line[2]) ||     // Type de nom en clair
        "NCC".equals(line[3]) ||      // Nom en clair (majuscules)
        "NCCENR".equals(line[4])) {   // Nom en clair (typographie riche)
      log.warn("File does not seem to correspond to INSEE region file");
    }
    Map<String, TypeNomClair> tncc = metadataService.getTypeNomClairMap();
    Map<String, TypeEntiteAdmin> tea = metadataService.getTypeEntiteAdminMap();
    Audit audit = new Audit();
    //audit.setId(id);
    audit.setAuteur("");
    audit.setDate(new Date());
    audit.setNote("Import par le Service " + getClass().getName());
    for (int i=1; i<regionList.size(); i++) {
      line = regionList.get(i);
      Region region = new Region();
      //region.setId(id);
      region.setTypeEntiteAdmin(tea.get("REG"));
      region.setCodeInsee(line[0]);
      region.setChefLieu(line[1]);
      region.setTypeNomClair(tncc.get(line[2]));
      region.setNomMajuscule(line[4]);
      region.setNomEnrichi(line[5]);
      region.setDebutValidite(debutValidite);
      region.setAudit(audit);
      regionJpaDao.save(region);
    }
  }

  public static List<String[]> tabSeparatedValueToList(final byte[] bytes) {
    String str = new String(bytes, StandardCharsets.UTF_8);
    List<String[]> list = null;
    try {
      @Cleanup StringReader sr = new StringReader(str);
      @Cleanup BufferedReader br = new BufferedReader(sr);
      list = tabSeparatedValueToList(br);
    }     
    catch (IOException e) {
        log.warn("Should never happen", e);
    }
    return list;
  }

  public static List<String[]> tabSeparatedValueToList(final BufferedReader reader)
    throws IOException {
    String line;
    List<String[]> list = new ArrayList<>();
    while ((line = reader.readLine()) != null ) {
      list.add(line.split("\t", -1));
    }
    return list;
  }
}

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.common.modelplus.CommunePlusWithGenealogie;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.CommuneSandreJpaDao;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.CommuneSandre;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.service.CommunePlusService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for Commune.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor @Slf4j
public class CommunePlusServiceImpl
  implements CommunePlusService {
  /** Data Access Object for Commune. */
  @Autowired @Setter
  private CommuneJpaDao communeJpaDao;
  /** Data Access Object for CommuneSandre. */
  @Autowired @Setter
  private CommuneSandreJpaDao communeSandreJpaDao;

  /**
   * List all Commune valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Commune.
   */
  @Override
  @Transactional(readOnly = true)
  public List<CommunePlus> getAllCommune(final Date date) {
    log.debug("Commune list requested for Date: date={}", date);
    Date testdate = (date == null ? new Date() : date);
    List<Commune> listInsee = communeJpaDao.findAllValidOnDate(testdate);
    List<CommuneSandre> listSandre = communeSandreJpaDao.findAllValidOnDate(testdate);
    HashMap<String, CommuneSandre> map = new HashMap<>(listSandre.size());
    for (CommuneSandre item : listSandre) {
      map.put(item.getCodeCommune(), item);
    }
    String code;
    CommunePlus commune;
    CommuneSandre sandre;
    List<CommunePlus> result = new ArrayList<>(listInsee.size());
    for (Commune item : listInsee) {
      if (item != null) {
        code = item.getCodeInsee();
        try {
          commune = new CommunePlus(code, testdate);
          commune.setCommuneInsee(item);
          sandre = map.get(code);
          if (sandre != null) {
            commune.setCommuneSandre(sandre);
          }
          result.add(commune);
        }
        catch (InvalidArgumentException e) {
          log.warn("Error assembling CommunePlus for Commune {} on {}", code, testdate, e);
        }
      }
    }
    return result;
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public CommunePlus getCommuneByCode(final String code, final Date date) {
    log.debug("Commune requested by code and date: code={}, date={}", code, date);
    if (code == null) {
      return null;
    }
    Date testdate = (date == null ? new Date() : date);
    Commune insee = communeJpaDao.findByCodeInseeValidOnDate(code, testdate);
    if (insee == null) {
      return null;
    }
    CommuneSandre sandre = communeSandreJpaDao.findByCodeInseeValidOnDate(code, testdate);
    CommunePlus result = new CommunePlus(code, testdate);
    try {
      result.setCommuneInsee(insee);
      if (sandre != null) {
        result.setCommuneSandre(sandre);
      }
    }
    catch (InvalidArgumentException e) {
      log.warn("Error assembling CommunePlus for Commune {} on {}", code, testdate, e);
    }
    return result;
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public CommunePlus getCommuneByCode(final String code, final String date) {
    log.debug("Commune requested by code and date: code={}, date={}", code, date);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return getCommuneByCode(code, sdf.parse(date));
    } catch (ParseException e) {
      log.warn("Commune requested by code and date: Exception parsing date {}", date, e);
      return null;
    }
  }
  
  /**
   * Returns a List of all Commune from the given codeInsee, departement,
   * region, circonscription, commune name and/or date.
   * @param codeInsee the code of the Communes.
   * @param codeDept the departement of the Communes.
   * @param codeBassin the circonscription of the Communes.
   * @param codeRegion the region of the Communes.
   * @param nomCommune a pattern to search for Communes with a name resembling.
   * @param dateEffet the date at which the Communes were valid.
   * @return a List of all Commune matching the given parameters.
   */
  @Override
  @Transactional(readOnly = true)
  public List<CommunePlusWithGenealogie> getCommuneByCriteria(String codeInsee,
                                            String codeDept,
                                            String codeBassin,
                                            String codeRegion,
                                            String nomCommune,
                                            Date dateEffet){
    List<Commune> communes = null;
    codeInsee = codeInsee == null || codeInsee.isEmpty() ? "%" : codeInsee;
    nomCommune = nomCommune == null || nomCommune.isEmpty() ? "%" : "%" + nomCommune + "%";
    
    if(codeRegion == null || codeRegion.isEmpty()){
      codeDept = codeDept == null || codeDept.isEmpty() ? "%" : codeDept;   
    }
        
    if(dateEffet == null){
      // Si la région est renseignée et que le département n'est pas renseigné : priorité du département sur la région
      // pendant une recherche
      if((codeDept == null || codeDept.isEmpty()) && (codeRegion != null && !codeRegion.isEmpty())){
        communes = communeJpaDao.findByCodeInseeLikeAndRegionLikeAndNomEnrichiLikeIgnoreCase(codeInsee, codeRegion, nomCommune);
      }else{
        communes = communeJpaDao.findByCodeInseeLikeAndDepartementLikeAndAndNomEnrichiLikeIgnoreCase(codeInsee, codeDept, nomCommune);
      }
    }else{
      if((codeDept == null || codeDept.isEmpty()) && (codeRegion != null && !codeRegion.isEmpty())){
        communes = communeJpaDao.findByCodeInseeLikeAndRegionLikeAndNomEnrichiLikeIgnoreCaseValidOnDate(codeInsee, codeRegion, nomCommune, dateEffet);
        
      }else{
        communes = communeJpaDao.findByCodeInseeLikeAndDepartementLikeAndNomEnrichiLikeIgnoreCaseValidOnDate(codeInsee, codeDept, nomCommune, dateEffet);
      }
    }

    // Recherche des communes sandre correspondant à chaque commune afin de créer une liste de
    // communePlus
    List<CommunePlusWithGenealogie> communesPlus = new ArrayList<>();

    for(Commune commune : communes){   
      try {
          CommunePlus cp = new CommunePlus(commune.getCodeInsee(), commune.getDebutValidite());
          cp.setCommuneInsee(commune);
          CommuneSandre communeSandre = null;
          
          // Recherche sur la commune sandre en fonction du code bassin passé en paramètre
          if(codeBassin != null && !codeBassin.isEmpty()){
            communeSandre = communeSandreJpaDao.findByCodeInseeValidOnDate(commune.getCodeInsee(), commune.getDebutValidite());
            
            try {
              cp.setCommuneSandre(communeSandre);
            } catch (InvalidArgumentException ex) {
              log.info("La commune sandre n'est pas valide : " + cp.getCodeInsee()+ "/" + cp.getFinValiditeCommuneInsee(), ex);
            }
          }
            
          if(codeBassin == null || communeSandre != null){
            communesPlus.add(new CommunePlusWithGenealogie(cp));
           }
        } catch (InvalidArgumentException ex) {
            log.info("La commune insee n'est pas valide : " + commune.getCodeInsee(), ex);
        }
    }
    return communesPlus;
  }
  
  
  /**
   * Get the Commune with the given code at the given date, and all it's
   * genealogie.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date, and all it's
   * genealogie.
   */
  @Override
  @Transactional(readOnly = true)
  public CommunePlusWithGenealogie getCommuneWithGenealogie(final String code,
                                                            final Date date) {
    CommunePlus commune = getCommuneByCode(code, date);
    if (commune == null) {
      return null;
    }
    CommunePlusWithGenealogie result = new CommunePlusWithGenealogie(commune);
    EntiteAdministrative tempEntity;
    Set<GenealogieEntiteAdmin> parents = commune.getParentsInsee();
    if (parents != null) {
      for (GenealogieEntiteAdmin parent : parents) {
        tempEntity = parent.getParentEnfant().getParent();
        assert "COM".equals(tempEntity.getTypeEntiteAdmin().getCode());
        try {
          result.addParent(parent.getTypeGenealogie(),
                           communeJpaDao.findById(tempEntity.getId()).get());
        } catch (InvalidArgumentException e) {
          log.warn("This should never happen! parent must exist: {}", parent);
        }
      }
    }
    Set<GenealogieEntiteAdmin> enfants = commune.getEnfantsInsee();
    if (enfants != null) {
      for (GenealogieEntiteAdmin enfant : enfants) {
        tempEntity = enfant.getParentEnfant().getParent();
        assert "COM".equals(tempEntity.getTypeEntiteAdmin().getCode());
        try {
          result.addParent(enfant.getTypeGenealogie(),
                           communeJpaDao.findById(tempEntity.getId()).get());
        } catch (InvalidArgumentException e) {
          log.warn("This should never happen! enfant must exist: {}", enfant);
        }
      }
    }

    return result;
  }
}

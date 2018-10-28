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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.GenealogieEntiteAdminJpaDao;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin.ParentEnfant;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.CommuneService;
import fr.aesn.rade.service.MetadataService;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Service Implementation for Commune.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor
public class CommuneServiceImpl
  implements CommuneService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(CommuneServiceImpl.class);
  /** Data Access Object for Commune. */
  @Autowired @Setter
  private CommuneJpaDao communeJpaDao;
  @Autowired @Setter
  private GenealogieEntiteAdminJpaDao genealogieEntiteAdminJpaDao;
  @Autowired @Setter
  private MetadataService metadataService;

  /**
   * List all Commune.
   * @return a List of all the Commune.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Commune> getAllCommune() {
    log.debug("Commune list requested");
    return communeJpaDao.findAll();
  }

  /**
   * List all Commune valid at the given date.
   * @param date the date at which the code was valid
   * @return a List of all the Commune.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Commune> getAllCommune(final Date date) {
    log.debug("Commune list requested for Date: date={}", date);
    return communeJpaDao.findAllValidOnDate(date);
  }

  /**
   * Returns a Map of all Commune indexed by ID.
   * @return a Map of all Commune indexed by ID.
   */
  @Override
  public Map<Integer, Commune> getCommuneMap() {
    log.debug("Commune map requested");
    List<Commune> list = getAllCommune();
    HashMap<Integer, Commune> map = new HashMap<>(list.size());
    for (Commune item : list) {
      map.put(item.getId(), item);
    }
    return map;
  }

  /**
   * Returns a Map of all Commune valid at the given date and indexed by code.
   * @param date the date at which the Commune are valid.
   * @return a Map of all Commune indexed by code INSEE.
   */
  @Override
  public Map<String, Commune> getCommuneMap(final Date date) {
    log.debug("Commune map requested for Date: date={}", date);
    List<Commune> list = getAllCommune(date);
    HashMap<String, Commune> map = new HashMap<>(list.size());
    for (Commune item : list) {
      map.put(item.getCodeInsee(), item);
    }
    return map;
  }

  /**
   * Get the Commune with the given ID.
   * @param id the Commune ID.
   * @return the Commune with the given ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Commune getCommuneById(final int id) {
    log.debug("Commune requested by ID: ID={}", id);
    Optional<Commune> result = communeJpaDao.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Get the Commune with the given code.
   * @param code the Commune code.
   * @return list of Communes that have historically had the given code.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Commune> getCommuneByCode(final String code) {
    log.debug("Commune requested by code: code={}", code);
    return communeJpaDao.findByCodeInsee(code);
    /*
    // Can also be done by using an Example:
    Commune criteria = new Commune();
    criteria.setCodeInsee(code);
    Example<Commune> example = Example.of(criteria);
    return communeJpaDao.findAll(example);
    */
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public Commune getCommuneByCode(final String code, final Date date) {
    log.debug("Commune requested by code and date: code={}, date={}", code, date);
    return communeJpaDao.findByCodeInseeValidOnDate(code, date);
  }

  /**
   * Get the Commune with the given code at the given date.
   * @param code the Commune code.
   * @param date the date at which the code was valid
   * @return the Commune with the given code at the given date.
   */
  @Override
  @Transactional(readOnly = true)
  public Commune getCommuneByCode(final String code, final String date) {
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
   * Invalidates the given commune by setting the communes finValidite
   * field to the given date.
   * @param commune the commune to invalidate.
   * @param date the date of end of validity for the commune.
   * @return the now invalidated commune.
   */
  @Override
  @Transactional(readOnly = false)
  public Commune invalidateCommune(final Commune commune,
                                   final Date date) {
    log.debug("Invalidate Commune requested: commune={}, date={}", commune, date);
    if ((commune == null) || (date == null)) {
      return null;
    }
    Commune oldCommune = getCommuneById(commune.getId());
    if (!(commune.equals(oldCommune))
        || (oldCommune.getFinValidite() != null)) {
      // given commune has other changes
      return null;
    }
    if (!(date.after(oldCommune.getDebutValidite()))) {
      // given end of validity if before commune beginning of validity
      return null;
    }
    oldCommune.setFinValidite(date);
    return communeJpaDao.save(oldCommune);
  }

  /**
   * Changes the name (MOD=100 : Changement de Nom) of the Commune with the
   * given CodeInsee effective as of the given Date.
   * @param codeInsee the code of Commune to change.
   * @param dateEffective the date that the change takes effect.
   * @param tnccoff the type of the official new name.
   * @param nccoff the official new name.
   * @param audit audit details about change.
   * @param commentaire comment for the genealogie link.
   * @return the new Commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public Commune mod100ChangementdeNom(final String codeInsee,
                                       final Date dateEffective,
                                       final String tnccoff,
                                       final String nccoff,
                                       final Audit audit,
                                       final String commentaire)
    throws InvalidArgumentException {
    log.info("Mod=100 (Changement de nom) requested: commune={}, date={}", codeInsee, dateEffective);
    if (codeInsee == null || dateEffective == null || tnccoff == null || nccoff == null || audit == null) {
      throw new InvalidArgumentException("A mandatory argument was null");
    }
    Commune oldCommune = getCommuneByCode(codeInsee, dateEffective);
    if ((oldCommune == null)) {
      throw new InvalidArgumentException("There is no Commune with the given codeInsee valid at the dateEffective");
    }
    if (oldCommune.getFinValidite() != null) {
      throw new InvalidArgumentException("The Commune has already been invalidated");
    }
    // invalidate old commune
    oldCommune.setFinValidite(dateEffective);
    Commune parent = communeJpaDao.save(oldCommune);
    // create new commune
    Commune newCommune = new Commune();
    newCommune.setTypeEntiteAdmin(metadataService.getTypeEntiteAdmin("COM"));
    newCommune.setCodeInsee(codeInsee);
    newCommune.setDebutValidite(dateEffective);
    newCommune.setAudit(audit);
    newCommune.setNomEnrichi(nccoff);
    newCommune.setNomMajuscule(nccoff.toUpperCase()); //TODO check this works
    TypeNomClair tncc = metadataService.getTypeNomClair(tnccoff);
    newCommune.setTypeNomClair(tncc);
    if (tncc.getArticleMaj() != null) {
      newCommune.setArticleEnrichi(StringUtils.capitalize(tncc.getArticleMaj().toLowerCase())); //TODO check this
    }
    newCommune.setCommentaire("");
    newCommune.setDepartement(oldCommune.getDepartement());
    Commune enfant = communeJpaDao.save(newCommune);
    // add genealogie
    ParentEnfant parentEnfant = new ParentEnfant();
    parentEnfant.setParent(parent);
    parentEnfant.setEnfant(enfant);
    GenealogieEntiteAdmin genealogie = new GenealogieEntiteAdmin();
    genealogie.setParentEnfant(parentEnfant);
    genealogie.setCommentaire(commentaire);
    genealogie.setTypeGenealogie(metadataService.getTypeGenealogieEntiteAdmin("100"));
    genealogieEntiteAdminJpaDao.save(genealogie);
    return getCommuneById(enfant.getId());
  }

  /**
   * Creates (MOD=200 : Creation) a new Commune with the given CodeInsee and
   * details, effective as of the given Date.
   * @param codeInsee the code of the new Commune.
   * @param dateEffective the date that the change takes effect.
   * @param departement the departement to which the new Commune belongs.
   * @param tnccoff the type of the official name.
   * @param nccoff the official name.
   * @param audit audit details about change.
   * @param commentaire comment for the new Commune.
   * @return the new Commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public Commune mod200Creation(final String codeInsee,
                                final Date dateEffective,
                                final String departement,
                                final String tnccoff,
                                final String nccoff,
                                final Audit audit,
                                final String commentaire)
    throws InvalidArgumentException {
    log.info("Mod=200 (Creation) requested: commune={}, date={}", codeInsee, dateEffective);
    if (codeInsee == null || dateEffective == null || departement == null || tnccoff == null || nccoff == null || audit == null) {
      throw new InvalidArgumentException("A mandatory argument was null");
    }
    Commune oldCommune = getCommuneByCode(codeInsee, dateEffective);
    if ((oldCommune != null)) {
      throw new InvalidArgumentException("There is already a Commune with the given codeInsee valid at the dateEffective");
    }
    // create new commune
    Commune newCommune = new Commune();
    newCommune.setTypeEntiteAdmin(metadataService.getTypeEntiteAdmin("COM"));
    newCommune.setCodeInsee(codeInsee);
    newCommune.setDebutValidite(dateEffective);
    newCommune.setAudit(audit);
    newCommune.setNomEnrichi(nccoff);
    newCommune.setNomMajuscule(nccoff.toUpperCase()); //TODO check this works
    TypeNomClair tncc = metadataService.getTypeNomClair(tnccoff);
    newCommune.setTypeNomClair(tncc);
    if (tncc.getArticleMaj() != null) {
      newCommune.setArticleEnrichi(StringUtils.capitalize(tncc.getArticleMaj().toLowerCase())); //TODO check this
    }
    newCommune.setCommentaire(commentaire == null ? "" : commentaire);
    newCommune.setDepartement(departement);
    return communeJpaDao.save(newCommune);
  }
}

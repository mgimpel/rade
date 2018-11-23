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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import fr.aesn.rade.common.InvalidArgumentException;
import fr.aesn.rade.common.util.StringConversionUtils;
import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.GenealogieEntiteAdminJpaDao;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin.ParentEnfant;
import fr.aesn.rade.persist.model.TypeGenealogieEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.CommuneService;
import fr.aesn.rade.service.MetadataService;
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
public class CommuneServiceImpl
  implements CommuneService {
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
   * Returns a List of all Commune from the given departement, resembling the
   * given name and valid at the given date.
   * @param dept the departement of the Communes.
   * @param nameLike a pattern to search for Communes with a name resembling.
   * @param date the date at which the Communes were valid.
   * @return a List of all Commune matching the given parameters.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Commune> getAllCommune(String dept, String nameLike, Date date){
    log.debug("Commune list requested for Date, Department and Name: date={}, departement={}, name like={}",
              date, dept, nameLike);
    if (StringUtils.isEmpty(dept) && StringUtils.isEmpty(nameLike))
      return communeJpaDao.findAllValidOnDate(date);
    else if(!StringUtils.isEmpty(dept) && StringUtils.isEmpty(nameLike))
      return communeJpaDao.findByDepartementValidOnDate(dept, date);
    else if(StringUtils.isEmpty(dept) && !StringUtils.isEmpty(nameLike))
      return communeJpaDao.findByNameLikeValidOnDate(nameLike, date);
    else if(!StringUtils.isEmpty(dept) && !StringUtils.isEmpty(nameLike))
      return communeJpaDao.findByDepartementAndNameLikeValidOnDate(dept, nameLike, date);
    return null;
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
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param codeInsee the code of Commune to change.
   * @param tnccoff the type of the official new name.
   * @param nccoff the official new name.
   * @param commentaire comment for the genealogie link.
   * @return the new Commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public Commune mod100ChangementdeNom(final Date dateEffective,
                                       final Audit audit,
                                       final String codeInsee,
                                       final String tnccoff,
                                       final String nccoff,
                                       final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException(
              "The date and audit are mandatory.");
    }
    if (codeInsee == null || tnccoff == null || nccoff == null) {
      throw new InvalidArgumentException(
              "A mandatory Commune detail was null");
    }
    log.info("Mod=100 (Changement de nom) requested: commune={}, date={}",
             codeInsee, dateEffective);
    // invalidate old commune
    Commune parent = invalidateCommune(codeInsee, dateEffective);
    // create new commune
    Commune newCommune = buildCommune(codeInsee,
                                      parent.getDepartement(),
                                      dateEffective,
                                      tnccoff,
                                      nccoff,
                                      null,
                                      null);
    newCommune.setAudit(audit);
    Commune enfant = communeJpaDao.save(newCommune);
    // add genealogie
    buildGenealogie(parent, enfant, "100", commentaire);
    return getCommuneById(enfant.getId());
  }

  /**
   * Creates (MOD=200 : Creation) a new Commune with the given CodeInsee and
   * details, effective as of the given Date.
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param codeInsee the code of the new Commune.
   * @param departement the departement to which the new Commune belongs.
   * @param tnccoff the type of the official name.
   * @param nccoff the official name.
   * @param commentaire comment for the new Commune.
   * @return the new Commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public Commune mod200Creation(final Date dateEffective,
                                final Audit audit,
                                final String codeInsee,
                                final String departement,
                                final String tnccoff,
                                final String nccoff,
                                final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException(
              "The date and audit are mandatory.");
    }
    if (codeInsee == null || departement == null || tnccoff == null || nccoff == null) {
      throw new InvalidArgumentException(
              "A mandatory Commune detail was null");
    }
    Commune commune = getCommuneByCode(codeInsee, dateEffective);
    if ((commune != null)) {
      throw new InvalidArgumentException(
              "There is already a Commune with the given codeInsee valid at the dateEffective");
    }
    log.info("Mod=200 (Creation) requested: date={}, code commune={}",
             dateEffective, codeInsee);
    // create new commune
    Commune newCommune = buildCommune(codeInsee,
                                      departement,
                                      dateEffective,
                                      tnccoff,
                                      nccoff,
                                      null,
                                      commentaire);
    newCommune.setAudit(audit);
    return communeJpaDao.save(newCommune);
  }

  /**
   * Recreates (MOD=210 : Retablissement, MOD=230 : Commune se separant) the
   * given Commune from the given source Commune, effective as of the given
   * Date.
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param com210retabli the new Commune.
   * @param com230source the source Commune.
   * @param commentaire comment for the genealogie link.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public void mod210x230Retablissement(final Date dateEffective,
                                       final Audit audit,
                                       final Commune com210retabli,
                                       final Commune com230source,
                                       final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException(
              "The date and audit are mandatory.");
    }
    if (com210retabli == null || com210retabli.getCodeInsee() == null
            || com210retabli.getDepartement() == null
            || com210retabli.getTypeNomClair() == null
            || com210retabli.getNomEnrichi() == null) {
      throw new InvalidArgumentException(
              "A mandatory detail for Commune retabli (210) was null.");
    }
    if (com210retabli.getId() != null) {
      throw new InvalidArgumentException(
              "Commune rétabli has an ID already set.");
    }
    if (!dateEffective.equals(com210retabli.getDebutValidite())) {
      throw new InvalidArgumentException(
              "Commune rétabli is not valid from the give date.");
    }
    Commune commune = communeJpaDao.findByCodeInseeValidOnDate(com210retabli.getCodeInsee(), dateEffective);
    if ((commune != null)) {
      throw new InvalidArgumentException(
              "Commune rétabli already exists for the given date.");
    }
    if (com230source == null || com230source.getCodeInsee() == null
            || com230source.getDepartement() == null
            || com230source.getTypeNomClair() == null
            || com230source.getNomEnrichi() == null) {
      throw new InvalidArgumentException(
              "A mandatory Commune detail for Commune source (230) was null.");
    }
    log.info("Mod=210-230 (Retablissement) requested: date={}, code commune 210={}, code commune 230={}",
             dateEffective, com210retabli.getCodeInsee(), com230source.getCodeInsee());
    // update source commune (230).
    Commune parentSource = invalidateCommune(com230source.getCodeInsee(), dateEffective);
    com230source.setId(null);
    com230source.setDebutValidite(dateEffective);
    if (com230source.getNomMajuscule() == null) {
      com230source.setNomMajuscule(StringConversionUtils.toUpperAsciiWithLookup(com230source.getNomEnrichi()));
    }
    if (com230source.getArticleEnrichi() == null) {
      com230source.setArticleEnrichi(com230source.getTypeNomClair().getArticle());
    }
    if (com230source.getCommentaire() == null) {
        com230source.setCommentaire("");
    }
    com230source.setAudit(audit);
    Commune enfantSource = communeJpaDao.save(com230source);
    buildGenealogie(parentSource, enfantSource, "230", commentaire);
    // create new commune retabli
    com210retabli.setAudit(audit);
    if (com210retabli.getNomMajuscule() == null) {
      com210retabli.setNomMajuscule(StringConversionUtils.toUpperAsciiWithLookup(com210retabli.getNomEnrichi()));
    }
    if (com210retabli.getArticleEnrichi() == null && com210retabli.getTypeNomClair().getArticleMaj() != null) {
      com210retabli.setArticleEnrichi(com210retabli.getTypeNomClair().getArticle());
    }
    Commune enfantRetabli = communeJpaDao.save(com210retabli);
    // add genealogie
    buildGenealogie(parentSource, enfantRetabli, "210", commentaire);
  }

  /**
   * Merges (MOD=310 : Fusion Commune absorbe, MOD=320 : Fusion Commune
   * absorbante) the given Communes, effective as of the given Date.
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param com310absorbe list of absorbed Commune.
   * @param com320absorbant the absorbing Commune.
   * @param commentaire comment for the genealogie link.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public void mod310x320Fusion(final Date dateEffective,
                               final Audit audit,
                               final List<Commune> com310absorbe,
                               final Commune com320absorbant,
                               final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException(
              "The date and audit are mandatory.");
    }
    if (com310absorbe == null || com310absorbe.isEmpty()) {
      throw new InvalidArgumentException(
              "Commune absorbé list cannot be null or empty.");
    }
    if (com320absorbant == null || com320absorbant.getCodeInsee() == null
            || com320absorbant.getDepartement() == null
            || com320absorbant.getTypeNomClair() == null
            || com320absorbant.getNomEnrichi() == null) {
      throw new InvalidArgumentException(
              "A mandatory detail for Commune absorbant (320) was null.");
    }
    if (com320absorbant.getId() != null) {
      throw new InvalidArgumentException(
              "Commune absorbant has an ID already set.");
    }
    if (!dateEffective.equals(com320absorbant.getDebutValidite())) {
      throw new InvalidArgumentException(
              "Commune absorbant is not valid from the give date.");
    }
    log.info("Mod=310-320 (Fusion) requested: date={}, code commune 320={}",
             dateEffective, com320absorbant.getCodeInsee());
    // create new Commune absorbant
    Commune parentAbsorbant = invalidateCommune(com320absorbant.getCodeInsee(), dateEffective);
    com320absorbant.setAudit(audit);
    if (com320absorbant.getNomMajuscule() == null) {
      com320absorbant.setNomMajuscule(StringConversionUtils.toUpperAsciiWithLookup(com320absorbant.getNomEnrichi()));
    }
    if (com320absorbant.getArticleEnrichi() == null
            && com320absorbant.getTypeNomClair().getArticleMaj() != null) {
      com320absorbant.setArticleEnrichi(com320absorbant.getTypeNomClair().getArticle());
    }
    Commune enfantAbsorbant = communeJpaDao.save(com320absorbant);
    buildGenealogie(parentAbsorbant, enfantAbsorbant, "320", commentaire);
    Commune parentAbsorbe;
    // invalidate all Commune absorbe
    for (Commune commune : com310absorbe) {
      if (com320absorbant.getCodeInsee().equals(commune.getCodeInsee())) {
        log.trace("Already invalidated and genealogised commune: {}", com320absorbant.getCodeInsee());
      } else {
        parentAbsorbe = invalidateCommune(commune.getCodeInsee(), dateEffective);
        buildGenealogie(parentAbsorbe, enfantAbsorbant, "320", commentaire);
      }
    }
  }

  /**
   * Merges (MOD=330 : Fusion-association Commune associee, MOD=340 : Fusion-
   * association Commune absorbante) the given Communes, effective as of the
   * given Date.
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param com330associe list of absorbed Commune.
   * @param com340absorbant the absorbing Commune.
   * @param commentaire comment for the genealogie link.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public void mod330x340FusionAssociation(final Date dateEffective,
                                          final Audit audit,
                                          final List<Commune> com330associe,
                                          final Commune com340absorbant,
                                          final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException(
              "The date and audit are mandatory.");
    }
    if (com330associe == null || com330associe.isEmpty()) {
      throw new InvalidArgumentException(
              "Commune absorbé list cannot be null or empty.");
    }
    if (com340absorbant == null || com340absorbant.getCodeInsee() == null
            || com340absorbant.getDepartement() == null
            || com340absorbant.getTypeNomClair() == null
            || com340absorbant.getNomEnrichi() == null) {
      throw new InvalidArgumentException(
              "A mandatory detail for Commune absorbant (340) was null.");
    }
    if (com340absorbant.getId() != null) {
      throw new InvalidArgumentException(
              "Commune absorbant has an ID already set.");
    }
    if (!dateEffective.equals(com340absorbant.getDebutValidite())) {
      throw new InvalidArgumentException(
              "Commune absorbant is not valid from the give date.");
    }
    log.info("Mod=330-340 (Fusion-Association) requested: date={}, code commune 340={}",
             dateEffective, com340absorbant.getCodeInsee());
    // create new Commune absorbant
    Commune parentAbsorbant = invalidateCommune(com340absorbant.getCodeInsee(), dateEffective);
    com340absorbant.setAudit(audit);
    if (com340absorbant.getNomMajuscule() == null) {
      com340absorbant.setNomMajuscule(StringConversionUtils.toUpperAsciiWithLookup(com340absorbant.getNomEnrichi()));
    }
    if (com340absorbant.getArticleEnrichi() == null
            && com340absorbant.getTypeNomClair().getArticleMaj() != null) {
      com340absorbant.setArticleEnrichi(com340absorbant.getTypeNomClair().getArticle());
    }
    Commune enfantAbsorbant = communeJpaDao.save(com340absorbant);
    buildGenealogie(parentAbsorbant, enfantAbsorbant, "340", commentaire);
    Commune parentAbsorbe;
    // invalidate all Commune associe
    for (Commune commune : com330associe) {
      if (com340absorbant.getCodeInsee().equals(commune.getCodeInsee())) {
        log.trace("Already invalidated and genealogised commune: {}", com340absorbant.getCodeInsee());
      } else {
        parentAbsorbe = invalidateCommune(commune.getCodeInsee(), dateEffective);
        buildGenealogie(parentAbsorbe, enfantAbsorbant, "340", commentaire);
      }
    }
  }

  /**
   * Merges (MOD=311 : Commune nouvelle non deleguee, MOD=321 : Commune
   * nouvelle sans deleguee) the given Communes, effective as of the given
   * Date.
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param com311 list of absorbed Commune.
   * @param com321nouvelle the new/absorbing Commune.
   * @param commentaire comment for the genealogie link.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public void mod311x321FusionSansDeleguee(final Date dateEffective,
                                           final Audit audit,
                                           final List<Commune> com311,
                                           final Commune com321nouvelle,
                                           final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException(
              "The date and audit are mandatory.");
    }
    if (com311 == null || com311.isEmpty()) {
      throw new InvalidArgumentException(
              "Commune absorbé list cannot be null or empty.");
    }
    if (com321nouvelle == null || com321nouvelle.getCodeInsee() == null
            || com321nouvelle.getDepartement() == null
            || com321nouvelle.getTypeNomClair() == null
            || com321nouvelle.getNomEnrichi() == null) {
      throw new InvalidArgumentException(
              "A mandatory detail for Commune absorbant (321) was null.");
    }
    if (com321nouvelle.getId() != null) {
      throw new InvalidArgumentException(
              "Commune absorbant has an ID already set.");
    }
    if (!dateEffective.equals(com321nouvelle.getDebutValidite())) {
      throw new InvalidArgumentException(
              "Commune absorbant is not valid from the give date.");
    }
    log.info("Mod=311-321 (Fusion sans déléguée) requested: date={}, code commune 321={}",
            dateEffective, com321nouvelle.getCodeInsee());
    // create new Commune absorbant
    Commune parentAbsorbant = invalidateCommune(com321nouvelle.getCodeInsee(), dateEffective);
    com321nouvelle.setAudit(audit);
    if (com321nouvelle.getNomMajuscule() == null) {
      com321nouvelle.setNomMajuscule(StringConversionUtils.toUpperAsciiWithLookup(com321nouvelle.getNomEnrichi()));
    }
    if (com321nouvelle.getArticleEnrichi() == null
            && com321nouvelle.getTypeNomClair().getArticleMaj() != null) {
      com321nouvelle.setArticleEnrichi(com321nouvelle.getTypeNomClair().getArticle());
    }
    Commune enfantAbsorbant = communeJpaDao.save(com321nouvelle);
    buildGenealogie(parentAbsorbant, enfantAbsorbant, "321", commentaire);
    Commune parentAbsorbe;
    // invalidate all Commune associe
    for (Commune commune : com311) {
      if (com321nouvelle.getCodeInsee().equals(commune.getCodeInsee())) {
        log.trace("Already invalidated and genealogised commune: {}", com321nouvelle.getCodeInsee());
      } else {
        parentAbsorbe = invalidateCommune(commune.getCodeInsee(), dateEffective);
        buildGenealogie(parentAbsorbe, enfantAbsorbant, "321", commentaire);
      }
    }
  }

  /**
   * Merges (MOD=331,332,333,311,312 : Commune absorbe, MOD=341 : Commune
   * nouvelle avec deleguee) the given Communes, effective as of the given
   * Date.
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param com331x332x333 list of absorbed Commune.
   * @param com341nouvelle the new/absorbing Commune.
   * @param commentaire comment for the genealogie link.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public void mod331x332x333x341FusionAvecDeleguee(final Date dateEffective,
                                                   final Audit audit,
                                                   final List<Commune> com331x332x333,
                                                   final Commune com341nouvelle,
                                                   final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException(
              "The date and audit are mandatory.");
    }
    if (com331x332x333 == null || com331x332x333.isEmpty()) {
      throw new InvalidArgumentException(
              "Commune absorbé list cannot be null or empty.");
    }
    if (com341nouvelle == null || com341nouvelle.getCodeInsee() == null
            || com341nouvelle.getDepartement() == null
            || com341nouvelle.getTypeNomClair() == null
            || com341nouvelle.getNomEnrichi() == null) {
      throw new InvalidArgumentException(
              "A mandatory detail for Commune absorbant (341) was null.");
    }
    if (com341nouvelle.getId() != null) {
      throw new InvalidArgumentException(
              "Commune absorbant has an ID already set.");
    }
    if (!dateEffective.equals(com341nouvelle.getDebutValidite())) {
      throw new InvalidArgumentException(
              "Commune absorbant is not valid from the give date.");
    }
    log.info("Mod=3xx-341 (Fusion avec déléguée) requested: date={}, code commune 341={}",
             dateEffective, com341nouvelle.getCodeInsee());
    // create new Commune absorbant
    Commune parentAbsorbant = invalidateCommune(com341nouvelle.getCodeInsee(), dateEffective);
    com341nouvelle.setAudit(audit);
    if (com341nouvelle.getNomMajuscule() == null) {
      com341nouvelle.setNomMajuscule(StringConversionUtils.toUpperAsciiWithLookup(com341nouvelle.getNomEnrichi()));
    }
    if (com341nouvelle.getArticleEnrichi() == null
            && com341nouvelle.getTypeNomClair().getArticleMaj() != null) {
      com341nouvelle.setArticleEnrichi(com341nouvelle.getTypeNomClair().getArticle());
    }
    Commune enfantAbsorbant = communeJpaDao.save(com341nouvelle);
    buildGenealogie(parentAbsorbant, enfantAbsorbant, "341", commentaire);
    Commune parentAbsorbe;
    // invalidate all Commune associe
    String mod;
    for (Commune commune : com331x332x333) {
      mod = commune.getCommentaire();
      if (com341nouvelle.getCodeInsee().equals(commune.getCodeInsee())) {
        log.trace("Already invalidated and genealogised commune: {}", com341nouvelle.getCodeInsee());
      } else if (mod != null && ("MOD=332".equals(mod) || "MOD=333".equals(mod))) {
        log.trace("No need to invalidate commune: {}", commune);
      } else {
        parentAbsorbe = invalidateCommune(commune.getCodeInsee(), dateEffective);
        buildGenealogie(parentAbsorbe, enfantAbsorbant, "341", commentaire);
      }
    }
  }

  @Override
  @Transactional(readOnly = false)
  public void mod350x360FusionAssociationSimple(final Date dateEffective,
                                                final Audit audit)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException("The date and audit are mandatory.");
    }
    log.info("Mod=350-360 (Fusion-Association simple) requested: date={}", dateEffective);
    // process change
    //TODO
  }

  @Override
  @Transactional(readOnly = false)
  public void mod351CommuneNouvelle(final Date dateEffective,
                                    final Audit audit)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException("The date and audit are mandatory.");
    }
    log.info("Mod=351 (Commune Nouvelle) requested: date={}", dateEffective);
    // process change
    //TODO
  }

  /**
   * Changes the departement (MOD=411 : Changement de departement) that the
   * Commune belongs to (NB: this involves changing it's codeInsee).
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param codeInsee the new code of the Commune.
   * @param departement the new departement to which the Commune belongs.
   * @param oldCodeInsee the old code for the Commune.
   * @param commentaire comment for the genealogie link.
   * @return the new Commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  @Override
  @Transactional(readOnly = false)
  public Commune mod411ChangementDept(final Date dateEffective,
                                      final Audit audit,
                                      final String codeInsee,
                                      final String departement,
                                      final String oldCodeInsee,
                                      final String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException("The date and audit are mandatory.");
    }
    if (codeInsee == null || departement == null || oldCodeInsee == null) {
      throw new InvalidArgumentException(
              "A mandatory detail was null");
    }
    Commune commune = getCommuneByCode(codeInsee, dateEffective);
    if ((commune != null)) {
      throw new InvalidArgumentException(
              "There is already a Commune with the given codeInsee valid at the dateEffective");
    }
    log.info("Mod=411 (Changement de Departement) requested: date={}, new code commune={}, old code commune={}",
             dateEffective, codeInsee, oldCodeInsee);
    // invalidate old commune
    Commune parent = invalidateCommune(oldCodeInsee, dateEffective);
    // create new commune
    Commune newCommune = buildCommune(codeInsee,
                                      departement,
                                      dateEffective,
                                      parent.getTypeNomClair(),
                                      parent.getNomEnrichi(),
                                      parent.getNomMajuscule(),
                                      null);
    newCommune.setAudit(audit);
    Commune enfant = communeJpaDao.save(newCommune);
    // add genealogie
    buildGenealogie(parent, enfant, "411", commentaire);
    return getCommuneById(enfant.getId());
  }

  /**
   * Removes the Commune (MOD=XXX : custom code).
   * @param dateEffective the date that the change takes effect.
   * @param audit audit details about change.
   * @param codeInsee the code of the Commune to remove.
   * @param commentaire comment.
   * @return the invalidated commune.
   * @throws InvalidArgumentException if an invalid argument has been passed.
   */
  public Commune modX30Supression(Date dateEffective, Audit audit,
                                  String codeInsee, String commentaire)
    throws InvalidArgumentException {
    // validate arguments
    if (dateEffective == null || audit == null) {
      throw new InvalidArgumentException("The date and audit are mandatory.");
    }
    if (codeInsee == null) {
      throw new InvalidArgumentException("The codeInsee is mandatory.");
    }
    Commune commune = getCommuneByCode(codeInsee, dateEffective);
    if ((commune == null)) {
      throw new InvalidArgumentException(
              "There is no Commune with the given codeInsee valid at the dateEffective");
    }
    log.info("Mod=XXX (Suppression) requested: date={}, code commune={}",
             dateEffective, codeInsee);
    // invalidate old commune
    return invalidateCommune(codeInsee, dateEffective);
  }

  /**
   * Create a Genealogic link between the given Communes.
   * @param parent the parent.
   * @param enfant the child.
   * @param type the type of Genealogic link.
   * @param commentaire a comment.
   * @return the newly created GenealogieEntiteAdmin Object.
   */
  private GenealogieEntiteAdmin buildGenealogie(final Commune parent,
                                                final Commune enfant,
                                                final String type,
                                                final String commentaire)
    throws InvalidArgumentException {
    return buildGenealogie(parent,
                           enfant,
                           metadataService.getTypeGenealogieEntiteAdmin(type),
                           commentaire);
  }

  /**
   * Create a Genealogic link between the given Communes.
   * @param parent the parent.
   * @param enfant the child.
   * @param type the type of Genealogic link.
   * @param commentaire a comment.
   * @return the newly created GenealogieEntiteAdmin Object.
   */
  private GenealogieEntiteAdmin buildGenealogie(final Commune parent,
                                                final Commune enfant,
                                                final TypeGenealogieEntiteAdmin type,
                                                final String commentaire)
    throws InvalidArgumentException {
    if (parent == null || enfant == null || type == null) {
      throw new InvalidArgumentException("The parent, child and type are mandatory.");
    }
    ParentEnfant parentEnfant = new ParentEnfant();
    parentEnfant.setParent(parent);
    parentEnfant.setEnfant(enfant);
    GenealogieEntiteAdmin genealogie = new GenealogieEntiteAdmin();
    genealogie.setParentEnfant(parentEnfant);
    genealogie.setCommentaire(commentaire);
    genealogie.setTypeGenealogie(type);
    return genealogieEntiteAdminJpaDao.save(genealogie);
  }

  private Commune buildCommune(final String codeInsee,
          final String departement,
          final Date debutValidite,
          final String tncc,
          final String nomEnrichi,
          final String nomMajuscule,
          final String commentaire)
    throws InvalidArgumentException {
    return buildCommune(codeInsee,
                        departement,
                        debutValidite,
                        metadataService.getTypeNomClair(tncc),
                        nomEnrichi,
                        nomMajuscule,
                        commentaire);
  }

  private Commune buildCommune(final String codeInsee,
                               final String departement,
                               final Date debutValidite,
                               final TypeNomClair tncc,
                               final String nomEnrichi,
                               final String nomMajuscule,
                               final String commentaire)
    throws InvalidArgumentException {
    if (codeInsee == null || codeInsee.length() != 5) {
      throw new InvalidArgumentException("codeInsee is invalid: " + codeInsee);
    }
    if (departement == null || !codeInsee.startsWith(departement)) {
      throw new InvalidArgumentException(
              "departement is invalid: " + departement);
    }
    if (tncc == null || nomEnrichi == null) {
      throw new InvalidArgumentException(
              "The TypeNomClair and nomEnrichi are mandatory.");
    }
    Commune commune = new Commune();
    commune.setTypeEntiteAdmin(metadataService.getTypeEntiteAdmin("COM"));
    commune.setCodeInsee(codeInsee);
    commune.setDepartement(departement);
    commune.setDebutValidite(debutValidite);
    commune.setTypeNomClair(tncc);
    commune.setArticleEnrichi(tncc.getArticle());
    commune.setNomEnrichi(nomEnrichi);
    commune.setNomMajuscule(nomMajuscule == null ? StringConversionUtils.toUpperAscii(nomEnrichi)
                                                 : nomMajuscule);
    commune.setCommentaire(commentaire == null ? ""
                                               : commentaire);
    return commune;
  }

  private Commune invalidateCommune(final String codeInsee, final Date dateEffective)
    throws InvalidArgumentException {
    if (codeInsee == null || dateEffective == null) {
      throw new InvalidArgumentException(
              "The codeInsee and date are mandatory.");
    }
    Commune commune = communeJpaDao.findByCodeInseeValidOnDate(codeInsee, dateEffective);
    if ((commune == null)) {
      throw new InvalidArgumentException(
              "There is no Commune with the given codeInsee (" + codeInsee
              + ") valid at the dateEffective ("+ dateEffective + ")");
    }
    if (commune.getFinValidite() != null) {
      throw new InvalidArgumentException(
              "The Commune has already been invalidated");
    }
    if (commune.getDebutValidite() != null
            && commune.getDebutValidite().after(dateEffective)) {
      throw new InvalidArgumentException(
              "The Commune is invalidated before first valid: "
              + commune.getDebutValidite());
    }
    commune.setFinValidite(dateEffective);
    return communeJpaDao.save(commune);
  }
}

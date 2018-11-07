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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.dao.StatutModificationJpaDao;
import fr.aesn.rade.persist.dao.TypeEntiteAdminJpaDao;
import fr.aesn.rade.persist.dao.TypeGenealogieEntiteAdminJpaDao;
import fr.aesn.rade.persist.dao.TypeNomClairJpaDao;
import fr.aesn.rade.persist.model.StatutModification;
import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeGenealogieEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.MetadataService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for Metadata.
 *
 * The Metadata managed by this Service include:
 * <ul>
 *   <li>TypeNomClair</li>
 *   <li>TypeEntiteAdmin</li>
 *   <li>TypeGenealogieEntiteAdmin</li>
 *   <li>StatutModification</li>
 * </ul>
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor @Slf4j
public class MetadataServiceImpl
  implements MetadataService {
  /** Data Access Object for TypeEntiteAdmin. */
  @Autowired @Setter
  private TypeEntiteAdminJpaDao typeEntiteAdminJpaDao;
  /** Data Access Object for TypeGenealogieEntiteAdmin. */
  @Autowired @Setter
  private TypeGenealogieEntiteAdminJpaDao typeGenealogieEntiteAdminJpaDao;
  /** Data Access Object for TypeNomClair. */
  @Autowired @Setter
  private TypeNomClairJpaDao typeNomClairJpaDao;
  /** Data Access Object for StatutModification. */
  @Autowired @Setter
  private StatutModificationJpaDao statutModificationJpaDao;

  /**
   * Gets the TypeNomClair for the given code.
   * @param code the code of the TypeNomClair.
   * @return the TypeNomClair for the given code.
   */
  @Override
  @Transactional(readOnly = true)
  public TypeNomClair getTypeNomClair(final String code) {
    Optional<TypeNomClair> result = typeNomClairJpaDao.findById(code);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Gets a list of all the TypeNomClair.
   * @return a list of all the TypeNomClair.
   */
  @Override
  @Transactional(readOnly = true)
  public List<TypeNomClair> getTypeNomClairList() {
    log.debug("TypeNomClair list requested");
    return typeNomClairJpaDao.findAll();
  }

  /**
   * Gets a map of all the TypeNomClair indexed by the code.
   * @return a map of all the TypeNomClair indexed by the code.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<String, TypeNomClair> getTypeNomClairMap() {
    log.debug("TypeNomClair map requested");
    List<TypeNomClair> list = getTypeNomClairList();
    HashMap<String, TypeNomClair> map = new HashMap<>(list.size());
    for (TypeNomClair tncc : list) {
      map.put(tncc.getCode(), tncc);
    }
    return map;
  }

  /**
   * Gets the TypeEntiteAdmin for the given code.
   * @param code the code of the TypeEntiteAdmin.
   * @return the TypeEntiteAdmin for the given code.
   */
  @Override
  @Transactional(readOnly = true)
  public TypeEntiteAdmin getTypeEntiteAdmin(final String code) {
    Optional<TypeEntiteAdmin> result = typeEntiteAdminJpaDao.findById(code);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Gets a list of all the TypeEntiteAdmin.
   * @return a list of all the TypeEntiteAdmin.
   */
  @Override
  @Transactional(readOnly = true)
  public List<TypeEntiteAdmin> getTypeEntiteAdminList() {
    log.debug("TypeEntiteAdmin list requested");
    return typeEntiteAdminJpaDao.findAll();
  }

  /**
   * Gets a map of all the TypeEntiteAdmin indexed by the code.
   * @return a map of all the TypeEntiteAdmin indexed by the code.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<String, TypeEntiteAdmin> getTypeEntiteAdminMap() {
    log.debug("TypeEntiteAdmin map requested");
    List<TypeEntiteAdmin> list = getTypeEntiteAdminList();
    HashMap<String, TypeEntiteAdmin> map = new HashMap<>(list.size());
    for (TypeEntiteAdmin tea : list) {
      map.put(tea.getCode(), tea);
    }
    return map;
  }

  /**
   * Gets the TypeGenealogieEntiteAdmin for the given code.
   * @param code the code of the TypeGenealogieEntiteAdmin.
   * @return the TypeGenealogieEntiteAdmin for the given code.
   */
  @Override
  @Transactional(readOnly = true)
  public TypeGenealogieEntiteAdmin getTypeGenealogieEntiteAdmin(final String code) {
    Optional<TypeGenealogieEntiteAdmin> result = typeGenealogieEntiteAdminJpaDao.findById(code);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Gets a list of all the TypeGenealogieEntiteAdmin.
   * @return a list of all the TypeGenealogieEntiteAdmin.
   */
  @Override
  @Transactional(readOnly = true)
  public List<TypeGenealogieEntiteAdmin> getTypeGenealogieEntiteAdminList() {
    log.debug("TypeGenealogieEntiteAdmin list requested");
    return typeGenealogieEntiteAdminJpaDao.findAll();
  }

  /**
   * Gets a map of all the TypeGenealogieEntiteAdmin indexed by the code.
   * @return a map of all the TypeGenealogieEntiteAdmin indexed by the code.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<String, TypeGenealogieEntiteAdmin> getTypeGenealogieEntiteAdminMap() {
    log.debug("TypeGenealogieEntiteAdmin map requested");
    List<TypeGenealogieEntiteAdmin> list = getTypeGenealogieEntiteAdminList();
    HashMap<String, TypeGenealogieEntiteAdmin> map = new HashMap<>(list.size());
    for (TypeGenealogieEntiteAdmin tgea : list) {
      map.put(tgea.getCode(), tgea);
    }
    return map;
  }

  /**
   * Gets the StatutModification for the given code.
   * @param code the code of the StatutModification.
   * @return the StatutModification for the given code.
   */
  @Override
  @Transactional(readOnly = true)
  public StatutModification getStatutModification(final String code) {
    Optional<StatutModification> result = statutModificationJpaDao.findById(code);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Gets a list of all the StatutModification.
   * @return a list of all the StatutModification.
   */
  @Override
  @Transactional(readOnly = true)
  public List<StatutModification> getStatutModificationList() {
    log.debug("StatutModification list requested");
    return statutModificationJpaDao.findAll();
  }

  /**
   * Gets a map of all the StatutModification indexed by the code.
   * @return a map of all the StatutModification indexed by the code.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<String, StatutModification> getStatutModificationMap() {
    log.debug("StatutModification map requested");
    List<StatutModification> list = getStatutModificationList();
    HashMap<String, StatutModification> map = new HashMap<>(list.size());
    for (StatutModification sm : list) {
      map.put(sm.getCode(), sm);
    }
    return map;
  }
}

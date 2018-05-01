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

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.model.StatutModification;
import fr.aesn.rade.persist.model.TypeEntiteAdmin;
import fr.aesn.rade.persist.model.TypeGenealogieEntiteAdmin;
import fr.aesn.rade.persist.model.TypeNomClair;
import fr.aesn.rade.service.MetadataService;

/**
 * Service Implementation for Metadata.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
public class MetadataServiceImpl
  implements MetadataService {
  /** SLF4J Logger. */
  private static final Logger logger =
    LoggerFactory.getLogger(MetadataServiceImpl.class);
  /** Entity Manager. */
  private final EntityManager em;

  /**
   * Creates a Service to manage Metadata.
   * The Metadata managed by this Service include:
   * <ul>
   *   <li>TypeNomClair</li>
   *   <li>TypeEntiteAdmin</li>
   *   <li>TypeGenealogieEntiteAdmin</li>
   *   <li>StatutModification</li>
   * </ul>
   * @param em JPA Entity Manager (must not be null).
   */
  public MetadataServiceImpl(final EntityManager em) {
    this.em = em;
  }

  /**
   * Gets the TypeNomClair for the given code.
   * @param code the code of the TypeNomClair.
   * @return the TypeNomClair for the given code.
   */
  public TypeNomClair getTypeNomClair(final String code) {
    return em.find(TypeNomClair.class, code);
  }

  /**
   * Gets a list of all the TypeNomClair.
   * @return a list of all the TypeNomClair.
   */
  public List<TypeNomClair> getTypeNomClairList() {
    logger.info("TypeNomClair list requested");
    return em.createQuery("FROM TypeNomClair", TypeNomClair.class).getResultList();
  }

  /**
   * Gets a map of all the TypeNomClair indexed by the code.
   * @return a map of all the TypeNomClair indexed by the code.
   */
  public Map<String, TypeNomClair> getTypeNomClairMap() {
    logger.info("TypeNomClair map requested");
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
  public TypeEntiteAdmin getTypeEntiteAdmin(final String code) {
    return em.find(TypeEntiteAdmin.class, code);
  }

  /**
   * Gets a list of all the TypeEntiteAdmin.
   * @return a list of all the TypeEntiteAdmin.
   */
  public List<TypeEntiteAdmin> getTypeEntiteAdminList() {
    logger.info("TypeEntiteAdmin list requested");
    return em.createQuery("FROM TypeEntiteAdmin", TypeEntiteAdmin.class).getResultList();
  }

  /**
   * Gets a map of all the TypeEntiteAdmin indexed by the code.
   * @return a map of all the TypeEntiteAdmin indexed by the code.
   */
  public Map<String, TypeEntiteAdmin> getTypeEntiteAdminMap() {
    logger.info("TypeEntiteAdmin map requested");
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
  public TypeGenealogieEntiteAdmin getTypeGenealogieEntiteAdmin(final String code) {
    return em.find(TypeGenealogieEntiteAdmin.class, code);
  }

  /**
   * Gets a list of all the TypeGenealogieEntiteAdmin.
   * @return a list of all the TypeGenealogieEntiteAdmin.
   */
  public List<TypeGenealogieEntiteAdmin> getTypeGenealogieEntiteAdminList() {
    logger.info("TypeGenealogieEntiteAdmin list requested");
    return em.createQuery("FROM TypeGenealogieEntiteAdmin", TypeGenealogieEntiteAdmin.class).getResultList();
  }

  /**
   * Gets a map of all the TypeGenealogieEntiteAdmin indexed by the code.
   * @return a map of all the TypeGenealogieEntiteAdmin indexed by the code.
   */
  public Map<String, TypeGenealogieEntiteAdmin> getTypeGenealogieEntiteAdminMap() {
    logger.info("TypeGenealogieEntiteAdmin map requested");
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
  public StatutModification getStatutModification(final String code) {
    return em.find(StatutModification.class, code);
  }

  /**
   * Gets a list of all the StatutModification.
   * @return a list of all the StatutModification.
   */
  public List<StatutModification> getStatutModificationList() {
    logger.info("StatutModification list requested");
    return em.createQuery("FROM StatutModification", StatutModification.class).getResultList();
  }

  /**
   * Gets a map of all the StatutModification indexed by the code.
   * @return a map of all the StatutModification indexed by the code.
   */
  public Map<String, StatutModification> getStatutModificationMap() {
    logger.info("StatutModification map requested");
    List<StatutModification> list = getStatutModificationList();
    HashMap<String, StatutModification> map = new HashMap<>(list.size());
    for (StatutModification sm : list) {
      map.put(sm.getCode(), sm);
    }
    return map;
  }
}

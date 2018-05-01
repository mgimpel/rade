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
package fr.aesn.rade.ws.aramis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.Delegation;
import fr.aesn.rade.persist.model.Departement;

/**
 * Mapper class to map Entities (from the service package) to VOs (defined in the WSDL).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class Entity2VoMapper {
  /** Logger */
  private static final Logger logger = Logger.getLogger(Entity2VoMapper.class.getName());
  /** Singleton instance. */
  private static final Entity2VoMapper instance = new Entity2VoMapper();

  /**
   * Private constructor to hide the implicit public one.
   * Utility classes, which are a collection of static members,
   * are not meant to be instantiated.
   */
  private Entity2VoMapper() {
    // Private constructor to hide the implicit public one.
  }

  /**
   * Returns Singleton instance of Entity2VoMapper.
   * @return Singleton instance of Entity2VoMapper.
   */
  public static final Entity2VoMapper getInstance() {
    return instance;
  }

  /**
   * Entity to VO mapping.
   * @param source Entity to Map.
   * @return VO mapped by Entity.
   */
  public static final DepartementVO departementEntity2Vo(Departement source) {
    logger.log(Level.FINE,"Mapping {}", source); // Debug
    if (source == null) {
      return null;
    }
    DepartementVO dest = new DepartementVO();
    dest.setNumero(source.getCodeInsee());
    dest.setNom(source.getNomEnrichi());
    return dest;
  }

  /**
   * Entity to VO List mapping.
   * @param sources Entity List to Map.
   * @return VO List mapped by Entity.
   */
  public static final List<DepartementVO> departementEntity2VoList(List<Departement> sources) {
    logger.log(Level.FINE,"Mapping List {}", sources); // Debug
    if (sources == null) {
      return null;
    }
    List<DepartementVO> dest = new ArrayList<DepartementVO>(sources.size());
    for (Departement source : sources) {
      dest.add(departementEntity2Vo(source));
    }
    return dest;
  }

  /**
   * Entity to VO mapping.
   * @param source Entity to Map.
   * @return VO mapped by Entity.
   */
  public static final CommuneVO communeEntity2Vo(Commune source) {
    logger.log(Level.FINE,"Mapping {}", source); // Debug
    if (source == null) {
      return null;
    }
    CommuneVO dest = new CommuneVO();
    dest.setNomCommune(source.getNomEnrichi());
    dest.setNomCourt(source.getNomMajuscule());
    dest.setNumInsee(source.getCodeInsee());
    dest.setBassin(source.getCirconscriptionBassin().getCode());
    // Code Bassin Seine-Normandie: "03"
    dest.setHorsBassin(!"03".equals(source.getCirconscriptionBassin().getCode()));
    return dest;
  }

  /**
   * Entity to VO List mapping.
   * @param sources Entity List to Map.
   * @return VO List mapped by Entity.
   */
  public static final List<CommuneVO> communeEntity2VoList(List<Commune> sources) {
    logger.log(Level.FINE,"Mapping List {}", sources); // Debug
    if (sources == null) {
      return null;
    }
    List<CommuneVO> dest = new ArrayList<CommuneVO>(sources.size());
    for (Commune source : sources) {
      dest.add(communeEntity2Vo(source));
    }
    return dest;
  }

  /**
   * Entity to VO mapping.
   * @param source Entity to Map.
   * @return VO mapped by Entity.
   */
  public static final DelegationVO delegationEntity2Vo(Delegation source) {
    logger.log(Level.FINE,"Mapping {}", source); // Debug
    if (source == null) {
      return null;
    }
    DelegationVO dest = new DelegationVO();
    dest.setCode(source.getCode());
    dest.setLibelle(source.getLibelle());
    dest.setAcheminement(source.getAcheminement());
    dest.setAdresse1(source.getAdresse1());
    dest.setAdresse2(source.getAdresse2());
    dest.setAdresse3(source.getAdresse3());
    dest.setAdresse4(source.getAdresse4());
    dest.setAdresse5(source.getAdresse5());
    dest.setCodePostal(source.getCodePostal());
    dest.setEmail(source.getEmail());
    dest.setFax(source.getFax());
    dest.setSiteWeb(source.getSiteWeb());
    dest.setTelephone(source.getTelephone());
    dest.setTelephone2(source.getTelephone2());
    dest.setTelephone3(source.getTelephone3());
    return dest;
  }

  /**
   * VO to Entity mapping.
   * @param source VO to Map.
   * @return Entity mapped by VO.
   */
  public static final Delegation delegationVo2Entity(DelegationVO source) {
    logger.log(Level.FINE,"Mapping {}", source); // Debug
    if (source == null) {
      return null;
    }
    Delegation dest = new Delegation();
    dest.setCode(source.getCode());
    dest.setLibelle(source.getLibelle());
    dest.setAcheminement(source.getAcheminement());
    dest.setAdresse1(source.getAdresse1());
    dest.setAdresse2(source.getAdresse2());
    dest.setAdresse3(source.getAdresse3());
    dest.setAdresse4(source.getAdresse4());
    dest.setAdresse5(source.getAdresse5());
    dest.setCodePostal(source.getCodePostal());
    dest.setEmail(source.getEmail());
    dest.setFax(source.getFax());
    dest.setSiteWeb(source.getSiteWeb());
    dest.setTelephone(source.getTelephone());
    dest.setTelephone2(source.getTelephone2());
    dest.setTelephone3(source.getTelephone3());
    return dest;
  }

  /**
   * Entity to VO List mapping.
   * @param sources Entity List to Map.
   * @return VO List mapped by Entity.
   */
  public static final List<DelegationVO> delegationEntity2VoList(List<Delegation> sources) {
    logger.log(Level.FINE,"Mapping List {}", sources); // Debug
    if (sources == null) {
      return null;
    }
    List<DelegationVO> dest = new ArrayList<DelegationVO>(sources.size());
    for (Delegation source : sources) {
      dest.add(delegationEntity2Vo(source));
    }
    return dest;
  }
}

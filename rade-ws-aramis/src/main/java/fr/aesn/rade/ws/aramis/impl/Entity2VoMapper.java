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

import fr.aesn.rade.common.modelplus.CommunePlus;
import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.Delegation;
import fr.aesn.rade.persist.model.Departement;
import lombok.extern.slf4j.Slf4j;

/**
 * Mapper class to map Entities (from the service package) to VOs (defined in the WSDL).
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public final class Entity2VoMapper {
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
   * VO to Entity mapping.
   * @param source VO to Map.
   * @return Entity mapped by VO.
   */
  public static final Delegation delegationVo2Entity(DelegationVO source) {
    log.debug("Mapping {}", source); // Debug
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
   * Entity to VO mapping.
   * @param source Entity to Map.
   * @return VO mapped by Entity.
   */
  public static final DelegationVO delegationEntity2Vo(Delegation source) {
    log.debug("Mapping {}", source); // Debug
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
   * Entity to VO List mapping.
   * @param sources Entity List to Map.
   * @return VO List mapped by Entity.
   */
  public static final List<DelegationVO> delegationEntity2VoList(List<Delegation> sources) {
    log.debug("Mapping List {}", sources); // Debug
    if (sources == null) {
      return null;
    }
    List<DelegationVO> dest = new ArrayList<>(sources.size());
    for (Delegation source : sources) {
      dest.add(delegationEntity2Vo(source));
    }
    return dest;
  }
  /**
   * Entity to VO mapping.
   * @param source Entity to Map.
   * @return VO mapped by Entity.
   */
  public static final DepartementVO departementEntity2Vo(Departement source) {
    log.debug("Mapping {}", source); // Debug
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
    log.debug("Mapping List {}", sources); // Debug
    if (sources == null) {
      return null;
    }
    List<DepartementVO> dest = new ArrayList<>(sources.size());
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
  public static final CommuneVO communePlusEntity2Vo(CommunePlus source) {
    log.debug("Mapping {}", source); // Debug
    if (source == null) {
      return null;
    }
    CommuneVO dest = new CommuneVO();
    dest.setNomCommune(source.getNomEnrichi());
    dest.setNomCourt(source.getNomMajuscule());
    dest.setNumInsee(source.getCodeInsee());
    CirconscriptionBassin bassin = source.getCirconscriptionBassin();
    String code;
    if (bassin == null) {
      log.info("Commune with no CirconscriptionBassin defined: {} - {}",
               source.getCodeInsee(), source.getNomEnrichi());
      code = "";
    } else {
      code = bassin.getCode();
    }
    dest.setBassin(code);
    // Code Bassin Seine-Normandie: "03"
    dest.setHorsBassin(!"03".equals(code));
    return dest;
  }

  /**
   * Entity to VO List mapping.
   * @param sources Entity List to Map.
   * @return VO List mapped by Entity.
   */
  public static final List<CommuneVO> communePlusEntity2VoList(List<CommunePlus> sources) {
    log.debug("Mapping List {}", sources); // Debug
    if (sources == null) {
      return null;
    }
    List<CommuneVO> dest = new ArrayList<>(sources.size());
    for (CommunePlus source : sources) {
      dest.add(communePlusEntity2Vo(source));
    }
    return dest;
  }
}

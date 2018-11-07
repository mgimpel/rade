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

import fr.aesn.rade.persist.dao.DelegationJpaDao;
import fr.aesn.rade.persist.model.Delegation;
import fr.aesn.rade.service.DelegationService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for Delegation.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor @Slf4j
public class DelegationServiceImpl
  implements DelegationService {
  /** Data Access Object for Delegation. */
  @Autowired @Setter
  private DelegationJpaDao delegationJpaDao;

  /**
   * Add a Delegation.
   * @param delegation the Delegation to add.
   */
  @Override
  public void addDelegation(final Delegation delegation) {
    log.debug("Delegation to be saved: Details={}", delegation);
    delegationJpaDao.save(delegation);
  }

  /**
   * Update a Delegation.
   * @param delegation the Delegation to update.
   */
  @Override
  public void updateDelegation(final Delegation delegation) {
    log.debug("Delegation to be updated: Details={}", delegation);
    delegationJpaDao.save(delegation);
  }

  /**
   * List all Delegation.
   * @return a List of all the Delegation.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Delegation> getAllDelegation() {
    log.debug("Delegation list requested");
    return delegationJpaDao.findAll();
  }

  /**
   * Returns a Map of all Delegation indexed by ID.
   * @return a Map of all Delegation indexed by ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Map<String, Delegation> getDelegationMap() {
    log.debug("Delegation map requested");
    List<Delegation> list = getAllDelegation();
    HashMap<String, Delegation> map = new HashMap<>(list.size());
    for (Delegation item : list) {
      map.put(item.getCode(), item);
    }
    return map;
  }

  /**
   * Get the Delegation with the given ID.
   * @param code the Delegation ID.
   * @return the Delegation with the given ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Delegation getDelegationById(final String code) {
    log.debug("Delegation requested by ID: ID={}", code);
    Optional<Delegation> result = delegationJpaDao.findById(code);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Delete the Delegation with the given ID.
   * @param code the Delegation ID.
   */
  @Override
  public void removeDelegation(final String code) {
    log.debug("Delegation to be deleted: ID={}", code);
    delegationJpaDao.deleteById(code);
  }
}

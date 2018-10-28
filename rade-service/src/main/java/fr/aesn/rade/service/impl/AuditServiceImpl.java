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

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aesn.rade.persist.dao.AuditJpaDao;
import fr.aesn.rade.persist.model.Audit;
import fr.aesn.rade.service.AuditService;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Service Implementation for Audit.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Service
@Transactional
@NoArgsConstructor
public class AuditServiceImpl
  implements AuditService {
    /** SLF4J Logger. */
    private static final Logger log =
      LoggerFactory.getLogger(AuditServiceImpl.class);
    /** Data Access Object for Audit. */
    @Autowired @Setter
    private AuditJpaDao auditJpaDao;

  /**
   * List all Audit.
   * @return a List of all the Audit.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Audit> getAllAudit() {
    log.debug("Audit list requested");
    return auditJpaDao.findAll();
  }

  /**
   * Get the Audit with the given ID.
   * @param id the Audit ID.
   * @return the Audit with the given ID.
   */
  @Override
  @Transactional(readOnly = true)
  public Audit getAuditbyId(final int id) {
    log.debug("Audit requested by ID: ID={}", id);
    Optional<Audit> result = auditJpaDao.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  /**
   * Create Audit.
   * @param audit the new Audit to persist.
   * @return the new Audit.
   */
  @Override
  public Audit createAudit(final Audit audit) {
    log.debug("Audit creation: {}", audit);
    return auditJpaDao.save(audit);
  }
}

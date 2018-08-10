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
package fr.aesn.rade.service;

import java.util.List;

import fr.aesn.rade.persist.model.Audit;

/**
 * Service Interface for Audit.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public interface AuditService {
  /**
   * List all Audit.
   * @return a List of all the Audit.
   */
  public List<Audit> getAllAudit();

  /**
   * Get the Audit with the given ID.
   * @param id the Audit ID.
   * @return the Audit with the given ID.
   */
  public Audit getAuditbyId(final int id);

  /**
   * Create Audit.
   * @param audit the new Audit to persist.
   * @return the new Audit.
   */
  public Audit createAudit(Audit audit);
}

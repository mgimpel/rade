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
package fr.aesn.rade.common;

import java.util.Date;

import org.springframework.beans.factory.config.AbstractFactoryBean;

import fr.aesn.rade.persist.model.Audit;
import lombok.Getter;
import lombok.Setter;

/**
 * Audit Factory.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter
public class AuditFactory extends AbstractFactoryBean<Audit> {

//  private EntityManager em
  private String auteur;
  private String note;

  public AuditFactory() {
    setSingleton(false);
  }

  @Override
  public Class<?> getObjectType() {
    return Audit.class;
  }

  @Override
  protected Audit createInstance() throws Exception {
    Audit audit = new Audit();
    audit.setAuteur(auteur);
    audit.setNote(note);
    audit.setDate(new Date());
    return audit;
  }
}

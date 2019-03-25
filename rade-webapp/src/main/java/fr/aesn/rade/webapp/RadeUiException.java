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
package fr.aesn.rade.webapp;

import fr.aesn.rade.common.RadeException;

/**
 * General UI Exception for Rade.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class RadeUiException
  extends RadeException {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 9138511605737118190L;

  /**
   * Constructs a new exception with null as its detail message.
   */
  public RadeUiException() {
  }

  /**
   * Constructs a new exception with the specified detail message.
   * @param message the detail message.
   */
  public RadeUiException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified cause.
   * @param cause the cause.
   */
  public RadeUiException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new exception with the specified detail message and cause. 
   * @param message the detail message.
   * @param cause the cause.
   */
  public RadeUiException(String message, Throwable cause) {
    super(message, cause);
  }
}

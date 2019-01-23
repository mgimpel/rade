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
package fr.aesn.rade.batch.util;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import lombok.Setter;

/**
 * Writes a the parameterized String as a file Header.
 *
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class StringHeaderWriter
  implements FlatFileHeaderCallback {
  /** The default header to write on callback. */
  public static final String DEFAULT_HEADER = "// Header";
  /** The header to write on callback. */
  @Setter
  private String header= DEFAULT_HEADER;

  /**
   * Write contents to a file using the supplied Writer.
   * It is not required to flush the writer inside this method.
   */
  @Override
  public void writeHeader(Writer writer)
    throws IOException {
    writer.write(header);
  }
}

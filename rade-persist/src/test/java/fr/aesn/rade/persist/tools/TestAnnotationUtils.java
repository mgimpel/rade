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
package fr.aesn.rade.persist.tools;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.junit.*;

import fr.aesn.rade.persist.model.Audit;

/**
 * Test Annotation Utils.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestAnnotationUtils {
  @Test
  public void TestGetFieldAnnotations()
    throws NoSuchFieldException {
    Map<Class<? extends Annotation>, Annotation> map =
      AnnotationUtils.getFieldAnnotations(Audit.class, "id");
    assertEquals(4, map.size());
  }

  @Test
  public void TestGetClassAnnotations()
    throws Exception {
    Map<Class<? extends Annotation>, Annotation> map =
      AnnotationUtils.getClassAnnotations(Audit.class);
    assertEquals(2, map.size());
  }
}

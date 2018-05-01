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

import static org.junit.Assert.*;

import org.junit.Test;

import fr.aesn.rade.persist.model.Delegation;

/**
 * Unit Tests for the Entity2VoMapper.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class TestMapper {
    /**
     * Test Delegation Mapping
     */
    @Test
    public void testDelegation() {
        Delegation obj1 = new Delegation();
        Delegation obj2;
        obj1.setCode("0PPC_");
        obj1.setLibelle("DPPC");
        obj1.setAcheminement("xxx0");
        obj1.setAdresse1("xxx1");
        obj1.setAdresse2("xxx2");
        obj1.setAdresse3("xxx3");
        obj1.setAdresse4("xxx4");
        obj1.setAdresse5("xxx5");
        obj1.setCodePostal("xxx6");
        obj1.setEmail("xxx7");
        obj1.setFax("xxx8");
        obj1.setSiteWeb("xxx0");
        obj1.setTelephone("0X XX XX XX X1");
        obj1.setTelephone2("0X XX XX XX X2");
        obj1.setTelephone3("0X XX XX XX X3");
        obj2 = Entity2VoMapper.delegationVo2Entity(Entity2VoMapper.delegationEntity2Vo(obj1));
        assertEquals("Item should be equal to itself",
                     obj1, obj1);
        assertEquals("Items passed through Mapper and back are supposed to be equal",
                     obj1, obj2);
        assertEquals("Items passed through Mapper and back are supposed to be equal",
                     obj2, obj1);
        assertTrue("Items passed through Mapper and back are supposed to be equal",
                   obj1.equals(obj2));
        assertTrue("Items passed through Mapper and back are supposed to be equal",
                   obj2.equals(obj1));
        assertEquals("Items passed through Mapper and back are supposed to be equal",
                     obj1.hashCode(), obj2.hashCode());
        assertTrue("Items passed through Mapper and back are different objects even if they are equal",
                   obj1.hashCode() == obj2.hashCode());
        assertNull("Should return null if that is what it was passed",
                   Entity2VoMapper.delegationEntity2Vo(null));
        assertNull("Should return null if that is what it was passed",
                   Entity2VoMapper.delegationVo2Entity(null));
    }

    /**
     * 
     */
/*
    @Test
    public void testDepartementList() {
        assertNull(Entity2VoMapper.demoDepartementEntity2VoList(null));
        Vector<DemoDepartement> list = new Vector<DemoDepartement>();
        DemoDepartement obj1 = new DemoDepartement();
        obj1.setNumeroDepartement("75");
        obj1.setNomDepartement("Paris");
        list.add(obj1);
        DemoDepartement obj2 = new DemoDepartement();
        obj2.setNumeroDepartement("91");
        obj2.setNomDepartement("Essonne");
        list.add(obj2);
        DemoDepartement obj3 = new DemoDepartement();
        obj3.setNumeroDepartement("92");
        obj3.setNomDepartement("Hauts-de-Seine");
        list.add(obj3);
        DemoDepartement obj4 = new DemoDepartement();
        obj4.setNumeroDepartement("93");
        obj4.setNomDepartement("Seine-Saint-Denis");
        list.add(obj4);
        DemoDepartement obj5 = new DemoDepartement();
        obj5.setNumeroDepartement("94");
        obj5.setNomDepartement("Val-de-Marne");
        list.add(obj5);
        DemoDepartement obj6 = new DemoDepartement();
        obj6.setNumeroDepartement("95");
        obj6.setNomDepartement("Val-d'Oise");
        list.add(obj6);
        list.add(null);
        DemoDepartement source, dest;
        List<DepartementVO> testlist = Entity2VoMapper.demoDepartementEntity2VoList(list);
        assertEquals("Result should be same size as initial list",
                     list.size(), testlist.size());
        for (int i=0; i<list.size(); i++) {
            source = list.get(i);
            dest = Entity2VoMapper.demoDepartementVo2Entity(testlist.get(i));
            assertEquals("Item should be equal to itself",
                         source, dest);
            assertEquals("Items passed through Mapper and back are supposed to be equal",
                         source, dest);
            assertEquals("Items passed through Mapper and back are supposed to be equal",
                         source, dest);
            if (source != null && dest != null) {
              assertTrue("Items passed through Mapper and back are supposed to be equal",
                         source.equals(dest));
              assertTrue("Items passed through Mapper and back are supposed to be equal",
                         dest.equals(source));
              assertEquals("Items passed through Mapper and back are supposed to be equal",
                           source.hashCode(), dest.hashCode());
              assertTrue("Items passed through Mapper and back are different objects even if they are equal",
                         source.hashCode() == dest.hashCode());
              assertNotEquals("Shouldn't be comparing the same Objects",
                              source.toString(), dest.toString());
            }
        }
    }
 */
}

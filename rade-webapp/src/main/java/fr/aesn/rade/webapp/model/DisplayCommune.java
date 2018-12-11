/*
 * Copyright (C) 2018 sophie.belin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.aesn.rade.webapp.model;

import fr.aesn.rade.persist.dao.CommuneJpaDao;
import fr.aesn.rade.persist.dao.DepartementJpaDao;
import fr.aesn.rade.persist.dao.RegionJpaDao;
import fr.aesn.rade.persist.model.EntiteAdministrative;
import fr.aesn.rade.persist.model.GenealogieEntiteAdmin;
import fr.aesn.rade.service.CommuneService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import java.util.Date;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author sophie.belin
 */
@Slf4j
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class DisplayCommune {
    String codeInsee;
    String motifModification;
    String nomEnrichi;
    String commentaireModification;
    String article;
    String articleEnrichi;
    String codeBassin;
    String nomBassin;
    String codeDepartement;
    String nomDepartement;
    String nomMajuscule;
    String nomRegion;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    Date dateCreation, dateModification, debutValidite, finValidite;

    CommuneService communeService;
    RegionService regionService;
    DepartementService departementService;

    Set<GenealogieEntiteAdmin> parents;
    Set<GenealogieEntiteAdmin> enfants;
    
    public String findCodeInsee(EntiteAdministrative entite){      
        if(entite != null){
            switch(entite.getTypeEntiteAdmin().getCode()){
                case "COM":
                    return communeService.getCommuneById(entite.getId()).getCodeInsee();
                case "REG":
                    return regionService.getRegionById(entite.getId()).getCodeInsee();
                case "DEP":
                    return departementService.getDepartementById(entite.getId()).getCodeInsee();
            }
        }

        return null;
    }
    
    public String findPage(EntiteAdministrative entite){
        if(entite != null){
            switch(entite.getTypeEntiteAdmin().getCode()){
                case "COM":
                    return "/referentiel/commune/" + findCodeInsee(entite) + "/" + entite.getDebutValidite();
                case "REG":
                    return "/referentiel/region/" + findCodeInsee(entite);
                case "DEP":
                    return "/referentiel/departement/" + findCodeInsee(entite);
            }
        }
        return null;
    }
}

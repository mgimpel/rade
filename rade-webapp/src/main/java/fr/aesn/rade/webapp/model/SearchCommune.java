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

import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.Region;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author sophie.belin
 */

@Slf4j
@Getter @Setter @NoArgsConstructor
public class SearchCommune {
    String codeRegion;
    String codeDepartement;
    String codeCirconscription;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date dateEffet;
    String codeInsee;
    String nomEnrichi;
    String page = "1";
    List<Departement> departements;
    Map<String,String> departementsByCodeInsee;
    Map<String,String> regionsByCodeInsee;
    Map<String, String> circonscriptionByCode;
    List<DisplayCommune> listeResultats;
    List<Commune> communes;
    
    /**
   * Renvoie la date au format dd/MM/yyyy 
   * ou un département
   * @param date 
   * @return Date
   */
    public String formatDate(Date date){
        String formatDate = null;
        if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            formatDate = sdf.format(date);
        }
        return formatDate;
    }
    

    public void setPage(String page){
        try{
           int numPage = Integer.parseInt(page);
           if(numPage <= getPageMax()){
               this.page = page;
           }
       }catch(NumberFormatException e){
           log.error("Paramètre non valide");
       }
    }

    /**
   * Renvoie l'index de la première commune
   * @return Index 
   */
    public int getFirstCommuneIndex(){
        int index = 1;
        if(communes != null && communes.size() > 0){
            try{
                index = Integer.parseInt(page) * 10 - 10;
            }catch(NumberFormatException ne){
                page = "1";
            }
            if(index > communes.size()){
                index = this.communes.size();
            }
        }
        return index;  
    }
    
    /**
   * Renvoie l'index de la dernière commune
   * @return Index
   */
     public int getLastCommuneIndex(){
        int index = 1;
        if(communes != null && communes.size() > 0){
            index = getFirstCommuneIndex() + 10;
            if(index > this.communes.size()){
                index = this.communes.size();
            }
        }
        return index;  
    }
     
     /**
   * Renvoie le nombre de pages au total pour afficher l'ensemble des comunes
   * ou un département
   * @return Nombre de page
   */
      public int getPageMax(){
        if(this.communes != null && this.communes.size() > 0){
            return (int) Math.ceil((double)this.communes.size() / (double)10);
        }else{
            return 1;
        }
      }
      
    public void setRegionsByCodeInsee(List<Region> regions){
        regionsByCodeInsee = new HashMap<>();
        if(regions != null){
            for(Region r : regions){
                if(!regionsByCodeInsee.containsKey(r.getCodeInsee())){
                    regionsByCodeInsee.put(r.getCodeInsee(), r.getNomEnrichi() );
                }
            }    

            regionsByCodeInsee = sortByValue(regionsByCodeInsee);
        }
    }
    
    public void setDepartementsByCodeInsee(List<Departement> departements){
        departementsByCodeInsee = new HashMap<>();
        if(departements != null){
            for(Departement r : departements){
                if(!departementsByCodeInsee.containsKey(r.getCodeInsee())){
                    departementsByCodeInsee.put(r.getCodeInsee(), r.getNomEnrichi() );
                }
            }    
            this.departementsByCodeInsee = sortByValue(departementsByCodeInsee);
            this.departements = departements;
        }
    }

    public void setCirconscriptionByCode(List<CirconscriptionBassin> circonscriptions){
        circonscriptionByCode = new HashMap<>();
        if(circonscriptions != null){
            for(CirconscriptionBassin c : circonscriptions){
                if(!circonscriptionByCode.containsKey(c.getCode())){
                    circonscriptionByCode.put(c.getCode(), c.getLibelleLong());
                }
            }  
            this.circonscriptionByCode = sortByValue(circonscriptionByCode);
        }
    }
      
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}


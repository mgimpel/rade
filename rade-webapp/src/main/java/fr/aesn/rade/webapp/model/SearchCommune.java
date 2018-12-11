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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.Cell;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author sophie.belin
 */

@Slf4j
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class SearchCommune {
    String codeRegion;
    String codeDepartement;
    String codeCirconscription;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date dateEffet;
    String codeInsee;
    String nomEnrichi;
    String errorMessage;
    String hasError;
    String page;
    
    public String formatDate(Date date){
        String formatDate = null;
        if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            formatDate = sdf.format(date);
        }
        return formatDate;
    }
    
    List<DisplayCommune> listeResultats;
    List<DisplayCommune> listeResultatsOfPage;
    
    private void generateXls(){
//        Workbook lClasseurExcel = new HSSFWorkbook();
//        Sheet lFeuilleExcelErreur = lClasseurExcel.createSheet("Liste des resultats");
//        List<Cell> listeAttributsCells = new ArrayList<Cell>();
//        // Style pour l'entete des colonnes
//        CellStyle lStyle = setStyle(lClasseurExcel);
//        CellStyle lStyleEntete = setStyleEntete(lClasseurExcel);
//        CellStyle lStyleErreur = setStyleErreur(lClasseurExcel);
//        CellStyle redBackground = setredBackground(lClasseurExcel);
//
//        // Style pour le corps du fichier
//        Font lFontCorps = lClasseurExcel.createFont();
//        lFontCorps.setFontName("Arial");
//        CellStyle lStyleCorps = lClasseurExcel.createCellStyle();
//        lStyleCorps.setFont(lFontCorps);
//        // lStyleCorps.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
//        lStyleCorps.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//
//        Set<String> casesTraitees = new HashSet<String>();
//        if (TypeFichier.CODE_SITOU.equals(fichesuivi.getTypeFichier().getCodeType())) {
//                traitementSitou(lFeuilleExcelErreur, fichesuivi, lStyle, fichierSrv, lStyleEntete, fichier,
//                                lStyleCorps, listeAttributsCells, lStyleErreur, lClasseurExcel,
//                                redBackground, casesTraitees);
//        } else if (TypeFichier.CODE_LIAISON.equals(fichesuivi.getTypeFichier().getCodeType())) {
//                traitementLiaison(fichier, lFeuilleExcelErreur, fichesuivi, lStyle, fichierSrv,
//                                lStyleEntete, lStyleCorps, listeAttributsCells, lStyleErreur, lClasseurExcel,
//                                redBackground, casesTraitees);
//        }
//        return lClasseurExcel;
    }
}


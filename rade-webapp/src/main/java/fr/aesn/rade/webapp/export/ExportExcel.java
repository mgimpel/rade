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
package fr.aesn.rade.webapp.export;

import fr.aesn.rade.common.modelplus.CommunePlusWithGenealogie;
import fr.aesn.rade.webapp.model.DisplayCommune;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Export du résultat de la recherche de communes au format excel
 * @author sophie.belin
 */
@Slf4j
public class ExportExcel implements Export {

  @Override
  public void exportCommune(OutputStream output, List<DisplayCommune> listeCommunes) {
    Workbook wb = buildWorkbook(listeCommunes);
    try {
      wb.write(output);
    } catch (IOException e) {
      log.info("Erreur lors de la génération de l'export excel", e);
    }
  }

  private Workbook buildWorkbook(List<DisplayCommune> listeCommunes) {
    Workbook wb = new HSSFWorkbook();
    Sheet sheet = wb.createSheet("Liste des communes");
    CreationHelper creationHelper = wb.getCreationHelper();
    CellStyle cs = wb.createCellStyle();
    cs.setDataFormat(creationHelper.createDataFormat().getFormat("DD/mm/yyyy"));
    CellStyle headercs = getHeaderCellStyle(wb);

    Row header1 = sheet.createRow(0);
    header1.createCell(0).setCellValue("Commune");
    header1.getCell(0).setCellStyle(headercs);
    header1.createCell(4).setCellValue("Entité mère");
    header1.getCell(4).setCellStyle(headercs);
    sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
    sheet.addMergedRegion(new CellRangeAddress(0,0,4,5));
    Row header2 = sheet.createRow(1);
    header2.createCell(0).setCellValue("Code");
    header2.getCell(0).setCellStyle(headercs);
    header2.createCell(1).setCellValue("Nom");
    header2.getCell(1).setCellStyle(headercs);
    header2.createCell(2).setCellValue("Début validité");
    header2.getCell(2).setCellStyle(headercs);
    header2.createCell(3).setCellValue("Fin validité");
    header2.getCell(3).setCellStyle(headercs);
    header2.createCell(4).setCellValue("Motif de modification");
    header2.getCell(4).setCellStyle(headercs);
    header2.createCell(5).setCellValue("Code Insee");
    header2.getCell(5).setCellStyle(headercs);

    for(int i = 0; i < listeCommunes.size(); i++) {
      Row row = sheet.createRow(i+2);
      DisplayCommune commune = listeCommunes.get(i);
      row.createCell(0).setCellValue(commune.getCodeInsee());
      row.getCell(0).setCellType(CellType.STRING);
      row.createCell(1).setCellValue(commune.getNomEnrichi());
      if(commune.getDebutValidite() != null){
        row.createCell(2).setCellValue(commune.getDebutValidite());
        row.getCell(2).setCellStyle(cs);
      }
      if(commune.getFinValidite() != null){
        row.createCell(3).setCellValue(commune.getFinValidite());
        row.getCell(3).setCellStyle(cs);
      }
      if(commune.getMotifModification() != null){
        row.createCell(4).setCellValue(commune.getMotifModification());
        row.getCell(4).setCellStyle(cs);
      }
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, CommunePlusWithGenealogie.GenealogieTypeAndEntity> entry : commune.getParents().entrySet()) {
        sb.append(entry.getKey());
        sb.append(" ");
      }
      row.createCell(5).setCellValue(sb.toString().trim());
    }
    sheet.autoSizeColumn(0);
    sheet.autoSizeColumn(1);
    sheet.autoSizeColumn(2);
    sheet.autoSizeColumn(3);
    sheet.autoSizeColumn(4);
    sheet.autoSizeColumn(5);
    return wb;
  }

  /**
   * Cell Style for header.
   * @param wb
   * @return
   */
  private CellStyle getHeaderCellStyle(Workbook wb) {
    CellStyle styleHeader = wb.createCellStyle();
    Font fontHeader = wb.createFont();
    fontHeader.setBold(true);
    styleHeader.setFont(fontHeader);
    styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    styleHeader.setWrapText(true);
    styleHeader.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
    styleHeader.setAlignment(HorizontalAlignment.CENTER);
    styleHeader.setBorderBottom(BorderStyle.THIN);
    styleHeader.setBorderLeft(BorderStyle.THIN);
    styleHeader.setBorderRight(BorderStyle.THIN);
    styleHeader.setBorderTop(BorderStyle.THIN);
    return styleHeader;
  }
}

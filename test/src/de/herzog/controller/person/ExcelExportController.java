package de.herzog.controller.person;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.herzog.controller.AbstractController;
import de.herzog.enums.person.ReligionEnum;
import de.herzog.util.Helper;
import de.herzog.util.converter.EventViewConverter;
import de.herzog.views.person.PersonView;

@ManagedBean(name = ExcelExportController.MANAGED_BEAN_NAME)
@SessionScoped
public class ExcelExportController extends AbstractController {

	private static final long serialVersionUID = -5399985461542241586L;
	
	 static final String MANAGED_BEAN_NAME = "ExcelExport";

	@Override
	protected void init() {
		super.init();
	}
	
	public void generateExport(ActionEvent event) {
		generateExport();
	}
	
	private void generateExport() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet();
		
		XSSFFont fontBold = workbook.createFont();
		fontBold.setBold(true);
		
		XSSFCellStyle csBold = workbook.createCellStyle();
		csBold.setFont(fontBold);
		csBold.setBorderTop(BorderStyle.MEDIUM);
		csBold.setBorderBottom(BorderStyle.MEDIUM);
		
		XSSFCellStyle cs = workbook.createCellStyle();
		
		XSSFCellStyle csRight = workbook.createCellStyle();
		csRight.setBorderRight(BorderStyle.THIN);
		
		XSSFCellStyle csBorderTop = workbook.createCellStyle();
		csBorderTop.setBorderTop(BorderStyle.MEDIUM);
		csBorderTop.setVerticalAlignment(VerticalAlignment.TOP);
		
		XSSFCellStyle csBorderBottom = workbook.createCellStyle();
		csBorderBottom.setBorderBottom(BorderStyle.MEDIUM);
		
		XSSFCellStyle csBorderBottom90 = workbook.createCellStyle();
		csBorderBottom90.setBorderBottom(BorderStyle.MEDIUM);
		csBorderBottom90.setRotation((short) 90);
		
		XSSFCellStyle csBorderTopRight = workbook.createCellStyle();
		csBorderTopRight.setBorderTop(BorderStyle.MEDIUM);
		csBorderTopRight.setBorderRight(BorderStyle.THIN);
		csBorderTopRight.setVerticalAlignment(VerticalAlignment.TOP);
		
		XSSFCellStyle csBorderBottomRight = workbook.createCellStyle();
		csBorderBottomRight.setBorderRight(BorderStyle.THIN);
		csBorderBottomRight.setBorderBottom(BorderStyle.MEDIUM);
		
		XSSFCellStyle csBorderRight = workbook.createCellStyle();
		csBorderRight.setBorderRight(BorderStyle.THIN);
		
		XSSFCellStyle csBorderTopRightThin = workbook.createCellStyle();
		csBorderTopRightThin.setBorderTop(BorderStyle.MEDIUM);
		csBorderTopRightThin.setBorderRight(BorderStyle.THIN);
		
		XSSFCellStyle csBorderBottomRightThin = workbook.createCellStyle();
		csBorderBottomRightThin.setBorderRight(BorderStyle.THIN);
		csBorderBottomRightThin.setBorderBottom(BorderStyle.MEDIUM);
		
		XSSFCellStyle csBorderRightThin = workbook.createCellStyle();
		csBorderRightThin.setBorderRight(BorderStyle.THIN);
		
		XSSFCellStyle csBoldGreen = workbook.createCellStyle();
		csBoldGreen.setFont(fontBold);
		csBoldGreen.setBorderTop(BorderStyle.MEDIUM);
		csBoldGreen.setBorderBottom(BorderStyle.MEDIUM);
		csBoldGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBoldGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csGreen = workbook.createCellStyle();
		csGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csRightGreen = workbook.createCellStyle();
		csRightGreen.setBorderRight(BorderStyle.THIN);
		csRightGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csRightGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderTopGreen = workbook.createCellStyle();
		csBorderTopGreen.setBorderTop(BorderStyle.MEDIUM);
		csBorderTopGreen.setVerticalAlignment(VerticalAlignment.TOP);
		csBorderTopGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderTopGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderBottomGreen = workbook.createCellStyle();
		csBorderBottomGreen.setBorderBottom(BorderStyle.MEDIUM);
		csBorderBottomGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderBottomGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderBottom90Green = workbook.createCellStyle();
		csBorderBottom90Green.setBorderBottom(BorderStyle.MEDIUM);
		csBorderBottom90Green.setRotation((short) 90);
		csBorderBottom90Green.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderBottom90Green.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderTopRightGreen = workbook.createCellStyle();
		csBorderTopRightGreen.setBorderTop(BorderStyle.MEDIUM);
		csBorderTopRightGreen.setBorderRight(BorderStyle.THIN);
		csBorderTopRightGreen.setVerticalAlignment(VerticalAlignment.TOP);
		csBorderTopRightGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderTopRightGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderBottomRightGreen = workbook.createCellStyle();
		csBorderBottomRightGreen.setBorderRight(BorderStyle.THIN);
		csBorderBottomRightGreen.setBorderBottom(BorderStyle.MEDIUM);
		csBorderBottomRightGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderBottomRightGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderRightGreen = workbook.createCellStyle();
		csBorderRightGreen.setBorderRight(BorderStyle.THIN);
		csBorderRightGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderRightGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderTopRightThinGreen = workbook.createCellStyle();
		csBorderTopRightThinGreen.setBorderTop(BorderStyle.MEDIUM);
		csBorderTopRightThinGreen.setBorderRight(BorderStyle.THIN);
		csBorderTopRightThinGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderTopRightThinGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderBottomRightThinGreen = workbook.createCellStyle();
		csBorderBottomRightThinGreen.setBorderRight(BorderStyle.THIN);
		csBorderBottomRightThinGreen.setBorderBottom(BorderStyle.MEDIUM);
		csBorderBottomRightThinGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderBottomRightThinGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		XSSFCellStyle csBorderRightThinGreen = workbook.createCellStyle();
		csBorderRightThinGreen.setBorderRight(BorderStyle.THIN);
		csBorderRightThinGreen.setFillForegroundColor(IndexedColors.GREEN.index);
		csBorderRightThinGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		// Überschirft
		XSSFRow row = sheet.createRow(0);
		
		sheet.setColumnWidth(0, 3 * 256);
		sheet.setColumnWidth(1, 14 * 256);
		sheet.setColumnWidth(2, 21 * 256);
		sheet.setColumnWidth(3, 4 * 256);
		sheet.setColumnWidth(4, 2 * 256);
		sheet.setColumnWidth(5, 10 * 256);
		sheet.setColumnWidth(6, 24 * 256);
		sheet.setColumnWidth(7, 3 * 256);
		sheet.setColumnWidth(8, 28 * 256);
		sheet.setColumnWidth(9, 3 * 256);
		sheet.setColumnWidth(10, 40 * 256);
		
		createCell(row, 0, csBorderBottom, "Nr.");
		createCell(row, 1, csBorderBottom, "Nachname");
		createCell(row, 2, csBorderBottom, "Vornamen");
		createCell(row, 3, csBorderBottom90, "Religion");
		createCell(row, 4, csBorderBottom, "");
		createCell(row, 5, csBorderBottom, "Datum");
		createCell(row, 6, csBorderBottomRight, "Ort");
		createCell(row, 7, csBorderBottom90, "Vorhanden");
		createCell(row, 8, csBorderBottom, "Standort");
		createCell(row, 9, csBorderBottom90, "Familysearch");
		createCell(row, 10, csBorderBottom, "Bemerkung");
		
		EventViewConverter eventConverter = new EventViewConverter();
		
		// Für jede Generation?
		int g = 0;
		StammbaumController stammbaumController = Helper.getManagedBean(StammbaumController.class);
		List<List<PersonView>> stammbaum = stammbaumController.getStammbaum();
		int r = 1;
		for (List<PersonView> generation : stammbaum) {
			row = sheet.createRow(r++);
			createCell(row, 0, csBold, g++ + ". Generation");
			for (int i = 1; i <= 10; i++) {
				createCell(row, i, csBold, "");
			}
			
			sheet.addMergedRegion(new CellRangeAddress(r - 1, r - 1, 0, 10));
			
			for (PersonView person : generation) {
				XSSFCellStyle style = null;
				XSSFCellStyle styleHochzeit = null;
				XSSFCellStyle styleHochzeitRight = null;
				XSSFCellStyle styleGeburt = null;
				XSSFCellStyle styleGeburtRight = null;
				XSSFCellStyle styleTod = null;
				XSSFCellStyle styleTodRight = null;
				
				if (person.isMann()) {
					style = csBorderTop;
					styleGeburt = csBorderTop;
					styleGeburtRight = csBorderTopRight;
					styleTod = cs;
					styleTodRight = csRight;
					styleHochzeit = cs;
					styleHochzeitRight = csRight;
			
					if (person.getGeburt() != null && person.getGeburt().isNachweis() && person.getTod() != null && person.getTod().isNachweis()) {
						style = csBorderTopGreen;						
					}
					
					if (person.getGeburt() != null && person.getGeburt().isNachweis()) {
						styleGeburt = csBorderTopGreen;
						styleGeburtRight = csBorderTopRightGreen;
					}
					
					if (person.getTod() != null && person.getTod().isNachweis()) {
						styleTod = csGreen;
						styleTodRight = csRightGreen;
					}
					
					if (person.getHochzeiten() != null && person.getHochzeiten().size() > 0 && person.getHochzeiten().get(0).getHochzeit() != null && person.getHochzeiten().get(0).getHochzeit().isNachweis()) {
						styleHochzeit = csGreen;
						styleHochzeitRight = csRightGreen;
					}
				} else {
					style = csBorderBottom;
					styleGeburt = cs;
					styleGeburtRight = csRight;
					styleTod = csBorderBottom;
					styleTodRight = csBorderBottomRight;
			
					if (person.getGeburt() != null && person.getGeburt().isNachweis() && person.getTod() != null && person.getTod().isNachweis()) {
						style = csGreen;
					}
					if (person.getGeburt() != null && person.getGeburt().isNachweis()) {
						styleGeburt = csGreen;
						styleGeburtRight = csRightGreen;
					}
					
					if (person.getTod() != null && person.getTod().isNachweis()) {
						styleTod = csBorderBottomGreen;
						styleTodRight = csBorderBottomRightGreen;
					}
				}
				
				row = sheet.createRow(r++);
				
				createCell(row, 0, style, String.valueOf(person.getKekule()).replace("[", "").replace("]", ""));
				sheet.addMergedRegion(new CellRangeAddress(r - 1, r, 0, 0));

				createCell(row, 1, style, person.getNachname());
				sheet.addMergedRegion(new CellRangeAddress(r - 1, r, 1, 1));

				createCell(row, 2, style, person.getVornamen());
				sheet.addMergedRegion(new CellRangeAddress(r - 1, r, 2, 2));

				ReligionEnum konfession = ReligionEnum.fromLabel(person.getKonfession());
				createCell(row, 3, style, konfession == null ? "" : String.valueOf(konfession));
				sheet.addMergedRegion(new CellRangeAddress(r - 1, r, 3, 3));

				createCell(row, 4, styleGeburt, "*");
				createCell(row, 5, styleGeburt, eventConverter.getAsString(null, null, person.getGeburt()));
				createCell(row, 6, styleGeburtRight, person.getGeburt().getLocation());
				createCell(row, 7, styleGeburt, person.getGeburt().isNachweis() ? "X" : "");
				createCell(row, 8, styleGeburt, person.getGeburt().getSource());
				createCell(row, 9, styleGeburt, person.getGeburt().isFamilySearch() ? "X" : "");
				createCell(row, 10, styleGeburt, person.getGeburt().getNotice());

				row = sheet.createRow(r++);
				
				createCell(row, 4, styleTod, "†");
				createCell(row, 5, styleTod, eventConverter.getAsString(null, null, person.getTod()));
				createCell(row, 6, styleTodRight, person.getTod().getLocation());
				createCell(row, 7, styleTod, person.getTod().isNachweis() ? "X" : "");
				createCell(row, 8, styleTod, person.getTod().getSource());
				createCell(row, 9, styleTod, person.getTod().isFamilySearch() ? "X" : "");
				createCell(row, 10, styleTod, person.getTod().getNotice());
				
				if (person.isMann()) {
					row = sheet.createRow(r++);
					
					for (int i = 0; i < 4; i++) {
						createCell(row, i, styleHochzeit, "");
					}
					
					createCell(row, 4, styleHochzeit, "∞");
					if (person.getHochzeiten() != null && person.getHochzeiten().size() > 0) {
						createCell(row, 5, styleHochzeit, eventConverter.getAsString(null, null, person.getHochzeiten().get(0).getHochzeit()));
						createCell(row, 6, styleHochzeitRight, person.getHochzeiten().get(0).getHochzeit().getLocation());
						createCell(row, 7, styleHochzeit, person.getHochzeiten().get(0).getHochzeit().isNachweis() ? "X" : "");
						createCell(row, 8, styleHochzeit, person.getHochzeiten().get(0).getHochzeit().getSource());
						createCell(row, 9, styleHochzeit, person.getHochzeiten().get(0).getHochzeit().isFamilySearch() ? "X" : "");
						createCell(row, 10, styleHochzeit, person.getHochzeiten().get(0).getHochzeit().getNotice());
					}
				}
			}
		}
		
		try {
			String filename = "export_" + Calendar.getInstance().getTimeInMillis() + ".xlsx";
	        File file = new File(
	                System.getProperty("catalina.home") + System.getProperty("file.separator") + "temp" + System
	                        .getProperty("file.separator") + filename);
	        System.out.println(file.getAbsolutePath());
	        OutputStream os = new FileOutputStream(file);
	        workbook.write(os);
	        os.close();
	        workbook.close();
		} catch (Exception e)  {
			// TODO
		}
	}
	
	private void createCell(XSSFRow row, int column, XSSFCellStyle style, String value) {
		XSSFCell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	
	@SuppressWarnings("unused")
	private void createCell(XSSFRow row, int column, XSSFCellStyle style, boolean value) {
		XSSFCell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	
}

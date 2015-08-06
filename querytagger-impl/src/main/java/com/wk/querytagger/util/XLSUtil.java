package com.wk.querytagger.util;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.wk.querytagger.impl.consts.Consts;
import com.wolterskluwer.services.types.common.SecurityToken;
import com.wolterskluwer.services.types.folder.Folder;
import com.wolterskluwer.services.types.folder.FolderId;
import com.wolterskluwer.services.types.folder.FolderItem;

public class XLSUtil {	
	
	public static HSSFWorkbook getQueryPhrasesData(SecurityToken sToken) {		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Current");
		
		// Populate result *.xls headers		
		Row headerRow = sheet.createRow(0);
		int cellNum = 0;
		for(String header : Consts.QUERY_PHRASE_HEADERS) {
			Cell cell = headerRow.createCell(cellNum++);
			cell.setCellValue(header);
		}
		
		List<Folder> userFolders = CommonUtil.getUserFolders(sToken);
		int rowIndex = 1;
		for(Folder fold : userFolders) {
			FolderId fId = fold.getFolderMetadata().getFolderId();
			String fName = fold.getFolderMetadata().getTitle();
			List<FolderItem> folderItems = CommonUtil.getFolderItems(sToken, fId);			
			for(FolderItem fItem : folderItems) {
				Row docRow = sheet.createRow(rowIndex++);
				Cell pidCell = docRow.createCell(0);
				pidCell.setCellValue(fItem.getItemId().getId().split(";")[1]);
				Cell qpCell = docRow.createCell(1);
				qpCell.setCellValue(fName);
				Cell headingCell = docRow.createCell(2);
				headingCell.setCellValue(fItem.getTitle());
				
				// TODO - Investigage, what should be stored in this cell.				
				Cell pubvolCell = docRow.createCell(3);
				pubvolCell.setCellValue("?");
				
				Cell dateInsCell = docRow.createCell(4);
				XMLGregorianCalendar dateInserted = fItem.getAddDateTime();
				String formattedDate = dateInserted.getDay() + "/" + dateInserted.getMonth() + "/" + dateInserted.getYear();
				dateInsCell.setCellValue(formattedDate);
								
			}
		}
		
		return workbook;
	}
}

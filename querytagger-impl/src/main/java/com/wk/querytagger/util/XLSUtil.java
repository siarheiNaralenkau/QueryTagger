package com.wk.querytagger.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.wk.querytagger.impl.consts.Consts;
import com.wolterskluwer.services.types.common.SecurityToken;
import com.wolterskluwer.services.types.documents.DocumentId;
import com.wolterskluwer.services.types.documents.DocumentMetadata;
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
				
				String documentId = fItem.getItemId().getId().split(";")[1];
				
				pidCell.setCellValue(documentId);
				Cell qpCell = docRow.createCell(1);
				qpCell.setCellValue(fName);
				Cell headingCell = docRow.createCell(2);
				headingCell.setCellValue(fItem.getTitle());
				
				// TODO - Investigage, what should be stored in this cell.	
				DocumentId docId = new DocumentId();
				docId.setDocumentIdentifier(documentId);
				DocumentMetadata docMeta = CommonUtil.getDocMeta(sToken, docId);
//				String pubvol = docMeta.getSubTitle();
				
				Cell pubvolCell = docRow.createCell(3);
				CommonUtil.getDocExtendedMeta(sToken, docId);
//				pubvolCell.setCellValue(pubvol);
				
				Cell dateInsCell = docRow.createCell(4);
				XMLGregorianCalendar dateInserted = fItem.getAddDateTime();
				String formattedDate = dateInserted.getDay() + "/" + dateInserted.getMonth() + "/" + dateInserted.getYear();
				dateInsCell.setCellValue(formattedDate);
								
			}
		}
		
		return workbook;
	}
	
	
	public static Map<String, List<String>> getDocumentsToImport(HSSFWorkbook workbook) {
		Map<String, List<String>> docsToImport = new LinkedHashMap<String, List<String>>();
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		if(rowIterator.hasNext()) {
			rowIterator.next();
			while(rowIterator.hasNext()) {
				Row docRow = rowIterator.next();
				Cell docIdCell = docRow.getCell(0);
				String docId = docIdCell.getStringCellValue();
				Cell folderNameCell = docRow.getCell(1);
				String folderName = folderNameCell.getStringCellValue();
				if(!docsToImport.containsKey(folderName)) {
					docsToImport.put(folderName, new ArrayList<String>());					
				}
				docsToImport.get(folderName).add(docId);
			}
		}
		return docsToImport;
	}
}

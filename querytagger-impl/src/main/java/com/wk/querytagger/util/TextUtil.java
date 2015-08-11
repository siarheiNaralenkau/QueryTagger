package com.wk.querytagger.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TextUtil {
	public static Map<String, List<String>> getDocumentsToImport(InputStream textInput) {
		Map<String, List<String>> docsToImport = new LinkedHashMap<String, List<String>>();
		Scanner scan = new Scanner(textInput).useDelimiter("\\A");
		String fileData = scan.hasNext() ? scan.next() : "";		
		// Parse file data.
		String[] foldersData = fileData.split("\r\n");
		for(String foldData : foldersData) {
			List<String> docsList = new ArrayList<String>();
			String folderName = foldData.substring(0, foldData.indexOf(" #ANSWERSET"));
			String docIds = foldData.substring(foldData.indexOf("#ANSWERSET") + 11, foldData.length());
			for(String docId : docIds.split(",")) {
				docsList.add(docId);
			}
			docsToImport.put(folderName, docsList);
		}
		return docsToImport;
	}
}

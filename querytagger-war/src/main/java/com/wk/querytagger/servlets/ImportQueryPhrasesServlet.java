package com.wk.querytagger.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.wk.querytagger.consts.Consts;
import com.wk.querytagger.util.CommonUtil;
import com.wk.querytagger.util.XLSUtil;
import com.wolterskluwer.services.types.common.SecurityToken;

@WebServlet("/import_query_phrases")
@MultipartConfig(
		fileSizeThreshold=20*1024*1024,
		maxFileSize=20*1024*1024,
		maxRequestSize=100*1024*1024
		)
public class ImportQueryPhrasesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImportQueryPhrasesServlet() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SecurityToken sToken = (SecurityToken)request.getSession().getAttribute(Consts.SECURITY_TOKEN);		
		if(sToken == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(Consts.JSP_LOGIN);
			dispatcher.forward(request, response);
		} else {			
			Part filePart = request.getPart("importFile");
			
			InputStream fileContent = filePart.getInputStream();
			HSSFWorkbook workbook = new HSSFWorkbook(fileContent);
			Map<String, List<String>> docToImport = XLSUtil.getDocumentsToImport(workbook);
			CommonUtil.importDocuments(sToken, docToImport);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(Consts.JSP_MAIN);
			dispatcher.forward(request, response);
		}
	}

}

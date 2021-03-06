package com.wk.querytagger.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.wk.querytagger.consts.Consts;
import com.wk.querytagger.util.CommonUtil;
import com.wk.querytagger.util.TextUtil;
import com.wolterskluwer.services.types.common.SecurityToken;

@WebServlet("/import_answer_sets")
@MultipartConfig(
		fileSizeThreshold=20*1024*1024,
		maxFileSize=20*1024*1024,
		maxRequestSize=100*1024*1024
)
public class ImportAnswerSetsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public ImportAnswerSetsServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SecurityToken sToken = (SecurityToken)request.getSession().getAttribute(Consts.SECURITY_TOKEN);
		if(sToken == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(Consts.JSP_LOGIN);
			dispatcher.forward(request, response);
		} else {
			Part filePart = request.getPart(Consts.IMPORT_FILE);			
			InputStream fileContent = filePart.getInputStream(); 
			CommonUtil.importDocuments(sToken, TextUtil.getDocumentsToImport(fileContent));
			RequestDispatcher dispatcher = request.getRequestDispatcher(Consts.JSP_MAIN);
			dispatcher.forward(request, response);
		}
	}

}

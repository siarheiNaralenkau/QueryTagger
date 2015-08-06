package com.wk.querytagger.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.wk.querytagger.consts.Consts;
import com.wk.querytagger.util.XLSUtil;
import com.wolterskluwer.services.types.common.SecurityToken;

public class QueryPhrasesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public QueryPhrasesServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SecurityToken sToken = (SecurityToken)request.getSession().getAttribute(Consts.SECURITY_TOKEN);		
		if(sToken == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(Consts.JSP_LOGIN);
			dispatcher.forward(request, response);
		} else {
			HSSFWorkbook workbook = XLSUtil.getQueryPhrasesData(sToken);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition","attachment; filename=query-phrases.xls");
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);		
			out.flush();
			out.close();
		}
				
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

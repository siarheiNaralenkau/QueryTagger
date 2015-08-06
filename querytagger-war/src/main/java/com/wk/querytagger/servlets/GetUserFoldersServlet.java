package com.wk.querytagger.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wk.querytagger.adapter.RsiAdapter;
import com.wk.querytagger.factory.RsiAdapterFactory;
import com.wk.querytagger.util.CommonUtil;
import com.wolterskluwer.services.docs.rsi.GetFolderById;
import com.wolterskluwer.services.docs.rsi.GetFolderByIdResponse;
import com.wolterskluwer.services.docs.rsi.GetUserFolders;
import com.wolterskluwer.services.docs.rsi.GetUserFoldersResponse;
import com.wolterskluwer.services.types.common.SecurityToken;
import com.wolterskluwer.services.types.folder.Folder;
import com.wolterskluwer.services.types.folder.FolderId;
import com.wolterskluwer.services.types.folder.FolderItem;

public class GetUserFoldersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RsiAdapter rsiAdapter;
       
    public GetUserFoldersServlet() {
        super();
    }
    
    public void init() throws ServletException {
    	rsiAdapter = RsiAdapterFactory.getInstance().getRsiAdapter();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetUserFoldersResponse uFoldResp = null;
		GetFolderByIdResponse foldByIdResp = null;
		SecurityToken sToken = (SecurityToken)request.getSession().getAttribute("securityToken");
		if(sToken != null) {
			GetUserFolders uFoldersReq = CommonUtil.buildUserFoldersRequest(sToken);
			uFoldResp = rsiAdapter.getUserFolders(uFoldersReq);
			List<Folder> foldersList = uFoldResp.getFolders();
			for(Folder fold : foldersList) {				
				FolderId fId = fold.getFolderMetadata().getFolderId();
				System.out.println("FolderId: " + fId.getId());
				GetFolderById fByIdRequest = CommonUtil.buildFolderByIdRequest(sToken, fId);
				foldByIdResp = rsiAdapter.getFolderById(fByIdRequest);
				List<FolderItem> items = foldByIdResp.getFolder().getItemList();
				for(FolderItem item : items) {
					System.out.println("DocumentId: " + item.getItemId().getId());
				}
			}
		} else {
			response.getWriter().write("Authentication required!");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

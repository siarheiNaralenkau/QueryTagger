package com.wk.querytagger.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wk.querytagger.adapter.IdentityAdapter;
import com.wk.querytagger.adapter.RsiAdapter;
import com.wk.querytagger.consts.Consts;
import com.wk.querytagger.factory.IdentityAdapterFactory;
import com.wk.querytagger.factory.RsiAdapterFactory;
import com.wk.querytagger.util.CommonUtil;
import com.wolterskluwer.services.common.faults.CommonClientFault;
import com.wolterskluwer.services.common.faults.InvalidProductFault;
import com.wolterskluwer.services.common.faults.MissingProductFault;
import com.wolterskluwer.services.common.faults.unchecked.UnexpectedServerFault;
import com.wolterskluwer.services.docs.identity.Authenticate;
import com.wolterskluwer.services.docs.identity.AuthenticateResponse;
import com.wolterskluwer.services.docs.rsi.GetFolderById;
import com.wolterskluwer.services.docs.rsi.GetFolderByIdResponse;
import com.wolterskluwer.services.docs.rsi.GetUserFolders;
import com.wolterskluwer.services.docs.rsi.GetUserFoldersResponse;
import com.wolterskluwer.services.identity.AuthenticationFailedFault;
import com.wolterskluwer.services.identity.IncompleteCredentialsFault;
import com.wolterskluwer.services.identity.InvalidCredentialsFault;
import com.wolterskluwer.services.identity.SubscriptionErrorFault;
import com.wolterskluwer.services.types.common.SecurityToken;
import com.wolterskluwer.services.types.folder.Folder;
import com.wolterskluwer.services.types.folder.FolderId;
import com.wolterskluwer.services.types.folder.FolderItem;

public class GoldenAnswerSetsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IdentityAdapter identityAdapter;
	private RsiAdapter rsiAdapter;
	
	@Override
	public void init() throws ServletException {
		identityAdapter = IdentityAdapterFactory.getInstance().getIdentityAdapter();
		rsiAdapter = RsiAdapterFactory.getInstance().getRsiAdapter();
	}
           
    public GoldenAnswerSetsServlet() {
        super();       
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SecurityToken sToken = (SecurityToken)request.getSession().getAttribute(Consts.SECURITY_TOKEN);
		String responseText = "";
		if(sToken == null) {
			Authenticate authRequest = CommonUtil.buildAuthenticateRequest(request.getParameter(Consts.LOGIN), request.getParameter(Consts.PASSWORD));
			try {
				AuthenticateResponse authResponse = identityAdapter.authenticate(authRequest);
				sToken = authResponse.getSecurityToken();
				request.getSession().setAttribute(Consts.SECURITY_TOKEN, sToken);
				responseText = getGoldenAnswerSets(sToken);
			} catch (InvalidCredentialsFault e) {
				e.printStackTrace();
			} catch (AuthenticationFailedFault e) {
				e.printStackTrace();
			} catch (UnexpectedServerFault e) {
				e.printStackTrace();
			} catch (SubscriptionErrorFault e) {
				e.printStackTrace();
			} catch (CommonClientFault e) {
				e.printStackTrace();
			} catch (IncompleteCredentialsFault e) {
				e.printStackTrace();
			} catch (MissingProductFault e) {
				e.printStackTrace();
			} catch (InvalidProductFault e) {
				e.printStackTrace();
			}
		} else {
			responseText = getGoldenAnswerSets(sToken);
		}
		
		response.setContentType("text/plain");
		response.setHeader("Content-disposition","attachment; filename=answers_set.txt");
		OutputStream out = response.getOutputStream();
		out.write(responseText.getBytes());		
		out.flush();
	}
	
	private String getGoldenAnswerSets(SecurityToken sToken) {
		StringBuilder sbResult = new StringBuilder(); 
		
		GetUserFoldersResponse userFoldResponse = null;
		GetFolderByIdResponse foldByIdResponse = null;
		
		GetUserFolders userFoldRequest = CommonUtil.buildUserFoldersRequest(sToken);
		userFoldResponse = rsiAdapter.getUserFolders(userFoldRequest);
		List<Folder> foldList = userFoldResponse.getFolders();
		for(Folder fold : foldList) {
			FolderId fId = fold.getFolderMetadata().getFolderId();
			String fName = fold.getFolderMetadata().getTitle();
			sbResult.append(fName).append(" #ANSWERSET ");
			GetFolderById foldByIdRequest = CommonUtil.buildFolderByIdRequest(sToken, fId);
			foldByIdResponse = rsiAdapter.getFolderById(foldByIdRequest);
			List<FolderItem> items = foldByIdResponse.getFolder().getItemList();
			for(int i = 0; i < items.size(); i++) {
				String docId = items.get(i).getItemId().getId();				
				docId = docId.split(";")[1];
				sbResult.append(docId);
				if(i < items.size()-1) {
					sbResult.append(", ");
				}
			}
			sbResult.append("\r\n");
		}
		
		return sbResult.toString();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

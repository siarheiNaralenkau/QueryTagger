package com.wk.querytagger.adapter;

import com.wolterskluwer.services.bindings.rsi.client.ws.RsiWebServiceClientFactory;
import com.wolterskluwer.services.common.faults.CommonClientFault;
import com.wolterskluwer.services.common.faults.InvalidIdFault;
import com.wolterskluwer.services.common.faults.InvalidProductFault;
import com.wolterskluwer.services.common.faults.MissingProductFault;
import com.wolterskluwer.services.common.faults.unchecked.UnexpectedServerFault;
import com.wolterskluwer.services.docs.rsi.GetFolderById;
import com.wolterskluwer.services.docs.rsi.GetFolderByIdResponse;
import com.wolterskluwer.services.docs.rsi.GetUserFolders;
import com.wolterskluwer.services.docs.rsi.GetUserFoldersResponse;
import com.wolterskluwer.services.rsi.Folder;

public class RsiIntelliconnectAdapter implements RsiAdapter {
	private Folder foldersClient;
	
//	private static final String XSLT_LOCATION = "xslt/common.xsl";
	
	@Override
	public GetUserFoldersResponse getUserFolders(GetUserFolders foldersRequest) {		
		GetUserFoldersResponse userFoldersResp = null;
		foldersClient = RsiWebServiceClientFactory.getInstance().getFolderClient();
	
		try {
			userFoldersResp = foldersClient.getUserFolders(foldersRequest);
		} catch (UnexpectedServerFault e) {
			e.printStackTrace();
		} catch (MissingProductFault e) {
			e.printStackTrace();
		} catch (CommonClientFault e) {
			e.printStackTrace();
		} catch (InvalidProductFault e) {
			e.printStackTrace();
		}
		
		return userFoldersResp;
	}

	@Override
	public GetFolderByIdResponse getFolderById(GetFolderById folderRequest) {
		GetFolderByIdResponse folderResponse = null;
		Folder foldersClient = RsiWebServiceClientFactory.getInstance().getFolderClient();
		try {
			folderResponse = foldersClient.getFolderById(folderRequest);
		} catch (UnexpectedServerFault e) {
			e.printStackTrace();
		} catch (MissingProductFault e) {
			e.printStackTrace();
		} catch (CommonClientFault e) {
			e.printStackTrace();
		} catch (InvalidIdFault e) {
			e.printStackTrace();
		} catch (InvalidProductFault e) {
			e.printStackTrace();
		}
		return folderResponse;
	}

}

package com.wk.querytagger.adapter;

import java.util.List;

import com.wolterskluwer.services.bindings.rsi.client.DocumentClient;
import com.wolterskluwer.services.bindings.rsi.client.FolderClient;
import com.wolterskluwer.services.bindings.rsi.client.ws.RsiWebServiceClientFactory;
import com.wolterskluwer.services.common.faults.CommonClientFault;
import com.wolterskluwer.services.common.faults.InvalidIdFault;
import com.wolterskluwer.services.common.faults.InvalidProductFault;
import com.wolterskluwer.services.common.faults.MissingProductFault;
import com.wolterskluwer.services.common.faults.NotAuthorizedFault;
import com.wolterskluwer.services.common.faults.unchecked.UnexpectedServerFault;
import com.wolterskluwer.services.docs.rsi.AddItemsToFolder;
import com.wolterskluwer.services.docs.rsi.AddItemsToFolderResponse;
import com.wolterskluwer.services.docs.rsi.CreateFolder;
import com.wolterskluwer.services.docs.rsi.CreateFolderResponse;
import com.wolterskluwer.services.docs.rsi.GetDocumentMetadata;
import com.wolterskluwer.services.docs.rsi.GetDocumentMetadataResponse;
import com.wolterskluwer.services.docs.rsi.GetExtendedMetadataForDocuments;
import com.wolterskluwer.services.docs.rsi.GetExtendedMetadataForDocumentsResponse;
import com.wolterskluwer.services.docs.rsi.GetFolderById;
import com.wolterskluwer.services.docs.rsi.GetFolderByIdResponse;
import com.wolterskluwer.services.docs.rsi.GetUserFolders;
import com.wolterskluwer.services.docs.rsi.GetUserFoldersResponse;
import com.wolterskluwer.services.rsi.DocumentNotInWorkspaceFault;
import com.wolterskluwer.services.rsi.Folder;
import com.wolterskluwer.services.rsi.MaxFolderItemsExceededFault;
import com.wolterskluwer.services.rsi.MaxFoldersExceededFault;
import com.wolterskluwer.services.types.documents.ExtendedDocumentMetadata;

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

	@Override
	public GetDocumentMetadataResponse getDocumentMeta(GetDocumentMetadata docMetaRequest) {
		GetDocumentMetadataResponse docMetaResponse = null;
		DocumentClient docClient = RsiWebServiceClientFactory.getInstance().getDocumentClient();
		
		try {
			docMetaResponse = docClient.getDocumentMetadata(docMetaRequest);
		} catch (MissingProductFault e) {
			e.printStackTrace();
		} catch (DocumentNotInWorkspaceFault e) {
			e.printStackTrace();
		} catch (CommonClientFault e) {
			e.printStackTrace();
		} catch (NotAuthorizedFault e) {
			e.printStackTrace();
		} catch (InvalidIdFault e) {
			e.printStackTrace();
		} catch (InvalidProductFault e) {
			e.printStackTrace();
		}
		
		return docMetaResponse;
	}

	@Override
	public CreateFolderResponse createFolder(CreateFolder createFolderRequest) {
		CreateFolderResponse response = null;
		FolderClient foldClient = RsiWebServiceClientFactory.getInstance().getFolderClient();
		try {
			response = foldClient.createFolder(createFolderRequest);
		} catch (MaxFolderItemsExceededFault e) {			
			e.printStackTrace();
		} catch (MissingProductFault e) {
			e.printStackTrace();
		} catch (CommonClientFault e) {
			e.printStackTrace();
		} catch (MaxFoldersExceededFault e) {
			e.printStackTrace();
		} catch (InvalidProductFault e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public AddItemsToFolderResponse addItemsToFolder(AddItemsToFolder addItemsReq) {
		AddItemsToFolderResponse response = null;	
		FolderClient foldClient = RsiWebServiceClientFactory.getInstance().getFolderClient();		
		try {
			response = foldClient.addItemsToFolder(addItemsReq);
		} catch (MaxFolderItemsExceededFault e) {
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

		return response;
	}

	@Override
	public GetExtendedMetadataForDocumentsResponse getDocExtendedMeta(GetExtendedMetadataForDocuments extMetaRequest) {
		GetExtendedMetadataForDocumentsResponse response = null;
		DocumentClient docClient = RsiWebServiceClientFactory.getInstance().getDocumentClient();		
		try {
			response = docClient.getExtendedMetadataForDocuments(extMetaRequest);
		} catch (MissingProductFault e) {			
			e.printStackTrace();
		} catch (CommonClientFault e) {			
			e.printStackTrace();
		} catch (NotAuthorizedFault e) {
			e.printStackTrace();
		} catch (InvalidIdFault e) {
			e.printStackTrace();
		} catch (InvalidProductFault e) {
			e.printStackTrace();
		}		
		return response;
	}

}

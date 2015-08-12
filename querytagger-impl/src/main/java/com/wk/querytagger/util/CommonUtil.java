package com.wk.querytagger.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wk.querytagger.adapter.RsiAdapter;
import com.wk.querytagger.factory.RsiAdapterFactory;
import com.wolterskluwer.services.docs.identity.Authenticate;
import com.wolterskluwer.services.docs.identity.Logout;
import com.wolterskluwer.services.docs.rsi.AddItemsToFolder;
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
import com.wolterskluwer.services.types.common.Credentials;
import com.wolterskluwer.services.types.common.PagingInfo;
import com.wolterskluwer.services.types.common.ProductId;
import com.wolterskluwer.services.types.common.SecurityToken;
import com.wolterskluwer.services.types.documents.DocumentId;
import com.wolterskluwer.services.types.documents.DocumentMetadata;
import com.wolterskluwer.services.types.documents.ExtendedDocumentMetadata;
import com.wolterskluwer.services.types.folder.DocFolderItem;
import com.wolterskluwer.services.types.folder.Folder;
import com.wolterskluwer.services.types.folder.FolderId;
import com.wolterskluwer.services.types.folder.FolderItem;
import com.wolterskluwer.services.types.folder.ItemInfo;
import com.wolterskluwer.services.types.message.ClientId;
import com.wolterskluwer.services.types.message.ClientInfo;
import com.wolterskluwer.services.types.message.InstanceId;
import com.wolterskluwer.services.types.message.OsaClientInfo;
import com.wolterskluwer.services.types.message.RequestInfo;

public class CommonUtil {
	private static final String IP = "127.0.0.1";	
	
	private static RsiAdapter rsiAdapter = RsiAdapterFactory.getInstance().getRsiAdapter();
    
    public static Authenticate buildAuthenticateRequest(String loginId, String password) {
        Authenticate authenticate = new Authenticate();
        
        authenticate.setRequestInfo(buildRequestInfo(null, null));
        
        Credentials credentials = new Credentials();
        credentials.setLoginId(loginId);
        credentials.setPassword(password);
        credentials.setIpAddress(IP);
        authenticate.setCredentials(credentials);

        return authenticate;
    }
    
    public static Logout buildLogout(SecurityToken securityToken, String sessionTicket) {
        Logout logout = new Logout();
        logout.setRequestInfo(buildRequestInfo(securityToken, sessionTicket));
        
        return logout;
    }
    
    public static GetUserFolders buildUserFoldersRequest(SecurityToken securityToken) {
    	GetUserFolders userFoldersReq = new GetUserFolders();
    	
    	userFoldersReq.setRequestInfo(buildRsiRequerstInfo(securityToken));
    	
    	PagingInfo pagingInfo = new PagingInfo();
    	pagingInfo.setStartItemIndex(0);
    	pagingInfo.setNumberOfItems(1000);
    	userFoldersReq.setPage(pagingInfo);
    	
    	return userFoldersReq;
    }
    
    public static GetFolderById buildFolderByIdRequest(SecurityToken securityToken, FolderId fId) {
    	GetFolderById foldById = new GetFolderById();
    	foldById.setRequestInfo(buildRsiRequerstInfo(securityToken));    	    	
    	foldById.setFolderId(fId);    	    	
    	return foldById;
    }
    
    public static GetDocumentMetadata buildDocumentMetadataRequest(SecurityToken sToken, DocumentId docId) {
    	GetDocumentMetadata docMetaReq = new GetDocumentMetadata();    	
    	docMetaReq.setDocId(docId);
    	docMetaReq.setRequestInfo(buildRsiRequerstInfo(sToken));
    	return docMetaReq;
    }
    
    public static List<Folder> getUserFolders(SecurityToken sToken) {
    	GetUserFolders userFoldRequest = buildUserFoldersRequest(sToken);
		GetUserFoldersResponse userFoldResponse = rsiAdapter.getUserFolders(userFoldRequest);
		List<Folder> foldList = userFoldResponse.getFolders();
		return foldList;
    }
    
    public static List<FolderItem> getFolderItems(SecurityToken sToken, FolderId fId) {
    	GetFolderById foldByIdRequest = buildFolderByIdRequest(sToken, fId);
    	GetFolderByIdResponse foldByIdResponse = rsiAdapter.getFolderById(foldByIdRequest);
    	List<FolderItem> items = foldByIdResponse.getFolder().getItemList();
    	return items;
    }
    
    public static DocumentMetadata getDocMeta(SecurityToken sToken, DocumentId docId) {
    	GetDocumentMetadata docMetaReq = buildDocumentMetadataRequest(sToken, docId);
    	GetDocumentMetadataResponse docMetaRes = rsiAdapter.getDocumentMeta(docMetaReq);
    	DocumentMetadata docMeta = docMetaRes.getMetadata();    	
    	return docMeta;
    }
    
    public static void importDocuments(SecurityToken sToken, Map<String, List<String>> docToImport) {
    	List<Folder> existingFolders = getUserFolders(sToken);
    	Map<String, FolderId> existingFolderIds = new HashMap<String, FolderId>();
    	for(Folder fold : existingFolders) {
    		existingFolderIds.put(fold.getFolderMetadata().getTitle(), fold.getFolderMetadata().getFolderId());
    	}
    	
    	for(String folderName : docToImport.keySet()) {
    		FolderId folderId;
    		if(!existingFolderIds.containsKey(folderName)) {    			    		
    			CreateFolder createFolderReq = buildCreateFolderRequest(sToken, folderName);
    			CreateFolderResponse response = rsiAdapter.createFolder(createFolderReq);
    			folderId = response.getFolder().getFolderMetadata().getFolderId();
    		} else {
    			folderId = existingFolderIds.get(folderName);
    		}
    		// Adding document items to newly created folder.  
    		List<ItemInfo> documentsList = new ArrayList<ItemInfo>();
    		for(String documentId : docToImport.get(folderName)) {
    			ItemInfo itemInfo = new ItemInfo();
    			DocFolderItem docItem = new DocFolderItem();
    			DocumentId docId = new DocumentId();
    			docId.setDocumentIdentifier(documentId);
    			docItem.setDocId(docId);
    			itemInfo.setDocumentFolderItem(docItem);
    			documentsList.add(itemInfo);
    		}
    		addDocumentsToFolder(sToken, folderId, documentsList);    		
    	}
    }
    
    public static void addDocumentsToFolder(SecurityToken sToken, FolderId folderId, List<ItemInfo> items) {    	
    	AddItemsToFolder addItemsReq = new AddItemsToFolder();    	
    	addItemsReq.setRequestInfo(buildRsiRequerstInfo(sToken));
    	addItemsReq.setFolderId(folderId); 
    	addItemsReq.getItems().addAll(items);
    	rsiAdapter.addItemsToFolder(addItemsReq);    	
    }
    
    public static void getDocExtendedMeta(SecurityToken sToken, DocumentId docId) {
    	GetExtendedMetadataForDocuments extMetaReq = new GetExtendedMetadataForDocuments();
    	extMetaReq.setRequestInfo(buildMetadataRequestInfo(sToken));
    	extMetaReq.getDocIds().add(docId);
    	extMetaReq.getExtendedFields().add("da-id");
    	extMetaReq.getExtendedFields().add("pubvol");
    	GetExtendedMetadataForDocumentsResponse extMetaResp = rsiAdapter.getDocExtendedMeta(extMetaReq);
    	List<ExtendedDocumentMetadata> extMetaList = extMetaResp.getMetadata();
    	System.out.println("Extended metadata received!");
    }
    
    private static CreateFolder buildCreateFolderRequest(SecurityToken sToken, String folderName) {
    	CreateFolder createFolderReq = new CreateFolder();
    	createFolderReq.setTitle(folderName);    	
    	createFolderReq.setRequestInfo(buildRsiRequerstInfo(sToken));
    	return createFolderReq;
    }
    
    private static RequestInfo buildRequestInfo(SecurityToken securityToken, String sessionTicket) {
        RequestInfo rInfo = new RequestInfo();
        if (securityToken != null) {
            rInfo.setSecurityToken(securityToken);
        }

        OsaClientInfo osaClientInfo = new OsaClientInfo();
        osaClientInfo.setOriginIp(IP);
        osaClientInfo.setLocale("en");
        rInfo.setClientInfo(osaClientInfo);

        return rInfo;
    }
    
    private static RequestInfo buildRsiRequerstInfo(SecurityToken securityToken) {
    	RequestInfo rInfo = new RequestInfo();
    	
    	// Create client info params    	
    	OsaClientInfo clientInfo = new OsaClientInfo();
    	
    	ClientId clientId = new ClientId();
    	clientId.setId("testframework");
    	clientInfo.setClientId(clientId);
    	
    	InstanceId instanceId = new InstanceId();
    	instanceId.setId("1");
    	clientInfo.setInstanceId(instanceId);
    	
    	clientInfo.setOriginIp("1");
    	
    	ClientInfo origin = new ClientInfo();
    	origin.setClientId(clientId);
    	origin.setInstanceId(instanceId);    	
    	clientInfo.setOrigin(origin);
    	
    	rInfo.setClientInfo(clientInfo);
    	
    	rInfo.setSecurityToken(securityToken);
    	
    	ProductId productId = new ProductId();
    	productId.setId("SOLR-TAA-IC");
    	rInfo.setProductId(productId);    	
    	rInfo.setNoCache(true);    	    	
    	return rInfo;
    }       
    
    private static RequestInfo buildMetadataRequestInfo(SecurityToken sToken) {
    	RequestInfo rInfo = new RequestInfo();
    	
    	// Create client info params    	
    	OsaClientInfo clientInfo = new OsaClientInfo();
    	
    	ClientId clientId = new ClientId();
    	clientId.setId("testframework");
    	clientInfo.setClientId(clientId);
    	
    	InstanceId instanceId = new InstanceId();
    	instanceId.setId("1");
    	clientInfo.setInstanceId(instanceId);
    	
    	clientInfo.setOriginIp("127.0.0.1");
    	clientInfo.setOsaSuiteVersion("local");
    	rInfo.setClientInfo(clientInfo);
    	
    	ProductId productId = new ProductId();
    	productId.setId("SOLR-TAA-IC");
    	rInfo.setProductId(productId);
    	
    	rInfo.setSecurityToken(sToken);
    	    	
    	return rInfo;
    }

}

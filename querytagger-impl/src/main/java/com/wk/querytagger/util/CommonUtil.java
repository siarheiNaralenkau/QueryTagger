package com.wk.querytagger.util;

import com.wolterskluwer.services.docs.identity.Authenticate;
import com.wolterskluwer.services.docs.identity.Logout;
import com.wolterskluwer.services.docs.rsi.GetFolderById;
import com.wolterskluwer.services.docs.rsi.GetUserFolders;
import com.wolterskluwer.services.types.common.Credentials;
import com.wolterskluwer.services.types.common.PagingInfo;
import com.wolterskluwer.services.types.common.ProductId;
import com.wolterskluwer.services.types.common.SecurityToken;
import com.wolterskluwer.services.types.folder.FolderId;
import com.wolterskluwer.services.types.message.ClientId;
import com.wolterskluwer.services.types.message.ClientInfo;
import com.wolterskluwer.services.types.message.InstanceId;
import com.wolterskluwer.services.types.message.OsaClientInfo;
import com.wolterskluwer.services.types.message.RequestInfo;

public class CommonUtil {
	private static final String IP = "127.0.0.1";		
    
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

}

package com.wk.querytagger.adapter;

import com.wolterskluwer.services.bindings.identity.client.ws.IdentityWebServiceClientFactory;
import com.wolterskluwer.services.common.faults.CommonClientFault;
import com.wolterskluwer.services.common.faults.InvalidProductFault;
import com.wolterskluwer.services.common.faults.MissingProductFault;
import com.wolterskluwer.services.common.faults.unchecked.UnexpectedServerFault;
import com.wolterskluwer.services.docs.identity.Authenticate;
import com.wolterskluwer.services.docs.identity.AuthenticateResponse;
import com.wolterskluwer.services.docs.identity.Logout;
import com.wolterskluwer.services.docs.identity.LogoutResponse;
import com.wolterskluwer.services.identity.Authentication;
import com.wolterskluwer.services.identity.AuthenticationFailedFault;
import com.wolterskluwer.services.identity.ConcurrentLoginsExceededFault;
import com.wolterskluwer.services.identity.InactiveUserFault;
import com.wolterskluwer.services.identity.IncompleteCredentialsFault;
import com.wolterskluwer.services.identity.InvalidCredentialsFault;
import com.wolterskluwer.services.identity.SubscriptionErrorFault;

public class IdentityIntelliconnectAdapter implements IdentityAdapter {
	
	private Authentication authenticationClient;
	
	public void init() {
		authenticationClient = IdentityWebServiceClientFactory.getInstance().getAuthenticationClient();
	}

	@Override
	public AuthenticateResponse authenticate(Authenticate authenticate)
			throws InvalidCredentialsFault, AuthenticationFailedFault, UnexpectedServerFault, SubscriptionErrorFault,
			CommonClientFault, IncompleteCredentialsFault, MissingProductFault, InvalidProductFault {
		
		init();
		AuthenticateResponse authResp = null;
		
		try {
			authResp = authenticationClient.authenticate(authenticate);
		} catch (InactiveUserFault e) {
			e.printStackTrace();
		} catch (ConcurrentLoginsExceededFault e) {
			e.printStackTrace();
		}
        
        return authResp;
	}

	@Override
	public LogoutResponse logout(Logout logout) throws UnexpectedServerFault, CommonClientFault {
		init();
        return authenticationClient.logout(logout);
	}

}

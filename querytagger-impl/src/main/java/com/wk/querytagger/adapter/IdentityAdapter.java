package com.wk.querytagger.adapter;

import com.wolterskluwer.services.common.faults.CommonClientFault;
import com.wolterskluwer.services.common.faults.InvalidProductFault;
import com.wolterskluwer.services.common.faults.MissingProductFault;
import com.wolterskluwer.services.common.faults.unchecked.UnexpectedServerFault;
import com.wolterskluwer.services.docs.identity.Authenticate;
import com.wolterskluwer.services.docs.identity.AuthenticateResponse;
import com.wolterskluwer.services.docs.identity.Logout;
import com.wolterskluwer.services.docs.identity.LogoutResponse;
import com.wolterskluwer.services.identity.AuthenticationFailedFault;
import com.wolterskluwer.services.identity.IncompleteCredentialsFault;
import com.wolterskluwer.services.identity.InvalidCredentialsFault;
import com.wolterskluwer.services.identity.SubscriptionErrorFault;

public interface IdentityAdapter {
	AuthenticateResponse authenticate(Authenticate authenticate) throws InvalidCredentialsFault, AuthenticationFailedFault, 
			UnexpectedServerFault, SubscriptionErrorFault, CommonClientFault, IncompleteCredentialsFault, MissingProductFault, InvalidProductFault;
	
	LogoutResponse logout(Logout logout) throws UnexpectedServerFault, CommonClientFault;
}

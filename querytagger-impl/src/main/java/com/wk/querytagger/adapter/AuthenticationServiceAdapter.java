package com.wk.querytagger.adapter;

import java.util.UUID;

import com.wolterskluwer.services.bindings.identity.adapters.AuthenticationAdapter;
import com.wolterskluwer.services.common.faults.CommonClientFault;
import com.wolterskluwer.services.common.faults.unchecked.UnexpectedServerFault;
import com.wolterskluwer.services.commons.security.OsaTokenHandler;
import com.wolterskluwer.services.commons.utils.ExceptionUtil;
import com.wolterskluwer.services.commons.utils.SecurityTokenFactory;
import com.wolterskluwer.services.docs.identity.Authenticate;
import com.wolterskluwer.services.docs.identity.AuthenticateResponse;
import com.wolterskluwer.services.docs.identity.ForgotPassword;
import com.wolterskluwer.services.docs.identity.ForgotPasswordResponse;
import com.wolterskluwer.services.docs.identity.ForgotUsername;
import com.wolterskluwer.services.docs.identity.ForgotUsernameResponse;
import com.wolterskluwer.services.docs.identity.GetActiveSession;
import com.wolterskluwer.services.docs.identity.GetActiveSessionResponse;
import com.wolterskluwer.services.docs.identity.GuestLogin;
import com.wolterskluwer.services.docs.identity.GuestLoginResponse;
import com.wolterskluwer.services.docs.identity.IsAuthentic;
import com.wolterskluwer.services.docs.identity.IsAuthenticResponse;
import com.wolterskluwer.services.docs.identity.Logout;
import com.wolterskluwer.services.docs.identity.LogoutResponse;
import com.wolterskluwer.services.docs.identity.RefreshSession;
import com.wolterskluwer.services.docs.identity.RefreshSessionResponse;
import com.wolterskluwer.services.docs.identity.ValidateSession;
import com.wolterskluwer.services.docs.identity.ValidateSessionResponse;
import com.wolterskluwer.services.identity.AuthenticationFailedFault;
import com.wolterskluwer.services.identity.IdentityExceptionUtil;
import com.wolterskluwer.services.identity.IncompleteCredentialsFault;
import com.wolterskluwer.services.identity.InvalidCredentialsFault;
import com.wolterskluwer.services.identity.SubscriptionErrorFault;
import com.wolterskluwer.services.types.common.Credentials;
import com.wolterskluwer.services.types.common.LoginId;
import com.wolterskluwer.services.types.common.SecurityToken;
import com.wolterskluwer.services.types.common.SessionTicket;
import com.wolterskluwer.services.types.common.User;
import com.wolterskluwer.services.types.common.UserKey;
import com.wolterskluwer.services.types.message.AuthenticationType;
import com.wolterskluwer.services.types.message.ResponseInfo;

public class AuthenticationServiceAdapter extends AuthenticationAdapter {
private static final String NOT_IMPLEMENTED_MESSAGE = "Method is not implemented in this implementation for service Authentication";
    
    private static final String LOGIN = "test@wk.com";
    private static final String PASSWORD = "password";
    private final static String INVALID_USER_PASSWORD = "Invalid login or password.";
    private final static String USER_KEY = "0123-4567-8910-1122";

    public AuthenticateResponse authenticate(Authenticate request) throws CommonClientFault, IncompleteCredentialsFault,
            UnexpectedServerFault, AuthenticationFailedFault, SubscriptionErrorFault, InvalidCredentialsFault {
        AuthenticateResponse response = new AuthenticateResponse();
        ResponseInfo respInfo = createDefaultResponseInfo();
        response.setResponseInfo(respInfo);

        if(LOGIN.equals(request.getCredentials().getLoginId()) && PASSWORD.equals(request.getCredentials().getPassword())) {
            User user = createAuthenticatedUser(request.getCredentials());
            response.getAuthenticationTypes().add(AuthenticationType.ID_AND_PASSWORD);

            SessionTicket sessionTicket = createUniqueSessionTicket();
            SecurityToken st = SecurityTokenFactory.createSecurityToken(user, sessionTicket, OsaTokenHandler.class);
            response.setSecurityToken(st);    
        } else {
            throw IdentityExceptionUtil.createAuthenticationFailedFault(INVALID_USER_PASSWORD);
        }
            
        return response;
    }
    
    public LogoutResponse logout(Logout request) throws CommonClientFault, UnexpectedServerFault {
        LogoutResponse response = new LogoutResponse();
        ResponseInfo respInfo = createDefaultResponseInfo();
        response.setResponseInfo(respInfo);                       

        return response;
    }
    
    public GuestLoginResponse guestLogin(GuestLogin request) throws CommonClientFault, UnexpectedServerFault {
        throw ExceptionUtil.createNotImplementedFault(NOT_IMPLEMENTED_MESSAGE);
    }

    public ForgotPasswordResponse forgotPassword(ForgotPassword request) throws CommonClientFault, UnexpectedServerFault {
        throw ExceptionUtil.createNotImplementedFault(NOT_IMPLEMENTED_MESSAGE);
    }

    public ForgotUsernameResponse forgotUsername(ForgotUsername request) throws CommonClientFault, UnexpectedServerFault {
        throw ExceptionUtil.createNotImplementedFault(NOT_IMPLEMENTED_MESSAGE);
    }

    public GetActiveSessionResponse getActiveSession(GetActiveSession request) throws CommonClientFault,
            IncompleteCredentialsFault, UnexpectedServerFault, InvalidCredentialsFault {
        throw ExceptionUtil.createNotImplementedFault(NOT_IMPLEMENTED_MESSAGE);
    }

    public IsAuthenticResponse isAuthentic(IsAuthentic request) throws CommonClientFault, IncompleteCredentialsFault,
            UnexpectedServerFault, AuthenticationFailedFault, InvalidCredentialsFault {
        throw ExceptionUtil.createNotImplementedFault(NOT_IMPLEMENTED_MESSAGE);
    }

    public RefreshSessionResponse refreshSession(RefreshSession request) throws CommonClientFault, UnexpectedServerFault {
        throw ExceptionUtil.createNotImplementedFault(NOT_IMPLEMENTED_MESSAGE);
    }

    public ValidateSessionResponse validateSession(ValidateSession request) throws CommonClientFault,
            UnexpectedServerFault {
        throw ExceptionUtil.createNotImplementedFault(NOT_IMPLEMENTED_MESSAGE);
    }
    
    /**
     * Create unique session ticket
     *
     * @return SessionTicket
     */
    private SessionTicket createUniqueSessionTicket() {
        String ticket = UUID.randomUUID().toString();
        SessionTicket sessionTicket = new SessionTicket();
        sessionTicket.setTicket(ticket);
        return sessionTicket;
    }
    
    /**
     * Creates authenticated user
     *
     * @return User
     */
    private User createAuthenticatedUser(Credentials credentials) {
        User user = new User();
        
        LoginId loginId = new LoginId();
        loginId.setId(credentials.getLoginId());
        user.setLoginId(loginId);
        UserKey userKey = new UserKey();
        String id = USER_KEY;
        userKey.setId(id);
        user.setUserKey(userKey);
        
        return user;
    }
    
    public ResponseInfo createDefaultResponseInfo() {
        ResponseInfo respInfo = new ResponseInfo();
        return respInfo;
    }

}

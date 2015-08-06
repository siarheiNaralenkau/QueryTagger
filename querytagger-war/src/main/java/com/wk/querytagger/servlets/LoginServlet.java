package com.wk.querytagger.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wk.querytagger.adapter.IdentityAdapter;
import com.wk.querytagger.consts.Consts;
import com.wk.querytagger.factory.IdentityAdapterFactory;
import com.wk.querytagger.util.CommonUtil;
import com.wolterskluwer.services.common.faults.CommonClientFault;
import com.wolterskluwer.services.common.faults.InvalidProductFault;
import com.wolterskluwer.services.common.faults.MissingProductFault;
import com.wolterskluwer.services.common.faults.unchecked.UnexpectedServerFault;
import com.wolterskluwer.services.docs.identity.Authenticate;
import com.wolterskluwer.services.docs.identity.AuthenticateResponse;
import com.wolterskluwer.services.identity.AuthenticationFailedFault;
import com.wolterskluwer.services.identity.IncompleteCredentialsFault;
import com.wolterskluwer.services.identity.InvalidCredentialsFault;
import com.wolterskluwer.services.identity.SubscriptionErrorFault;
import com.wolterskluwer.services.types.common.SecurityToken;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
	private IdentityAdapter identityAdapter;
	
	@Override
	public void init() throws ServletException {
		identityAdapter = IdentityAdapterFactory.getInstance().getIdentityAdapter();
	}
    
    public LoginServlet() {
    }	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(Consts.LOGIN);
		String password = request.getParameter(Consts.PASSWORD);		
		
		Authenticate authenticate = CommonUtil.buildAuthenticateRequest(login, password);
		try {
			AuthenticateResponse authResp = identityAdapter.authenticate(authenticate);
			SecurityToken securityToken = authResp.getSecurityToken();
			if(securityToken != null) {
				request.getSession().setAttribute(Consts.SECURITY_TOKEN, securityToken);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Consts.JSP_MAIN);
				dispatcher.forward(request, response);
			}
		} catch (InvalidCredentialsFault e) {
			processError(request, response, e.getMessage());
		} catch (AuthenticationFailedFault e) {
			processError(request, response, e.getMessage());
		} catch (UnexpectedServerFault e) {
			processError(request, response, e.getMessage());
		} catch (SubscriptionErrorFault e) {
			processError(request, response, e.getMessage());
		} catch (CommonClientFault e) {
			processError(request, response, e.getMessage());
		} catch (IncompleteCredentialsFault e) {
			processError(request, response, e.getMessage());
		} catch (MissingProductFault e) {
			processError(request, response, e.getMessage());
		} catch (InvalidProductFault e) {
			processError(request, response, e.getMessage());
		}
	}
	
	private void processError(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
		log.error(message);
		req.setAttribute(Consts.ERROR_MSG, message);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Consts.JSP_ERROR);
		dispatcher.forward(req, resp);
	}
}

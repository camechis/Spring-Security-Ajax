package org.camechis;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AjaxSuccessAuthenticationHandler implements
		AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(
				response);

		Writer out = responseWrapper.getWriter();
		Map<String, Object> authResponse = new HashMap<String, Object>();
		authResponse.put("success", true);
		authResponse.put("user", SecurityContextHolder.getContext()
				.getAuthentication().getName());
		List<String> authorities = new ArrayList<String>();
		for (GrantedAuthority auth : SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities()) {
			authorities.add(auth.getAuthority());
		}
		authResponse.put("authorities", authorities);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, authResponse);
		out.close();

	}

}

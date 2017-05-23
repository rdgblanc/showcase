package br.com.vitrinedecristal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.vitrinedecristal.util.RequestUtil;

@WebFilter(filterName = "AllowOriginFilter", urlPatterns = "/*")
public class AllowOriginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;

		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Credentials", "true");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, HEAD, OPTIONS");
		res.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		
		HttpServletRequest req = (HttpServletRequest) request;
		// Just ACCEPT and REPLY OK if OPTIONS
	    if (req.getMethod().equals("OPTIONS")) {
	        res.setStatus(HttpServletResponse.SC_OK);
	        return;
	    }
		
		chain.doFilter(request, response);

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String ipAddress = httpRequest.getHeader("X-FORWARDED-FOR");
		// System.out.println("IP address (X-FORWARDED-FOR) '" + ipAddress + "'");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
			// System.out.println("IP address (remoteAddr) '" + ipAddress + "'");
		}
		RequestUtil.getInstance().setRequestIp(ipAddress);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
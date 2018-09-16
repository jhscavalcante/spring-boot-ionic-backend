package com.nelioalves.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter {

	@Override
	public void destroy() {		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse res = (HttpServletResponse) response;
		
		// expondo conteúdo de resposta (location) do cabeçalho para requisões externas ao meu sistema backend
		res.addHeader("access-control-expose-headers", "location"); 
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}

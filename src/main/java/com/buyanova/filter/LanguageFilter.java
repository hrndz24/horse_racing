package com.buyanova.filter;

import com.buyanova.command.JSPParameter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LanguageFilter implements Filter {

    private static final String DEFAULT_LOCALE = "es";

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getSession().getAttribute(JSPParameter.LANGUAGE.getParameter()) == null) {
            req.getSession().setAttribute(JSPParameter.LANGUAGE.getParameter(), DEFAULT_LOCALE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

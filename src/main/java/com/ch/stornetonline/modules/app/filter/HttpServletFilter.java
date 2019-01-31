package com.ch.stornetonline.modules.app.filter;

import com.ch.stornetonline.modules.app.http.RequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "HttpServletFilter", urlPatterns = "/*")
public class HttpServletFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        ServletRequest requestWrapper = null;
        if (req instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) req);
        }
        if (requestWrapper == null) {
            chain.doFilter(req, resp);
        } else {
            chain.doFilter(requestWrapper, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

package com.mjwframework.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by pony on 2016/6/8.
 */
public class SystemFilter extends HttpServlet implements Filter {
    private static final Logger log = Logger.getLogger(SystemFilter.class);
    private static final long serialVersionUID = -8106541855834737460L;

    private String staticContextPath = "";
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        req.setAttribute("staticContextPath", staticContextPath);

       /* IQiNiuComponent qiNiuComponent = (IQiNiuComponent) SpringComponent.getBean(QiNiuComponent.class);
        req.setAttribute("picDomain", qiNiuComponent.getBucketDomain());*/

        chain.doFilter(servletRequest, servletResponse);
        return;
    }

    public void init(FilterConfig fl) throws ServletException {
        this.staticContextPath = fl.getServletContext().getInitParameter("contextPathConfig");
    }

}

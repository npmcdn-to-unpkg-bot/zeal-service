package com.zeal.filter;


import com.zeal.http.response.Response;
import com.zeal.utils.LogUtils;
import com.zeal.utils.SessionUtils;
import com.zeal.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang_shoulai on 2015/10/15.
 * 用户会话过滤器，检测用户当前请求API是否需要登录
 */
public class SessionFilter implements Filter {

    private static final String SPLITER = "[,;\r\n]";

    protected String[] excludes = new String[0];

    protected PathMatcher pathMatcher = new AntPathMatcher();

    public void init(FilterConfig filterConfig) throws ServletException {
        LogUtils.error(SessionFilter.class, "/***** SessionFilter initialization start *****");
        String excludeUrls = filterConfig.getInitParameter("exclude-urls");
        List<String> list = new ArrayList<String>();
        if (!StringUtils.isEmpty(excludeUrls)) {
            String[] array = excludeUrls.split(SPLITER);
            for (String url : array) {
                if (!url.trim().isEmpty()) {
                    list.add(url.trim());
                }
            }
        }
        if (list.isEmpty()) {
            LogUtils.error(SessionFilter.class, "No Exclude Url Found!");
        } else {
            excludes = list.toArray(excludes);
            LogUtils.error(SessionFilter.class, "Found Exclude Urls:");
            for (String url : excludes) {
                LogUtils.error(SessionFilter.class, url);
            }
        }
        LogUtils.error(SessionFilter.class, "***** SessionFilter has been initialized *****/");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        if (LogUtils.isDebugEnable()) {
            LogUtils.debug(SessionFilter.class, "do filter with servlet path = " + servletPath);
        }
        if (isBlack(servletPath)) {
            Object userInfo = SessionUtils.getAttribute(request, SessionUtils.KEY_USERINFO);
            if (userInfo == null) {
                response.setStatus(HttpStatus.OK.value());
                response.getWriter().write(Response.UNAUTHENTICATED.toJson());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isBlack(String servletPath) {
        for (String exclude : excludes) {
            if (pathMatcher.match(exclude, servletPath)) {
                return false;
            }
        }
        return true;
    }


    public void destroy() {
        LogUtils.error(SessionFilter.class, " Session Filter Destroy!");
    }

}

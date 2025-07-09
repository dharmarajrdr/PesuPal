package com.pesupal.server.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Component
public class RequestContextInterceptor implements HandlerInterceptor {

    /**
     * This method is called before the handler is executed.
     *
     * @param request
     * @param response
     * @param handler
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            RequestContext.set(header.toUpperCase(), value); // Normalize
        }

        return true;
    }

    /**
     * This method is called after the handler is executed.
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        RequestContext.clear(); // Always clear context
    }
}

package org.example.springbootmasterclass;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        System.out.println("LoggingFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.getHeaderNames().asIterator()
                .forEachRemaining(n -> System.out.println(n + ": " + request.getHeader(n)));

        String amigoscode = request.getHeader("Amigoscode");

        if (amigoscode != null && amigoscode.equals("reject")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        filterChain.doFilter(request, servletResponse);
    }
}

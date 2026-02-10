
package com.ivpulse.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CorrelationIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            String cid = ((HttpServletRequest) request).getHeader("X-Correlation-ID");
            if (cid == null || cid.isBlank()) {
                cid = CorrelationIdMdcUtil.getOrCreate();
            } else {
                CorrelationIdMdcUtil.set(cid);
            }
            chain.doFilter(request, response);
        } finally {
            CorrelationIdMdcUtil.clear();
        }
    }
}

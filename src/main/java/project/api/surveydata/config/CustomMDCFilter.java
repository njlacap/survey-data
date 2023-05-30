package project.api.surveydata.config;


import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class CustomMDCFilter extends OncePerRequestFilter {

    private String mdcCorrelationId;
    private String mdcToken;

    public CustomMDCFilter() { }
    public CustomMDCFilter(final String mdcCorrelationId, final String mdcToken)
    {
        this.mdcCorrelationId = mdcCorrelationId;
        this.mdcToken = mdcToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            final String token = generateToken();
            MDC.put(mdcToken, token);
            response.addHeader(mdcCorrelationId, token);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(mdcToken);
        }
    }

    private String generateToken()
    {
        return UUID.randomUUID().toString().toUpperCase();
    }

}

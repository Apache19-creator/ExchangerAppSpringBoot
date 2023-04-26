package com.herotech.auth.utils;

import com.herotech.data.entities.APIError;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException exception) {
            exception.printStackTrace();
            setErrorResponse(HttpStatus.BAD_REQUEST, response, exception);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, exception);
        }


    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable exception) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        APIError apiError = new APIError(status, exception);
        try {
            String JsonOutput = apiError.convertToJson();
            response.getWriter().write(JsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

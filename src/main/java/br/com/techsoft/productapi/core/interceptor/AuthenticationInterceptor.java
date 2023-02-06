package br.com.techsoft.productapi.core.interceptor;

import br.com.techsoft.productapi.authentication.ApplicationUser;
import br.com.techsoft.productapi.authentication.AuthenticationService;
import br.com.techsoft.productapi.core.exception.HttpException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.NonNull;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Bearer ";
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationInterceptor(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if(isOptions(request)) return true;

        String authorizationToken = request.getHeader(AUTHORIZATION_HEADER);

        if(authorizationToken == null || !authorizationToken.contains(AUTHORIZATION_PREFIX))
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Token is missing!");

        ApplicationUser user = authenticationService.authenticateUser(authorizationToken);

        authenticationService.setUserRequest(user);

        return true;
    }

    private boolean isOptions(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}

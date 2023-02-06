package br.com.techsoft.productapi.authentication;

import br.com.techsoft.productapi.core.ResponseError;
import br.com.techsoft.productapi.core.exception.HttpException;
import br.com.techsoft.productapi.core.helpers.RequestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private static final String USER_SESSION_KEY = "APPLICATION_LOGGED_USER_SESSION";
    private final AuthenticationClient authenticationClient;
    private final ObjectMapper objectMapper;

    public void setUserRequest(ApplicationUser user) {
        RequestHelper
                .getCurrentRequest()
                .getSession()
                .setAttribute(USER_SESSION_KEY, user);
    }

    public ApplicationUser getUserRequest() {
        return (ApplicationUser) RequestHelper
                .getCurrentRequest()
                .getSession()
                .getAttribute(USER_SESSION_KEY);
    }

    public ApplicationUser authenticateUser(String token) {
        try {
            ResponseEntity<ApplicationUser> response = authenticationClient.authenticate(token);

            if (response.getBody() == null)
                throw new HttpException(BAD_REQUEST, "Failed to request Authentication API!");

            return response.getBody();
        } catch (FeignException exception) {
            ResponseError error = handleAuthenticationException(exception);

            throw new HttpException(
                    HttpStatus.valueOf(error.getStatus()),
                    error.getMessage()
            );
        }
    }

    public ResponseError handleAuthenticationException(FeignException feignException) {
        try {
            log.error(feignException.contentUTF8());

            return objectMapper.readValue(feignException.contentUTF8(), ResponseError.class);
        } catch (JsonProcessingException exceptionJson) {
            throw new HttpException(BAD_REQUEST, "Error in Authentication API!");
        }
    }
}

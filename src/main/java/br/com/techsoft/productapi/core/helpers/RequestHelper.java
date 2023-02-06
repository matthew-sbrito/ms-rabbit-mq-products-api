package br.com.techsoft.productapi.core.helpers;

import br.com.techsoft.productapi.core.exception.HttpException;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (servletRequestAttributes == null)
                throw new HttpException(HttpStatus.BAD_REQUEST, "The current request could not be processed!");

            return servletRequestAttributes.getRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new HttpException(HttpStatus.BAD_REQUEST, "The current request could not be processed!");
        }
    }
}

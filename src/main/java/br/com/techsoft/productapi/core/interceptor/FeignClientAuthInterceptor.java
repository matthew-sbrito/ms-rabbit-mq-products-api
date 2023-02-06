package br.com.techsoft.productapi.core.interceptor;

import br.com.techsoft.productapi.core.helpers.RequestHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientAuthInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("FeignClientAuthInterceptor was called!");

        HttpServletRequest request = RequestHelper.getCurrentRequest();
        requestTemplate.header("Authorization", request.getHeader(AUTHORIZATION_HEADER));
    }
}

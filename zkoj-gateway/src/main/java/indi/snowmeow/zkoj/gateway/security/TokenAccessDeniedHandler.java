package indi.snowmeow.zkoj.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 无权访问处理器
 * @author snowmeow
 * @date 2021/4/8
 */
public class TokenAccessDeniedHandler implements ServerAccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAccessDeniedHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        LOGGER.info("拦截：AccessDeniedHandler");
        return Mono.fromRunnable(() -> {
            serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        });

    }
}

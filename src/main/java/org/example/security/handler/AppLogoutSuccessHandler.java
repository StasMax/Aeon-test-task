package org.example.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.service.TokenBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.example.common.Constants.TOKEN_HEADER;

@Slf4j
public class AppLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenBlackListService tokenBlackListService;


    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        tokenBlackListService.saveIncorrectToken(httpServletRequest.getHeader(TOKEN_HEADER).replace("Bearer ", ""));
        log.info("========== Success logout");
        httpServletResponse.getWriter().println("Success logout");
    }
}

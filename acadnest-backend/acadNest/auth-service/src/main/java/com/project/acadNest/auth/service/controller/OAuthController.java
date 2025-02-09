package com.project.acadNest.auth.service.controller;


import com.project.acadNest.auth.service.component.OAuthComponent;
import com.project.acadNest.auth.service.pojo.AuthResponsePojo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OAuthController {

    private final OAuthComponent oAuthComponent;

    @GetMapping("/google")
    public void googleLogin(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws IOException {
        if (principal == null) {
            log.error("Google authentication failed: Principal is null");
            response.sendRedirect("http://localhost:3000/login?error=oauth_failed");
            return;
        }

        log.info("Google principal attributes: {}", principal.getAttributes());

        String email = principal.getAttribute("email");
        String googleId = principal.getAttribute("sub");

        if (email == null || googleId == null) {
            log.error("Missing email or Google ID from OAuth response.");
            response.sendRedirect("http://localhost:3000/login?error=oauth_failed");
            return;
        }

        try {
            AuthResponsePojo authResponsePojo = oAuthComponent.createUser(email, googleId);
            String jwt = authResponsePojo.getJwt();

            response.sendRedirect("http://localhost:3000/oauth-success?jwt=" + URLEncoder.encode(jwt, "UTF-8"));
        } catch (BadRequestException e) {
            log.error("Exception during authentication: {}", e.getMessage());
            response.sendRedirect("http://localhost:3000/login?error=" + e.getMessage());
        } catch (Exception e) {
            log.error("Exception during authentication: {}", e.getMessage());
            response.sendRedirect("http://localhost:3000/login?error=internal_error");
        }
    }

}

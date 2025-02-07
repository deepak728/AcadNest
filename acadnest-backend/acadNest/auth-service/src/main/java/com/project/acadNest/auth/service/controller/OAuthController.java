package com.project.acadNest.auth.service.controller;


import com.project.acadNest.auth.service.constant.ROLE;
import com.project.acadNest.auth.service.pojo.User;
import com.project.acadNest.auth.service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OAuthController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final JwtUtil jwtUtil;  // Utility class for JWT generation

    @GetMapping("/google")
    public ResponseEntity<?> googleLogin(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            log.error("Google authentication failed: Principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google authentication failed");
        }

        log.info("Google principal attributes: {}", principal.getAttributes());

        String email = principal.getAttribute("email");
        String googleId = principal.getAttribute("sub");

        if (email == null || googleId == null) {
            log.error("Missing email or Google ID from OAuth response.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OAuth2 response");
        }

        User user = new User();
        user.setGoogleId(googleId);
        user.setEmailId(email);
        user.setRole(ROLE.STUDENT);
        log.info("User details: {}", user);

        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(email, ROLE.STUDENT.toString());
        log.info("Generated token: {}", jwtToken);

        return ResponseEntity.ok(jwtToken);
    }

}

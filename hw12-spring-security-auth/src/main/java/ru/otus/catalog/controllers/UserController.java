package ru.otus.catalog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.dto.UserLoginDto;
import ru.otus.catalog.security.UserService;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtEncoder encoder;

    public UserController(UserService userService, DaoAuthenticationProvider daoAuthenticationProvider,
                          JwtEncoder encoder) {
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.encoder = encoder;
    }

    @PostMapping("api/v1/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserLoginDto user) {
        UserDetails principal = userService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, user.getPassword(), principal.getAuthorities());
        Authentication authentication = daoAuthenticationProvider.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Instant now = Instant.now();
        long expiry = 36000L;
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return new ResponseEntity<>(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(),
                HttpStatus.OK);
    }
}

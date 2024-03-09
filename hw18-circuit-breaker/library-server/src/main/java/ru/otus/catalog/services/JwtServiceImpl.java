package ru.otus.catalog.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder encoder;

    private final JwtDecoder jwtDecoder;

    public JwtServiceImpl(@Lazy JwtEncoder encoder, @Lazy JwtDecoder jwtDecoder) {
        this.encoder = encoder;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    public String extractUserName(String token) {
        return extractClaim(token, "sub");
    }

    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claim           название
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, String claim) {
        final Map<String, Object> claims = extractAllClaims(token);
        return (T) claims.get(claim);
    }

    /**
     * Генерация токена
     *
     * @param authentication данные пользователя
     * @return токен
     */
    public String generateToken(Authentication authentication) {
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
                .claim("role", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(new Date().toInstant());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Instant extractExpiration(String token) {
        return extractClaim(token, "exp");
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Map<String, Object> extractAllClaims(String token) {
        return jwtDecoder.decode(token).getClaims();
    }
}

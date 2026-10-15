package it.arenahub.backend.utente;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import it.arenahub.backend.utente.UtenteDtos.LoginRequest;
import it.arenahub.backend.utente.UtenteDtos.LoginResponse;
import it.arenahub.backend.utente.UtenteDtos.RegistrazioneRequest;
import it.arenahub.backend.utente.UtenteDtos.UtenteResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UtenteService utenti;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final long durataMinuti;

    public AuthController(UtenteService utenti, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder,
            @Value("${app.security.jwt.token-minutes}") long durataMinuti) {
        this.utenti = utenti;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.durataMinuti = durataMinuti;
    }

    @PostMapping({"/registrazione", "/register"})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CREATED)
    public UtenteResponse registra(@Valid @RequestBody RegistrazioneRequest request) {
        return utenti.registra(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Utente utente = utenti.richiediPerEmail(request.email().trim());
        if (!passwordEncoder.matches(request.password(), utente.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }
        Instant now = Instant.now();
        Instant expiresAt = now.plus(durataMinuti, ChronoUnit.MINUTES);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("arenahub-backend")
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(utente.getEmail())
                .claim("uid", utente.getId())
                .claim("role", utente.getRuolo().name())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new LoginResponse(token, "Bearer", durataMinuti * 60,
                UtenteResponse.da(utente));
    }

    /**
     * Il JWT è stateless: il server non conserva una sessione da invalidare.
     * La disconnessione effettiva consiste nel cancellare il token dal client.
     */
    @PostMapping("/logout")
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() { }
}

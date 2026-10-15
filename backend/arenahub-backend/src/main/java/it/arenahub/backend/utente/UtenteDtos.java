package it.arenahub.backend.utente;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class UtenteDtos {
    private UtenteDtos() { }

    public record RegistrazioneRequest(
            @NotBlank @Size(max = 50) String nome,
            @NotBlank @Size(max = 50) String cognome,
            @NotBlank @Email @Size(max = 100) String email,
            @NotBlank @Size(min = 8, max = 72) String password) { }

    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) { }

    public record ModificaUtenteRequest(
            @NotBlank @Size(max = 50) String nome,
            @NotBlank @Size(max = 50) String cognome,
            @NotBlank @Email @Size(max = 100) String email,
            @Size(min = 8, max = 72) String nuovaPassword,
            Ruolo ruolo) { }

    public record UtenteResponse(Integer id, String nome, String cognome, String email,
            Ruolo ruolo, LocalDateTime dataRegistrazione) {
        static UtenteResponse da(Utente utente) {
            return new UtenteResponse(utente.getId(), utente.getNome(), utente.getCognome(),
                    utente.getEmail(), utente.getRuolo(), utente.getDataRegistrazione());
        }
    }

    public record LoginResponse(String token, String tipo, long scadeTraSecondi,
            UtenteResponse utente) { }
}

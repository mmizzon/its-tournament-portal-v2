package it.arenahub.backend.utente;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import it.arenahub.backend.utente.UtenteDtos.ModificaUtenteRequest;
import it.arenahub.backend.utente.UtenteDtos.RegistrazioneRequest;
import it.arenahub.backend.utente.UtenteDtos.UtenteResponse;

@Service
public class UtenteService {
    private final UtenteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UtenteResponse registra(RegistrazioneRequest request) {
        String email = request.email().trim().toLowerCase();
        if (repository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già registrata");
        }
        Utente utente = new Utente(request.nome().trim(), request.cognome().trim(), email,
                passwordEncoder.encode(request.password()));
        return UtenteResponse.da(repository.save(utente));
    }

    @Transactional(readOnly = true)
    public Utente richiediPerEmail(String email) {
        return repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide"));
    }

    @Transactional(readOnly = true)
    public UtenteResponse leggi(Integer id) {
        return UtenteResponse.da(trova(id));
    }

    @Transactional(readOnly = true)
    public List<UtenteResponse> elenco() {
        return repository.findAll().stream().map(UtenteResponse::da).toList();
    }

    @Transactional
    public UtenteResponse modifica(Integer id, ModificaUtenteRequest request, boolean admin) {
        Utente utente = trova(id);
        String email = request.email().trim().toLowerCase();
        if (repository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già usata da un altro utente");
        }
        utente.setNome(request.nome().trim());
        utente.setCognome(request.cognome().trim());
        utente.setEmail(email);
        if (request.nuovaPassword() != null && !request.nuovaPassword().isBlank()) {
            utente.setPasswordHash(passwordEncoder.encode(request.nuovaPassword()));
        }
        if (admin && request.ruolo() != null) utente.setRuolo(request.ruolo());
        return UtenteResponse.da(repository.save(utente));
    }

    @Transactional
    public void elimina(Integer id) {
        repository.delete(trova(id));
    }

    private Utente trova(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
    }
}

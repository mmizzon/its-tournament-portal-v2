package it.arenahub.backend.utente;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import it.arenahub.backend.utente.UtenteDtos.ModificaUtenteRequest;
import it.arenahub.backend.utente.UtenteDtos.UtenteResponse;
import it.arenahub.backend.torneo.TorneoService;
import it.arenahub.backend.torneo.TorneoDtos.TorneoResponse;

@RestController
@RequestMapping("/api")
public class UtenteController {
    private final UtenteService utenti;
    private final TorneoService tornei;

    public UtenteController(UtenteService utenti, TorneoService tornei) {
        this.utenti = utenti;
        this.tornei = tornei;
    }

    @GetMapping({"/utenti/me", "/profilo"})
    public UtenteResponse mioProfilo(Authentication auth) {
        return utenti.leggi(idUtente(auth));
    }

    @GetMapping("/profilo/tornei")
    public List<TorneoResponse> mieiTornei(Authentication auth) {
        if (!(auth instanceof JwtAuthenticationToken)) {
            return List.of();
        }
        return tornei.miei(idUtente(auth));
    }

    @PutMapping("/utenti/me")
    public UtenteResponse modificaMioProfilo(Authentication auth,
            @Valid @RequestBody ModificaUtenteRequest request) {
        return utenti.modifica(idUtente(auth), request, false);
    }

    @GetMapping("/admin/utenti")
    public List<UtenteResponse> elencoUtenti() { return utenti.elenco(); }

    @GetMapping("/admin/utenti/{id}")
    public UtenteResponse leggiUtente(@PathVariable Integer id) { return utenti.leggi(id); }

    @PutMapping("/admin/utenti/{id}")
    public UtenteResponse modificaUtente(@PathVariable Integer id,
            @Valid @RequestBody ModificaUtenteRequest request) {
        return utenti.modifica(id, request, true);
    }

    @DeleteMapping("/admin/utenti/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaUtente(@PathVariable Integer id) { utenti.elimina(id); }

    private Integer idUtente(Authentication auth) {
        Number id = ((JwtAuthenticationToken) auth).getToken().getClaim("uid");
        return id.intValue();
    }
}

package it.arenahub.backend.torneo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import it.arenahub.backend.torneo.TorneoDtos.TorneoRequest;
import it.arenahub.backend.torneo.TorneoDtos.TorneoResponse;
import it.arenahub.backend.torneo.SquadraDtos.CreaSquadraRequest;
import it.arenahub.backend.torneo.SquadraDtos.SquadraResponse;

@RestController
@RequestMapping("/api/tornei")
public class TorneoController {
    private final TorneoService tornei;

    public TorneoController(TorneoService tornei) { this.tornei = tornei; }

    @GetMapping
    public List<TorneoResponse> elenco() { return tornei.elenco(); }

    @GetMapping("/{id}")
    public TorneoResponse dettaglio(@PathVariable Integer id) { return tornei.dettaglio(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TorneoResponse crea(@Valid @RequestBody TorneoRequest request) { return tornei.crea(request); }

    @PutMapping("/{id}")
    public TorneoResponse modifica(@PathVariable Integer id, @Valid @RequestBody TorneoRequest request) {
        return tornei.modifica(id, request);
    }

    @DeleteMapping("/{id}")
    public TorneoResponse annulla(@PathVariable Integer id) { return tornei.annulla(id); }

    @PostMapping("/{id}/iscrizione")
    @ResponseStatus(HttpStatus.CREATED)
    public void iscrivi(@PathVariable Integer id, Authentication auth) {
        tornei.iscrivi(id, idUtente(auth));
    }

    @DeleteMapping("/{id}/iscrizione")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disiscrivi(@PathVariable Integer id, Authentication auth) {
        tornei.disiscrivi(id, idUtente(auth));
    }

    @PostMapping("/{id}/squadre")
    @ResponseStatus(HttpStatus.CREATED)
    public SquadraResponse creaSquadra(@PathVariable Integer id, Authentication auth,
            @Valid @RequestBody CreaSquadraRequest request) {
        return tornei.creaSquadra(id, idUtente(auth), request);
    }

    @DeleteMapping("/{id}/squadre")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void annullaSquadra(@PathVariable Integer id, Authentication auth) {
        tornei.annullaSquadra(id, idUtente(auth));
    }

    private Integer idUtente(Authentication auth) {
        Number id = ((JwtAuthenticationToken) auth).getToken().getClaim("uid");
        return id.intValue();
    }
}

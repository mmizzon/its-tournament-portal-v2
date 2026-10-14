package it.arenahub.backend.torneo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.arenahub.backend.torneo.TorneoDtos.TorneoRequest;
import it.arenahub.backend.torneo.TorneoDtos.TorneoResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/tornei")
public class AdminTorneoController {
    private final TorneoService tornei;

    public AdminTorneoController(TorneoService tornei) { this.tornei = tornei; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TorneoResponse crea(@Valid @RequestBody TorneoRequest request) {
        return tornei.crea(request);
    }

    @PostMapping("/{id}/cancella")
    public TorneoResponse cancella(@PathVariable Integer id) {
        return tornei.annulla(id);
    }
}

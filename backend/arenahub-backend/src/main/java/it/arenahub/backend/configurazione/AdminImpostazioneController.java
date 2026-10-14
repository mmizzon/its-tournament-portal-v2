package it.arenahub.backend.configurazione;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.arenahub.backend.configurazione.ImpostazioneDtos.AggiornaMaxTorneiRequest;
import it.arenahub.backend.configurazione.ImpostazioneDtos.MaxTorneiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/impostazioni/max-tornei-attivi")
public class AdminImpostazioneController {
    private final ImpostazioneService impostazioni;

    public AdminImpostazioneController(ImpostazioneService impostazioni) {
        this.impostazioni = impostazioni;
    }

    @GetMapping
    public MaxTorneiResponse leggiLimite() {
        return impostazioni.leggiMaxTorneiAttivi();
    }

    @PutMapping
    public MaxTorneiResponse aggiornaLimite(
            @Valid @RequestBody AggiornaMaxTorneiRequest request) {
        return impostazioni.aggiornaMaxTorneiAttivi(request);
    }
}

package it.arenahub.backend.configurazione;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.arenahub.backend.configurazione.ImpostazioneDtos.AggiornaMaxTorneiRequest;
import it.arenahub.backend.configurazione.ImpostazioneDtos.MaxTorneiResponse;

@Service
public class ImpostazioneService {
    public static final int LIMITE_TORNEI_PREDEFINITO = 3;

    private final ImpostazioneRepository repository;

    public ImpostazioneService(ImpostazioneRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public int maxTorneiAttivi() {
        return repository.findById(Impostazione.ID_MAX_TORNEI_ATTIVI)
                .map(Impostazione::getValore)
                .orElse(LIMITE_TORNEI_PREDEFINITO);
    }

    @Transactional
    public MaxTorneiResponse aggiornaMaxTorneiAttivi(AggiornaMaxTorneiRequest request) {
        Impostazione impostazione = repository.findById(Impostazione.ID_MAX_TORNEI_ATTIVI)
                .orElseGet(() -> new Impostazione(Impostazione.ID_MAX_TORNEI_ATTIVI,
                        LIMITE_TORNEI_PREDEFINITO));
        impostazione.setValore(request.valore());
        Impostazione salvata = repository.save(impostazione);
        return new MaxTorneiResponse(salvata.getValore());
    }

    @Transactional(readOnly = true)
    public MaxTorneiResponse leggiMaxTorneiAttivi() {
        return new MaxTorneiResponse(maxTorneiAttivi());
    }
}

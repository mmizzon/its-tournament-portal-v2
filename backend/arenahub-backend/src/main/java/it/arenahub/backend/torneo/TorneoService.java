package it.arenahub.backend.torneo;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import it.arenahub.backend.torneo.TorneoDtos.TorneoRequest;
import it.arenahub.backend.torneo.TorneoDtos.TorneoResponse;
import it.arenahub.backend.torneo.SquadraDtos.CreaSquadraRequest;
import it.arenahub.backend.torneo.SquadraDtos.SquadraResponse;
import it.arenahub.backend.utente.Utente;
import it.arenahub.backend.utente.UtenteRepository;

@Service
public class TorneoService {
    private final TorneoRepository repository;
    private final IscrizioneRepository iscrizioni;
    private final SquadraRepository squadre;
    private final ComponenteSquadraRepository componenti;
    private final UtenteRepository utenti;

    public TorneoService(TorneoRepository repository, IscrizioneRepository iscrizioni,
            SquadraRepository squadre, ComponenteSquadraRepository componenti,
            UtenteRepository utenti) {
        this.repository = repository;
        this.iscrizioni = iscrizioni;
        this.squadre = squadre;
        this.componenti = componenti;
        this.utenti = utenti;
    }

    @Transactional(readOnly = true)
    public List<TorneoResponse> elenco() {
        return repository.findAll().stream().map(this::risposta).toList();
    }

    @Transactional(readOnly = true)
    public TorneoResponse dettaglio(Integer id) { return risposta(trova(id)); }

    @Transactional(readOnly = true)
    public List<TorneoResponse> miei(Integer utenteId) {
        Map<Integer, Torneo> tornei = new LinkedHashMap<>();
        iscrizioni.findAllByUtente_Id(utenteId)
                .forEach(iscrizione -> tornei.put(iscrizione.getTorneo().getId(), iscrizione.getTorneo()));
        componenti.findAllByUtente_Id(utenteId)
                .forEach(componente -> tornei.put(componente.getSquadra().getTorneo().getId(),
                        componente.getSquadra().getTorneo()));
        return tornei.values().stream().map(this::risposta).toList();
    }

    @Transactional
    public void iscrivi(Integer torneoId, Integer utenteId) {
        Torneo torneo = trova(torneoId);
        controllaIscrizioniAperte(torneo);
        if (modalita(torneo) != ModalitaTorneo.INDIVIDUALE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Questo torneo richiede l'iscrizione di una squadra");
        }
        if (iscrizioni.existsByTorneo_IdAndUtente_Id(torneoId, utenteId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Utente già iscritto al torneo");
        }
        if (iscrizioni.countByTorneo_Id(torneoId) >= torneo.getIndividuale().getMaxPartecipanti()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Torneo al completo");
        }
        Utente utente = utenti.getReferenceById(utenteId);
        iscrizioni.save(new Iscrizione(torneo, utente));
    }

    @Transactional
    public void disiscrivi(Integer torneoId, Integer utenteId) {
        Torneo torneo = trova(torneoId);
        controllaIscrizioniAperte(torneo);
        Iscrizione iscrizione = iscrizioni.findByTorneo_IdAndUtente_Id(torneoId, utenteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Iscrizione non trovata"));
        iscrizioni.delete(iscrizione);
    }

    @Transactional
    public SquadraResponse creaSquadra(Integer torneoId, Integer creatoreId, CreaSquadraRequest request) {
        Torneo torneo = trova(torneoId);
        controllaIscrizioniAperte(torneo);
        if (modalita(torneo) != ModalitaTorneo.SQUADRE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Questo torneo accetta iscrizioni individuali");
        }
        String nome = request.nome().trim();
        if (squadre.existsByTorneo_IdAndNomeIgnoreCase(torneoId, nome)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Esiste già una squadra con questo nome nel torneo");
        }
        if (squadre.existsByTorneo_IdAndCreatore_Id(torneoId, creatoreId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Hai già creato una squadra per questo torneo");
        }
        if (squadre.countByTorneo_Id(torneoId) >= torneo.getSquadre().getMaxSquadre()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Torneo al completo");
        }
        Utente creatore = utenti.getReferenceById(creatoreId);
        Squadra squadra = squadre.save(new Squadra(torneo, nome, creatore));
        componenti.save(new ComponenteSquadra(squadra, creatore));
        return SquadraResponse.da(squadra);
    }

    @Transactional
    public void annullaSquadra(Integer torneoId, Integer creatoreId) {
        Torneo torneo = trova(torneoId);
        controllaIscrizioniAperte(torneo);
        Squadra squadra = squadre.findByTorneo_IdAndCreatore_Id(torneoId, creatoreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Non hai creato una squadra per questo torneo"));
        // La FK del DB elimina i componenti della squadra tramite ON DELETE CASCADE.
        squadre.delete(squadra);
    }

    @Transactional
    public TorneoResponse crea(TorneoRequest request) {
        valida(request);
        Torneo torneo = new Torneo();
        aggiornaDati(torneo, request);
        applicaModalita(torneo, request, true);
        return risposta(repository.save(torneo));
    }

    @Transactional
    public TorneoResponse modifica(Integer id, TorneoRequest request) {
        valida(request);
        Torneo torneo = trova(id);
        ModalitaTorneo modalitaAttuale = modalita(torneo);
        if (modalitaAttuale != request.modalita()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La modalità di un torneo esistente non può essere cambiata");
        }
        aggiornaDati(torneo, request);
        applicaModalita(torneo, request, false);
        return risposta(repository.save(torneo));
    }

    @Transactional
    public TorneoResponse annulla(Integer id) {
        Torneo torneo = trova(id);
        torneo.setCancellato(true);
        return risposta(repository.save(torneo));
    }

    private void aggiornaDati(Torneo torneo, TorneoRequest request) {
        torneo.setNome(request.nome().trim());
        torneo.setDescrizione(request.descrizione() == null ? null : request.descrizione().trim());
        torneo.setDataInizioIscrizioni(request.dataInizioIscrizioni());
        torneo.setDataFineIscrizioni(request.dataFineIscrizioni());
        torneo.setDataInizioTorneo(request.dataInizioTorneo());
        torneo.setDataFineTorneo(request.dataFineTorneo());
    }

    private void applicaModalita(Torneo torneo, TorneoRequest request, boolean nuova) {
        if (request.modalita() == ModalitaTorneo.INDIVIDUALE) {
            if (request.maxPartecipanti() == null || request.maxPartecipanti() <= 0
                    || request.maxSquadre() != null || request.componenti() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Per un torneo individuale specificare solo maxPartecipanti");
            }
            if (nuova) torneo.setIndividuale(new TorneoIndividuale(torneo, request.maxPartecipanti()));
            else torneo.getIndividuale().setMaxPartecipanti(request.maxPartecipanti());
        } else {
            if (request.maxSquadre() == null || request.maxSquadre() <= 0
                    || request.componenti() == null || request.componenti() <= 0
                    || request.maxPartecipanti() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Per un torneo a squadre specificare maxSquadre e componenti");
            }
            if (nuova) torneo.setSquadre(new TorneoSquadra(torneo, request.maxSquadre(), request.componenti()));
            else {
                torneo.getSquadre().setMaxSquadre(request.maxSquadre());
                torneo.getSquadre().setComponenti(request.componenti());
            }
        }
    }

    private void valida(TorneoRequest request) {
        if (request.dataInizioIscrizioni().isAfter(request.dataFineIscrizioni())
                || request.dataFineIscrizioni().isAfter(request.dataInizioTorneo())
                || request.dataInizioTorneo().isAfter(request.dataFineTorneo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le date devono rispettare: inizio iscrizioni <= fine iscrizioni <= inizio torneo <= fine torneo");
        }
    }

    private void controllaIscrizioniAperte(Torneo torneo) {
        LocalDate oggi = LocalDate.now();
        if (torneo.isCancellato() || oggi.isBefore(torneo.getDataInizioIscrizioni())
                || oggi.isAfter(torneo.getDataFineIscrizioni())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le iscrizioni al torneo non sono aperte");
        }
    }

    private TorneoResponse risposta(Torneo torneo) {
        ModalitaTorneo modalita = modalita(torneo);
        Integer maxPartecipanti = modalita == ModalitaTorneo.INDIVIDUALE
                ? torneo.getIndividuale().getMaxPartecipanti() : null;
        Integer maxSquadre = modalita == ModalitaTorneo.SQUADRE
                ? torneo.getSquadre().getMaxSquadre() : null;
        Integer componenti = modalita == ModalitaTorneo.SQUADRE
                ? torneo.getSquadre().getComponenti() : null;
        StatoTorneo stato = stato(torneo);
        return new TorneoResponse(torneo.getId(), torneo.getNome(), torneo.getDescrizione(),
                torneo.getDataInizioIscrizioni(), torneo.getDataFineIscrizioni(),
                torneo.getDataInizioTorneo(), torneo.getDataFineTorneo(), torneo.isCancellato(),
                stato, modalita, maxPartecipanti, maxSquadre, componenti);
    }

    private StatoTorneo stato(Torneo torneo) {
        if (torneo.isCancellato()) return StatoTorneo.CANCELLATO;
        LocalDate oggi = LocalDate.now();
        if (oggi.isBefore(torneo.getDataInizioTorneo())) return StatoTorneo.APERTO;
        if (!oggi.isAfter(torneo.getDataFineTorneo())) return StatoTorneo.IN_CORSO;
        return StatoTorneo.CONCLUSO;
    }

    private ModalitaTorneo modalita(Torneo torneo) {
        if (torneo.getIndividuale() != null) return ModalitaTorneo.INDIVIDUALE;
        if (torneo.getSquadre() != null) return ModalitaTorneo.SQUADRE;
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Torneo senza modalità individuale o a squadre");
    }

    private Torneo trova(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneo non trovato"));
    }
}

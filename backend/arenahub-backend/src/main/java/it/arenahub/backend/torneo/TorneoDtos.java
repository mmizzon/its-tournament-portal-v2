package it.arenahub.backend.torneo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public final class TorneoDtos {
    private TorneoDtos() { }

    public record TorneoRequest(
            @NotBlank @Size(max = 100) String nome,
            @Size(max = 2000) String descrizione,
            @NotNull LocalDate dataInizioIscrizioni,
            @NotNull LocalDate dataFineIscrizioni,
            @NotNull LocalDate dataInizioTorneo,
            @NotNull LocalDate dataFineTorneo,
            @NotNull ModalitaTorneo modalita,
            @Positive Integer maxPartecipanti,
            @Positive Integer maxSquadre,
            @Positive Integer componenti) { }

    public record TorneoResponse(Integer id, String nome, String descrizione,
            LocalDate dataInizioIscrizioni, LocalDate dataFineIscrizioni,
            LocalDate dataInizioTorneo, LocalDate dataFineTorneo,
            boolean cancellato, StatoTorneo stato, ModalitaTorneo modalita,
            Integer maxPartecipanti, Integer maxSquadre, Integer componenti) { }
}

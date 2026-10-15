package it.arenahub.backend.torneo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class SquadraDtos {
    private SquadraDtos() { }

    public record CreaSquadraRequest(@NotBlank @Size(max = 100) String nome) { }

    public record SquadraResponse(Integer id, Integer torneoId, String nome,
            Integer creatoreId, LocalDateTime dataCreazione) {
        static SquadraResponse da(Squadra squadra) {
            return new SquadraResponse(squadra.getId(), squadra.getTorneo().getId(),
                    squadra.getNome(), squadra.getCreatore().getId(), squadra.getDataCreazione());
        }
    }
}

package it.arenahub.backend.configurazione;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public final class ImpostazioneDtos {
    private ImpostazioneDtos() { }

    public record AggiornaMaxTorneiRequest(
            @NotNull @Min(1) Integer valore) { }

    public record MaxTorneiResponse(int valore) { }
}

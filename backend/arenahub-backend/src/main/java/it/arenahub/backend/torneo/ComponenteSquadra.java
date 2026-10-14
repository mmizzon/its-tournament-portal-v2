package it.arenahub.backend.torneo;

import java.time.LocalDateTime;

import it.arenahub.backend.utente.Utente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "componente_squadra")
public class ComponenteSquadra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_componente_squadra")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_squadra", nullable = false)
    private Squadra squadra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @Column(name = "data_inserimento", nullable = false, updatable = false)
    private LocalDateTime dataInserimento;

    protected ComponenteSquadra() { }

    public ComponenteSquadra(Squadra squadra, Utente utente) {
        this.squadra = squadra;
        this.utente = utente;
    }

    @PrePersist
    void assegnaData() {
        if (dataInserimento == null) dataInserimento = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Squadra getSquadra() { return squadra; }
    public Utente getUtente() { return utente; }
    public LocalDateTime getDataInserimento() { return dataInserimento; }
}

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
@Table(name = "iscrizione")
public class Iscrizione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iscrizione")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_torneo", nullable = false)
    private Torneo torneo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @Column(name = "data_iscrizione", nullable = false, updatable = false)
    private LocalDateTime dataIscrizione;

    protected Iscrizione() { }

    public Iscrizione(Torneo torneo, Utente utente) {
        this.torneo = torneo;
        this.utente = utente;
    }

    @PrePersist
    void assegnaData() {
        if (dataIscrizione == null) dataIscrizione = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Torneo getTorneo() { return torneo; }
    public Utente getUtente() { return utente; }
    public LocalDateTime getDataIscrizione() { return dataIscrizione; }
}

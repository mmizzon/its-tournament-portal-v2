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
@Table(name = "squadra")
public class Squadra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_squadra")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_torneo", nullable = false)
    private Torneo torneo;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_creatore", nullable = false)
    private Utente creatore;

    @Column(name = "data_creazione", nullable = false, updatable = false)
    private LocalDateTime dataCreazione;

    protected Squadra() { }

    public Squadra(Torneo torneo, String nome, Utente creatore) {
        this.torneo = torneo;
        this.nome = nome;
        this.creatore = creatore;
    }

    @PrePersist
    void assegnaData() {
        if (dataCreazione == null) dataCreazione = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public Torneo getTorneo() { return torneo; }
    public String getNome() { return nome; }
    public Utente getCreatore() { return creatore; }
    public LocalDateTime getDataCreazione() { return dataCreazione; }
}

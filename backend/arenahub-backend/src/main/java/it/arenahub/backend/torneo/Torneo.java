package it.arenahub.backend.torneo;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "torneo")
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torneo")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 2000)
    private String descrizione;

    @Column(name = "data_inizio_iscrizioni", nullable = false)
    private LocalDate dataInizioIscrizioni;

    @Column(name = "data_fine_iscrizioni", nullable = false)
    private LocalDate dataFineIscrizioni;

    @Column(name = "data_inizio_torneo", nullable = false)
    private LocalDate dataInizioTorneo;

    @Column(name = "data_fine_torneo", nullable = false)
    private LocalDate dataFineTorneo;

    @Column(nullable = false)
    private boolean cancellato;

    @OneToOne(mappedBy = "torneo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TorneoIndividuale individuale;

    @OneToOne(mappedBy = "torneo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TorneoSquadra squadre;

    protected Torneo() { }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public LocalDate getDataInizioIscrizioni() { return dataInizioIscrizioni; }
    public void setDataInizioIscrizioni(LocalDate data) { this.dataInizioIscrizioni = data; }
    public LocalDate getDataFineIscrizioni() { return dataFineIscrizioni; }
    public void setDataFineIscrizioni(LocalDate data) { this.dataFineIscrizioni = data; }
    public LocalDate getDataInizioTorneo() { return dataInizioTorneo; }
    public void setDataInizioTorneo(LocalDate data) { this.dataInizioTorneo = data; }
    public LocalDate getDataFineTorneo() { return dataFineTorneo; }
    public void setDataFineTorneo(LocalDate data) { this.dataFineTorneo = data; }
    public boolean isCancellato() { return cancellato; }
    public void setCancellato(boolean cancellato) { this.cancellato = cancellato; }
    public TorneoIndividuale getIndividuale() { return individuale; }
    public void setIndividuale(TorneoIndividuale individuale) { this.individuale = individuale; }
    public TorneoSquadra getSquadre() { return squadre; }
    public void setSquadre(TorneoSquadra squadre) { this.squadre = squadre; }
}

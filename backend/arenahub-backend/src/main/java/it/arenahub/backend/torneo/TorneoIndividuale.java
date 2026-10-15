package it.arenahub.backend.torneo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "torneo_individuale")
public class TorneoIndividuale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torneo_individuale")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_torneo", nullable = false, unique = true)
    private Torneo torneo;

    @Column(name = "max_partecipanti", nullable = false)
    private int maxPartecipanti;

    protected TorneoIndividuale() { }

    public TorneoIndividuale(Torneo torneo, int maxPartecipanti) {
        this.torneo = torneo;
        this.maxPartecipanti = maxPartecipanti;
    }

    public Integer getId() { return id; }
    public Torneo getTorneo() { return torneo; }
    public int getMaxPartecipanti() { return maxPartecipanti; }
    public void setMaxPartecipanti(int maxPartecipanti) { this.maxPartecipanti = maxPartecipanti; }
}

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
@Table(name = "torneo_squadra")
public class TorneoSquadra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torneo_squadra")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_torneo", nullable = false, unique = true)
    private Torneo torneo;

    @Column(name = "max_squadre", nullable = false)
    private int maxSquadre;

    @Column(nullable = false)
    private int componenti;

    protected TorneoSquadra() { }

    public TorneoSquadra(Torneo torneo, int maxSquadre, int componenti) {
        this.torneo = torneo;
        this.maxSquadre = maxSquadre;
        this.componenti = componenti;
    }

    public Integer getId() { return id; }
    public Torneo getTorneo() { return torneo; }
    public int getMaxSquadre() { return maxSquadre; }
    public void setMaxSquadre(int maxSquadre) { this.maxSquadre = maxSquadre; }
    public int getComponenti() { return componenti; }
    public void setComponenti(int componenti) { this.componenti = componenti; }
}

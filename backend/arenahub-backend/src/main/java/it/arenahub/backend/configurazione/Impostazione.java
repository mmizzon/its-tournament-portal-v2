package it.arenahub.backend.configurazione;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "impostazione")
public class Impostazione {
    public static final int ID_MAX_TORNEI_ATTIVI = 1;

    @Id
    @Column(name = "id_impostazione")
    private Integer id;

    @Column(name = "valore", nullable = false)
    private Integer valore;

    protected Impostazione() { }

    public Impostazione(Integer id, Integer valore) {
        this.id = id;
        this.valore = valore;
    }

    public Integer getId() { return id; }
    public Integer getValore() { return valore; }
    public void setValore(Integer valore) { this.valore = valore; }
}

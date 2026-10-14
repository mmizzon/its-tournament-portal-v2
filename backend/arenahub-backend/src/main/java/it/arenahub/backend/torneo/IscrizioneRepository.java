package it.arenahub.backend.torneo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IscrizioneRepository extends JpaRepository<Iscrizione, Integer> {
    boolean existsByTorneo_IdAndUtente_Id(Integer torneoId, Integer utenteId);
    long countByTorneo_Id(Integer torneoId);
    Optional<Iscrizione> findByTorneo_IdAndUtente_Id(Integer torneoId, Integer utenteId);
    List<Iscrizione> findAllByUtente_Id(Integer utenteId);

    @Query(value = """
            SELECT COUNT(*)
            FROM (
                SELECT i.id_torneo
                FROM iscrizione i
                JOIN torneo t ON t.id_torneo = i.id_torneo
                WHERE i.id_utente = :utenteId
                  AND t.cancellato = 0
                  AND t.data_fine_torneo >= CURDATE()
                UNION
                SELECT s.id_torneo
                FROM componente_squadra cs
                JOIN squadra s ON s.id_squadra = cs.id_squadra
                JOIN torneo t ON t.id_torneo = s.id_torneo
                WHERE cs.id_utente = :utenteId
                  AND t.cancellato = 0
                  AND t.data_fine_torneo >= CURDATE()
            ) AS tornei_utente
            """, nativeQuery = true)
    long countTorneiAttiviByUtenteId(@Param("utenteId") Integer utenteId);
}

package it.arenahub.backend.torneo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IscrizioneRepository extends JpaRepository<Iscrizione, Integer> {
    boolean existsByTorneo_IdAndUtente_Id(Integer torneoId, Integer utenteId);
    long countByTorneo_Id(Integer torneoId);
    Optional<Iscrizione> findByTorneo_IdAndUtente_Id(Integer torneoId, Integer utenteId);
    List<Iscrizione> findAllByUtente_Id(Integer utenteId);
}

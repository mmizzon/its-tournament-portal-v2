package it.arenahub.backend.torneo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadraRepository extends JpaRepository<Squadra, Integer> {
    long countByTorneo_Id(Integer torneoId);
    boolean existsByTorneo_IdAndNomeIgnoreCase(Integer torneoId, String nome);
    boolean existsByTorneo_IdAndCreatore_Id(Integer torneoId, Integer creatoreId);
    Optional<Squadra> findByTorneo_IdAndCreatore_Id(Integer torneoId, Integer creatoreId);
}

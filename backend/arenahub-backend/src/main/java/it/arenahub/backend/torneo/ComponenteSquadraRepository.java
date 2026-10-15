package it.arenahub.backend.torneo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponenteSquadraRepository extends JpaRepository<ComponenteSquadra, Integer> {
    boolean existsBySquadra_IdAndUtente_Id(Integer squadraId, Integer utenteId);
    List<ComponenteSquadra> findAllByUtente_Id(Integer utenteId);
    long countBySquadra_Id(Integer squadraId);
}

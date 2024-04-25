package com.gao.Offre;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreRepository extends JpaRepository<Offre,UUID> {
}

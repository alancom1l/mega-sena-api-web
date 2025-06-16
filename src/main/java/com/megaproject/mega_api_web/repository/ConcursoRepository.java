package com.megaproject.mega_api_web.repository;

import com.megaproject.mega_api_web.entity.Concurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcursoRepository extends JpaRepository<Concurso, Integer> {

    @Query("SELECT MAX(c.id) FROM Concurso c")
    int findUltimoConcurso();

    @Query("SELECT c FROM Concurso c WHERE c.id >= :idMinimo")
    List<Concurso> findConcursosRecentes(@Param("idMinimo") int idMinimo);
}
package com.alura.literalura.repository;

import com.alura.literalura.model.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    @Query(value = "SELECT * FROM autores a WHERE a.ano_nascimento <= :ano AND (a.ano_morte > :ano OR a.ano_morte IS NULL)",
            nativeQuery = true)
    List<AutorEntity> findAutoresVivosNoAno(@Param("ano") Integer ano);
}

package com.alura.literalura.repository;

import com.alura.literalura.model.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<LivroEntity, Long> {
    List<LivroEntity> findByIdiomasContaining(String idioma);
}

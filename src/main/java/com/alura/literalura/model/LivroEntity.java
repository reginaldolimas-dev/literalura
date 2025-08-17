package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class LivroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LivroAutorEntity> autores = new ArrayList<>();
    private List<String> resumos;
    private List<String> assuntos;
    private List<String> estantes;
    private List<String> idiomas;
    private String tipoMidia;
    private int downloads;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<LivroAutorEntity> getAutores() {
        return autores;
    }

    public void setAutores(List<LivroAutorEntity> autores) {
        this.autores = autores;
    }

    public List<String> getResumos() {
        return resumos;
    }

    public void setResumos(List<String> resumos) {
        this.resumos = resumos;
    }

    public List<String> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<String> assuntos) {
        this.assuntos = assuntos;
    }

    public List<String> getEstantes() {
        return estantes;
    }

    public void setEstantes(List<String> estantes) {
        this.estantes = estantes;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getTipoMidia() {
        return tipoMidia;
    }

    public void setTipoMidia(String tipoMidia) {
        this.tipoMidia = tipoMidia;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}

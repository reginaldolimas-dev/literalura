package com.alura.literalura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<Autor> autores,
        @JsonAlias("summaries") List<String> resumos,
        @JsonAlias("translators") List<Tradutor> tradutores,
        @JsonAlias("subjects") List<String> assuntos,
        @JsonAlias("bookshelves") List<String> estantes,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("copyright") boolean copyright,
        @JsonAlias("media_type") String tipoMidia,
        @JsonAlias("formats") Map<String, String> formatos,
        @JsonAlias("download_count") int downloads
) {
}

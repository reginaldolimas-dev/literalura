package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String ENDERECO = "http://gutendex.com/books/?search=";
    private ConverteDados converteDados = new ConverteDados();

    LivroRepository livroRepository;
    AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivo em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscaLivroPorTitulo();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscaLivroPorTitulo() {
        System.out.println("Digite o título do livro para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+"));
        ResultadoApi resultado = converteDados.obterDados(json, ResultadoApi.class);

        for (DadosLivro livro : resultado.results()) {
            System.out.println("------ LIVRO -------");
            System.out.println("Título: " + livro.titulo());
            System.out.println("Autor: " + livro.autores()
                    .stream()
                    .map(Autor::nome)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Desconhecido"));

            String idiomas = livro.idiomas()
                    .stream()
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Desconhecido");

            System.out.println("Idioma: " + idiomas);
            System.out.println("Número de downloads: " + livro.downloads());
            System.out.println("----------------------");

            LivroEntity livroEntidade = new LivroEntity();
            livroEntidade.setTitulo(livro.titulo());
            livroEntidade.setDownloads(livro.downloads());
            String resumoTexto = String.join("\n", livro.resumos());
            livroEntidade.setResumos(resumoTexto);
            String idiomasTexto = String.join(", ", livro.idiomas());
            livroEntidade.setIdiomas(idiomasTexto);

            for (Autor autor : livro.autores()) {
                AutorEntity autorEntidade = new AutorEntity();
                autorEntidade.setNome(autor.nome());
                autorEntidade.setAnoNascimento(autor.anoNascimento());
                autorEntidade.setAnoMorte(autor.anoMorte());

                LivroAutorEntity livroAutorEntidade = new LivroAutorEntity();
                livroAutorEntidade.setLivro(livroEntidade);
                livroAutorEntidade.setAutor(autorEntidade);

                livroEntidade.getAutores().add(livroAutorEntidade);
            }

            livroRepository.save(livroEntidade);
        }
    }

    private void listarLivros() {
        List<LivroEntity> livrosEncontrados = livroRepository.findAll();

        livrosEncontrados.forEach(livro -> {
            System.out.println("------ LIVRO -------");

            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutores()
                    .stream()
                    .map(e -> e.getAutor().getNome())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Desconhecido"));

            System.out.println("Idioma: " + livro.getIdiomas());
            System.out.println("Número de downloads: " + livro.getDownloads());
            System.out.println("----------------------");

        });

    }

    private void listarAutores() {
        List<AutorEntity> autoresEncontrados = autorRepository.findAll();
        autoresEncontrados.forEach(autor -> {
            System.out.println("------ AUTOR -------");
            System.out.println("Nome: " + autor.getNome());
            System.out.println("Nascimento: " + autor.getAnoNascimento());
            System.out.println("Morte: " + autor.getAnoMorte());
            System.out.println("Livros: " + autor.getLivros().stream()
                    .map(e -> e.getLivro().getTitulo())
                    .collect(Collectors.joining(", ")));
            System.out.println("----------------------");
        });
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Insira o ano que deseja consultar");
        var ano = leitura.nextInt();
        leitura.nextLine();

        List<AutorEntity> autoresEncontrados = autorRepository.findAutoresVivosNoAno(ano);

        autoresEncontrados.forEach(autor -> {
            System.out.println("------ AUTOR -------");
            System.out.println("Nome: " + autor.getNome());
            System.out.println("Nascimento: " + autor.getAnoNascimento());
            System.out.println("Morte: " + autor.getAnoMorte());
            System.out.println("Livros: " + autor.getLivros().stream()
                    .map(e -> e.getLivro().getTitulo())
                    .collect(Collectors.joining(", ")));
        });
    }
}

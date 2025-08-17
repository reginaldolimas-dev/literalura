📚 LiterAlura
Um catálogo de livros interativo desenvolvido em Java com Spring Boot, que consome dados da API Gutendex para gerenciar uma biblioteca digital.
🚀 Funcionalidades

Buscar livro pelo título: Pesquisa livros na API Gutendx e salva no banco de dados
Listar livros registrados: Exibe todos os livros salvos localmente
Listar autores registrados: Mostra todos os autores com seus respectivos livros
Listar autores vivos em um ano específico: Filtra autores que estavam vivos em determinado ano
Listar livros por idioma: Busca livros em idiomas específicos (Português, Inglês, Espanhol, Francês)

🛠️ Tecnologias Utilizadas

Java 17+
Spring Boot 3.x
Spring Data JPA
Hibernate
PostgreSQL (ou H2 para desenvolvimento)
Jackson (para parsing JSON)
Maven

📦 Estrutura do Projeto
src/main/java/com/alura/literalura/
├── model/
│   ├── AutorEntity.java           # Entidade Autor
│   ├── LivroEntity.java          # Entidade Livro
│   ├── LivroAutorEntity.java     # Entidade de relacionamento
│   ├── Autor.java                # DTO Autor da API
│   ├── DadosLivro.java           # DTO Livro da API
│   └── ResultadoApi.java         # DTO Resultado da API
├── repository/
│   ├── AutorRepository.java      # Repository Autor
│   └── LivroRepository.java      # Repository Livro
├── service/
│   ├── ConsumoApi.java           # Serviço de consumo da API
│   └── ConverteDados.java        # Serviço de conversão JSON
├── principal/
│   └── Principal.java            # Classe principal com menu
└── LiteraluraApplication.java    # Classe main Spring Boot
🗄️ Modelo de Dados
Entidades Principais

AutorEntity: Armazena dados dos autores (nome, ano nascimento, ano morte)
LivroEntity: Armazena dados dos livros (título, idiomas, downloads, resumos)
LivroAutorEntity: Tabela de relacionamento many-to-many entre livros e autores

Relacionamentos

Um autor pode ter vários livros
Um livro pode ter vários autores
Relacionamento many-to-many implementado com entidade intermediária

🚀 Como Executar
Pré-requisitos

Java 17 ou superior
Maven 3.6+
PostgreSQL (ou use H2 para testes)

Configuração do Banco de Dados

Configure o application.properties:

properties# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost/literalura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Para H2 (desenvolvimento)
# spring.datasource.url=jdbc:h2:mem:literalura
# spring.datasource.driver-class-name=org.h2.Driver
# spring.jpa.hibernate.ddl-auto=create-drop
Executando o Projeto

Clone o repositório:

bashgit clone https://github.com/seu-usuario/literalura.git
cd literalura

Execute o projeto:

bashmvn spring-boot:run

O menu interativo será exibido no terminal

📋 Menu de Opções
1 - Buscar livro pelo título
2 - Listar livros registrados  
3 - Listar autores registrados
4 - Listar autores vivo em um determinado ano
5 - Listar livros em um determinado idioma
0 - Sair
🌐 API Externa
O projeto consome a Gutendex API, uma API gratuita que fornece dados do Project Gutenberg:

Base URL: http://gutendx.com/books/
Busca por título: http://gutendx.com/books/?search={titulo}

📝 Exemplos de Uso
Buscar Livro por Título
Digite o título do livro para busca
> Dom Casmurro

------ LIVRO -------
Título: Dom Casmurro
Autor: Machado de Assis
Idioma: pt
Número de downloads: 1523
----------------------
Listar Autores Vivos em um Ano
Insira o ano que deseja consultar
> 1900

------ AUTOR -------
Nome: Oscar Wilde
Nascimento: 1854
Morte: 1900
Livros: The Picture of Dorian Gray
Buscar por Idioma
Insira o idioma que deseja consultar
es - Espanhol
en - Inglês  
fr - Francês
pt - Português

> pt

------ LIVRO -------
Título: O Cortiço
Autor: Aluísio Azevedo
Idioma: pt
Número de downloads: 856
----------------------
🔍 Queries Principais
Autores Vivos em um Ano
sqlSELECT * FROM autores a 
WHERE a.ano_nascimento <= ? 
AND (a.ano_morte > ? OR a.ano_morte IS NULL)
Livros por Idioma
sqlSELECT * FROM livros l 
WHERE l.idiomas LIKE %?%

create database biblioteca;
-- drop database biblioteca;
use Biblioteca;

CREATE TABLE Aluno(
	ra INT NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL
	);
	
CREATE TABLE Exemplar(
    codigo INT NOT NULL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    qtdPaginas INT NOT NULL
	);
					
CREATE TABLE Livro(
    isbn CHAR(13) NOT NULL,
    edicao INT NOT NULL,
    exemplarCodigo INT,
    FOREIGN KEY (exemplarCodigo) REFERENCES exemplar(codigo) ON DELETE CASCADE
	);
	
CREATE TABLE Revista(
    issn CHAR(8) NOT NULL,
    exemplarCodigo INT,
    FOREIGN KEY (exemplarCodigo) REFERENCES exemplar(codigo) ON DELETE CASCADE
	);
	
CREATE TABLE Aluguel(
    data_retirada VARCHAR(10) NOT NULL,
    data_devolucao VARCHAR(10) NOT NULL,
    alunoRa INT,
    exemplarCodigo INT,
    FOREIGN KEY (alunoRa) REFERENCES Aluno(ra) ON DELETE CASCADE,
    FOREIGN KEY (exemplarCodigo) REFERENCES Exemplar(codigo) ON DELETE CASCADE,
    PRIMARY KEY (data_retirada, alunoRa, exemplarCodigo)
	);
    
DELIMITER //

CREATE PROCEDURE livroOrRevista (IN idIn INT)
BEGIN
	declare id int;
    declare saida varchar(30);
    select exemplarCodigo from Revista where exemplarCodigo = idIn into id;
    if (id is not null) then
		set saida = "Revista";
    else 
		select exemplarCodigo from Livro where exemplarCodigo = idIn into id;
        if (id is not null) then
			set saida = "Livro";
		else 
			set saida = "Não Existe";
		end if;
	end if;
    select saida;
END //
DELIMITER ;
# call livroOrRevista(3);
# select @saida;


/*Insercao de Alunos e Livros*/

INSERT INTO ALUNO (ra, nome, email) VALUES

(101,"Gabriel", "gabriel.santos@gmail.com"),
(102,"Clara", "clara.carvalho@gmail.com"),
(103,"Pedro", "pedro.henrique@gmail.com"),
(104,"Leandro", "leandro.colevati@gmail.com");

INSERT INTO EXEMPLAR (codigo, nome, qtdPaginas) VALUES

(1001, "Harry Potter: A Camera Secreta", 231),
(1002, "Analise e Projeto de Software", 421),
(1003, "Programacao e Orientação a Objetos", 854),
(1004, "Administração Geral", 231),
(1005, "Veja Geral", 12),
(1006, "Globo", 16);

INSERT INTO LIVRO (exemplarCodigo, isbn, edicao) VALUES

(1001, "954-214-35-2", 3),
(1002, "652-134-96-7", 6),
(1003, "025-454-32-4", 9),
(1004, "215-384-99-0", 5);

INSERT INTO REVISTA (exemplarCodigo, issn) VALUES

(1005, "95-974-4"),
(1006, "21-751-0");

INSERT INTO ALUGUEL (exemplarCodigo, alunoRa, data_retirada, data_devolucao) VALUES

(1001, 101, "2024-06-01", "2024-09-01"),
(1002, 102, "2024-11-01", "2025-02-04");
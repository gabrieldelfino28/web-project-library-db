package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Livro;
import model.Revista;
import persistance.GenericDao;
import persistance.LivroDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LivroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("livro.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Entrada
		String cmd = request.getParameter("botao");
		String codigo = request.getParameter("codigo");
		String nome = request.getParameter("nome");
		String qtd = request.getParameter("qtd");
		String isbn = request.getParameter("isbn");
		String edicao = request.getParameter("edicao");
		
		//Sa�da
		String saida = "";
		String erro = "";
		Livro l = new Livro();
		List<Livro> livros = new ArrayList<>();
		
		LivroController lc = new  LivroController();
		
		/*try {
			livros = lc.listarLivros();
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("livros", livros);
		}*/
		
		if (!cmd.contains("Listar")) {
			l.setCodigo(Integer.parseInt(codigo));
		}
		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			l.setNome(nome);
			l.setQtdPaginas(Integer.parseInt(qtd));
			l.setISBN(isbn);
			l.setEdicao(Integer.parseInt(edicao));
		}
		try {
			if (cmd.contains("Cadastrar")) {
				lc.cadastrarLivro(l);
				saida = "Livro cadastrado com sucesso!";
				l = null;
			}
			if (cmd.contains("Alterar")) {
				lc.alterarLivro(l);
				saida = "Livro alterado com sucesso!";
				l = null;
			}
			if (cmd.contains("Excluir")) {
				lc.excluirLivro(l);
				saida = "Livro excluído com sucesso!";
				l = null;
			}
			if (cmd.contains("Buscar")) {
				l = lc.buscarLivro(l);
			}
			if (cmd.contains("Listar")) {
				livros = lc.listarLivros();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("livro", l);
			request.setAttribute("livros", livros);
			
			RequestDispatcher rd = request.getRequestDispatcher("livro.jsp");
			rd.forward(request, response);
		}
	}

	

	

}

package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;
import model.Revista;
import persistance.GenericDao;
import persistance.RevistaDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/revista")
public class RevistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RevistaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("revista.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Entrada
		String cmd = request.getParameter("botao");
		String codigo = request.getParameter("codigo");
		String nome = request.getParameter("nome");
		String qtd = request.getParameter("qtd");
		String issn = request.getParameter("issn");
		
		//Sa�da
		String saida = "";
		String erro = "";
		Revista r = new Revista();
		List<Revista> revistas = new ArrayList<>();
		
		RevistaController rc = new RevistaController();
		
		/*try {
			revistas = rc.listarRevistas();
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("revistas", revistas);
		}*/
		
		if (!cmd.contains("Listar")) {
			r.setCodigo(Integer.parseInt(codigo));
		}
		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			r.setNome(nome);
			r.setQtdPaginas(Integer.parseInt(qtd));
			r.setISSN(issn);

		}
		try {
			if (cmd.contains("Cadastrar")) {
				rc.cadastrarRevista(r);
				saida = "Revista cadastrada com sucesso!";
				r = null;
			}
			if (cmd.contains("Alterar")) {
				rc.alterarRevista(r);
				saida = "Revista alterada com sucesso!";
				r = null;
			}
			if (cmd.contains("Excluir")) {
				rc.excluirRevista(r);
				saida = "Revista excluída com sucesso!";
				r = null;
			}
			if (cmd.contains("Buscar")) {
				r = rc.buscarRevista(r);
			}
			if (cmd.contains("Listar")) {
				revistas = rc.listarRevistas();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("revista", r);
			request.setAttribute("revistas", revistas);
			
			RequestDispatcher rd = request.getRequestDispatcher("revista.jsp");
			rd.forward(request, response);
		}
	}

	

}

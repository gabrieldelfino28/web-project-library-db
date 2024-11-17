package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;
import persistance.AlunoDao;
import persistance.GenericDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AlunoServlet() {
        super();    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("aluno.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Entrada
		String cmd = request.getParameter("botao");
		String ra = request.getParameter("ra");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		
		//Saída
		String saida = "";
		String erro = "";
		Aluno a = new Aluno();
		List<Aluno> alunos = new ArrayList<>();
		
		AlunoController ac = new AlunoController();
		
		/*try {
			alunos = ac.listarAlunos();
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {

			request.setAttribute("erro", erro);
			request.setAttribute("alunos", alunos);
		}*/
		
		if (!cmd.contains("Listar")) {
			a.setRa(Integer.parseInt(ra));
		}
		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			a.setNome(nome);
			a.setEmail(email);
		}
		try {
			if (cmd.contains("Cadastrar")) {
				ac.cadastrarAluno(a);
				saida = "Aluno cadastrado com sucesso!";
				a = null;
			}
			if (cmd.contains("Alterar")) {
				ac.alterarAluno(a);
				saida = "Aluno alterado com sucesso!";
				a = null;
			}
			if (cmd.contains("Excluir")) {
				ac.excluirAluno(a);
				saida = "Aluno excluído com sucesso!";
				a = null;
			}
			if (cmd.contains("Buscar")) {
				a = ac.buscarAluno(a);
			}
			if (cmd.contains("Listar")) {
				alunos = ac.listarAlunos();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("aluno", a);
			request.setAttribute("alunos", alunos);
			
			RequestDispatcher rd = request.getRequestDispatcher("aluno.jsp");
			rd.forward(request, response);
		}
	}

	

}

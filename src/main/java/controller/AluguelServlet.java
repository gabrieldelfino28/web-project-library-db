package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluguel;
import model.Aluno;
import model.Exemplar;
import model.Livro;
import model.Revista;
import persistance.AluguelDao;
import persistance.AlunoDao;
import persistance.GenericDao;
import persistance.LivroDao;
import persistance.RevistaDao;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/aluguel")
public class AluguelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AluguelServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String erro = "";
		List<Aluno> alunos = new ArrayList<>();
		List<Livro> livros = new ArrayList<>();
		List<Revista> revistas = new ArrayList<>();
		List<Exemplar> exemplares = new ArrayList<>();
		
		GenericDao gDao = new GenericDao();
		AlunoDao alDao = new AlunoDao(gDao);
		LivroDao lDao = new LivroDao(gDao);
		RevistaDao rDao = new RevistaDao(gDao);
		
		try {
			alunos = alDao.findAll();
			livros = lDao.findAll();
			revistas = rDao.findAll();
			if (!(livros==null)) {
				for (Livro l : livros) {
					exemplares.add(l);
				}
			}
			if (!(revistas==null)) {
				for (Revista r : revistas) {
					exemplares.add(r);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("alunos", alunos);
			request.setAttribute("exemplares", exemplares);
		}
		RequestDispatcher rd = request.getRequestDispatcher("aluguel.jsp");
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Entrada
		String cmd = request.getParameter("botao");
		String aluno = request.getParameter("aluno");
		String exemplar = request.getParameter("exemplar");
		String dataRetirada = request.getParameter("dataRetirada");
		String dataDevolucao = request.getParameter("dataDevolucao");
		
		//Saída
		String saida = "";
		String erro = "";
		Aluguel al = new Aluguel();
		List<Aluguel> alugueis = new ArrayList<>();
		List<Aluno> alunos = new ArrayList<>();
		List<Livro> livros = new ArrayList<>();
		List<Revista> revistas = new ArrayList<>();
		List<Exemplar> exemplares = new ArrayList<>();
		
		GenericDao gDao = new GenericDao();
		AlunoController ac = new AlunoController();
		LivroController lc =  new LivroController();
		RevistaController rc = new RevistaController();
		AluguelController alc = new AluguelController();

		
		try {
			alunos = ac.listarAlunos();
			livros = lc.listarLivros();
			revistas = rc.listarRevistas();
			if (!(livros==null)) {
				for (Livro l : livros) {
					exemplares.add(l);
				}
			}
			if (!(revistas==null)) {
				for (Revista r : revistas) {
					exemplares.add(r);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("alunos", alunos);
			request.setAttribute("exemplares", exemplares);
		}
		
		if (!cmd.contains("Listar")) {
			Aluno a = new Aluno();
			a.setRa(Integer.parseInt(aluno));
			try {
				a = ac.buscarAluno(a);
			} catch (ClassNotFoundException | SQLException e) {
				erro = e.getMessage();
			}
			al.setAluno(a);
			Exemplar e = new Exemplar();
			for (Exemplar ex : exemplares) {
				if (ex.getCodigo() == Integer.parseInt(exemplar)) {
					e = ex;
				}
			}
			al.setExemplar(e);
		}
		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			al.setDataDevolucao(LocalDate.parse(dataDevolucao));
			al.setDataRetirada(LocalDate.parse(dataRetirada));
		}
		try {
			if (cmd.contains("Cadastrar")) {
				alc.cadastrarAluguel(al);
				saida = "Aluguel cadastrado com sucesso!";
				al = null;
			}
			if (cmd.contains("Alterar")) {
				alc.alterarAluguel(al);
				saida = "Aluguel alterado com sucesso!";
				al = null;
			}
			if (cmd.contains("Excluir")) {
				alc.excluirAluguel(al);
				saida = "Aluguel excluído com sucesso!";
				al = null;
			}
			if (cmd.contains("Buscar")) {
				al = alc.buscarAluguel(al);
			}
			if (cmd.contains("Listar")) {
				alugueis = alc.listarAluguel();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} catch (Exception e){
			erro = e.getMessage();
			System.out.println(e.getMessage());
		}finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("aluguel", al);
			request.setAttribute("alugueis", alugueis);
			
			RequestDispatcher rd = request.getRequestDispatcher("aluguel.jsp");
			rd.forward(request, response);
		}
	}

	

}

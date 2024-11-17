package controller;

import java.sql.SQLException;
import java.util.List;

import model.Aluno;
import persistance.AlunoDao;
import persistance.GenericDao;

public class AlunoController {
	
	private GenericDao gDao;
	private AlunoDao alDao;

	public AlunoController() {
		gDao = new GenericDao();
		alDao = new AlunoDao(gDao);
	}
	
	public void cadastrarAluno(Aluno a) throws SQLException, ClassNotFoundException {
		alDao.insert(a);
		
	}

	public void alterarAluno(Aluno a) throws SQLException, ClassNotFoundException{
		alDao.update(a);
		
	}

	public void excluirAluno(Aluno a) throws SQLException, ClassNotFoundException{
		alDao.delete(a);
		
	}

	public Aluno buscarAluno(Aluno a) throws SQLException, ClassNotFoundException{
		a = alDao.findOne(a);
		return a;
	}

	public List<Aluno> listarAlunos() throws SQLException, ClassNotFoundException{
		List<Aluno> alunos = alDao.findAll();
		return alunos;
	}

}

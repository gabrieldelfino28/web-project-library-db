package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Aluguel;
import persistance.AluguelDao;
import persistance.GenericDao;

public class AluguelController {
	
	private GenericDao gDao;
	private AluguelDao alDao;

	public AluguelController() {
		gDao = new GenericDao();
		alDao = new AluguelDao(gDao);
	}
	
	public void cadastrarAluguel(Aluguel al) throws SQLException, ClassNotFoundException {
		alDao.insert(al);
	}

	public void alterarAluguel(Aluguel al) throws SQLException, ClassNotFoundException {
		alDao.update(al);
	}

	public void excluirAluguel(Aluguel al) throws SQLException, ClassNotFoundException {
		alDao.delete(al);
	}

	public Aluguel buscarAluguel(Aluguel al) throws SQLException, ClassNotFoundException, Exception {
		al = alDao.findOne(al);
		if (al.getAluno().equals(null) || al.getExemplar().equals(null)) {
			throw new Exception("Selecione um aluno e um exemplar para buscar");
		}
		return al;
	}

	public List<Aluguel> listarAluguel() throws SQLException, ClassNotFoundException {
		List<Aluguel> alugueis = new ArrayList<>();
		alugueis = alDao.findAll();
		return alugueis;
	}

}

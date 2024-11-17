package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Revista;
import persistance.GenericDao;
import persistance.RevistaDao;

public class RevistaController {
	
	private GenericDao gDao;
	private RevistaDao rDao;

	public RevistaController() {
		gDao = new GenericDao();
		rDao = new RevistaDao(gDao);
	}
	
	public void cadastrarRevista(Revista r) throws SQLException, ClassNotFoundException {
		rDao.insert(r);
		
	}

	public void alterarRevista(Revista r) throws SQLException, ClassNotFoundException {
		rDao.update(r);
		
	}

	public void excluirRevista(Revista r) throws SQLException, ClassNotFoundException {
		rDao.delete(r);
		
	}

	public Revista buscarRevista(Revista r) throws SQLException, ClassNotFoundException {
		r = rDao.findOne(r);
		return r;
	}

	public List<Revista> listarRevistas() throws SQLException, ClassNotFoundException {
		List<Revista> revistas = new ArrayList<>();
		revistas = rDao.findAll();
		return revistas;
	}

}

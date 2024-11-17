package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Livro;
import persistance.GenericDao;
import persistance.LivroDao;

public class LivroController {
	
	private GenericDao gDao;
	private LivroDao lDao;

	public LivroController() {
		gDao = new GenericDao();
		lDao = new LivroDao(gDao);
	}
	
	public void cadastrarLivro(Livro l) throws SQLException, ClassNotFoundException {
		lDao.insert(l);
		
	}
	
	public void alterarLivro(Livro l) throws SQLException, ClassNotFoundException {
		lDao.update(l);
	}

	public void excluirLivro(Livro l) throws SQLException, ClassNotFoundException {
		lDao.delete(l);
	}

	public Livro buscarLivro(Livro l) throws SQLException, ClassNotFoundException {
		l = lDao.findOne(l);
		return l;
	}

	public List<Livro> listarLivros() throws SQLException, ClassNotFoundException {
		List<Livro> livros = new ArrayList<>();
		livros = lDao.findAll();
		return livros;
	}

}

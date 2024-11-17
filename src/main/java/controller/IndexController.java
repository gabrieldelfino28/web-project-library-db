package controller;
 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import model.Livro;
import persistance.GenericDao;
import persistance.IndexDao;
import persistance.LivroDao;
 
public class IndexController {
	
	private GenericDao gDao;
	private IndexDao IDao;
 
	public IndexController() {
		gDao = new GenericDao();
		IDao = new IndexDao(gDao);
	}
 
	public String buscarExemplar(Livro l) throws SQLException, ClassNotFoundException {
		String Tipo = IDao.identificaItem(l);
		return Tipo;
	}
 
}
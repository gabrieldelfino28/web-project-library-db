package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.ICRUDDao;
import model.Livro;

public class LivroDao implements ICRUDDao<Livro>{

	private GenericDao gDao;

	public LivroDao(GenericDao gDao) {
		this.gDao = gDao;
	}


	@Override
	public void insert(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "INSERT INTO Exemplar VALUES (?, ?, ?)";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, l.getCodigo());
		ps.setString(2, l.getNome());
		ps.setInt(3, l.getQtdPaginas());
		ps.execute();

		SQL = "INSERT INTO Livro VALUES (?, ?, ?)";
		ps = c.prepareStatement(SQL);
		ps.setInt(3, l.getCodigo());
		ps.setString(1, l.getISBN());
		ps.setInt(2, l.getEdicao());
		ps.execute();

		ps.close();
		c.close();
		
	}

	@Override
	public void update(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "UPDATE exemplar SET nome=?, qtdPaginas=? WHERE codigo=?";
		PreparedStatement ps = c.prepareStatement(SQL);

		ps.setString(1, l.getNome());
		ps.setInt(2, l.getQtdPaginas());
		ps.setInt(3, l.getCodigo());
		ps.execute();

		SQL = "UPDATE livro SET isbn=?, edicao=? WHERE ExemplarCodigo=?";
		ps = c.prepareStatement(SQL);
		ps.setString(1, l.getISBN());
		ps.setInt(2, l.getEdicao());
		ps.setInt(3, l.getCodigo());

		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void delete(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "DELETE FROM livro WHERE ExemplarCodigo=?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, l.getCodigo());
		ps.execute();

		SQL = "DELETE FROM exemplar WHERE codigo=?";
		ps = c.prepareStatement(SQL);
		ps.setInt(1, l.getCodigo());
		ps.execute();

		ps.close();
		c.close();
	}

	@Override
	public Livro findOne(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT l.isbn AS isbn, l.edicao AS edicao, ");
		sql.append("e.codigo AS codigoExemplar, e.nome AS nome, e.qtdPaginas AS paginas ");
		sql.append("FROM livro l, exemplar e ");
		sql.append("WHERE l.ExemplarCodigo = e.codigo ");
		sql.append("AND l.ExemplarCodigo = ? ");

		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setInt(1, l.getCodigo());
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			l.setCodigo(rs.getInt("codigoExemplar"));
			l.setNome(rs.getString("nome"));
			l.setISBN(rs.getString("isbn"));
			l.setQtdPaginas(rs.getInt("paginas"));
			l.setEdicao(rs.getInt("edicao"));
		}

		rs.close();
		ps.close();
		c.close();

		return l;
	}

	@Override
	public List<Livro> findAll() throws SQLException, ClassNotFoundException {
		List<Livro> livros = new ArrayList<>();
		Connection c = gDao.getConnection();

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT l.isbn AS isbn, l.edicao AS edicao, ");
		sql.append("e.codigo AS codigoExemplar, e.nome AS nome, e.qtdPaginas AS paginas ");
		sql.append("FROM livro l, exemplar e ");
		sql.append("WHERE l.ExemplarCodigo = e.codigo ");


		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Livro l = new Livro();
			l.setCodigo(rs.getInt("codigoExemplar"));
			l.setNome(rs.getString("nome"));
			l.setISBN(rs.getString("isbn"));
			l.setQtdPaginas(rs.getInt("paginas"));
			l.setEdicao(rs.getInt("edicao"));
			livros.add(l);
		}

		rs.close();
		ps.close();
		c.close();

		return livros;
	}

}

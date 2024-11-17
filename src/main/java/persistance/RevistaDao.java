package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.ICRUDDao;
import model.Revista;

public class RevistaDao implements ICRUDDao<Revista> {

	private GenericDao gDao;

	public RevistaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public void insert(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "INSERT INTO Exemplar VALUES (?, ?, ?)";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, r.getCodigo());
		ps.setString(2, r.getNome());
		ps.setInt(3, r.getQtdPaginas());
		ps.execute();

		SQL = "INSERT INTO Revista VALUES (?, ?)";
		ps = c.prepareStatement(SQL);
		ps.setInt(2, r.getCodigo());
		ps.setString(1, r.getISSN());
		ps.execute();

		ps.close();
		c.close();
	}

	@Override
	public void update(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "UPDATE exemplar SET nome=?, qtdPaginas=? WHERE codigo=?";
		PreparedStatement ps = c.prepareStatement(SQL);

		ps.setString(1, r.getNome());
		ps.setInt(2, r.getQtdPaginas());
		ps.setInt(3, r.getCodigo());
		ps.execute();

		SQL = "UPDATE revista SET issn=? WHERE ExemplarCodigo=?";
		ps = c.prepareStatement(SQL);
		ps.setString(1, r.getISSN());
		ps.setInt(2, r.getCodigo());

		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void delete(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "DELETE FROM revista WHERE ExemplarCodigo=?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, r.getCodigo());
		ps.execute();
		
		SQL = "DELETE FROM exemplar WHERE codigo=?";
		ps = c.prepareStatement(SQL);
		ps.setInt(1, r.getCodigo());
		ps.execute();

		ps.close();
		c.close();
		
	}

	@Override
	public Revista findOne(Revista r) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT r.issn, ");
		sql.append("e.codigo AS codigoExemplar, e.nome AS nome, e.qtdPaginas AS paginas ");
		sql.append("FROM revista r, exemplar e ");
		sql.append("WHERE r.ExemplarCodigo = e.codigo ");
		sql.append("AND r.ExemplarCodigo = ? ");

		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setInt(1, r.getCodigo());
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			r.setCodigo(rs.getInt("codigoExemplar"));
			r.setNome(rs.getString("nome"));
			r.setQtdPaginas(rs.getInt("paginas"));
			r.setISSN(rs.getString("issn"));
		}

		rs.close();
		ps.close();
		c.close();

		return r;
	}

	@Override
	public List<Revista> findAll() throws SQLException, ClassNotFoundException {
		List<Revista> revistas = new ArrayList<>();
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT r.issn, ");
		sql.append("e.codigo AS codigoExemplar, e.nome AS nome, e.qtdPaginas AS paginas ");
		sql.append("FROM revista r, exemplar e ");
		sql.append("WHERE r.ExemplarCodigo = e.codigo ");

		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Revista r =  new Revista();
			r.setCodigo(rs.getInt("codigoExemplar"));
			r.setNome(rs.getString("nome"));
			r.setQtdPaginas(rs.getInt("paginas"));
			r.setISSN(rs.getString("issn"));
			revistas.add(r);
		}

		rs.close();
		ps.close();
		c.close();

		return revistas;
	}

}

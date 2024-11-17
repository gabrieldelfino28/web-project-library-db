package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import controller.ICRUDDao;
import model.Aluno;

public class AlunoDao implements ICRUDDao<Aluno>{
	
	private GenericDao gDao;

	public AlunoDao(GenericDao gDao) {
		this.gDao = gDao;
	}


	@Override
	public void insert(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO aluno VALUES (?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, a.getRa());
		ps.setString(2, a.getNome());
		ps.setString(3, a.getEmail());
		ps.execute();
		ps.close();
		c.close();
		
	}

	@Override
	public void update(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE aluno SET nome=?, email=? WHERE ra=?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, a.getNome());
		ps.setString(2, a.getEmail());
		ps.setInt(3, a.getRa());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void delete(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE FROM aluno WHERE ra=?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, a.getRa());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public Aluno findOne(Aluno a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "select ra, nome, email FROM aluno WHERE ra=?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, a.getRa());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			a.setRa(rs.getInt("ra"));
			a.setNome(rs.getString("nome"));
			a.setEmail(rs.getString("email"));
		}
		ps.close();
		c.close();
		return a;
	}

	@Override
	public List<Aluno> findAll() throws SQLException, ClassNotFoundException {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Connection c = gDao.getConnection();
		String sql = "select ra, nome, email FROM aluno";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Aluno a = new Aluno();
			a.setRa(rs.getInt("ra"));
			a.setNome(rs.getString("nome"));
			a.setEmail(rs.getString("email"));
			
			alunos.add(a);
		}
		ps.close();
		c.close();
		return alunos;
	}

}

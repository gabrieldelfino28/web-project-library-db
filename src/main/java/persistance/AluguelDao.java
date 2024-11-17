package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.ICRUDDao;
import model.Aluguel;
import model.Aluno;
import model.Exemplar;
import model.Livro;
import model.Revista;

public class AluguelDao implements ICRUDDao<Aluguel> {

	private GenericDao gDao;

	public AluguelDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public void insert(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO aluguel VALUES (?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(3, al.getExemplar().getCodigo());
		ps.setInt(4, al.getAluno().getRa());
		ps.setString(1, al.getDataRetirada().toString());
		ps.setString(2, al.getDataDevolucao().toString());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void update(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "UPDATE Aluguel SET data_retirada=?, data_devolucao=?, ExemplarCodigo=?, AlunoRa=? WHERE ExemplarCodigo=? AND AlunoRa=?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setString(1, al.getDataRetirada().toString());
		ps.setString(2, al.getDataDevolucao().toString());
		ps.setInt(3, al.getExemplar().getCodigo());
		ps.setInt(4, al.getAluno().getRa());
		ps.setInt(5, al.getExemplar().getCodigo());
		ps.setInt(6, al.getAluno().getRa());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void delete(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String SQL = "DELETE FROM Aluguel WHERE AlunoRa=? AND ExemplarCodigo=?";
		PreparedStatement ps = c.prepareStatement(SQL);
		ps.setInt(1, al.getAluno().getRa());
		ps.setInt(2, al.getExemplar().getCodigo());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public Aluguel findOne(Aluguel al) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT al.data_retirada, al.data_devolucao, ");
		sql.append("a.ra AS alunoRa, a.nome AS nomeAluno, a.email, ");
		sql.append("e.codigo AS codigoExemplar, e.nome AS nomeExemplar, e.qtdPaginas ");
		sql.append("FROM aluguel al, exemplar e, aluno a ");
		sql.append("WHERE al.ExemplarCodigo = e.codigo AND  al.AlunoRa = a.ra ");
		sql.append("AND al.ExemplarCodigo = ? AND al.AlunoRa = ?");

		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setInt(1, al.getExemplar().getCodigo());
		ps.setInt(2, al.getAluno().getRa());
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Exemplar e = null;
			Aluno a = new Aluno();
			a.setRa(rs.getInt("alunoRa"));
			a.setNome(rs.getString("nomeAluno"));
			a.setEmail(rs.getString("email"));
			
			al.setAluno(a);
			String SQL = "Select count(*) from livro WHERE ExemplarCodigo = ? AND edicao IS NOT NULL";
			PreparedStatement ps1 = c.prepareStatement(SQL);
			ps1.setInt(1,  rs.getInt("codigoExemplar"));
			ResultSet rs1 = ps1.executeQuery();
			
			if (rs1.next()) {
				if (rs1.getInt(1) > 0) {
					e = new Livro();
				} else {
					e = new Revista();
				}
			}
			
			e.setCodigo(rs.getInt("codigoExemplar"));
			e.setNome(rs.getString("nomeExemplar"));
			e.setQtdPaginas(rs.getInt("qtdPaginas"));
			
			al.setExemplar(e);
			al.setDataRetirada(LocalDate.parse(rs.getString("data_retirada")));
			al.setDataDevolucao(LocalDate.parse(rs.getString("data_devolucao")));
		}
		ps.close();
		c.close();
		return al;
	}

	@Override
	public List<Aluguel> findAll() throws SQLException, ClassNotFoundException {
		List<Aluguel> alugueis = new ArrayList<>();
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT al.data_retirada, al.data_devolucao, ");
		sql.append("a.ra AS alunoRa, a.nome AS nomeAluno, a.email, ");
		sql.append("e.codigo AS codigoExemplar, e.nome AS nomeExemplar, e.qtdPaginas ");
		sql.append("FROM aluguel al, exemplar e, aluno a ");
		sql.append("WHERE al.ExemplarCodigo = e.codigo AND  al.AlunoRa = a.ra");
		
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Aluguel al = new Aluguel();
			Exemplar e = null;
			Aluno a = new Aluno();
			a.setRa(rs.getInt("alunoRa"));
			a.setNome(rs.getString("nomeAluno"));
			a.setEmail(rs.getString("email"));
			
			al.setAluno(a);
			String SQL = "Select count(*) from livro WHERE ExemplarCodigo = ? AND edicao IS NOT NULL";
			PreparedStatement ps1 = c.prepareStatement(SQL);
			ps1.setInt(1,  rs.getInt("codigoExemplar"));
			ResultSet rs1 = ps1.executeQuery();
			
			if (rs1.next()) {
				if (rs1.getInt(1) > 0) {
					e = new Livro();
				} else {
					e = new Revista();
				}
			}
			
			e.setCodigo(rs.getInt("codigoExemplar"));
			e.setNome(rs.getString("nomeExemplar"));
			e.setQtdPaginas(rs.getInt("qtdPaginas"));
			
			al.setExemplar(e);
			al.setDataRetirada(LocalDate.parse(rs.getString("data_retirada")));
			al.setDataDevolucao(LocalDate.parse(rs.getString("data_devolucao")));
			
			alugueis.add(al);
		}
		ps.close();
		c.close();
		return alugueis;
	}

}

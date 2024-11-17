package persistance;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import com.mysql.cj.jdbc.CallableStatement;
 
import model.Livro;
 
public class IndexDao{
 
	private GenericDao gDao;
 
	public IndexDao(GenericDao gDao) {
		this.gDao = gDao;
	}
	
	public String identificaItem(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		CallableStatement cs;
		
		cs = (CallableStatement) c.prepareCall("{CALL livroOrRevista(?)}");
		cs.setInt(1, l.getCodigo());
		//cs.registerOutParameter(1, java.sql.Types.VARCHAR);
		ResultSet rs = cs.executeQuery();
		
		String tipo = "";
		if (rs.next()) {
 
			tipo = rs.getString("saida");
		}
		
		rs.close();
		cs.close();
		c.close();
 
		return tipo;
	}
}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {
	private Connection connection;

	public SQL(){
		try
		{
			this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PAC", "postgres", "Kyliann.0Bado");
			/*Faire la somme en equivalent TD de toute les affectation des intervenants en fonction de chaque semestre*/
			ResultSet resIntervenant = connection.createStatement().executeQuery("SELECT i.* FROM Intervenant i JOIN Afffectation a ON i.codInter = a.codInter");
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
}

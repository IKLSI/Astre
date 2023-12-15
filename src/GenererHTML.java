import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

public class GenererHTML
{
	public static void GenererIntervenant()
	{
		try
		{
			ResultSet resultSet = Controleur.getIntervenant_final();
			PrintWriter pw = new PrintWriter( new FileOutputStream("sortie.html") );
			pw.println ("<!DOCTYPE html>\n" + //
						"<html lang=\"fr\">\n" + //
						"\t<head>\n" + //
						"\t\t<title>Prévisualisation</title>\n" + //
						"\t\t<meta charset=utf-8>\n" + //
						"\t</head>\n" + //
						"\t<body>\n" + //
						"\t\t<table>\n" + //
						"\t\t\t<thead>\n" + //
						"\t\t\t\t<tr>\n");
			
			resultSet.next();
			for(int cpt = 0; cpt < resultSet.getMetaData().getColumnCount(); cpt++)
				pw.println ("\t\t\t\t\t<th>\n" + //
							"\t\t\t\t\t\t"+resultSet.getString(cpt)+"\n" + //
							"\t\t\t\t\t</th>\n");

			pw.println ("\t\t\t\t</tr>\n" + //
						"\t\t\t</thead>\n" + //
						"\t\t\t<tbody>\n");
					
			while(resultSet.next()) {
				pw.println ("\t\t\t\t<tr>\n");
				for(int cpt = 0; cpt < resultSet.getMetaData().getColumnCount(); cpt++) {
					pw.println ("\t\t\t\t\t<td>\n" + //
								"\t\t\t\t\t"+resultSet.getString(cpt)+"\n" +
								"\t\t\t\t\t<\td>\n");
				}
				pw.println ("\t\t\t\t</tr>\n");
			}
			pw.println ("\t\t\t</tbody>\n" + //
						"\t\t</table>\n" + //
						"\t</body>\n" + //
						"</html>");
		}
		catch (Exception e){ e.printStackTrace(); }
	}
}
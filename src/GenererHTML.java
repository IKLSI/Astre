import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.*;  

public class GenererHTML
{
	public static void GenererIntervenant(String nom,int codInter)
	{
		try
		{
            ResultSet resultSetInter = Controleur.getIntervenant_final(codInter);
            ResultSet resultSetAffect = Controleur.getModuleParIntervenant(codInter);
			PrintWriter pw = new PrintWriter( new FileOutputStream(nom+".html") );
			pw.println ("<!DOCTYPE html>\n" + //
						"<html lang=\"fr\">\n" + //
						"\t<head>\n" + //
						"\t\t<title>Prévisualisation</title>\n" + //
						"\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"webapp.css\">\n" + //
						"\t\t<meta charset=utf-8>\n" + //
						"\t</head>\n" + //
						"\t<body>\n" + //
						"\t\t<h2>Prévisualisation d'intervenant</h2>\n" + //
						"\t\t<table>\n" + //
						"\t\t\t<thead>\n" + //
						"\t\t\t\t<tr>\n");

			for(int cpt = 1; cpt <= resultSetInter.getMetaData().getColumnCount(); cpt++)
				pw.println ("\t\t\t\t\t<th>\n" + //
							"\t\t\t\t\t\t"+resultSetInter.getMetaData().getColumnName(cpt)+"\n" + //
							"\t\t\t\t\t</th>\n");

			pw.println ("\t\t\t\t</tr>\n" + //
						"\t\t\t</thead>\n" + //
						"\t\t\t<tbody>\n");

			while(resultSetInter.next()) {
				pw.println ("\t\t\t\t<tr>\n");
				for(int cpt = 1; cpt <= resultSetInter.getMetaData().getColumnCount(); cpt++) {
					pw.println ("\t\t\t\t\t<td>\n" + //
								"\t\t\t\t\t"+resultSetInter.getString(cpt)+"\n" +
								"\t\t\t\t\t</td>\n");
				}
				pw.println ("\t\t\t\t</tr>\n");
			}

			pw.println ("\t\t\t</tbody>\n" + //
						"\t\t</table>\n" + 
						"\t\t<h2>Prévisualisation des affectations</h2>\n" + //
						"\t\t<table>\n" + //
						"\t\t\t<thead>\n" + //
						"\t\t\t\t<tr>\n");
			pw.println ("\t\t\t\t\t<th>\n" + //
						"\t\t\t\t\t\t"+resultSetAffect.getMetaData().getColumnName(1)+"\n" + //
						"\t\t\t\t\t</th>\n");
			for(int cpt = 4; cpt <= resultSetAffect.getMetaData().getColumnCount(); cpt++)
				pw.println ("\t\t\t\t\t<th>\n" + //
							"\t\t\t\t\t\t"+resultSetAffect.getMetaData().getColumnName(cpt)+"\n" + //
							"\t\t\t\t\t</th>\n");

			pw.println ("\t\t\t\t</tr>\n" + //
						"\t\t\t</thead>\n" + //
						"\t\t\t<tbody>\n");

			while(resultSetAffect.next()) {
				pw.println ("\t\t\t\t<tr>\n");
				pw.println ("\t\t\t\t\t<td>\n" + //
							"\t\t\t\t\t"+resultSetAffect.getString(1)+"\n" +
							"\t\t\t\t\t</td>\n");
				for(int cpt = 4; cpt <= resultSetAffect.getMetaData().getColumnCount(); cpt++) {
					if(resultSetAffect.getString(cpt) == null)
						pw.println ("\t\t\t\t\t<td>\n" + //
									"\t\t\t\t\t"+0+"\n" +
									"\t\t\t\t\t</td>\n");
					else
						pw.println ("\t\t\t\t\t<td>\n" + //
									"\t\t\t\t\t"+resultSetAffect.getString(cpt)+"\n" +
									"\t\t\t\t\t</td>\n");
				}
				pw.println ("\t\t\t\t</tr>\n");
			}

			pw.println ("\t</body>\n" + //
						"</html>");
			pw.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

	public static void GenererModule(String nom, String codMod)
	{
		try
		{
            ResultSet resultSetModule = Controleur.getModule(codMod);
            ResultSet resultSetAffect = Controleur.getAffectationRessource(codMod);
			PrintWriter pw = new PrintWriter( new FileOutputStream(nom+".html") );
			pw.println ("<!DOCTYPE html>\n" + //
						"<html lang=\"fr\">\n" + //
						"\t<head>\n" + //
						"\t\t<title>Prévisualisation</title>\n" + //
						"\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"webapp.css\">\n" + //
						"\t\t<meta charset=utf-8>\n" + //
						"\t</head>\n" + //
						"\t<body>\n" + //
						"\t\t<h2>Prévisualisation de module</h2>\n" + //
						"\t\t<table>\n" + //
						"\t\t\t<thead>\n" + //
						"\t\t\t\t<tr>\n");

			for(int cpt = 1; cpt <= resultSetModule.getMetaData().getColumnCount(); cpt++)
				pw.println ("\t\t\t\t\t<th>\n" + //
							"\t\t\t\t\t\t"+resultSetModule.getMetaData().getColumnName(cpt)+"\n" + //
							"\t\t\t\t\t</th>\n");

			pw.println ("\t\t\t\t</tr>\n" + //
						"\t\t\t</thead>\n" + //
						"\t\t\t<tbody>\n");

			while(resultSetModule.next()) {
				pw.println ("\t\t\t\t<tr>\n");
				for(int cpt = 1; cpt <= resultSetModule.getMetaData().getColumnCount(); cpt++) {
					pw.println ("\t\t\t\t\t<td>\n" + //
								"\t\t\t\t\t"+resultSetModule.getString(cpt)+"\n" +
								"\t\t\t\t\t</td>\n");
				}
				pw.println ("\t\t\t\t</tr>\n");
			}

			pw.println ("\t\t\t</tbody>\n" + //
						"\t\t</table>\n" + 
						"\t\t<h2>Prévisualisation des affectations</h2>\n" + //
						"\t\t<table>\n" + //
						"\t\t\t<thead>\n" + //
						"\t\t\t\t<tr>\n");
			for(int cpt = 3; cpt <= resultSetAffect.getMetaData().getColumnCount(); cpt++)
				pw.println ("\t\t\t\t\t<th>\n" + //
							"\t\t\t\t\t\t"+resultSetAffect.getMetaData().getColumnName(cpt)+"\n" + //
							"\t\t\t\t\t</th>\n");

			pw.println ("\t\t\t\t</tr>\n" + //
						"\t\t\t</thead>\n" + //
						"\t\t\t<tbody>\n");

			while(resultSetAffect.next()) {
				pw.println ("\t\t\t\t<tr>\n");
				for(int cpt = 3; cpt <= resultSetAffect.getMetaData().getColumnCount(); cpt++) {
					if(resultSetAffect.getString(cpt) == null)
						pw.println ("\t\t\t\t\t<td>\n" + //
									"\t\t\t\t\t"+0+"\n" +
									"\t\t\t\t\t</td>\n");
					else
						pw.println ("\t\t\t\t\t<td>\n" + //
									"\t\t\t\t\t"+resultSetAffect.getString(cpt)+"\n" +
									"\t\t\t\t\t</td>\n");
				}
				pw.println ("\t\t\t\t</tr>\n");
			}

			pw.println ("\t</body>\n" + //
						"</html>");
			pw.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}

	public static void genererCSV(String nom) {
		try
		{
            ResultSet rs = Controleur.getIntervenant_final();
			PrintWriter pw = new PrintWriter( new FileOutputStream(nom+".csv") );
			for(int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++) {
				if(cpt != 1)
					pw.print(",");
				pw.print(rs.getMetaData().getColumnName(cpt));
			}
			while(rs.next())
			{
				for(int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++) {
					if(cpt != 1)
						pw.print(",");
					pw.print(rs.getString(cpt));
				}
				pw.print("\n");
			}
			pw.close();
		}
		catch (Exception e){ e.printStackTrace(); }
	}
}
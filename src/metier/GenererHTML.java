package metier;
import java.io.*;
import java.sql.*;
import controleur.Controleur;

public class GenererHTML
{
	public static void GenererIntervenant(String nom,int codInter)
	{
		try
		{
			ResultSet resultSetInter = Controleur.getInformationsInter(codInter);
			ResultSet resultSetAffect = Controleur.getModuleParIntervenant(codInter);
			PrintWriter pw = new PrintWriter( new FileOutputStream(nom+".html") );
			pw.println ("<!DOCTYPE html>\n" + //
						"<html lang=\"fr\">\n" + //
						"\t<head>\n" + //
						"\t\t<title>Prévisualisation</title>\n" + //
						"\t\t<meta charset=utf-8>\n" + //
						"\t\t<style>\n" + //
						"\t\t\tbody {\n" + //
						"\t\t\t\tmax-width: 800px;\n" + //
						"\t\t\t\tmargin: 20px auto;\n" + //
						"\t\t\t\tpadding: 20px;\n" + //
						"\t\t\t\tfont-family: Arial, sans-serif;\n" + //
						"\t\t\t\tline-height: 1.6;\n" + //
						"\t\t\t}\n" + //
						"\t\t\ttable {\n" + //
						"\t\t\t\twidth: 100%;\n" + //
						"\t\t\t\tborder-collapse: collapse;\n" + //
						"\t\t\t\tmargin-top: 10px;\n" + //
						"\t\t\t}\n" + //
						"\t\t\tth, td {\n" + //
						"\t\t\t\tborder: 1px solid #ddd;\n" + //
						"\t\t\t\tpadding: 8px;\n" + //
						"\t\t\t\ttext-align: left;\n" + //
						"\t\t\t}\n" + //
						"\t\t\tth {\n" + //
						"\t\t\t\tbackground-color: #f2f2f2;\n" + //
						"\t\t\t}\n" + //
						"\t\t\th2 {\n" + //
						"\t\t\t\tcolor: #333;\n" + //
						"\t\t\t\tmargin-bottom: 10px;\n" + //
						"\t\t\t\tmargin-top: 50px;\n" + //
						"\t\t\t\ttext-align: center;\n" + //
						"\t\t\t}\n" + //
						"\t\t</style>\n" + //
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

			while(resultSetInter.next())
			{
				pw.println ("\t\t\t\t<tr>\n");
				for(int cpt = 1; cpt <= resultSetInter.getMetaData().getColumnCount(); cpt++)
				{
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

			while(resultSetAffect.next())
			{
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
			ResultSet resultSetAffect = Controleur.getAffectation(codMod);
			PrintWriter pw = new PrintWriter( new FileOutputStream(nom+".html") );
			pw.println ("<!DOCTYPE html>\n" + //
						"<html lang=\"fr\">\n" + //
						"\t<head>\n" + //
						"\t\t<title>Prévisualisation</title>\n" + //
						"\t\t<style>\n" + //
						"\t\t\tbody {\n" + //
						"\t\t\t\tmax-width: 800px;\n" + //
						"\t\t\t\tmargin: 20px auto;\n" + //
						"\t\t\t\tpadding: 20px;\n" + //
						"\t\t\t\tfont-family: Arial, sans-serif;\n" + //
						"\t\t\t\tline-height: 1.6;\n" + //
						"\t\t\t}\n" + //
						"\t\t\ttable {\n" + //
						"\t\t\t\twidth: 100%;\n" + //
						"\t\t\t\tborder-collapse: collapse;\n" + //
						"\t\t\t\tmargin-top: 10px;\n" + //
						"\t\t\t}\n" + //
						"\t\t\tth, td {\n" + //
						"\t\t\t\tborder: 1px solid #ddd;\n" + //
						"\t\t\t\tpadding: 8px;\n" + //
						"\t\t\t\ttext-align: left;\n" + //
						"\t\t\t}\n" + //
						"\t\t\tth {\n" + //
						"\t\t\t\tbackground-color: #f2f2f2;\n" + //
						"\t\t\t}\n" + //
						"\t\t\th2 {\n" + //
						"\t\t\t\tcolor: #333;\n" + //
						"\t\t\t\tmargin-bottom: 10px;\n" + //
						"\t\t\t\tmargin-top: 50px;\n" + //
						"\t\t\t\ttext-align: center;\n" + //
						"\t\t\t}\n" + //
						"\t\t</style>\n" + //
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

			while(resultSetModule.next())
			{
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

	public static void genererCSV(String nom)
	{
		try
		{
			ResultSet rs = Controleur.getIntervenant_complet();
			PrintWriter pw = new PrintWriter( new FileOutputStream(nom + ".csv") );
			for(int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++)
			{
				if(cpt != 1)
					pw.print(",");
				pw.print(rs.getMetaData().getColumnName(cpt));
			}

			pw.print("\n");

			while(rs.next())
			{
				for(int cpt = 1; cpt <= rs.getMetaData().getColumnCount(); cpt++)
				{
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

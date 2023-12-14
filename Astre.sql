------------------------------
--		Projet ASTRE		--
------------------------------

-- suppression des tables si elles existent déjà
-- NB : cela supprime donc les éventuels tuples contenus

-- Table
DROP TABLE IF EXISTS Intervenant 			CASCADE ;
DROP TABLE IF EXISTS Module 				CASCADE ;
DROP TABLE IF EXISTS Semestre 				CASCADE ;
DROP TABLE IF EXISTS Affectation 			CASCADE ;
DROP TABLE IF EXISTS CategorieIntervenant 	CASCADE ;
DROP TABLE IF EXISTS CategorieHeure 		CASCADE ;
DROP TABLE IF EXISTS TypeModule 	        CASCADE ;


-- Fonction
DROP FUNCTION IF EXISTS getService  (INTEGER) CASCADE;
DROP FUNCTION IF EXISTS getMaxHeure (INTEGER) CASCADE;
DROP FUNCTION IF EXISTS verifTypMod (INTEGER) CASCADE;

-- Fonction JSP
DROP FUNCTION IF EXISTS verifHP() CASCADE;
DROP FUNCTION IF EXISTS calculNbAffect() CASCADE;
DROP FUNCTION IF EXISTS modif_cat_inter() CASCADE;
DROP FUNCTION IF EXISTS default_hServ() CASCADE;
DROP FUNCTION IF EXISTS default_maxHeure() CASCADE;
DROP FUNCTION IF EXISTS calculCoeff() CASCADE;
DROP FUNCTION IF EXISTS getCatInter() CASCADE;

-- Fonction de trigger en cas de suppresion d'une clef primaire
DROP FUNCTION IF EXISTS delAffectationModFonc() 		CASCADE;
DROP FUNCTION IF EXISTS delAffectationInterFonc() 		CASCADE;
DROP FUNCTION IF EXISTS delAffectationCatHFonc() 		CASCADE;
DROP FUNCTION IF EXISTS delIntervenantCatInterFonc() 	CASCADE;
DROP FUNCTION IF EXISTS delModuleSemFonc() 				CASCADE;
DROP FUNCTION IF EXISTS delModuleTypModFonc() 			CASCADE;


-- Trigger
DROP TRIGGER IF EXISTS default_hServ_trigger 	ON Intervenant;

-- Trigger JSP
DROP TRIGGER IF EXISTS default_maxHeure_trigger ON Intervenant;

-- Trigger en cas de suppresion d'une clef primaire
DROP TRIGGER IF EXISTS delAffectationInter 		ON Intervenant;
DROP TRIGGER IF EXISTS delAffectationMod 		ON Module;
DROP TRIGGER IF EXISTS delAffectationCatH		ON CategorieHeure;
DROP TRIGGER IF EXISTS delIntervenantCatInter 	ON CategorieIntervenant;
DROP TRIGGER IF EXISTS delModuleSem 			ON Semestre;
DROP TRIGGER IF EXISTS delModuleTypMod 			ON TypeModule;


-- Vue
DROP VIEW IF EXISTS affectation_final 	CASCADE;
DROP VIEW IF EXISTS inter 				CASCADE;
DROP VIEW IF EXISTS intervenant_final 	CASCADE;
DROP VIEW IF EXISTS module_final 		CASCADE;
DROP VIEW IF EXISTS liste_module 		CASCADE;


-- creation de la table CategorieIntervenant

CREATE TABLE CategorieIntervenant (
	codCatInter        SERIAL PRIMARY KEY,
	nomCat             VARCHAR(20) NOT NULL,
	service            INTEGER,
	maxHeure           INTEGER,
	ratioTPCatInterNum INTEGER,
	ratioTPCatInterDen INTEGER,
	CONSTRAINT check_coeff CHECK (ratioTPCatInterNum::NUMERIC / ratioTPCatInterDen BETWEEN 0.5 AND 1)
);

-- creation de la table Intervenant

CREATE TABLE Intervenant (
	codInter    SERIAL PRIMARY KEY,
	nom         VARCHAR(40),
	prenom      VARCHAR(40),
	codCatInter INTEGER REFERENCES CategorieIntervenant(codCatInter),
	hServ       INTEGER,
	maxHeure    INTEGER 
);

/*Ajout d'un trigger s'executant avant un insert sur Intervenant qui initialise hServ a service de 
sa categorie d'intervenant. Cela sert a mettre service en valeur par defaut de hServ.*/

CREATE OR REPLACE FUNCTION default_hServ()
RETURNS TRIGGER AS $$
BEGIN
	IF NEW.hServ = NULL THEN
		NEW.hServ := (SELECT service FROM CategorieIntervenant WHERE codCatInter = NEW.codCatInter);
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER default_hServ_trigger
BEFORE INSERT ON Intervenant
FOR EACH ROW EXECUTE FUNCTION default_hServ();

/*Ajout d'un trigger s'executant avant un insert sur Intervenant qui initialise maxHeure a maxHeure de 
sa categorie d'intervenant. Cela sert a mettre service en valeur par defaut de maxHeure.*/

CREATE OR REPLACE FUNCTION default_maxHeure()
RETURNS TRIGGER AS $$
BEGIN
	IF NEW.maxHeure = NULL THEN
		NEW.maxHeure := (SELECT maxHeure FROM CategorieIntervenant WHERE codCatInter = NEW.codCatInter);
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER default_maxHeure_trigger
BEFORE INSERT ON Intervenant
FOR EACH ROW EXECUTE FUNCTION default_maxHeure();

/* Lorsqu'on supprime un type de module, ses modules sont supprimés */
CREATE OR REPLACE FUNCTION delIntervenantCatInterFonc()
RETURNS TRIGGER AS $$
BEGIN
  UPDATE CategorieIntervenant SET codCatInter = NULL
  WHERE codCatInter = OLD.codCatInter;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delIntervenantCatInter
BEFORE DELETE ON CategorieIntervenant
FOR EACH ROW
EXECUTE FUNCTION delIntervenantCatInterFonc();

-- creation de la table CategorieHeure

CREATE TABLE CategorieHeure (
	codCatHeure SERIAL PRIMARY KEY,
	nomCatHeure VARCHAR(20),
	coeffNum    INTEGER NOT NULL,
	coeffDen    INTEGER NOT NULL
);

-- creation de la fonction calculCoeff

CREATE OR REPLACE FUNCTION calculCoeff(VARCHAR)
RETURNS INTEGER AS $$
DECLARE
	coeff FLOAT;
BEGIN
	SELECT coeffNum/coeffDen INTO coeff
    FROM   CategorieHeure
	WHERE  $1 = nomCatHeure ;
  RETURN   coeff;
END;
$$ LANGUAGE plpgsql;

-- creation de la table Semestre

CREATE TABLE Semestre (
	codSem     VARCHAR(2) PRIMARY KEY,
	nbGrpTD    INTEGER,
	nbGrpTP    INTEGER,
	nbEtd      INTEGER,
	nbSemaines INTEGER
);

CREATE TABLE TypeModule (
	codTypMod SERIAL PRIMARY KEY,
	nomTypMod VARCHAR(20)
);

CREATE OR REPLACE FUNCTION verifTypMod(VARCHAR,VARCHAR)
	RETURNS BOOLEAN AS
$$
DECLARE
	valnomTypMod VARCHAR;
BEGIN
	SELECT nomTypMod INTO valnomTypMod FROM Module m JOIN TypeModule t ON m.codTypMod = t.codTypMod WHERE codMod = $1;
	RETURN valnomTypMod = $2;
END;
$$
LANGUAGE plpgsql;

-- creation de la table Module

CREATE TABLE Module (
	codMod    VARCHAR(5) PRIMARY KEY,
	codTypMod INTEGER REFERENCES TypeModule(codTypMod),
	codSem    VARCHAR(2) REFERENCES Semestre(codSem),
	
	libLong   VARCHAR(50),
	libCourt  VARCHAR(20),

	valid BOOLEAN,

	/*Spécifique a ressource*/
	nbHPnCM   			INTEGER CHECK (verifTypMod(codMod,'Ressources') OR verifTypMod(codMod,'PPP') OR nbHPnCM 			= NULL), 
	nbHPnTD   			INTEGER CHECK (verifTypMod(codMod,'Ressources') OR verifTypMod(codMod,'PPP') OR nbHPnTD 			= NULL),
	nbHPnTP   			INTEGER CHECK (verifTypMod(codMod,'Ressources') OR verifTypMod(codMod,'PPP') OR nbHPnTP 			= NULL),

	nbSemaineTD		   	INTEGER CHECK (verifTypMod(codMod,'Ressources') 						     OR nbSemaineTD 		= NULL),
	nbSemaineTP	   		INTEGER CHECK (verifTypMod(codMod,'Ressources')  						     OR nbSemaineTP 		= NULL),
	nbSemaineCM   		INTEGER CHECK (verifTypMod(codMod,'Ressources')                              OR nbSemaineCM 		= NULL),

	nbHParSemaineTD   	INTEGER CHECK (verifTypMod(codMod,'Ressources') OR verifTypMod(codMod,'PPP') OR nbHParSemaineTD 	= NULL),
	nbHParSemaineTP   	INTEGER CHECK (verifTypMod(codMod,'Ressources') OR verifTypMod(codMod,'PPP') OR nbHParSemaineTP 	= NULL),
	nbHParSemaineCM   	INTEGER CHECK (verifTypMod(codMod,'Ressources') OR verifTypMod(codMod,'PPP') OR nbHParSemaineCM 	= NULL),
	
	hPonctuelle 		INTEGER CHECK (verifTypMod(codMod,'Ressources') OR verifTypMod(codMod,'PPP') OR hPonctuelle 		= NULL),

	/*Spécifique a sae*/
	nbHPnSaeParSemestre INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHPnSaeParSemestre = NULL),
	nbHPnTutParSemestre INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHPnTutParSemestre = NULL),
	nbHSaeParSemestre 	INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHSaeParSemestre 	= NULL),
	nbHTutParSemestre 	INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHTutParSemestre 	= NULL),

	/*Spécifique a stage*/
	nbHPnREH 	INTEGER CHECK (verifTypMod(codMod,'Stage') OR nbHREH = NULL),
	nbHPnTut 	INTEGER CHECK (verifTypMod(codMod,'Stage') OR verifTypMod(codMod,'PPP') OR nbHTut = NULL),
	nbHREH 		INTEGER CHECK (verifTypMod(codMod,'Stage') OR nbHREH = NULL),
	nbHTut 		INTEGER CHECK (verifTypMod(codMod,'Stage') OR verifTypMod(codMod,'PPP') OR nbHTut = NULL)
);

/* Lorsqu'on supprime un type de module, ses modules sont supprimés */
CREATE OR REPLACE FUNCTION delModuleTypModFonc()
RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM TypeModule
  WHERE codTypMod = OLD.codTypMod;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delModuleTypMod
BEFORE DELETE ON CategorieHeure
FOR EACH ROW
EXECUTE FUNCTION delModuleTypModFonc();

/* Lorsqu'on supprime un semestre, ses modules sont supprimés */
CREATE OR REPLACE FUNCTION delModuleSemFonc()
RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM Semestre
  WHERE codSem = OLD.codSem;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delModuleSem
BEFORE DELETE ON Semestre
FOR EACH ROW
EXECUTE FUNCTION delModuleSemFonc();

/* Verifie si l'affectation a comme categorie d'heure heure ponctuelle*/
CREATE OR REPLACE FUNCTION verifHP(INTEGER)
	RETURNS BOOLEAN AS
$$
DECLARE
	valVerif INTEGER;
BEGIN
	SELECT COUNT(codCatHeure) INTO valVerif FROM CategorieHeure WHERE codCatHeure = $1 AND nomCatHeure = 'HP';
	RETURN valVerif > 0;
END;
$$
LANGUAGE plpgsql;

-- creation de la table Affectation

CREATE TABLE Affectation (
	codAffec SERIAL PRIMARY KEY,
	codMod VARCHAR(5) REFERENCES Module(codMod),
	codInter INTEGER REFERENCES Intervenant(codInter),
	codCatHeure INTEGER REFERENCES CategorieHeure(codCatHeure),
	commentaire TEXT,

	/*Spécifique a ressource*/
	nbSem INTEGER CHECK (verifTypMod(codMod,'Ressources') AND verifHP(codCatHeure) OR nbSem = NULL),
	nbGrp INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbGrp = NULL),

	/*Spécifique a sae/stage*/
	nbH INTEGER CHECK (verifTypMod(codMod,'SAE') OR verifTypMod(codMod,'Stage') OR nbH = NULL)
);

/* Lorsqu'on supprime un intervenant, ses affectations sont supprimés */
CREATE OR REPLACE FUNCTION delAffectationInterFonc()
RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM Affectation
  WHERE codInter = OLD.codInter;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delAffectationInter
BEFORE DELETE ON Intervenant
FOR EACH ROW
EXECUTE FUNCTION delAffectationInterFonc();

/* Lorsqu'on supprime un module, ses affectations sont supprimés */
CREATE OR REPLACE FUNCTION delAffectationModFonc()
RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM Affectation
  WHERE codMod = OLD.codMod;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delAffectationMod
BEFORE DELETE ON Module
FOR EACH ROW
EXECUTE FUNCTION delAffectationModFonc();


/* Lorsqu'on supprime une categorie d'heure, ses affectations sont supprimés */
CREATE OR REPLACE FUNCTION delAffectationCatHFonc()
RETURNS TRIGGER AS $$
BEGIN
  DELETE FROM CategorieHeure
  WHERE codCatHeure = OLD.codCatHeure;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delAffectationCatH
BEFORE DELETE ON CategorieHeure
FOR EACH ROW
EXECUTE FUNCTION delAffectationCatHFonc();



CREATE OR REPLACE VIEW affectation_final AS 
SELECT  m.codMod,i.codInter,i.nom,c.nomCatHeure,
		a.nbSem,a.nbGrp,
		nbH,
		ROUND(
		CASE
			WHEN m.codTypMod = 1 THEN COALESCE(a.nbSem,1)*COALESCE(a.nbGrp,1)*
				CASE 
					WHEN c.nomCatHeure =  'TD' THEN m.nbHParSemaineTD
					WHEN c.nomCatHeure =  'TP' THEN m.nbHParSemaineTP
					WHEN c.nomCatHeure =  'CM' THEN m.nbHParSemaineCM
					ELSE 1
				END
			ELSE COALESCE(nbH,1)
		END *(c.coeffNum::NUMERIC/c.coeffDen::NUMERIC),1) AS "tot eqtd"
FROM Affectation a JOIN CategorieHeure c ON a.codCatHeure = c.codCatHeure
				   JOIN Module      m    ON a.codMod      = m.codMod
				   JOIN Intervenant i    ON i.codInter    = a.codInter;

CREATE OR REPLACE VIEW inter AS
SELECT  i.nom,s.codSem,
	    ROUND(SUM(CASE WHEN a.nomCatHeure = 'TP' THEN (c.ratioTPCatInterNum::NUMERIC/c.ratioTPCatInterDen::NUMERIC) ELSE 1 END * a."tot eqtd"),1) AS "tot sem"
FROM affectation_final a JOIN Module            m ON a.codMod      = m.codMod
						 JOIN Semestre          s ON m.codSem      = s.codSem
						 JOIN Intervenant i ON i.codInter = a.codInter
						 JOIN CategorieIntervenant c ON i.codCatInter = c.codCatInter
GROUP BY i.nom,s.codSem;

CREATE OR REPLACE VIEW intervenant_final AS
SELECT  c.nomCat, i.nom, i.prenom, i.hServ, i.maxHeure, (ratioTPCatInterNum || '/' || ratioTPCatInterDen)::VARCHAR AS "Coef TP", 
		MAX(CASE WHEN s.codSem = 'S1' THEN "tot sem" END) AS S1,
		MAX(CASE WHEN s.codSem = 'S2' THEN "tot sem" END) AS S2,
		MAX(CASE WHEN s.codSem = 'S3' THEN "tot sem" END) AS S3,
		MAX(CASE WHEN s.codSem = 'S4' THEN "tot sem" END) AS S4,
		MAX(CASE WHEN s.codSem = 'S5' THEN "tot sem" END) AS S5,
		MAX(CASE WHEN s.codSem = 'S6' THEN "tot sem" END) AS S6
FROM Intervenant i JOIN affectation_final a ON i.codInter    = a.codInter
				   JOIN Module            m ON a.codMod      = m.codMod
				   JOIN inter             s ON m.codSem      = s.codSem
				   JOIN CategorieIntervenant c ON i.codCatInter = c.codCatInter
GROUP BY c.nomCat,i.nom,i.prenom,i.hServ,i.maxHeure,ratioTPCatInterNum,ratioTPCatInterDen
UNION 
SELECT c.nomCat, i.nom, i.prenom, i.hServ,i.maxHeure,(c.ratioTPCatInterNum || '/' || c.ratioTPCatInterDen)::VARCHAR AS "Coef TP", 
	   NULL AS S1,NULL AS S2, NULL AS S3,NULL AS S4,NULL AS S5, NULL AS S6 
	   FROM Intervenant i JOIN  CategorieIntervenant c ON i.codCatInter = c.codCatInter 
	   WHERE i.codInter NOT IN (SELECT codInter FROM Affectation);

-- creation de la fonction calculNbAffect
CREATE OR REPLACE FUNCTION calculNbAffect(VARCHAR,VARCHAR)
RETURNS INTEGER AS $$
DECLARE
	totH INTEGER;
BEGIN
	SELECT SUM("tot eqtd") INTO totH
	FROM   affectation_final 
	WHERE  codMod = $1 AND $1 = nomCatHeure;
	RETURN totH; 
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE VIEW module_final AS
SELECT t.nomTypMod, s.codSem,m.codMod,m.libLong,m.libCourt,s.nbEtd,s.nbGrpTD,s.nbGrpTP,
	
	   -- POUR LES RESSOURCES.
	   -- Heure PN GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnCM END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnTD END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnTP END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnCM+nbHPnTD+nbHPnTP END AS TO sommePn,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN COALESCE(nbHPnCM,0)*COALESCE(calculCoeff('CM'),1) END AS TO totalEqtdPromoPnCm,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN COALESCE(nbHPnTD,0)*COALESCE(calculCoeff('TD'),1)*COALESCE(s.nbGrpTD,0) END AS TO totalEqtdPromoPnTd,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN COALESCE(nbHPnTP,0)*COALESCE(calculCoeff('TP'),1)*COALESCE(s.nbGrpTP,0) END AS TO totalEqtdPromoPnTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN COALESCE(nbHPnCM,0)*COALESCE(calculCoeff('CM'),1)+COALESCE(nbHPnTD,0)*COALESCE(calculCoeff('TD'),1)*COALESCE(s.nbGrpTD,0)+COALESCE(nbHPnTP,0)*COALESCE(calculCoeff('TP'),1)*COALESCE(s.nbGrpTP,0) END AS TO sommeTotalEqtdPromoPn,
	   
	   -- Répartition 1 GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM 	END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHParSemaineCM END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTD 	END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHParSemaineTD END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTP 	END,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHParSemaineTP END,

	   -- Repartition 2 GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN COALESCE(nbSemaineCM,1)*COALESCE(nbHParSemaineCM,1) 	END AS TO nbSXnbHCM, 
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTD*nbHParSemaineTD 	END AS TO nbSXnbHTD, 	
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTP*nbHParSemaineTP 	END AS TO nbSXnbHTP, 	 
	   CASE WHEN t.nomTypMod = 'Ressource' THEN hPonctuelle 					END,     
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM*nbHParSemaineCM+nbSemaineTD*nbHParSemaineTD+nbSemaineTP*nbHParSemaineTP+hPonctuelle END AS TO SommeNbSXnbH,
	   
	   --Total promo eqtd GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM*nbHParSemaineCM*calculCoeff('CM') 			END AS TO promoEqtdCM,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTD*nbHParSemaineTD*calculCoeff('TD')*nbGrpTD 	END AS TO promoEqtdTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTP*nbHParSemaineTP*calculCoeff('TP')*nbGrpTP 	END AS TO promoEqtdTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN hPonctuelle*calculCoeff('HP')*nbGrpTD 					END AS TO promoEqtdHP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN (nbSemaineCM*nbHParSemaineCM*calculCoeff('CM')+nbSemaineTD*nbHParSemaineTD*calculCoeff('TD')*nbGrpTD+nbSemaineTP*nbHParSemaineTP*calculCoeff('TP')*nbGrpTP+hPonctuelle*calculCoeff('HP')*nbGrpTD) END AS TO sommeTotPromoEqtd,
	   
	   --Total affecté eqtd
	   CASE WHEN t.nomTypMod = 'Ressource' THEN calculNbAffect(m.codMod,'CM') END AS TO eqtdCM,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN calculNbAffect(m.codMod,'TD') END AS TO eqtdTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN calculNbAffect(m.codMod,'TP') END AS TO eqtdTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN calculNbAffect(m.codMod,'HP') END AS TO eqtdHP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN calculNbAffect(m.codMod,'CM')+calculNbAffect(m.codMod,'TD')+calculNbAffect(m.codMod,'TP')+calculNbAffect(m.codMod,'HP') END AS TO sommeTotAffectEqtd,
	   
	   --POUR LES SAE.
	   --Heure pn GOOD
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHPnSaeParSemestre 						END,
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHPnTutParSemestre 						END,
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHPnSaeParSemestre+nbHPnTutParSemestre	END,
	   
	   --Repartition premiere ligne GOOD
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHSaeParSemestre 												END,
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHTutParSemestre 												END,
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHSaeParSemestre+nbHTutParSemestre  	END AS TO sommeTotPromoEqtd,
	   
	   --Repartition seconde ligne GOOD
	   CASE WHEN t.nomTypMod = 'SAE' THEN calculNbAffect(m.codMod,'Sae') END AS TO nbHAffecteSAE,
	   CASE WHEN t.nomTypMod = 'SAE' THEN calculNbAffect(m.codMod,'HT') END AS TO nbHAffecteHT
	   CASE WHEN t.nomTypMod = 'SAE' THEN calculNbAffect(m.codMod,'Sae')+calculNbAffect(m.codMod,'HT')   	END AS TO sommeTotAffectEqtd,

	   --POUR LES STAGE.
	   --Heure pn GOOD
	   CASE WHEN t.nomTypMod = 'Stage' THEN nbHPnREH 			END,
	   CASE WHEN t.nomTypMod = 'Stage' THEN nbHPnTut 			END,
	   CASE WHEN t.nomTypMod = 'Stage' THEN nbHPnREH+nbHPnTut 	END,
	   
	   --Repartition premiere ligne GOOD
	   CASE WHEN t.nomTypMod = 'Stage' THEN nbHREH 										END,
	   CASE WHEN t.nomTypMod = 'Stage' THEN nbHTut 										END,
	   CASE WHEN t.nomTypMod = 'Stage' THEN nbHREH+nbHTut  	END AS TO sommeTotPromoEqtd,
	   
	   --Repartition seconde ligne GOOD
	   CASE WHEN t.nomTypMod = 'Stage' THEN calculNbAffect(m.codMod,'REH') 	END AS TO nbHAffecteREH,
	   CASE WHEN t.nomTypMod = 'Stage' THEN calculNbAffect(m.codMod,'HT') 	END AS TO nbHAffecteHT,
	   CASE WHEN t.nomTypMod = 'Stage' THEN calculNbAffect(m.codMod,'REH')+calculNbAffect(m.codMod,'HT') END AS To sommeTotAffectEqtd,

	   --POUR LES PPP.
	   --Heure pn ??
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHPnCM END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHPnTD END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHPnTP END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHPnTut END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHPnCM+nbHPnTD+nbHPnTP+nbHPnTut END AS TO sommeHeurePnPPP,
	   	-- Répartition 1 ??
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHParSemaineCM END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHParSemaineTD END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHParSemaineTP END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHTut END,
	   CASE WHEN t.nomTypMod = 'PPP' THEN hPonctuelle END,   
	   CASE WHEN t.nomTypMod = 'PPP' THEN nbHParSemaineCM+nbHParSemaineTD+nbHParSemaineTP+nbHTut+hPonctuelle END AS TO sommeTotPromoEqtd,
	   	-- Répartition 2 EFFECTUER ??
	   CASE WHEN t.nomTypMod = 'PPP' THEN calculNbAffect(m.codMod,'CM') END AS TO nbHAffecteCM,
	   CASE WHEN t.nomTypMod = 'PPP' THEN calculNbAffect(m.codMod,'TD') END AS TO nbHAffecteTD,
	   CASE WHEN t.nomTypMod = 'PPP' THEN calculNbAffect(m.codMod,'TP') END AS TO nbHAffecteTP,
	   CASE WHEN t.nomTypMod = 'PPP' THEN calculNbAffect(m.codMod,'HT') END AS TO nbHAffecteHT,
	   CASE WHEN t.nomTypMod = 'PPP' THEN calculNbAffect(m.codMod,'HP') END AS TO nbHAffecteHP,
	   CASE WHEN t.nomTypMod = 'PPP' THEN calculNbAffect(m.codMod,'CM')+calculNbAffect(m.codMod,'TD')+calculNbAffect(m.codMod,'TP')+calculNbAffect(m.codMod,'HT')+calculNbAffect(m.codMod,'HP') END AS TO sommeTotAffectEqtd,
	   
	   m.valid
FROM Module m JOIN TypeModule t ON t.codTypMod = m.codTypMod
			  JOIN Semestre   s ON s.codSem    = m.codSem;




CREATE OR REPLACE VIEW liste_module AS 
SELECT codSem, codMod, libLong, (sommeTotPromoEqtd || '/' || sommeTotPromoEqtd)::VARCHAR AS "heureAffect/heurePn", valid
FROM module_final;

CREATE OR REPLACE FUNCTION getCatInter(VARCHAR)
RETURNS INTEGER AS $$
DECLARE 
	res INTEGER;
BEGIN
	SELECT codCatInter INTO res FROM CategorieIntervenant WHERE $1 = nomCat;
	RETURN res;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modif_cat_inter(INTEGER,VARCHAR)
RETURNS VOID AS $$	
BEGIN
	UPDATE Intervenant 
	SET codCatInter = getCatInter($1)
	WHERE codInter = $1;
END;
$$ LANGUAGE plpgsql;
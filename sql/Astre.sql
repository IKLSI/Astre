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

-- Fonction de trigger en cas de suppresion d'une clef primaire
DROP FUNCTION IF EXISTS delAffectationModFonc() CASCADE;
DROP FUNCTION IF EXISTS delAffectationInterFonc() CASCADE;
DROP FUNCTION IF EXISTS delAffectationCatHFonc() CASCADE;
DROP FUNCTION IF EXISTS delIntervenantCatInterFonc() CASCADE;
DROP FUNCTION IF EXISTS delModuleSemFonc() CASCADE;
DROP FUNCTION IF EXISTS delModuleTypModFonc() CASCADE;


-- Trigger
DROP TRIGGER IF EXISTS default_hServ_trigger;

-- Trigger en cas de suppresion d'une clef primaire
DROP TRIGGER IF EXISTS delAffectationInter;
DROP TRIGGER IF EXISTS delAffectationMod;
DROP TRIGGER IF EXISTS delAffectationCatH;
DROP TRIGGER IF EXISTS delIntervenantCatInter;
DROP TRIGGER IF EXISTS delModuleSem;
DROP TRIGGER IF EXISTS delModuleTypMod;


-- Vue
DROP VIEW IF EXISTS affectation_final CASCADE;
DROP VIEW IF EXISTS inter CASCADE;
DROP VIEW IF EXISTS intervenant_final CASCADE;


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
	nbHPnCM   			INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbHPnCM 			= NULL),
	nbHPnTD   			INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbHPnTD 			= NULL),
	nbHPnTP   			INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbHPnTP 			= NULL),

	nbSemaineTD		   	INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbSemaineTD 		= NULL),
	nbSemaineTP	   		INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbSemaineTP 		= NULL),
	nbSemaineCM   		INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbSemaineCM 		= NULL),

	nbHParSemaineTD   	INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbHParSemaineTD 	= NULL),
	nbHParSemaineTP   	INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbHParSemaineTP 	= NULL),
	nbHParSemaineCM   	INTEGER CHECK (verifTypMod(codMod,'Ressources') OR nbHParSemaineCM 	= NULL),
	
	hPonctuelle 		INTEGER CHECK (verifTypMod(codMod,'Ressources') OR hPonctuelle 		= NULL),

	/*Spécifique a sae*/
	nbHPnSaeParSemestre INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHPnSaeParSemestre = NULL),
	nbHPnTutParSemestre INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHPnTutParSemestre = NULL),
	nbHSaeParSemestre 	INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHSaeParSemestre 	= NULL),
	nbHTutParSemestre 	INTEGER CHECK (verifTypMod(codMod,'SAE') OR nbHTutParSemestre 	= NULL),

	/*Spécifique a stage*/
	nbHPnREH 	INTEGER CHECK (verifTypMod(codMod,'Stage') OR nbHREH = NULL),
	nbHPnTut 	INTEGER CHECK (verifTypMod(codMod,'Stage') OR nbHTut = NULL),
	nbHREH 		INTEGER CHECK (verifTypMod(codMod,'Stage') OR nbHREH = NULL),
	nbHTut 		INTEGER CHECK (verifTypMod(codMod,'Stage') OR nbHTut = NULL),

	/*Spécifique a PPP*/ --TODO ET MODIF INSERT
	nbHTutPnSAE INTEGER CHECK (verifTypMod(codMod,'PPP') OR nbHTutPnSAE = NULL),
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

CREATE OR REPLACE VIEW module_final AS
SELECT t.nomTypMod, s.codSem,m.codMod,m.libLong,m.libCourt,s.nbEtd,s.nbGrpTD,s.nbGrpTP,
	   
	   -- Heure PN GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnCM,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnCM+nbHPnTD+nbHPnTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnCM*calculCoeff('CM')*1, --1 Seule groupe CM
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnTD*calculCoeff('TD')*s.nbGrpTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnTP*calculCoeff('TP')*s.nbGrpTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHPnCM*calculCoeff('CM')*1+nbHPnTD*calculCoeff('TD')*s.nbGrpTD+nbHPnTP*calculCoeff('TP')*s.nbGrpTP,
	   
	   -- Répartition 1 GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHParSemaineCM,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHParSemaineTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbHParSemaineTP,

	   -- Repartition 2 GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM*nbHParSemaineCM, 
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTD*nbHParSemaineTD, 	
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTP*nbHParSemaineTP, 	
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineHTut*nbHParSemaineHTut, 
	   CASE WHEN t.nomTypMod = 'Ressource' THEN hPonctuelles,     
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM*nbHParSemaineCM+nbSemaineTD*nbHParSemaineTD+nbSemaineTP*nbHParSemaineTP+nbSemaineHTut*nbHParSemaineHTut+hPonctuelles, --SOMME DE TOUT
	   
	   --Total promo eqtd GOOD
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM*nbHParSemaineCM*calculCoeff('CM'),
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTD*nbHParSemaineTD*calculCoeff('TD')*nbGrpTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineTP*nbHParSemaineTP*calculCoeff('TP')*nbGrpTP,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN hPonctuelles*calculCoeff('HP')*nbGrpTD,
	   CASE WHEN t.nomTypMod = 'Ressource' THEN nbSemaineCM*nbHParSemaineCM*calculCoeff('CM')+nbSemaineTD*nbHParSemaineTD*calculCoeff('TD')*nbGrpTD+nbSemaineTP*nbHParSemaineTP*calculCoeff('TP')*nbGrpTP+hPonctuelles*calculCoeff('HP')*nbGrpTD,
	   
	   --Total affecté eqtd
	   CASE WHEN t.nomTypMod = 'Ressource' THEN 
	
	WHERE a.codMod = m.codMod;


-- creation de la fonction calculHP
CREATE OR REPLACE FUNCTION calculNbAffect(INTEGER,VARCHAR)
RETURNS INTEGER AS $$
DECLARE
	totH INTEGER
BEGIN
	SELECT SUM(a.nbSem*nbGrp)*CASE(WHEN $2 = 'TP' THEN WHEN $2 = 'TD' THEN WHEN $2 = 'CM' THEN WHEN $2 = 'HP')
	FROM   Affectation a JOIN Module m ON a.codMod = m.codMod
	WHERE  m.codMod;
	RETURN totH; 
END;
$$ LANGUAGE plpgsql;
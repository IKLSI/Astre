------------------------------
--		Projet ASTRE	      	--
------------------------------

-- suppression des tables si elles existent déjà
-- NB : cela supprime donc les éventuels tuples contenus

-- Table
DROP TABLE IF EXISTS Annee                  CASCADE ;
DROP TABLE IF EXISTS Intervenant 			CASCADE ;
DROP TABLE IF EXISTS Module 				CASCADE ;
DROP TABLE IF EXISTS Semestre 				CASCADE ;
DROP TABLE IF EXISTS Affectation 			CASCADE ;
DROP TABLE IF EXISTS CategorieIntervenant 	CASCADE ;
DROP TABLE IF EXISTS CategorieHeure 		CASCADE ;
DROP TABLE IF EXISTS TypeModule 	        CASCADE ;
DROP TABLE IF EXISTS cloneAffectation CASCADE;
DROP TABLE IF EXISTS cloneModule CASCADE;
DROP TABLE IF EXISTS cloneIntervenant CASCADE;
DROP TABLE IF EXISTS cloneSemestre CASCADE;


-- Fonction
DROP FUNCTION IF EXISTS getService  (INTEGER) CASCADE;
DROP FUNCTION IF EXISTS getMaxHeure (INTEGER) CASCADE;
DROP FUNCTION IF EXISTS verifTypMod (INTEGER) CASCADE;

-- Fonction
DROP FUNCTION IF EXISTS verifHP(INTEGER) CASCADE;
DROP FUNCTION IF EXISTS calculNbAffect(VARCHAR,VARCHAR,INTEGER) CASCADE;
DROP FUNCTION IF EXISTS modif_cat_inter(INTEGER,VARCHAR) CASCADE;
DROP FUNCTION IF EXISTS default_hServ() CASCADE;
DROP FUNCTION IF EXISTS default_maxHeure() CASCADE;
DROP FUNCTION IF EXISTS calculCoeff(VARCHAR) CASCADE;
DROP FUNCTION IF EXISTS getCatInter(VARCHAR) CASCADE;
DROP FUNCTION IF EXISTS clonage(annee_source INTEGER, annee_destination INTEGER) CASCADE;

-- Fonction de trigger en cas de suppresion d'une clef primaire
DROP FUNCTION IF EXISTS delAffectationModFonc() 		CASCADE;
DROP FUNCTION IF EXISTS delAffectationInterFonc() 		CASCADE;
DROP FUNCTION IF EXISTS delAffectationCatHFonc() 		CASCADE;
DROP FUNCTION IF EXISTS delIntervenantCatInterFonc() 	CASCADE;
DROP FUNCTION IF EXISTS delModuleSemFonc() 				CASCADE;
DROP FUNCTION IF EXISTS delModuleTypModFonc() 			CASCADE;


-- Trigger
DROP TRIGGER IF EXISTS default_hServ_trigger 	ON Intervenant;

-- Trigger
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
DROP VIEW IF EXISTS inter_cat 			CASCADE;
DROP VIEW IF EXISTS intervenant_final 	CASCADE;
DROP VIEW IF EXISTS module_final 		CASCADE;
DROP VIEW IF EXISTS liste_module 		CASCADE;


-- creation de la table CategorieIntervenant

CREATE TABLE Annee (
	annee INTEGER PRIMARY KEY
);

CREATE TABLE CategorieIntervenant (
	codCatInter        SERIAL PRIMARY KEY,
	nomCat             VARCHAR(20) NOT NULL,
	service            INTEGER,
	maxHeure           INTEGER,
	ratioTPCatInterNum INTEGER DEFAULT 1,
	ratioTPCatInterDen INTEGER DEFAULT 1,
	CONSTRAINT check_coeff CHECK (ratioTPCatInterNum::NUMERIC / ratioTPCatInterDen BETWEEN 0.5 AND 1)
);

-- creation de la table Intervenant

CREATE TABLE Intervenant (
	codInter    SERIAL,
	nom         VARCHAR(40),
	prenom      VARCHAR(40),
	codCatInter INTEGER REFERENCES CategorieIntervenant(codCatInter),
	hServ       INTEGER,
	maxHeure    INTEGER,
	annee INTEGER REFERENCES Annee(annee),
	PRIMARY KEY(codInter,annee),
	UNIQUE(nom,prenom)
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

CREATE OR REPLACE FUNCTION delIntervenantCatInterFonc()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (SELECT 1 FROM Intervenant WHERE codCatInter = OLD.codCatInter) THEN
		RAISE EXCEPTION 'Impossible de supprimer la catégorie d''intervenant car il existe des intervenants liés à elle.';
	END IF;

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
	coeffNum    FLOAT NOT NULL,
	coeffDen    FLOAT NOT NULL
);

-- creation de la fonction calculCoeff

CREATE OR REPLACE FUNCTION calculCoeff(VARCHAR)
RETURNS FLOAT AS $$
DECLARE
	coeff FLOAT;
BEGIN
	SELECT coeffNum::FLOAT/coeffDen::FLOAT INTO coeff
    FROM   CategorieHeure
	WHERE  $1 = nomCatHeure ;
  RETURN   coeff;
END;
$$ LANGUAGE plpgsql;



-- creation de la table Semestre

CREATE TABLE Semestre (
	codSem     VARCHAR(2),
	nbGrpTD    INTEGER,
	nbGrpTP    INTEGER,
	nbEtd      INTEGER,
	nbSemaines INTEGER,
	annee INTEGER REFERENCES Annee(annee),
	PRIMARY KEY(codSem,annee)
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
	codMod    VARCHAR(5),
	codTypMod INTEGER REFERENCES TypeModule(codTypMod),
	codSem    VARCHAR(2),
	annee     INTEGER,

	FOREIGN KEY(codSem,annee) REFERENCES Semestre(codSem,annee),
	PRIMARY KEY(codMod,annee),

	libLong   VARCHAR(60),
	libCourt  VARCHAR(30),

	valide BOOLEAN,

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
	nbHTut 		INTEGER CHECK (verifTypMod(codMod,'Stage') OR verifTypMod(codMod,'PPP') OR nbHTut = NULL),

	/*Spécifique a PPP*/
	nbHPnHTut   INTEGER CHECK (verifTypMod(codMod,'PPP') OR nbHPnHTut = NULL)
);

/* Lorsqu'on supprime un type de module, ses modules sont supprimés */
CREATE OR REPLACE FUNCTION delModuleTypModFonc()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (SELECT 1 FROM Module WHERE codTypMod = OLD.codTypMod AND annee = OLD.annee) THEN
		RAISE EXCEPTION 'Impossible de supprimer le type de module car il existe des modules liés lui.';
	END IF;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delModuleTypMod
BEFORE DELETE ON CategorieHeure
FOR EACH ROW
EXECUTE FUNCTION delModuleTypModFonc();

CREATE OR REPLACE FUNCTION delModuleSemFonc()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (SELECT 1 FROM Module WHERE codSem = OLD.codSem AND annee = OLD.annee) THEN
		RAISE EXCEPTION 'Impossible de supprimer le semestre car il existe des modules liés lui.';
	END IF;

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
	codAffec SERIAL,
	codMod VARCHAR(5),
	codInter INTEGER,
	codCatHeure INTEGER REFERENCES CategorieHeure(codCatHeure),
	annee INTEGER,
	PRIMARY KEY(codAffec,annee),

	FOREIGN KEY (codMod,annee) REFERENCES Module(codMod,annee),
	FOREIGN KEY (codInter,annee) REFERENCES Intervenant(codInter,annee),

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
	IF EXISTS (SELECT 1 FROM Affectation WHERE codInter = OLD.codInter AND annee = OLD.annee) THEN
		RAISE EXCEPTION 'Impossible de supprimer l''intervenant car il existe des affectations liés lui.';
	END IF;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delAffectationInter
BEFORE DELETE ON Intervenant
FOR EACH ROW
EXECUTE FUNCTION delAffectationInterFonc();

CREATE OR REPLACE FUNCTION delAffectationModFonc()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (SELECT 1 FROM Affectation WHERE codMod = OLD.codMod AND annee = OLD.annee) THEN
		RAISE EXCEPTION 'Impossible de supprimer le module car il existe des affectations liés a lui.';
	END IF;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delAffectationMod
BEFORE DELETE ON Module
FOR EACH ROW
EXECUTE FUNCTION delAffectationModFonc();

CREATE OR REPLACE FUNCTION delAffectationCatHFonc()
RETURNS TRIGGER AS $$
BEGIN
	IF EXISTS (SELECT 1 FROM Affectation WHERE codCatHeure = OLD.codCatHeure AND annee = OLD.annee) THEN
		RAISE EXCEPTION 'Impossible de supprimer la categorie d''heure car il existe des affectations liés a elle.';
	END IF;

  RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER delAffectationCatH
BEFORE DELETE ON CategorieHeure
FOR EACH ROW
EXECUTE FUNCTION delAffectationCatHFonc();

CREATE OR REPLACE VIEW affectation_final AS
SELECT DISTINCT m.annee,m.codMod,i.codInter,i.nom,c.nomCatHeure,
		a.nbSem,a.nbGrp, a.commentaire, c.codCatHeure,
		a.nbH,
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
				   JOIN Module      m    ON a.codMod      = m.codMod AND a.annee = m.annee
				   JOIN Intervenant i    ON i.codInter    = a.codInter AND a.annee = i.annee;

CREATE OR REPLACE VIEW inter_cat AS
SELECT DISTINCT s.codSem,a.annee,i.nom,ROUND(SUM("tot eqtd")*(c.ratioTPCatInterNum::NUMERIC/c.ratioTPCatInterDen::NUMERIC),1)  AS "tot sem"
FROM affectation_final a JOIN Module m On a.codMod = m.codMod AND a.annee = m.annee
						 JOIN Semestre s ON s.annee = a.annee AND m.codSem = s.codSem
						 JOIN Intervenant i ON i.codInter = a.codInter AND i.annee = a.annee
						 JOIN CategorieIntervenant c ON i.codCatInter = c.codCatInter
GROUP BY i.nom,a.annee,s.codSem,c.ratioTPCatInterNum,c.ratioTPCatInterDen;

CREATE OR REPLACE VIEW intervenant_final AS
SELECT DISTINCT i.annee,i.codInter,c.nomCat, i.nom, i.prenom, i.hServ, i.maxHeure, (ratioTPCatInterNum || '/' || ratioTPCatInterDen)::VARCHAR AS "Coef TP",
		COALESCE(MAX(CASE WHEN s.codSem = 'S1' THEN "tot sem" END),0) AS S1,
		COALESCE(MAX(CASE WHEN s.codSem = 'S3' THEN "tot sem" END),0) AS S3,
		COALESCE(MAX(CASE WHEN s.codSem = 'S5' THEN "tot sem" END),0) AS S5,
		COALESCE(MAX(CASE WHEN s.codSem = 'S1' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S3' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S5' THEN "tot sem" END),0) AS sTotImpair,
		COALESCE(MAX(CASE WHEN s.codSem = 'S2' THEN "tot sem" END),0) AS S2,
		COALESCE(MAX(CASE WHEN s.codSem = 'S4' THEN "tot sem" END),0) AS S4,
		COALESCE(MAX(CASE WHEN s.codSem = 'S6' THEN "tot sem" END),0) AS S6,
		COALESCE(MAX(CASE WHEN s.codSem = 'S2' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S4' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S6' THEN "tot sem" END),0) AS sTotPair,
		COALESCE(MAX(CASE WHEN s.codSem = 'S1' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S3' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S5' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S2' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S4' THEN "tot sem" END),0)+COALESCE(MAX(CASE WHEN s.codSem = 'S6' THEN "tot sem" END),0) AS Total
FROM Intervenant i JOIN affectation_final a ON i.codInter    = a.codInter AND i.annee = a.annee
				   JOIN Module            m ON a.codMod      = m.codMod AND m.annee = i.annee
				   JOIN inter_cat             s ON m.codSem      = s.codSem AND i.annee = s.annee
				   JOIN CategorieIntervenant c ON i.codCatInter = c.codCatInter
GROUP BY i.annee,i.codInter,c.nomCat,i.nom,i.prenom,i.hServ,i.maxHeure,ratioTPCatInterNum,ratioTPCatInterDen
UNION
SELECT i.annee,i.codInter,c.nomCat, i.nom, i.prenom, i.hServ,i.maxHeure,(c.ratioTPCatInterNum || '/' || c.ratioTPCatInterDen)::VARCHAR AS "Coef TP",
	   0 AS S1,0 AS S3,0 AS S5,0 AS sTotImpair,0 AS S2,0 AS S4,0 AS S6,0 AS sTotPair,0 AS Total
	   FROM Intervenant i JOIN  CategorieIntervenant c ON i.codCatInter = c.codCatInter
	   WHERE i.codInter NOT IN (SELECT codInter FROM Affectation);

-- creation de la fonction calculNbAffect
CREATE OR REPLACE FUNCTION calculNbAffect(VARCHAR,VARCHAR,INTEGER)
RETURNS INTEGER AS $$
DECLARE
	totH INTEGER;
BEGIN
	SELECT SUM("tot eqtd") INTO totH
	FROM   affectation_final
	WHERE  codMod = $1 AND $2 = nomCatHeure AND $3 = annee;
	RETURN totH;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE VIEW module_final AS
SELECT DISTINCT m.annee,t.nomTypMod, s.codSem,m.codMod,m.libLong,m.libCourt,s.nbEtd,s.nbGrpTD,s.nbGrpTP,

	   -- POUR LES RESSOURCES.
	   -- Heure PN
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN nbHPnCM END AS nbHPnCM,
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN nbHPnTD END AS nbHPnTD,
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN nbHPnTP END AS nbHPnTP,
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN COALESCE(nbHPnCM,0)+COALESCE(nbHPnTD,0)+COALESCE(nbHPnTP,0) 	    END AS sommePn,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbHPnCM,0)*COALESCE(calculCoeff('CM'),3/2) 				     END AS totalEqtdPromoPnCm,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbHPnTD,0)*COALESCE(calculCoeff('TD'),1)*COALESCE(s.nbGrpTD,0) END AS totalEqtdPromoPnTd,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbHPnTP,0)*COALESCE(calculCoeff('TP'),1)*COALESCE(s.nbGrpTP,0) END AS totalEqtdPromoPnTP,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbHPnCM,0)*COALESCE(calculCoeff('CM'),3/2)+COALESCE(nbHPnTD,0)*COALESCE(calculCoeff('TD'),1)*COALESCE(s.nbGrpTD,0)+COALESCE(nbHPnTP,0)*COALESCE(calculCoeff('TP'),1)*COALESCE(s.nbGrpTP,0) END AS sommeTotalEqtdPromoPn,

	   -- Répartition 1
	   CASE WHEN t.nomTypMod = 'Ressources' 				       THEN nbSemaineCM 		END AS nbSemaineCM,
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN nbHParSemaineCM 	END AS nbHParSemaineCM,
	   CASE WHEN t.nomTypMod = 'Ressources' 					   THEN nbSemaineTD 		END AS nbSemaineTD,
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN nbHParSemaineTD 	END AS nbHParSemaineTD,
	   CASE WHEN t.nomTypMod = 'Ressources' 					   THEN nbSemaineTP 		END AS nbSemaineTP,
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN nbHParSemaineTP 	END AS nbHParSemaineTP,

	   -- Repartition 2
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineCM,1)*COALESCE(nbHParSemaineCM,0) END AS nbSXnbHCM,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineTD,1)*COALESCE(nbHParSemaineTD,0) END AS nbSXnbHTD,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineTP,1)*COALESCE(nbHParSemaineTP,0) END AS nbSXnbHTP,
	   CASE WHEN t.nomTypMod = 'Ressources' OR t.nomTypMod = 'PPP' THEN hPonctuelle 				 END AS hPonctuelle,

	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineCM,0)*COALESCE(nbHParSemaineCM,0)+COALESCE(nbSemaineTD,0)*COALESCE(nbHParSemaineTD,0)+COALESCE(nbSemaineTP,0)*COALESCE(nbHParSemaineTP,0)+COALESCE(hPonctuelle,0) END AS SommeNbSXnbH,

	   --Total promo eqtd
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineCM,1)*COALESCE(nbHParSemaineCM,0)*COALESCE(calculCoeff('CM'),3/2) 			END AS promoEqtdCM,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineTD,1)*COALESCE(nbHParSemaineTD,0)*COALESCE(calculCoeff('TD'),1)*COALESCE(nbGrpTD,0) 	END AS promoEqtdTD,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineTP,1)*COALESCE(nbHParSemaineTP,0)*COALESCE(calculCoeff('TP'),1)*COALESCE(nbGrpTP,0) 	END AS promoEqtdTP,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(hPonctuelle,0)*COALESCE(calculCoeff('HP'),1)*COALESCE(nbGrpTD,0) 					END AS promoEqtdHP,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(nbSemaineCM,1)*COALESCE(nbHParSemaineCM,0)*COALESCE(calculCoeff('CM'),3/2)+COALESCE(nbSemaineTD,1)*COALESCE(nbHParSemaineTD,0)*COALESCE(calculCoeff('TD'),1)*COALESCE(nbGrpTD,0)+COALESCE(nbSemaineTP,0)*COALESCE(nbHParSemaineTP,0)*COALESCE(calculCoeff('TP'),1)*COALESCE(nbGrpTP,1)+COALESCE(hPonctuelle,0)*COALESCE(calculCoeff('HP'),1)*COALESCE(nbGrpTD,0)
	   		WHEN t.nomTypMod = 'SAE'   	    THEN COALESCE(nbHSaeParSemestre,0)+COALESCE(nbHTutParSemestre,0)
			WHEN t.nomTypMod = 'Stage'      THEN COALESCE(nbHREH,0)+COALESCE(nbHTut,0)
			WHEN t.nomTypMod = 'PPP'        THEN COALESCE(nbHParSemaineCM,0)+COALESCE(nbHParSemaineTD,0)+COALESCE(nbHParSemaineTP,0)+COALESCE(nbHTut,0)+COALESCE(hPonctuelle,0)
			END AS sommeTotPromoEqtd,

	   --Total affecté eqtd
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(calculNbAffect(m.codMod,'CM',s.annee),0) END AS eqtdCM,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(calculNbAffect(m.codMod,'TD',s.annee),0) END AS eqtdTD,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(calculNbAffect(m.codMod,'TP',s.annee),0) END AS eqtdTP,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(calculNbAffect(m.codMod,'HP',s.annee),0) END AS eqtdHP,
	   CASE WHEN t.nomTypMod = 'Ressources' THEN COALESCE(calculNbAffect(m.codMod,'CM',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'TD',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'TP',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'HP',s.annee),0)
	   		WHEN t.nomTypMod = 'SAE'        THEN COALESCE(calculNbAffect(m.codMod,'Sae',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'HT',s.annee),0)
			WHEN t.nomTypMod = 'Stage' 	    THEN COALESCE(calculNbAffect(m.codMod,'REH',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'HT',s.annee),0)
			WHEN t.nomTypMod = 'PPP' 	    THEN COALESCE(calculNbAffect(m.codMod,'CM',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'TD',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'TP',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'HT',s.annee),0)+COALESCE(calculNbAffect(m.codMod,'HP',s.annee) ,0)
			END AS sommeTotAffectEqtd,

	   --POUR LES SAE.
	   --Heure pn
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHPnSaeParSemestre 						END AS nbHPnSaeParSemestre,
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHPnTutParSemestre 						END AS nbHPnTutParSemestre,
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHPnSaeParSemestre+nbHPnTutParSemestre	END AS sommeHPnSAE,

	   --Repartition premiere ligne
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHSaeParSemestre 												END AS nbHSaeParSemestre,
	   CASE WHEN t.nomTypMod = 'SAE' THEN nbHTutParSemestre 												END AS nbHTutParSemestre,

	   --Repartition seconde ligne
	   CASE WHEN t.nomTypMod = 'SAE'                                                 THEN COALESCE(calculNbAffect(m.codMod,'Sae',s.annee),0) END AS nbHAffecteSAE,
	   CASE WHEN t.nomTypMod = 'SAE' OR t.nomTypMod = 'Stage' OR t.nomTypMod = 'PPP' THEN COALESCE(calculNbAffect(m.codMod,'HT',s.annee),0)  END AS nbHAffecteHT,

	   --POUR LES STAGE.
	   --Heure pn
	   CASE WHEN t.nomTypMod = 'Stage' 							          THEN nbHPnREH 			                            END AS nbHPnREH,
	   CASE WHEN t.nomTypMod = 'Stage' OR t.nomTypMod = 'PPP'	THEN nbHPnTut END AS nbHPnTut,
	   CASE WHEN t.nomTypMod = 'Stage' 							          THEN COALESCE(nbHPnREH,0)+COALESCE(nbHPnTut,0) 	END AS sommeHPnStage,

	   --Repartition premiere ligne
	   CASE WHEN t.nomTypMod = 'Stage' 						            THEN nbHREH END AS nbHREH,
	   CASE WHEN t.nomTypMod = 'Stage' OR t.nomTypMod = 'PPP' THEN nbHTut END AS nbHTut,

	   --Repartition seconde ligne
	   CASE WHEN t.nomTypMod = 'Stage' THEN COALESCE(calculNbAffect(m.codMod,'REH',s.annee),0) 	END AS nbHAffecteREH,

	   --POUR LES PPP.
	   --Heure pn ??
	   CASE WHEN t.nomTypMod = 'PPP' THEN COALESCE(nbHPnCM,0)+COALESCE(nbHPnTD,0)+COALESCE(nbHPnTP,0)+COALESCE(nbhpntut,0) END AS sommeHeurePnPPP,
	   	-- Répartition 1 ??
	   	-- Répartition 2 EFFECTUER ??
	   CASE WHEN t.nomTypMod = 'PPP' THEN COALESCE(calculNbAffect(m.codMod,'CM',s.annee),0) END AS nbHAffecteCM,
	   CASE WHEN t.nomTypMod = 'PPP' THEN COALESCE(calculNbAffect(m.codMod,'TD',s.annee),0) END AS nbHAffecteTD,
	   CASE WHEN t.nomTypMod = 'PPP' THEN COALESCE(calculNbAffect(m.codMod,'TP',s.annee),0) END AS nbHAffecteTP,
	   CASE WHEN t.nomTypMod = 'PPP' THEN COALESCE(calculNbAffect(m.codMod,'HP',s.annee),0) END AS nbHAffecteHP,

	   m.valide
FROM Module m JOIN TypeModule t ON t.codTypMod = m.codTypMod
			  JOIN Semestre   s ON s.codSem    = m.codSem AND m.annee = s.annee;


CREATE OR REPLACE VIEW liste_module AS
SELECT DISTINCT annee,codSem,codMod, libLong, (sommeTotAffectEqtd || '/' || sommeTotPromoEqtd)::VARCHAR AS hAP, valid
FROM module_final
ORDER BY codMod;

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
	SET codCatInter = getCatInter($2)
	WHERE codInter = $1;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE VIEW intervenant_complet AS
SELECT DISTINCT (f.nom || ' ' || f.prenom)::VARCHAR AS nom, f.nomCat, i.annee,
		S1 * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheoS1,
		S1 AS ReelS1,
		S3 * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheoS3,
		S3 AS ReelS3,
		S5 * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheoS5,
		S5 AS ReelS5,
		sTotImpair * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheossImpTot,
		sTotImpair AS ReelssImpTot,
		S2 * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheoS2,
		S2 AS ReelS2,
		S4 * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheoS4,
		S4 AS ReelS4,
		S6 * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheoS6,
		S6 AS ReelS6,
		sTotPair * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheossPairTot,
		sTotPair AS ReelssPairTot,
		Total * (c.ratioTPCatInterDen::NUMERIC/c.ratioTPCatInterNum::NUMERIC) AS TheoTot,
		Total AS ReelTot
FROM intervenant_final f JOIN intervenant i ON f.codInter = i.codInter
						 JOIN CategorieIntervenant c ON i.codCatInter = c.codCatInter;


CREATE OR REPLACE FUNCTION clonage(annee_source INTEGER, annee_destination INTEGER)
RETURNS VOID AS $$
DECLARE
	nbAnnee 		 INTEGER;
	cloneSemestre 	 RECORD;
	cloneIntervenant RECORD;
	cloneModule 	 RECORD;
	cloneAffectation RECORD;
BEGIN
	SELECT COUNT(annee) INTO nbAnnee FROM Annee WHERE annee = annee_destination;
	/*On test si l'année n'est pas déja crée*/
	IF 0 != nbAnnee THEN
		RETURN;
	END IF;

	INSERT INTO Annee VALUES (annee_destination);

	CREATE TEMPORARY TABLE cloneSemestre 	AS SELECT * FROM Semestre 	 WHERE annee = annee_source;
	CREATE TEMPORARY TABLE cloneIntervenant AS SELECT * FROM Intervenant WHERE annee = annee_source;
    CREATE TEMPORARY TABLE cloneModule 		AS SELECT * FROM Module 	 WHERE annee = annee_source;
    CREATE TEMPORARY TABLE cloneAffectation AS SELECT * FROM Affectation WHERE annee = annee_source;


	UPDATE cloneSemestre 	SET annee = annee_destination;
	UPDATE cloneIntervenant SET annee = annee_destination;
	UPDATE cloneModule 		SET annee = annee_destination;
	UPDATE cloneAffectation SET annee = annee_destination;

	INSERT INTO Semestre 	SELECT * FROM cloneSemestre;
	INSERT INTO Intervenant SELECT * FROM cloneIntervenant;
	INSERT INTO Module 		SELECT * FROM cloneModule;
	INSERT INTO Affectation SELECT * FROM cloneAffectation;

	DROP TABLE IF EXISTS cloneAffectation CASCADE;
	DROP TABLE IF EXISTS cloneModule CASCADE;
	DROP TABLE IF EXISTS cloneIntervenant CASCADE;
	DROP TABLE IF EXISTS cloneSemestre CASCADE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION supprimer_annee(INTEGER)
RETURNS VOID AS $$
BEGIN
	DELETE FROM Affectation WHERE annee = $1;
	DELETE FROM Module WHERE annee = $1;
	DELETE FROM Semestre WHERE annee = $1;
	DELETE FROM Intervenant WHERE annee = $1;
	DELETE FROM Annee WHERE annee = $1;
END;
$$ LANGUAGE plpgsql;

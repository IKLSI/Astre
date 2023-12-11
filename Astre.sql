------------------------------
--		Projet ASTRE		--
------------------------------

-- suppression des tables si elles existent déjà
-- NB : cela supprime donc les éventuels tuples contenus

DROP TABLE IF EXISTS Intervenant 			CASCADE ;
DROP TABLE IF EXISTS Module 				CASCADE ;
DROP TABLE IF EXISTS Semestre 				CASCADE ;
DROP TABLE IF EXISTS Affectation 			CASCADE ;
DROP TABLE IF EXISTS CategorieIntervenant 	CASCADE ;
DROP TABLE IF EXISTS CategorieHeure 		CASCADE ;
DROP TABLE IF EXISTS TypeModule 	        CASCADE ;

DROP FUNCTION IF EXISTS getService  (integer) CASCADE;
DROP FUNCTION IF EXISTS getMaxHeure (integer) CASCADE;
DROP FUNCTION IF EXISTS getCodTypMod(integer) CASCADE;

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
	NEW.hServ := (SELECT service FROM CategorieIntervenant WHERE codCatInter = NEW.codCatInter);
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
	NEW.maxHeure := (SELECT maxHeure FROM CategorieIntervenant WHERE codCatInter = NEW.codCatInter);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER default_maxHeure_trigger
BEFORE INSERT ON Intervenant
FOR EACH ROW EXECUTE FUNCTION default_maxHeure();

-- creation de la table CategorieHeure

CREATE TABLE CategorieHeure (
	codCatHeure SERIAL PRIMARY KEY,
	nomCatHeure VARCHAR(20),
	coeffNum    INTEGER,
	coeffDen    INTEGER
);

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

-- creation de la table Module

CREATE TABLE Module (
	codMod    VARCHAR(5) PRIMARY KEY,
	codTypMod INTEGER REFERENCES TypeModule(codTypMod),
	codSem    VARCHAR(2) REFERENCES Semestre(codSem),
	
	libLong   VARCHAR(50),
	libCourt  VARCHAR(20),

	valid BOOLEAN,

	/*Spécifique a ressource*/
	nbHParSemaineTD   INTEGER CHECK (codTypMod=1 OR nbHParSemaineTD = NULL),
	nbHParSemaineTP   INTEGER CHECK (codTypMod=1 OR nbHParSemaineTP = NULL),
	nbHParSemaineHTut INTEGER CHECK (codTypMod=1 OR nbHParSemaineHTut = NULL),

	/*Spécifique a sae*/
	nbHPnSaeParSemestre INTEGER CHECK (codTypMod=2 OR nbHPnSaeParSemestre = NULL),
	nbHPnTutParSemestre INTEGER CHECK (codTypMod=2 OR nbHPnTutParSemestre = NULL),

	/*Spécifique a stage*/
	nbHREH INTEGER CHECK (codTypMod=3 OR nbHREH = NULL),
	nbHTut INTEGER CHECK (codTypMod=3 OR nbHTut = NULL)
);

CREATE OR REPLACE FUNCTION getCodTypMod(integer)
	RETURNS INTEGER AS
$$
DECLARE
	valcodTypMod INTEGER;
BEGIN
	SELECT codTypMod INTO valcodTypMod FROM Module WHERE codMod = $1;
	RETURN valcodTypMod;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION verifHP(integer)
	RETURNS BOOLEAN AS
$$
DECLARE
	valVerif INTEGER;
BEGIN
	SELECT COUNT(codCatHeure) INTO valVerif FROM CategorieHeure WHERE codCatHeure = $1 AND nomCatHeure = 'HP';
	RETURN valVerif = 1;
END;
$$
LANGUAGE plpgsql;

-- creation de la table Affectation

CREATE TABLE Affectation (
	codMod VARCHAR(5) REFERENCES Module(codMod),
	codInter INTEGER REFERENCES Intervenant(codInter),
	codCatHeure INTEGER REFERENCES CategorieHeure(codCatHeure),
	commentaire TEXT,
	PRIMARY KEY(codInter,codCatHeure,codMod),

	/*Spécifique a ressource*/
	nbSem INTEGER CHECK (getCodTypMod(codMod)=1 AND verifHP(codCatHeure) OR nbSem = NULL),
	nbGrp INTEGER CHECK (getCodTypMod(codMod)=1 OR nbGrp = NULL),

	/*Spécifique a sae/stage*/
	nbH INTEGER CHECK (getCodTypMod(codMod) = 2 OR getCodTypMod(codMod) = 3 OR nbH = NULL)
);







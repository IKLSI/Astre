------------------------------
--		Projet ASTRE		--
------------------------------

-- suppression des tables si elles existent déjà
-- NB : cela supprime donc les éventuels tuples contenus

drop table if exists Intervenant 			CASCADE ;
drop table if exists Module 				CASCADE ;
drop table if exists Semestre 				CASCADE ;
drop table if exists Affectation 			CASCADE ;
drop table if exists CategorieIntervenant 	CASCADE ;
drop table if exists CategorieHeure 		CASCADE ;
drop table if exists TypeModule 	        CASCADE ;

-- creation de la table CategorieIntervenant

CREATE TABLE CategorieIntervenant (
	codCatInter        SERIAL PRIMARY KEY,
	nomCat             VARCHAR(20) NOT NULL,
	service            INTEGER,
	maxHeures          INTEGER,
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
	hServ       INTEGER DEFAULT getService(),
	maxHeure    INTEGER DEFAULT getMaxHeure()
);

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

-- creation de la table Affectation

CREATE TABLE Affectation (
	codMod VARCHAR(5) REFERENCES Module(codMod),
	codInter INTEGER REFERENCES Intervenant(codInter),
	codCatHeure INTEGER REFERENCES CategorieHeure(codCatHeure),
	commentaire TEXT,
	PRIMARY KEY(codInter,codCatHeure,codMod),

	/*Spécifique a ressource*/
	nbSem INTEGER CHECK (getCodTypMod()=1),
	nbGrp INTEGER CHECK (getCodTypMod()=1),

	/*Spécifique a sae/stage*/
	nbH INTEGER CHECK (getCodTypMod() = 2 OR getCodTypMod() = 3 OR nbH = NULL)
);

CREATE OR REPLACE FUNCTION getService(integer)
    RETURNS INTEGER AS
$$
DECLARE
    valService INTEGER;
BEGIN
    SELECT service INTO valService FROM CategorieIntervenant WHERE codCatInter = $1;
    RETURN valService;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION getMaxHeure(integer)
    RETURNS INTEGER AS
$$
DECLARE
    valmaxHeure INTEGER;
BEGIN
    SELECT maxHeure INTO valmaxHeure FROM CategorieIntervenant WHERE codCatInter = $1;
    RETURN valMaxHeure;
END;
$$
LANGUAGE plpgsql;

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

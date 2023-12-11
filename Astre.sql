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


-- creation de la table Intervenant

CREATE TABLE Intervenant (
	codInter        INTEGER SERIAL PRIMARY KEY,
	nom			    VARCHAR(40),
	prenom          VARCHAR(40),
	codeCatInter    INTEGER REFERENCES CategorieIntervenant(codeCatInter),
	hServ           INTEGER,
	maxHeure        INTEGER,
	ratioTPInterNum INTEGER,
	ratioTPInterDen INTEGER
);

-- creation de la table CategorieIntervenant

CREATE TABLE CategorieIntervenant (
	codCatInter        INTEGER SERIAL PRIMARY KEY,
	nomCat             VARCHAR(20) NOT NULL,
	service            INTEGER,
	maxHeures          INTEGER,
	ratioTPCatInterNum INTEGER,
	ratioTPCatInterDen INTEGER
);

-- creation de la table CategorieHeure

CREATE TABLE CategorieHeure (
	codCatHeure INTEGER SERIAL PRIMARY KEY,
	nomCatHeure VARCHAR(20),
	coeffNum    INTEGER,
	coeffDen    INTEGER,
	CONSTRAINT CHECK (coeffNum/coeffDen BETWEEN 0.5 AND 1)
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
	numSem    INTEGER REFERENCES Semestre(numSem),
	code      VARCHAR(5),
	
	libLong   VARCHAR(50),
	libCourt  VARCHAR(20),

	valid BOOLEAN,

	/*Spécifique a ressource*/
	nbHParSemaineTD   INTEGER CHECK (codTypMod=1),
	nbHParSemaineTP   INTEGER CHECK (codTypMod=1),
	nbHParSemaineHTut INTEGER CHECK (codTypMod=1),

	/*Spécifique a sae*/
	nbHPnSaeParSemestre INTEGER CHECK (codTypMod=2),
	nbHPnTutParSemestre INTEGER CHECK (codTypMod=2),

	/*Spécifique a stage*/
	nbHREH INTEGER CHECK (codTypMod=3),
	nbHTut INTEGER CHECK (codTypMod=3)
);

-- creation de la table Affectation

CREATE TABLE Affectation (
	codInter SERIAL REFERENCES Intervenant(codeInter),
	codeCatHeure INTEGER SERIAL REFERENCES CategorieHeure(codeCatHeure),
	commentaire TEXT,
	PRIMARY KEY(codeInter,codeCatHeure),

	/*Spécifique a ressource*/
	nbSem INTEGER CHECK (codeCatHeure=1),
	nbGrp INTEGER CHECK (codeCatHeure=1),

	/*Spécifique a sae/stage*/
	nbH INTEGER CHECK (codeCatHeure = 2 OR codeCatHeure = 3)
);

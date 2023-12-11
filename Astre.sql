------------------------------
--		Projet ASTRE		--
------------------------------

-- suppression des tables si elles existent déjà
-- NB : cela supprime donc les éventuels tuples contenus

drop table if exists Intervenant 			cascade ;
drop table if exists Module 				cascade ;
drop table if exists Semestre 				cascade ;
drop table if exists Affectation 			cascade ;
drop table if exists CategorieIntervenant 	cascade ;
drop table if exists CategorieHeure 		cascade ;


-- creation de la table Intervenant

CREATE TABLE Intervenant (
	codInter     INTEGER PRIMARY KEY,
	nom			 VARCHAR(40),
	prenom       VARCHAR(40),
	codeCatInter REFERENCES CategorieIntervenant(codeCatInter),
	hServ        INTEGER,
	maxHeure     INTEGER,
	ratioTP      INTEGER
);

-- creation de la table CategorieIntervenant

CREATE TABLE CategorieIntervenant (
	codCatInter INTEGER PRIMARY KEY,
	nomCat       VARCHAR(20) CHECK (categorie IN ('contractuel','vacataire','enseignant chercheur')) NOT NULL,
	service      INTEGER,
	maxHeures    INTEGER,
	ratioTP      INTEGER
);

-- creation de la table CategorieHeure

CREATE TABLE CategorieHeure (
	codCatHeure INTEGER PRIMARY KEY,
	nomCatHeure VARCHAR(20),
	coeff FLOAT CHECK (coeffInter BETWEEN 1/2 and 1)
);

-- creation de la table Semestre

CREATE TABLE Semestre (
	codSem    INTEGER PRIMARY KEY,
	nbGrpTD    INTEGER,
	nbGrpTP    INTEGER,
	nbEtd      INTEGER,
	nbSemaines INTEGER
);

CREATE TABLE TypeModule (
	codTypMod INTEGER PRIMARY KEY,
	nomTypMod VARCHAR(20)
);

-- creation de la table Module

CREATE TABLE Module (
	codMod VARCHAR(5) PRIMARY KEY,
	typeMod INTEGER REFERENCES ,
	numSem INTEGER REFERENCES Semestre(numSem),
	code VARCHAR(5),
	libLong VARCHAR(50),
	libCourt VARCHAR(20),
	hCM INTEGER,
	hTD INTEGER,
	hTP INTEGER,
	hSom INTEGER
);

-- creation de la table Affectation

CREATE TABLE Affectation (
	codInter INTEGER REFERENCES Intervenant(codeInter),
	codeCatHeure INTEGER REFERENCES CategorieHeure(codeCatHeure),
	nbGrp INTEGER,
	nbSemaine INTEGER,
	PRIMARY KEY(codeInter,codeCatHeure)
);

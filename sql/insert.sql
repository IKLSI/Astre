-- Insertion de données dans la table CategorieIntervenant
INSERT INTO CategorieIntervenant (nomCat, service, maxHeure, ratioTPCatInterNum, ratioTPCatInterDen)
VALUES   ('info_ec' , 192, 364, 1,1),
	   ('vaca_pro', 120, 187, 2,3),
	   ('vac_sd'  , 90 , 187, 1,1),
	   ('vaca_ret', 80 , 96 , 2,3),
	   ('info_sd' , 384, 576, 1,1);

-- Insertion de données dans la table TypeModule
INSERT INTO TypeModule (nomTypMod)
VALUES   ('Ressources'),
	   ('SAE'       ),
	   ('Stage'     ),
	   ('PPP'       );


-- Insertion de données dans la table CategorieHeure
INSERT INTO CategorieHeure (nomCatHeure, coeffNum, coeffDen)
VALUES ('CM'   , 3,2),
		   ('TD'   , 1,1),
	     ('TP'   , 1,1),
	   	 ('HT'   , 1,1),
		   ('REH'  , 1,1),
		   ('Sae'  , 1,1),
		   ('HP'   , 1,1);



-- Insertion de données dans la table Semestre
INSERT INTO Semestre (codSem, nbGrpTD, nbGrpTP, nbEtd, nbSemaines)
VALUES ('S1', 4, 7, 85, 15),
	     ('S2', 4, 7, 65, 16),
	     ('S3', 4, 7, 48, 15),
	     ('S4', 4, 7, 48, 16),
	     ('S5', 4, 7, 42, 15),
	     ('S6', 4, 7, 42, 16);



-- Insertion de données dans la table Intervenants
INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure)
VALUES ('Boukachour', 'Hadhoum'  , 1, 192, 364),
	     ('Colignon'  , 'Thomas'   , 2, 120, 187),
	     ('Dubocage'  , 'Tiphaine' , 2, 120, 187),
	     ('Hervé'     , 'Nathalie' , 3,  90, 187),
	     ('Pecqueret' , 'Véronique', 4,  80,  96),
	     ('Laffeach'  , 'Quentin'  , 5, 384, 576),
	     ('Le Pivert' , 'Philippe' , 5, 384, 576),
	     ('Legrix'    , 'Bruno'    , 5, 384, 576),
	     ('Nivet'     , 'Laurence' , 5, 384, 576);



-- Insertion de données dans la table Module ressource
INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valid, nbHPnCM, nbHPnTD, nbHPnTP, nbSemaineTD, nbSemaineTP, nbSemaineCM, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle)
VALUES ('R1.01', 'S1' , 1, 'Initiation au développement'           , 'Init Dev'      , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.02', 'S1' , 1, 'Développement interfaces Web'          , 'Dev Web'       , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.03', 'S1' , 1, 'Introduction Architecture'             , 'Archi'         , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.04', 'S1' , 1, 'Introduction Système'                  , 'Système'       , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.05', 'S1' , 1, 'Introduction Base de données'          , 'Bado'          , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.06', 'S1' , 1, 'Mathématiques discrètes'               , 'Maths '        , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.07', 'S1' , 1, 'Outils mathématiques fondamentaux'     , 'Outils num'    , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.08', 'S1' , 1, 'Gestion de projet et des organisations', 'GPO'           , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.09', 'S1' , 1, 'Économie durable et numérique'         , 'Économie'      , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.10', 'S1' , 1, 'Anglais Technique'                     , 'Anglais Pro'   , false,6,65,28,14,14,6,4,2,1,9),
       ('R1.11', 'S1' , 1, 'Bases de la communication'             , 'Communication' , false,6,65,28,14,14,6,4,2,1,9),
       ('R2.01', 'S2' , 1, 'Développement orienté objets'          , 'Dev Objet'     , false,6,65,28,14,14,6,4,2,1,9),
       ('R2.02', 'S2' , 1, 'Développement d''applications avec IHM', 'Dev IHM'       , false,0,0,93,0,13,0,0,3,0,0),
       ('R3.02', 'S3' , 1, 'Développement Efficace'                , 'Dev Effi'      , false,6,65,28,14,14,6,4,2,1,9),
       ('R4.01', 'S4' , 1, 'Architecture logicielle'               , 'Archi Logi'    , false,6,65,28,13,13,6,2,2,1,0);      

	 
-- Insertion de données dans la table Module SAÉ
INSERT INTO Module (codMod, codTypMod, codSem, libLong, libCourt, valid, nbHPnSaeParSemestre, nbHPnTutParSemestre, nbHSaeParSemestre, nbHTutParSemestre)
VALUES ('S1.1', 2, 'S1' , 'Implémentation d''un besoin client'    , 'SAE-01'   , false,6 ,0 , 6, 0),
       ('S2.2', 2, 'S2' , 'Développement d''une application'      , 'SAE-02'   , false,8 ,0 , 8, 8),
       ('S3.1', 2, 'S3' , 'Développement d''une application'      , 'Dev appli', false,40,38, 40, 38);


-- Insertion de données dans la table Module stage
INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valid, nbHREH, nbHTut, nbHPnREH, nbHPnTut)
VALUES ('S4.ST','S4', 3 , 'Stage'                                 , 'Stage'    ,false ,10,2, NULL, NULL);

-- Insertion de données dans la table Module PPP
INSERT INTO Module (codMod, codSem, codTypMod, libLong, libCourt, valid, nbHPnCM, nbHPnTD, nbHPnTP, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle, nbHPnHTut)
VALUES             ('P3.01','S3', 4 , 'Portfolio'                 , 'Portfolio' ,false ,1, 1, 1, 2, 4, 0, 1, 4);



-- Insertion de données dans la table Affectation SAE / STAGE
INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbH)
VALUES('S3.1',1,6,            NULL,   10), 
      ('S3.1',1,7,            NULL,    8),

      ('S3.1',6,6,            NULL,    5),
      ('S3.1',6,7,            NULL,    7),

      ('S3.1',1,6,            NULL,   10),
      ('S3.1',1,7,            NULL,    8),

      ('S3.1',1,6,            NULL,   10),
      ('S3.1',1,7,            NULL,    8),

      ('S3.1',1,6,            NULL,    5),
      ('S3.1',1,7,            NULL,    7),

      ('S4.ST',6,7,           NULL,    3);



-- Insertion de données dans la table Affectation Ressource
INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbSem, nbGrp)
VALUES('R1.01',7,1,'3 CM d''1h30.',  6, 1),  
      ('R1.01',7,2,           NULL, 14, 2),  
      ('R1.01',7,3,           NULL, 14, 2),  
      ('R1.01',7,7,          'TP0',  1, 6), 
      ('R1.01',7,7,    'DS PAPIER',  1, 5),  
      ('R1.01',7,7,   'DS MACHINE',  1, 5),  

      ('R1.01',8,2,           NULL, 14, 2), 
      ('R1.01',8,7,           'TP0', 1, 6),
      ('R1.01',8,7,     'DS PAPIER', 1, 3),

      ('R1.01',2,3,           NULL, 14, 2),
      ('R1.01',2,7,   'DS MACHINE',  1, 3), 

      ('R1.01',3,3,           NULL, 14, 2),
      ('R1.01',3,7,   'DS MACHINE',  1, 3), 

      ('R1.01',1,7,    'DS PAPIER', 1,  5),
      --Ajout random
      ('R2.02',3,2,           NULL, 13, 2);
      ('R2.02',7,2,           NULL, 13, 2);
      ('R4.01',2,1,    'NbSem tmp',  6, 1);
      ('R4.01',2,2,    'NbSem tmp', 13, 2);
      ('R4.01',2,3,    'NbSem tmp', 13, 2);

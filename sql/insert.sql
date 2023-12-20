-- Insertion de données dans la table CategorieIntervenant

-- Insertion de données dans la table Annee
INSERT INTO Annee (annee)
VALUES (2023);


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
INSERT INTO Semestre (codSem, nbGrpTD, nbGrpTP, nbEtd, nbSemaines, annee)
VALUES ('S1', 4, 7, 85, 15, 2023),
	 ('S2', 4, 7, 65, 16, 2023),
	 ('S3', 4, 7, 48, 15, 2023),
	 ('S4', 4, 7, 48, 16, 2023),
	 ('S5', 4, 7, 42, 15, 2023),
	 ('S6', 4, 7, 42, 16, 2023);


-- Insertion de données dans la table Intervenants
INSERT INTO Intervenant (nom, prenom, codCatInter, hServ, maxHeure, annee)
VALUES('Boukachour', 'Hadhoum'  , 1, 192, 364, 2023),
	('Colignon'  , 'Thomas'   , 2, 120, 187, 2023),
	('Dubocage'  , 'Tiphaine' , 2, 120, 187, 2023),
	('Hervé'     , 'Nathalie' , 3,  90, 187, 2023),
	('Pecqueret' , 'Véronique', 4,  80,  96, 2023),
	('Laffeach'  , 'Quentin'  , 5, 384, 576, 2023),
	('Le Pivert' , 'Philippe' , 5, 384, 576, 2023),
	('Legrix'    , 'Bruno'    , 5, 384, 576, 2023),
	('Nivet'     , 'Laurence' , 5, 384, 576, 2023),
      ('Serin'     , 'Frédéric' , 5, 384, 576, 2023),
      ('Duflo'     , 'Hugues'   , 5, 384, 576, 2023),
      ('Boukachour', 'Jaouad'   , 5, 384, 576, 2023),
      ('Hassan'    , 'Alabboud' , 5, 384, 576, 2023),
      ('Abderrazak', 'Zahour'   , 5, 384, 576, 2023),
      ('Foubert'   , 'Jean'     , 5, 384, 576, 2023),
      ('Guinand'   , 'Frederic' , 5, 384, 576, 2023),
      ('Pytel'     , 'Steeve'   , 5, 384, 576, 2023),
      ('Boudebous' , 'Dalila'   , 5, 384, 576, 2023),
      ('Sadeg'     , 'Bruno'    , 5, 384, 576, 2023),
      ('Rembert'   , 'Pascal'   , 5, 384, 576, 2023),
      ('Bertin'    , 'Sébastien', 5, 384, 576, 2023),
      ('Delarue'   , 'Isabelle' , 5, 384, 576, 2023),
      ('Boquet'    , 'Benjamin' , 5, 384, 576, 2023),
      ('Jiménéz'   , 'Jan-Luis' , 5, 384, 576, 2023),
      ('Colin'     , 'Jean-Yves', 5, 384, 576, 2023),
      ('Keith'     , 'Emmanuel' , 5, 384, 576, 2023),
      ('Gaied'     , 'Mouhaned' , 5, 384, 576, 2023),
      ('Khraimeche', 'Salim'    , 5, 384, 576, 2023),
      ('Perroud'   , 'Florian'  , 5, 384, 576, 2023),
      ('Duvallet'  , 'Claude'   , 5, 384, 576, 2023);

-- Insertion de données dans la table Module ressource
INSERT INTO Module (nomMod, codSem, codTypMod, libLong, libCourt, valid, nbHPnCM, nbHPnTD, nbHPnTP, nbSemaineTD, nbSemaineTP, nbSemaineCM, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle, annee)
VALUES ('R1.01', 'S1' , 1, 'Initiation au développement'                                 , 'Init Dev'                   , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.02', 'S1' , 1, 'Développement interfaces Web'                                , 'Dev Web'                    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.03', 'S1' , 1, 'Introduction Architecture'                                   , 'Archi'                      , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.04', 'S1' , 1, 'Introduction Système'                                        , 'Système'                    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.05', 'S1' , 1, 'Introduction Base de données'                                , 'Bado'                       , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.06', 'S1' , 1, 'Mathématiques discrètes'                                     , 'Maths '                     , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.07', 'S1' , 1, 'Outils mathématiques fondamentaux'                           , 'Outils num'                 , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.08', 'S1' , 1, 'Gestion de projet et des organisations'                      , 'GPO'                        , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.09', 'S1' , 1, 'Économie durable et numérique'                               , 'Économie'                   , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.10', 'S1' , 1, 'Anglais Technique'                                           , 'Anglais Pro'                , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R1.11', 'S1' , 1, 'Bases de la communication'                                   , 'Communication'              , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.01', 'S2' , 1, 'Développement orienté objets'                                , 'Dev Objet'                  , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.02', 'S2' , 1, 'Développement d''applications avec IHM'                      , 'Dev IHM'                    , false, 0,  0, 93,  0, 13, 0, 0, 3, 0, 0, 2023),
       ('R2.03', 'S2' , 1, 'Qualité de développement'                                    , 'Dev Qualité'                , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.04', 'S2' , 1, 'Communication et fonctionnement bas niveau'                  , 'Comm Bas Niv'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.05', 'S2' , 1, 'Introduction aux services réseaux'                           , 'Intro Réseaux'              , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.06', 'S2' , 1, 'Exploitation d''une base de données'                         , 'Exploitation BDD'           , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.07', 'S2' , 1, 'Graphes'                                                     , 'Graphes'                    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.08', 'S2' , 1, 'Outils numériques pour les statistiques'                     , 'Outils Stats'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.09', 'S2' , 1, 'Méthodes Numériques'                                         , 'Méthodes Num'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.10', 'S2' , 1, 'Gestion de projet et des organisations'                      , 'Gestion Projet'             , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.11', 'S2' , 1, 'Droit'                                                       , 'Droit'                      , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.12', 'S2' , 1, 'Anglais d''entreprise'                                       , 'Anglais Entreprise'         , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.13', 'S2' , 1, 'Communication Technique'                                     , 'Comm Technique'             , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R2.14', 'S2' , 1, 'Projet Professionnel et Personnel'                           , 'Projet Pro Perso'           , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.01', 'S3' , 1, 'Développement WEB'                                           , 'Dev WEB'                    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.02', 'S3' , 1, 'Développement Efficace'                                      , 'Dev Effi'                   , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.03', 'S3' , 1, 'Analyse'                                                     , 'Analyse'                    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.04', 'S3' , 1, 'Qualité de développement 3'                                  , 'Dev Qualité 3'              , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.05', 'S3' , 1, 'Programmation Système'                                       , 'Prog Système'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.06', 'S3' , 1, 'Architecture des réseaux'                                    , 'Arch Réseaux'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.07', 'S3' , 1, 'SQL dans un langage de programmation'                        , 'SQL Langage'                , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.08', 'S3' , 1, 'Probabilités'                                                , 'Probabilités'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.09', 'S3' , 1, 'Cryptographie et sécurité'                                   , 'Cryptographie'              , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.10', 'S3' , 1, 'Management des systèmes d''information'                      , 'Mgmt SI'                    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.11', 'S3' , 1, 'Droits des contrats et du numérique'                         , 'Droits Contrats'            , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.12', 'S3' , 1, 'Anglais 3'                                                   , 'Anglais 3'                  , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.13', 'S3' , 1, 'Communication professionnelle'                               , 'Comm Professionnelle'       , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R3.14', 'S3' , 1, 'PPP 3'                                                       , 'PPP 3'                      , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.01', 'S4' , 1, 'Architecture logicielle'                                     , 'Archi Logi'                 , false, 6, 65, 28, 13, 13, 6, 2, 2, 1, 0, 2023),
       ('R4.02', 'S4' , 1, 'Qualité de développement 4'                                  , 'Dev Qualité 4'              , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.03', 'S4' , 1, 'Qualité et au-delà du relationnel'                           , 'Qualité Relationnel'        , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.04', 'S4' , 1, 'Méthodes d''optimisation'                                    , 'Méthodes Opti'              , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.05', 'S4' , 1, 'Anglais 4'                                                   , 'Anglais 4'                  , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.06', 'S4' , 1, 'Communication interne'                                       , 'Comm Interne'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.07', 'S4' , 1, 'PPP 4'                                                       , 'PPP 4'                      , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.08', 'S4' , 1, 'Virtualisation'                                              , 'Virtualisation'             , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.09', 'S4' , 1, 'Management avancé des Systèmes d''information'               , 'Mgmt Avancé SI'             , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.10', 'S4' , 1, 'Complément web'                                              , 'Complément Web'             , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.11', 'S4' , 1, 'Développement mobile'                                        , 'Dev Mobile'                 , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R4.12', 'S4' , 1, 'Automates'                                                   , 'Automates'                  , true, 6, 65, 28, 14, 14, 6, 4, 2, 1,  9, 2023),
       ('R5.01', 'S5' , 1, 'Initiation au management d’une équipe de projet informatique', 'Init Mgmt Projet'           , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.02', 'S5' , 1, 'Projet Personnel et Professionnel'                           , 'Projet Perso Pro'           , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.03', 'S5' , 1, 'Politique de communication'                                  , 'Communication'              , false, 0, 93,  0, 16,  0, 0, 2, 0, 0, 2, 2023),
       ('R5.04', 'S5' , 1, 'Qualité algorithmique'                                       , 'Qualité Algo'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.05', 'S5' , 1, 'Programmation avancée'                                       , 'Prog Avancée'               , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.06', 'S5' , 1, 'Programmation multimédia'                                    , 'Prog Multimédia'            , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.07', 'S5' , 1, 'Automatisation de la chaîne de production'                   , 'Auto Production'            , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.08', 'S5' , 1, 'Qualité de développement'                                    , 'Dev Qualité'                , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.09', 'S5' , 1, 'Virtualisation avancée'                                      , 'Virtualisation Avancée'     , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.10', 'S5' , 1, 'Nouveaux paradigmes de base de données'                      , 'Nouveaux Paradigmes BDD'    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.11', 'S5' , 1, 'Méthodes d’optimisation pour l’aide à la décision'           , 'Optimisation Décision'      , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.12', 'S5' , 1, 'Modélisations mathématiques'                                 , 'Modélisations Mathématiques', false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.13', 'S5' , 1, 'Économie durable et numérique'                               , 'Économie Durable'           , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R5.14', 'S5' , 1, 'Anglais'                                                     , 'Anglais'                    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R6.01', 'S6' , 1, 'Initiation à l’entrepreneuriat'                              , 'Init Entrepreneuriat'       , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R6.02', 'S6' , 1, 'Droit du numérique et de la propriété intellectuelle'        , 'Droit Numérique'            , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R6.03', 'S6' , 1, 'Communication : organisation et diffusion de l’information'  , 'Comm Organisation'          , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R6.04', 'S6' , 1, 'Projet Personnel et Professionnel'                           , 'PPP 6'                      , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R6.05', 'S6' , 1, 'Développement Avancé'                                        , 'Dev Avancé'                 , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023),
       ('R6.06', 'S6' , 1, 'Maintenance applicative'                                     , 'Maintenance Applicative'    , false, 6, 65, 28, 14, 14, 6, 4, 2, 1, 9, 2023);


-- Insertion de données dans la table Module SAÉ
INSERT INTO Module (nomMod, codTypMod, codSem, libLong, libCourt, valid, nbHPnSaeParSemestre, nbHPnTutParSemestre, nbHSaeParSemestre, nbHTutParSemestre, annee)
VALUES ('S1.1', 2, 'S1' , 'Implémentation d''un besoin client'    , 'SAE-01'   , false,  6,  0,  6,  0, 2023),
       ('S2.2', 2, 'S2' , 'Développement d''une application'      , 'SAE-02'   , false,  8,  0,  8,  8, 2023),
       ('S3.1', 2, 'S3' , 'Développement d''une application'      , 'Dev appli', false, 40, 38, 40, 38, 2023);


-- Insertion de données dans la table Module stage
INSERT INTO Module (nomMod, codSem, codTypMod, libLong, libCourt, valid, nbHREH, nbHTut, nbHPnREH, nbHPnTut, annee)
VALUES ('S4.ST', 'S4', 3 , 'Stage'                                 , 'Stage'    ,false ,10,2, NULL, NULL, 2023),
       ('R6.1' , 'S6', 3 , 'Stage'                                 , 'Stage'    ,false ,10,2, NULL, NULL, 2023);

-- Insertion de données dans la table Module PPP
INSERT INTO Module (nomMod, codSem, codTypMod, libLong, libCourt, valid, nbHPnCM, nbHPnTD, nbHPnTP, nbHParSemaineTD, nbHParSemaineTP, nbHParSemaineCM, hPonctuelle, nbHPnHTut, annee)
VALUES ('P3.01', 'S3', 4 , 'Portfolio'                 , 'Portfolio' ,false , 1, 1, 1, 2, 4, 0, 1, 4, 2023),
       ('P1.02', 'S4', 4 , 'Portfolio'                 , 'Portfolio', false , 1, 1, 1, 2, 4, 0, 1, 4, 2023);


-- Insertion de données dans la table Affectation SAE / STAGE
INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbH, annee)
VALUES(71,7,6,              NULL,    4, 2023), 
      (71,1,6,              NULL,    4, 2023), 
      (71,8,6,              NULL,    4, 2023), 
      (72,8,6, 'Absent 1semaine',   12, 2023), 
      --]Ajout random]
      (73,1,6,              NULL,   10, 2023), 
      (73,1,7,              NULL,    8, 2023),
      (73,6,6,              NULL,    5, 2023),
      (73,6,7,              NULL,    7, 2023),
      (73,1,6,              NULL,   10, 2023),
      (73,1,7,              NULL,    8, 2023),
      (73,1,6,              NULL,   10, 2023),
      (73,1,7,              NULL,    8, 2023),
      (73,1,6,              NULL,    5, 2023),
      (73,1,7,              NULL,    7, 2023),
      (74,6,7,             NULL,    3, 2023);


-- Insertion de données dans la table Affectation Ressource
INSERT INTO Affectation (codMod, codInter, codCatHeure, commentaire, nbSem, nbGrp, annee)
VALUES(1,7,1,'3 CM d''1h30.',  6, 1, 2023),  
      (1,7,2,           NULL, 14, 2, 2023),  
      (1,7,3,           NULL, 14, 2, 2023),  
      (1,7,7,          'TP0',  1, 6, 2023), 
      (1,7,7,    'DS PAPIER',  1, 5, 2023),  
      (1,7,7,   'DS MACHINE',  1, 5, 2023),  
      (1,8,2,           NULL, 14, 2, 2023), 
      (1,8,7,           'TP0', 1, 6, 2023),
      (1,8,7,     'DS PAPIER', 1, 3, 2023),
      (1,2,3,           NULL, 14, 2, 2023),
      (1,2,7,   'DS MACHINE',  1, 3, 2023), 
      (1,3,3,           NULL, 14, 2, 2023),
      (1,3,7,   'DS MACHINE',  1, 3, 2023), 
      (1,1,7,    'DS PAPIER', 1,  5, 2023),
      --Ajout random
      (13,3,2,           NULL, 13, 2, 2023),
      (13,7,2,           NULL, 13, 2, 2023), --R2.02
      (39,2,1,    'NbSem tmp',  6, 1, 2023), --R4.04
      (39,2,2,    'NbSem tmp', 13, 2, 2023),
      (39,2,3,    'NbSem tmp', 13, 2, 2023),
      (53,9,2,           NULL, 15, 2, 2023), --R5.03
      (69,1,1,'3 CM d''1h30.',  6, 1, 2023),  
      (69,1,2,           NULL, 14, 2, 2023),  
      (69,1,3,           NULL, 14, 2, 2023),  
      (69,1,7,   'DS MACHINE',  1, 5, 2023),  
      (69,1,7,   'DS MACHINE',  1, 5, 2023);  --R6.05
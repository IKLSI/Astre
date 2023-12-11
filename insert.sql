-- Insertion de données dans la table Intervenants
INSERT INTO CategorieIntervenant (nomCat, service, maxHeures, ratioTPCatInterNum, ratioTPCatInterDen)
VALUES ('info_ec' , 192, 364, 1,1),
       ('vaca_pro', 120, 187, 2,3),
       ('vac_sd'  , 90 , 187, 1,1),
       ('vaca_ret', 80 , 96 , 2,3),
       ('info_sd' , 384, 576, 1,1);

-- Insertion de données dans la table TypeModule
INSERT INTO TypeModule (nomTypMod)
VALUES ('CM'),
       ('TD'),
       ('TP'),
       ('H tut'),
       ('REH'),
       ('H saé'),
       ('HP');

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




/*
Catégorie Nom Prénom hServ hMax Coef TP S1 S3 S5 sTot S2 S4 S6 sTot Total

info_ec Boukachour Hadhoum 192 364 1 106.5 18.0 0.0 124.5 0.0 0.0 0.0 0.0 124.5
vaca_pro Colignon Thomas 120 187 2/3 40.3 0.0 0.0 40.3 0.0 0.0 0.0 0.0 40.3
vaca_pro Dubocage Tiphaine 120 187 2/3 40.3 0.0 0.0 40.3 0.0 0.0 0.0 0.0 40.3
vac_sd Hervé Nathalie 90 187 2/3 0.0 37.3 0.0 37.3 0.0 0.0 0.0 0.0 37.3
vaca_ret Pecqueret Véronique 80 96 2/3 0.0 37.3 0.0 37.3 0.0 0.0 0.0 0.037.3
info_sd Laffeach Quentin 384 576 1 155.0 12.0 0.0 167.0 0.0 3.0 0.0 3.0 170.0
info_sd LePivert Philippe 384 576 1 188.0 18.0 0.0 206.0 0.0 0.0 0.0 0.0 206.0
info_sd Legrix Bruno 384 576 1 118.0 18.0 0.0 136.0 0.0 0.0 0.0 0.0 136.0
info_sd Nivet Laurence 384 576 1 75.0 12.0 0.0 87.0 0.0 0.0 0.0 0.0 87.0
*/


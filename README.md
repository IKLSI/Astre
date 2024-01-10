# SAÉ 3.01 - Équipe n°5 - Manuel d'utilisation

## Présentation du projet

- L'objectif de ce projet est d'élaborer un outil de gestion des
heures d’enseignement prévues pour chaque intervenant, au
sein d’une application monoposte en accès limité.
La direction du Département Informatique souhaite une
application qui permette de répartir entre les groupes
d’étudiants les heures d’enseignement disponibles selon le
programme national.
Il s’agit de saisir les heures prévisionnelles des différents
intervenants et les groupes qui leur sont attribués.

- Pour ce projet nous avons décidé d'utiliser le langage Java et la librairie JavaFX pour la partie graphique. Nous avons également utilisé une base de données PostGreSQL pour stocker les données.

## Comment installer le projet à l'aide de l'installeur

- Pour installer le projet et ses dépendances il suffit de télécharger et de lancer l'installeur ```install.sh``` sous Linux. Pour cela il faut se placer dans le dossier où se trouve le fichier d'installation puis lancer la commande ```chmod +x install.sh``` puis ```./install.sh```. Il faut ensuite suivre les instructions de l'installeur.

## Comment exécuter le projet

Si l'installation a été faite à l'aide de l'installeur il suffit de lancer le fichier Astre généré par l'installeur sur votre Bureau.

Dans le cas contraire, uniquement besoin de lancer le fichier run selon votre système d'exploitation, d'avoir au préalable être connecté en ssh à une base de données à l'aide du fichier ```connexion/ssh.sh``` et d'avoir installé les dépendances nécessaires à l'aide du fichier ```install.sh```.

## Structure du projet

- Le projet suit une structure standard MVC (Modèle-Vue-Contrôleur). Les fichiers sources sont situés dans le répertoire `src/` et les fichiers de vue FXML dans le répertoire `bin/interface/`.
	- ```controleur``` : contient le fichier Contrôleur.java
	- ```ihm``` : contient les différents fichiers .java faisant le lien entre le java et le FXML
	- ```metier``` : contient les différents fichiers .java nécessaires au fonctionnement du projet

# Membres de l'équipe

- LEVESQUE Kyliann
- QUEVAL Martin
- DE MACEDO Lorenzo
- LEGRAND Allan
- LANGLOIS Lucas
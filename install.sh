#!/bin/bash

# Demande de saisie du nom d'utilisateur, du nom de la base de données et du mot de passe

while [ -z "$user" ]
do
	user=$(whiptail --title "Connexion à la base de données" --inputbox "Veuillez saisir votre nom d'utilisateur relié à la base de données" 10 50 3>&1 1>&2 2>&3)
	if [ $? -ne 0 ]
	then
		echo "Vous avez quittez l'installation de ASTRE"
		exit -1
	fi
done

while [ -z "$pwd" ]
do
	pwd=$(whiptail --title "Connexion à la base de données" --passwordbox "Veuillez saisir votre mot de passe" 10 50 3>&1 1>&2 2>&3)
	if [ $? -ne 0 ]
	then
		echo "Vous avez quittez l'installation de ASTRE"
		exit -1
	fi
done

# Téléchargement du dossier final sur GitHub

if [ -d "SAE_3.01" ]; then
	rm -rf SAE_3.01
	mkdir SAE_3.01
fi

echo "Téléchargement du dossier GitHub..."
git clone https://github.com/IKLSI/SAE_3.01.git

if [ $? -eq 0 ]; then
	echo "Téléchargement réussi."
else
	echo "Échec du téléchargement. Veuillez vérifier votre connexion Internet ou l'URL du dépôt."
	exit -1
fi

install=$(pwd)/SAE_3.01

# Si le dossier config existe déjà, on le supprime puis on le crée à nouveau sinon on le crée directement

if [ -d "$install/config" ]; then
	rm -rf $install/config
	mkdir $install/config
else
	mkdir $install/config
fi

# Ajout du fichier login.dat dans le dossier config

echo "$user" > $install/config/login.data
echo "$pwd" >> $install/config/login.data

echo '\i SAE_3.01/sql/Astre.sql' | PGPASSWORD=$pwd psql -h woody -d $user -U $user
echo '\i SAE_3.01/sql/insert.sql' | PGPASSWORD=$pwd psql -h woody -d $user -U $user

# Téléchargement et placement de la bibliothèque JavaFX 17 dans le dossier lib

echo "Téléchargement de JavaFX 17..."
wget https://download2.gluonhq.com/openjfx/17.0.9/openjfx-17.0.9_linux-x64_bin-sdk.zip
unzip openjfx-17.0.9_linux-x64_bin-sdk.zip -d $install/lib

# Nettoyage des fichiers temporaires

rm openjfx-17.0.9_linux-x64_bin-sdk.zip

chmod +x $install/*.sh

touch $HOME/Bureau/ASTRE.desktop

# création du raccourci sur le bureau
echo "[Desktop Entry]" > $HOME/Bureau/ASTRE.desktop
echo "Type=Application" >> $HOME/Bureau/ASTRE.desktop
echo "Name=Astre" >> $HOME/Bureau/ASTRE.desktop
echo "Exec=$install/iut.sh" >> $HOME/Bureau/ASTRE.desktop
echo "Icon=$install/lib/img/LogoIUT.png" >> $HOME/Bureau/ASTRE.desktop
echo "Path=$install" >> $HOME/Bureau/ASTRE.desktop
echo "Terminal=false" >> $HOME/Bureau/ASTRE.desktop
echo "StartupNotify=false" >> $HOME/Bureau/ASTRE.desktop

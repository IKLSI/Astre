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

echo "Téléchargement du dossier GitHub..."
git clone https://github.com/IKLSI/SAE_3.01.git

if [ $? -eq 0 ]; then
    echo "Téléchargement réussi."
else
    echo "Échec du téléchargement. Veuillez vérifier votre connexion Internet ou l'URL du dépôt."
    exit -1
fi

install=$(pwd)/SAE_3.01

mkdir $install/config

# Ajout du fichier login.dat dans le dossier config

echo "$user" > $install/config/login.data
echo "$pwd" >> $install/config/login.data

touch $HOME/Bureau/ASTRE.desktop

# création du raccourci sur le bureau
echo "[Desktop Entry]" > $HOME/Bureau/Astre.desktop
echo "Type=Application" >> $HOME/Bureau/ASTRE.desktop
echo "Name=Astre" >> $HOME/Bureau/ASTRE.desktop
echo "Exec=$install/iut.sh" >> $HOME/Bureau/ASTRE.desktop
echo "Icon=$install/lib/LogoIUT.png" >> $HOME/Bureau/ASTRE.desktop
echo "Path=$install" >> $HOME/Bureau/ASTRE.desktop
echo "Terminal=false" >> $HOME/Bureau/ASTRE.desktop
echo "StartupNotify=false" >> $HOME/Bureau/ASTRE.desktop
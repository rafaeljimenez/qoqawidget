# Qoqa widget

Est un widget permettant d'afficher les offres du moment du site http://qoqa.ch

## Infos

* Version 0.1.0 (selon http://semver.org/) 
* Plateforme : Android
* IDE : Android Studio
* Documentation : Javadoc (disponible ici : http://rafaeljimenez.github.io/qoqawidgetdoc)

## Ce qui a été réalisé dans cette version 

* Téléchargement des offres du moment depuis l'api qoqa (http://www.qoqa.ch/api/v3/fr/shops/summary)
* Affichage des informations téléchargées
* Stockage des informations téléchargées
* Bouton d'actualisation
* Affichage de la date de la dernière actualisation
* Widget peut avoir plusieurs tailles
* Affichage de l'offre sur le site lorsque l'on click dessus

## Eléments à améliorer

* Affichage de messages d'erreur lorsqu'il n'y a pas de connexion internet par exemple
* Créer une base de donnée sqlite par défaut pour les données statiques
* Barrer une offre qui a été complétement vendue
* Afficher la barre de progression des achats
* Donner la possibilité de chosir les "shops" à afficher dans le widget

## Structure des fichiers

```
com.qoqa.widget
├─ activities
├─ helpers
├─ network
├─ models
├─ utils
└─ views
   └─ widgets
```


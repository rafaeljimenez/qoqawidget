# Qoqa widget

Est un widget permettant d'afficher les offres du moment du site http://qoqa.ch

## Infos

* Version 0.1.1 (selon http://semver.org/)
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
* Résolution d'un bug plusieurs widgets peuvent être affiché en même temp

## Eléments à améliorer

* Affichage de messages d'erreur lorsqu'il n'y a pas de connexion internet par exemple
* Créer une base de donnée sqlite par défaut pour les données statiques
* Barrer une offre qui a été complétement vendue
* Afficher la barre de progression des achats
* Donner la possibilité de chosir les "shops" à afficher dans le widget

## Unit Test

Sur Android il est possible de faire deux types de tests unitaires :

1. Les **Local tests**, sont des tests que nous pouvons exécuter directement sur l'ordinateur sans avoir besoin de téléphone ni d'émulateur. Ces tests ont l'avantage d'être exécutés rapidement mais ont le désavantage de ne pas pouvoir tester les composants du téléphone ce qui au final est logique.

      Les fichiers pour ces tests sont enregistrés [ici](/app/src/test/java/com/qoqa/widget)

      Le test réalisé dans cette catégorie pour l'app est le suivant :
      - Vérification que le formateur de date crée la date dans le bon format

2. Les **Instrumented tests**, sont des tests unitaires qui sont exécutés sur le téléphone protable. Ils sont plus lents que les precédants mais permettent de faire plus de choses. Avec ces tests nous allons pouvoir simuler des cliques sur les vues, créer des scénarios de navigations sur les vues ainsi que d'avoir accès aux composants du téléphone (ex: sauvegarde de données)

      Les fichiers pour ces tests sont enregistrés [ici](/app/src/androidTest/java/com/qoqa/widget)

      Les tests réalisés dans cette catégorie pour l'app sont les suivants :
      - Vérifier si la date du dernier rafraîchissement peut être enregistrée et lue
      - Vérifier si le texte de la première vue existe bien

## Structure des fichiers

Les fichiers interessants se trouvent [ici](/app/src/main/java/com/qoqa/widget)
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



# Rade - Referentiel Administrative

Le référentiel administratif a pour vocation de gérer les données attributaires sur :
* des données géographiques issues de l’INSEE : commune, département, région, pays,
* des données géographiques sur le bassin : circonscription de bassin

Ces informations sont ensuite accessibles par les autres applications du SI ainsi que par les utilisateurs.

Ne seront pas pris en compte les cantons, pseudo-canton, arrondissements et pays (utilisés dans Tiers pour les adresses). De même la cohérence avec les couches SIG fournies par l’IGN (des communes, départements, régions) n’est pas traitée ici. 

# Build

Le projet est compilé et packagé avec Apache Maven 3. Il suffit d'executer la commande suivante:
```sh
$ mvn install
```

# Run

Le projet est conçu autour des librairies Spring, en particulier Spring-Boot pour l'application web et Spring-Batch pour les batchs.

Pour lancer l'application web:
```sh
$ java -jar rade-webapp\target\rade-webapp.war
```

Pour lancer un batch:
```sh
$ java -jar rade-batch\target\rade-batch.jar -h
```

# TP2 - Camelot à vélo
---
## Informations
C'est un petit jeu créer dans le cadre du cours de programmation 420-SF3-RE du Collège Bois-de-Boulogne.

## But du jeu
Vous jouez un camelot qui livre des journaux et tente de faire le plus de profit.
Vous devez donc distribuer les journaux à la liste de maison donné.

Deux types d'objets sont intéractibles, les boîtes aux lettres et les fênetre.

### Boîtes aux lettres
![Boite aux lettres](src/main/resources/assets/boite-aux-lettres.png)
Lorsque vous distribuer un journal dans la boîte au lettre d'une maison abonné, votre patron vous verse une commition. Par contre rien ne vous est reversé si vous en remettez un à une maison non abonnée.
**Gare à votre nombre de journaux, ceux-ci sont limités...**

### Fenêtres
![Fenêtre](src/main/resources/assets/fenetre.png)

Si le journal touche la fenêtre d'une maison non abonné, alors votre employeur vous verceras de l'argent (les occupants de la maison n'avaient qu'à être abonné 😈).
Mais, **Attention, si vous casser une fenêtre et que la maison est présente dans la liste d'abonnés vous perdez de l'argent.**

###Particules chargées
 À partir du deuxième niveau, il y a des particules chargées qui affèctent la trajectoires des journaux

## Commandes

```→``` pour accélerer

```←``` pour ralentir

```↑``` ou ```espace``` pour sauter

```X``` pour lancer un journal vers l'avant

```Z``` pour lancer un journal vers le haut

```shift``` pour augmenter la force du lancé de journal


## Commandes autres

```Q``` permet de se rajouter 10 journaux

```D``` permet de passer en mode débuggage et d'affcher les hitboxes

```F``` permet d'ouvrir le débuggage 

```I``` mets toutes les particules chargées aux extrémités de l'écran pour débugger

```L``` permet de passer au prochain niveau

```K``` termine la partie actuelle



```P``` Petit Easter Egg pas totalement compléter par manque de temps






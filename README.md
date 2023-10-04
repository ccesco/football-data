# Football-data

## But du projet
Le projet a pour but de créer un écosystème de micro-services avec les différents éléments d'une infra micro-service (service-discovery, api-gateway, ...). 

Ce projet va mettre à disposition dans un premier temps d'API Rest pour récupérer les informations sur les équipes et les joueurs.

Je vais me baser sur les données du site TransfertMarkt qui est une référence dans le domaine.



## Commandes utiles

Pour démarrer toutes les applications via docker (force le build des images) :
```
 docker-compose up --build
```

## Choix techniques

Utilisation de Java comme language avec Spring Boot.

### Micro-service

Utilisation de la stack Netflix pour l'architecture micro-service car :
- c'est devenu natif avec Spring. 
- avec quelques annotations, on peut créer rapidement et facilement une architecture.
- l'architecture a été éprouvée depuis.

### Developpement en API-First

En suivant l'approche API-first, nous spécifions une API avant de commencer le dev. Grâce à la description de l'API, les équipes peuvent collaborer à définir le contrat d'interface sans avoir encore développer la moindre ligne de code.

On peut donc se concentrer plus sur l'API et ainsi créer de meilleures API, et qui répondent plus aux besoins des consommateurs.

Ces langages de description spécifient les endpoints, la sécurité, les DTO. De plus, la plupart du temps, nous pouvons générer ces éléments à partir du fichier de description.

Souvent, une spécification d'API devient également la documentation de l'API.

Les DTO ainsi que le controller est généré par maven afin de gagner du temps et d'être sur d'avoir la même interface qui va être consommé par les consommateurs.
On doit implémenter l'interface de délégation ainsi que redefinir les méthodes.

### Utilisation de l'archi hexagonale

Elle permet de séparer le domain métier des parties techniques de la partie User Side et de la partie Server Side.
Le domaine métier peut donc être appelé par plusieurs consommateurs comme des controller, des commands ou autre.
On peut aussi changer facilement et sans impact la base de données ou autres éléments du server side sans impacter le domaine métier.
Il ne faut pas mettre de framework dans le domaine métier afin d'être le plus décorréler de la technique.

C'est vrai que cette archi n'est pas faite pour tous les projets, ici ça peut être un peu overkill.

### Postgrespl via Docker

Avec Docker, le processus est automatisé, mais ça permet aussi de le rendre reproductible et portable.
Mais si le projet arrive en production, je ne pense pas l'utiliser de cette manière, dans ce cas c'est pour faciliter le développement.


## Documentation

https://medium.com/@tharanganilupul/microservices-implementation-netflix-stack-ba4f4a57a79f

https://github.com/felipeall/transfermarkt-api

https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.0.0.RELEASE/single/spring-cloud-netflix.html

https://cloud.spring.io/spring-cloud-netflix/reference/html/
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

Utilisation de la stack Netflix pour l'architecture micro-service car :
- c'est devenu natif avec Spring. 
- avec quelques annotations, on peut créer rapidement et facilement une architecture.
- l'architecture a été éprouvée depuis.


## Documentation

https://medium.com/@tharanganilupul/microservices-implementation-netflix-stack-ba4f4a57a79f

https://github.com/felipeall/transfermarkt-api

https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.0.0.RELEASE/single/spring-cloud-netflix.html

https://cloud.spring.io/spring-cloud-netflix/reference/html/
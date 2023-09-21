# Developpement en API-First 

En suivant l'approche API-first, nous spécifions une API avant de commencer le dev. Grâce à la description de l'API, les équipes peuvent collaborer à définir le contrat d'interface sans avoir encore développer la moindre ligne de code.

On peut donc se concentrer plus sur l'API et ainsi créer de meilleures API, et qui répondent plus aux besoins des consommateurs.

Ces langages de description spécifient les endpoints, la sécurité, les DTO. De plus, la plupart du temps, nous pouvons générer ces éléments à partir du fichier de description.

Souvent, une spécification d'API devient également la documentation de l'API.

Les DTO ainsi que le controller est généré par maven afin de gagner du temps et d'être sur d'avoir la même interface qui va être consommé par les consommateurs.
On doit implémenter l'interface de délégation ainsi que redefinir les méthodes.

# Utilisation de l'archi hexagonale

Elle permet de séparer le domain métier des parties techniques de la partie User Side et de la partie Server Side. 
Le domaine métier peut donc être appelé par plusieurs consommateurs comme des controller, des commands ou autre. 
On peut aussi changer facilement et sans impact la base de données ou autres éléments du server side sans impacter le domaine métier.
Il ne faut pas mettre de framework dans le domaine métier afin d'être le plus décorréler de la technique. 

C'est vrai que cette archi n'est pas faite pour tous les projets, ici ça peut être un peu overkill.


# Utilisation de MapStruct

On aurait pu utiliser MapStruct pour facilier les mapper, mais j'ai choisi d'avoir une main total sur le mapping. 
Si dans le futur beaucoup d'attribut sont identiques, je reverrai peut-être ma position pour ce projet.  
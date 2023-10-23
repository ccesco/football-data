# Init Service

Servi ce qui va lire une liste de code compétition afin d'initialiser les competitions, et les différentes équipes.

## Liste des compétitions

Elles sont dans les properties afin de pouvoir ajouter, modifier facilement

## Kafka idempotence du message / retry (à améliorer)

Pour éviter d'ajouter le topic des competitions si le code est déjà présent.
Retry max de 3

## Send message

Message envoyé dans le topic via KafkaTemplate qui est l'implémentation Spring de KafkaProducer.
Choix de réaliser un GET sur le ComparableFuture pour avoir le résultat, même si ce n'est pas le plus performant et le plus asynchrone.

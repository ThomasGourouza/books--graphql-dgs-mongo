command: gradle or ./gradlew

gradle build --refresh-dependencies
gradle clean build
gradle build
gradle build -x test
gradle bootRun




In schema, was ist der Unterschied zwischen .graphql und .graphqls ?



____________ Tips: ____________


query GetTest($x: String!) {
  monTest(input: $x)

  oneHello {
    ...allFields
  }
  allHellos {
    ...allFields
  }
}

fragment allFields on Hello {
  text
  randomNumber
}

_____________ variable _____________

{
  "x": "Voici le fameux texte"
}



------------------------------------------------------------------------


query fun($year: Int = 2015, $pri: Boolean) {
  monAutreTest(uneVariable: { year: $year, printedEdition: $pri })
}


_____________ variable _____________

{
  "pri": true
}


section 4, 23 -> filters

------------------------------------------------------------------------

query pets($petFilter: PetFilter) {
  pets(petFilter: $petFilter) {
    __typename
    name
    food

    ... on Dog {
      breed
      size
      coatLength
    }

    ... on Cat {
      breed
      registry
    }
  }
}

____________

{
  "petFilter": {
    "petType": "Cat"
  }
}

------------------------------------------------------------------------

mutation whatever($helloInput: HelloInput!) {
  addHello(helloInput: $helloInput)
}

____________

{
  "helloInput": {
    "number": 4,
    "newText": "text"
  }
}

------------------------------------------------------------------------

http://localhost:8082/endpoint?mandatoryParam=MandatoryParam&optionalParam=opt


query {
  additionalOnRequest
}

------------------------------------------------------------------------

subscription url: ws://localhost:8082/subscriptions

subscription getStockEveryInterval {
  randomStock {
    symbol
    price
  }
}


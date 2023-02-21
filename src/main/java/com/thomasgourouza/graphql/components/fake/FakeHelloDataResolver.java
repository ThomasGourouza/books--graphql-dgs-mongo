package com.thomasgourouza.graphql.components.fake;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.netflix.dgs.codegen.generated.types.Hello;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery
    public List<Hello> allHellos() {
        List<Hello> hellos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var hello = Hello.newBuilder().randomNumber(5)
                    .text("Voici un texte")
                    .build();
            hellos.add(hello);
        }
        return hellos;
    }

    @DgsQuery
    public Hello oneHello() {
        return allHellos().get(3);
    }

    @DgsQuery
    public String monTest(String input) {
        return "Vous avez écrit: '" + input + "'";
    }

}

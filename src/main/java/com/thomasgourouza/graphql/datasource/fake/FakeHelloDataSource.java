package com.thomasgourouza.graphql.datasource.fake;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.dgs.codegen.generated.types.Hello;

import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;

public class FakeHelloDataSource {

    @Autowired
    private Faker faker;

    public static final List<Hello> HELLO_LIST = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 20; i++) {
            var hello = Hello.newBuilder().randomNumber(faker.random().nextInt(5000))
                    .text(faker.company().name())
                    .build();
            HELLO_LIST.add(hello);
        }
    }
    
}

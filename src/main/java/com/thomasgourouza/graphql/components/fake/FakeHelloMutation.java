package com.thomasgourouza.graphql.components.fake;

import java.util.List;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.thomasgourouza.graphql.generated.DgsConstants;
import com.thomasgourouza.graphql.generated.types.Hello;
import com.thomasgourouza.graphql.generated.types.HelloInput;

@DgsComponent
public class FakeHelloMutation {

    // @DgsMutation
    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddHello)
    public Integer addHello(@InputArgument(name = "helloInput") HelloInput input) {
        var hello = Hello.newBuilder().text(input.getNewText())
            .randomNumber(input.getNumber()).build();
        var list = List.of(hello);
        return list.size();
    }

}

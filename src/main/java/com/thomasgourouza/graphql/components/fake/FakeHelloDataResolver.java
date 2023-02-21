package com.thomasgourouza.graphql.components.fake;

import java.util.ArrayList;
import java.util.List;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.thomasgourouza.graphql.generated.DgsConstants;
import com.thomasgourouza.graphql.generated.types.Hello;
import com.thomasgourouza.graphql.generated.types.ReleaseHistoryInput;

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

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.MonTest)
    public String myTestWithInput(@InputArgument(name = "input") String text) {
        return "Vous avez écrit: '" + text + "'";
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.MonAutreTest)
    public String getMonAutreTest(@InputArgument(name = "uneVariable") ReleaseHistoryInput release) {
        return release.toString();
    }

    // @DgsData(
    // parentType = DgsConstants.QUERY_TYPE,
    // field = DgsConstants.QUERY.MonAutreTest
    // )
    // public String getMonAutreTest(DataFetchingEnvironment
    // dataFetchingEnvironment) {
    // Map<String, Object> map = dataFetchingEnvironment.getArgument("uneVariable");
    // var input = ReleaseHistoryInput.newBuilder().printedEdition((boolean)
    // map.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
    // .year((int) map.get(DgsConstants.RELEASEHISTORYINPUT.Year))
    // .build();

    // ReleaseHistory value = new ReleaseHistory();
    // value.setPrintedEdition(false);
    // value.setReleasedCountry("France");
    // value.setYear(2020);
    // if (this.matchReleaseHistory(input, value)) {
    // return "Bingo !";
    // }
    // return "Et non !";
    // }

    // private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory
    // element) {
    // return input.getPrintedEdition().equals(element.getPrintedEdition())
    // && input.getYear() == element.getYear();
    // }

}

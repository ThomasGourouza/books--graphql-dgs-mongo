package com.thomasgourouza.graphql.components.fake;

import java.util.List;
import java.util.Optional;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.thomasgourouza.graphql.generated.DgsConstants;
import com.thomasgourouza.graphql.generated.types.SmartSearchResult;

@DgsComponent
public class FakeSmartDataResolver {

    @DgsData(
      parentType = DgsConstants.QUERY_TYPE,
      field = DgsConstants.QUERY.SmartSearch
  )
  public List<SmartSearchResult> getSmartSearch(@InputArgument(name = "keyword") Optional<String> keyword) {
    return null;
  }

}

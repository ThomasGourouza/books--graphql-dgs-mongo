package com.thomasgourouza.graphql.resolvers.fake;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
// import com.netflix.graphql.dgs.DgsSubscription;
import com.thomasgourouza.graphql.datasource.fake.FakeStockDataSource;
import com.thomasgourouza.graphql.generated.DgsConstants;
import com.thomasgourouza.graphql.generated.types.Stock;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DgsComponent
public class FakeStockDataResolver {

    @Autowired
    private FakeStockDataSource dataSource;

//    @DgsSubscription
    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = DgsConstants.SUBSCRIPTION.RandomStock)
    public Publisher<Stock> randomStock() {
        return Flux.interval(Duration.ofSeconds(3)).map(t -> dataSource.randomStock());
    }

}

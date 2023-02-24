package com.thomasgourouza.graphql.resolvers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.thomasgourouza.graphql.generated.DgsConstants;
import com.thomasgourouza.graphql.generated.types.Address;
import com.thomasgourouza.graphql.generated.types.Author;
import com.thomasgourouza.graphql.generated.types.Book;
import com.thomasgourouza.graphql.generated.types.BookInput;
import com.thomasgourouza.graphql.generated.types.ReleaseHistory;
import com.thomasgourouza.graphql.services.BookService;

@DgsComponent
public class BookMutation {

    @Autowired
    private BookService bookService;

    // @DgsMutation
    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddBook)
    public Book addBook(@InputArgument(name = "bookInput") BookInput input) {
        List<Address> addresses = input.getAuthor().getAddresses().stream().map(addressInput ->
            Address.newBuilder()
                .street(addressInput.getStreet())
                .city(addressInput.getCity())
                .zipCode(addressInput.getZipCode())
                .country(addressInput.getCountry())
                .build()
        ).collect(Collectors.toList());

        var author = Author.newBuilder()
            .name(input.getAuthor().getName())
            .originCountry(input.getAuthor().getOriginCountry())
            .addresses(addresses)
            .build();

        var released = ReleaseHistory.newBuilder()
            .year(input.getReleased().getYear())
            .printedEdition(input.getReleased().getPrintedEdition())
            .releasedCountry(input.getReleased().getReleasedCountry())
            .build();
        
        var book = Book.newBuilder()
            .title(input.getTitle())
            .publisher(input.getPublisher())
            .author(author)
            .released(released)
            .build();

        return bookService.createBook(book);
    }

}

package com.thomasgourouza.graphql.resolvers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.thomasgourouza.graphql.generated.DgsConstants;
import com.thomasgourouza.graphql.generated.types.Address;
import com.thomasgourouza.graphql.generated.types.Author;
import com.thomasgourouza.graphql.generated.types.AuthorInput;
import com.thomasgourouza.graphql.generated.types.Book;
import com.thomasgourouza.graphql.generated.types.BookInput;
import com.thomasgourouza.graphql.generated.types.ReleaseHistory;
import com.thomasgourouza.graphql.generated.types.ReleaseHistoryInput;
import com.thomasgourouza.graphql.services.BookService;

@DgsComponent
public class BookMutation {

    @Autowired
    private BookService bookService;

    // @DgsMutation
    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddBook)
    public Book addBook(@InputArgument(name = "bookInput") BookInput input) {
        var book = Book.newBuilder()
            .title(input.getTitle())
            .publisher(input.getPublisher())
            .author(buildAuthor(input.getAuthor()))
            .released(buildRelease(input.getReleased()))
            .build();

        return bookService.createBook(book);
    }

    // @DgsMutation
    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UpdateBook)
    public Book updateBook(@InputArgument(name = "id") String id, @InputArgument(name = "bookInput") BookInput input) {
        Book book = bookService.getBook(id);
        if (book == null) {
            return null;
        }
        book.setTitle(input.getTitle());
        book.setPublisher(input.getPublisher());
        book.setAuthor(buildAuthor(input.getAuthor()));
        book.setReleased(buildRelease(input.getReleased()));

        return bookService.createBook(book);
    }

    // @DgsMutation
    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.DeleteBookById)
    public Boolean deleteBook(@InputArgument(name = "id") String id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            return false;
        }
        bookService.removeBook(id);

        return true;
    }

    private Author buildAuthor(AuthorInput authorInput) {
        List<Address> addresses = authorInput.getAddresses().stream().map(addressInput ->
            Address.newBuilder()
                .street(addressInput.getStreet())
                .city(addressInput.getCity())
                .zipCode(addressInput.getZipCode())
                .country(addressInput.getCountry())
                .build()
        ).collect(Collectors.toList());

        return Author.newBuilder()
            .name(authorInput.getName())
            .originCountry(authorInput.getOriginCountry())
            .addresses(addresses)
            .build();
    }

    private ReleaseHistory buildRelease(ReleaseHistoryInput releaseHistoryInput) {
        return ReleaseHistory.newBuilder()
            .year(releaseHistoryInput.getYear())
            .printedEdition(releaseHistoryInput.getPrintedEdition())
            .releasedCountry(releaseHistoryInput.getReleasedCountry())
            .build();
    }
}

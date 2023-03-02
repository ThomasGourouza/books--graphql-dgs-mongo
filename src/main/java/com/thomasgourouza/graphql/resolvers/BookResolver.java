package com.thomasgourouza.graphql.resolvers;

import com.thomasgourouza.graphql.datasource.fake.FakeBookDataSource;
import com.thomasgourouza.graphql.generated.DgsConstants;
import com.thomasgourouza.graphql.generated.types.Book;
import com.thomasgourouza.graphql.generated.types.ReleaseHistory;
import com.thomasgourouza.graphql.generated.types.ReleaseHistoryInput;
import com.thomasgourouza.graphql.services.BookService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class BookResolver {

        @Autowired
        private BookService bookService;

        @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Books)
        public List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String> authorName) {

                return bookService.getAllBooks();

                // if (authorName.isEmpty() || StringUtils.isBlank(authorName.get())) {
                //         return FakeBookDataSource.BOOK_LIST;
                // }

                // return FakeBookDataSource.BOOK_LIST.stream()
                //                 .filter(b -> StringUtils.containsIgnoreCase(
                //                                 b.getAuthor().getName(), authorName.get()))
                //                 .collect(Collectors.toList());
        }

        @DgsData(parentType = DgsConstants.QUERY_TYPE,  field = DgsConstants.QUERY.BookById)
        public Book getBookById(@InputArgument(name = "id") String id) {
                return bookService.getBook(id);
        }

        @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.BooksByReleased)
        public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
                @SuppressWarnings("unchecked")
                var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");
                var releasedInput = ReleaseHistoryInput.newBuilder()
                                .printedEdition((boolean) releasedMap
                                                .get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                                .year((int) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                                .build();

                return FakeBookDataSource.BOOK_LIST.stream().filter(
                                b -> this.matchReleaseHistory(releasedInput, b.getReleased()))
                                .collect(Collectors.toList());
        }

        private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory element) {
                return input.getPrintedEdition().equals(element.getPrintedEdition())
                                && input.getYear() == element.getYear();
        }

}

package com.thomasgourouza.graphql.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomasgourouza.graphql.generated.types.Book;
import com.thomasgourouza.graphql.repositories.BookRepository;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public void removeBook(String id) {
        bookRepository.deleteById(id);
    }

    public void removeAllBooks() {
        bookRepository.deleteAll();
    }

}

package com.thomasgourouza.graphql.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thomasgourouza.graphql.generated.types.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}

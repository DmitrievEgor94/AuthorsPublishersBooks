package com.mycompany.books_authors_publishers.entities.restorators;

import com.mycompany.books_authors_publishers.Author;
import com.mycompany.books_authors_publishers.entities.AuthorEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorsRestorator {
    static public List<Author> getListOfAuthors(List<AuthorEntity> entities) {
        return entities.stream()
                .map(AuthorsRestorator::getAuthor)
                .collect(Collectors.toList());
    }

    static private Author getAuthor(AuthorEntity entity) {
        return new Author(entity.getName(), entity.getDayOfBirthday(), entity.getDayOfDeath(), entity.getSex());
    }
}

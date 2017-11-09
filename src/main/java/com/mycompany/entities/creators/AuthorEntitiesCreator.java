package com.mycompany.entities.creators;

import com.mycompany.models.Author;
import com.mycompany.entities.AuthorEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AuthorEntitiesCreator {
    static public List<AuthorEntity> getListAuthorEntities(List<Author> authors) {

        if (authors == null) return null;

        AtomicInteger counter = new AtomicInteger(0);

        return authors.stream()
                .map(author -> new AuthorEntity(author, counter.incrementAndGet()))
                .collect(Collectors.toList());
    }
}

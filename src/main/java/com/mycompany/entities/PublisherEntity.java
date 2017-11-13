package com.mycompany.entities;

import com.mycompany.models.Publisher;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PublisherEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private List<Integer> books;

    PublisherEntity(Publisher publisher, Map<String, Integer> mapWithBooksTitles, int id) {
        this.name = publisher.getName();
        this.books = publisher.getBooks().stream()
                .map(b -> mapWithBooksTitles.get(b.getTitle()))
                .collect(Collectors.toList());
    }

    public PublisherEntity(String name, List<Integer> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getBooksId() {
        return books;
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        PublisherEntity entity = (PublisherEntity) obj;

        return Objects.equals(name, entity.name)
                && Objects.equals(books, entity.books);

    }
}

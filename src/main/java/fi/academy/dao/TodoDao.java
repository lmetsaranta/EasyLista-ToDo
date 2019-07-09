package fi.academy.dao;

import fi.academy.Todo;

import java.util.List;

public interface TodoDao {
    List<Todo> haeKaikki();
    int lisaa(Todo todo);
    Todo poista(int id);
}

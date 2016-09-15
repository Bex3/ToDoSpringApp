package com.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by bearden-tellez on 9/15/16.
 */
public interface ToDoRepository extends CrudRepository<ToDoItem, Integer> {
    List<ToDoItem> findByUser(User user);
}

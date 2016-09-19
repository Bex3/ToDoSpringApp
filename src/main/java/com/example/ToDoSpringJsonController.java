package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

/**
 * Created by bearden-tellez on 9/19/16.
 */
public class ToDoSpringJsonController {
    @Autowired
    ToDoRepository todos;

    @RequestMapping(path = "/todos.json", method = RequestMethod.GET)
    public ArrayList<ToDoItem> jsonHome(String todotext, Boolean isDone, User user) {
        ArrayList<ToDoItem> todoList = new ArrayList<ToDoItem>();
        Iterable<ToDoItem> allToDoItems = todos.findAll(); // hibernate (object relational mapping) uses the repo
        for (ToDoItem item : allToDoItems) {
            todoList.add(item);
        }

        return todoList; // returns an object instead of a view b/c this is a restful webservice - only gives data
    }




}

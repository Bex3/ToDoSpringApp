package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by bearden-tellez on 9/19/16.
 */


@RestController
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

    ArrayList<ToDoItem> getToDos() {
        ArrayList<ToDoItem> todoList = new ArrayList<ToDoItem>();
        Iterable<ToDoItem> allGames = todos.findAll();
        for (ToDoItem item : allGames) {
            todoList.add(item);
        }

        return todoList;
    }
    @RequestMapping(path = "/addToDo.json", method = RequestMethod.POST) //post & rest combined means that we take the game signaled at the rest controller and turn it into a java object
    public ArrayList<ToDoItem> addToDoItem(HttpSession session, @RequestBody ToDoItem todoitem) throws Exception {
        User user = (User)session.getAttribute("user");

        if (user == null) {
            throw new Exception("Unable to add game without an active user in the session");
        }
        todoitem.user = user;


        System.out.println("this is what I'm souting" + todoitem);
        System.out.println(todoitem.todotext);
        System.out.println(todoitem.isDone);

        todos.save(todoitem);

        return getToDos();
    }
}

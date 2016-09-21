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

    User user;

    @RequestMapping(path = "/todos.json", method = RequestMethod.GET)
    public ArrayList<ToDoItem> todosjson(HttpSession session) {
        user = (User)session.getAttribute("user");
        ArrayList<ToDoItem> todoList = new ArrayList<ToDoItem>();
        Iterable<ToDoItem> allToDoItems = todos.findAll(); // hibernate (object relational mapping) uses the repo


        for (ToDoItem item : allToDoItems) {
            todoList.add(item);
        }



        return getToDos(); // returns an object instead of a view b/c this is a restful webservice - only gives data
    }

    ArrayList<ToDoItem> getToDos() {
        ArrayList<ToDoItem> todoList = new ArrayList<ToDoItem>();
        Iterable<ToDoItem> allTodos = todos.findByUser(user);
//        for (ToDoItem item : allTodos) {
//            todoList.add(item);
//        }

        if (user != null){
            for (ToDoItem item : allTodos) {
                todoList.add(item);
            }
        }
        return todoList;
    }



    @RequestMapping(path = "/addToDo.json", method = RequestMethod.POST) //post & rest combined means that we take the game signaled at the rest controller and turn it into a java object
    public ArrayList<ToDoItem> addToDoItem(HttpSession session, @RequestBody ToDoItem todoitem) throws Exception {
        User user = (User)session.getAttribute("user");

        if (user == null) {
            throw new Exception("Unable to add a todo without an active user in the session");
        }
        todoitem.user = user;


        System.out.println("this is what I'm souting" + todoitem);
        System.out.println(todoitem.todotext);
        System.out.println(todoitem.isDone);

        todos.save(todoitem);

        return getToDos();
    }

    @RequestMapping(path = "/toggleToDo.json", method = RequestMethod.GET)
    public ArrayList<ToDoItem> toggleTodo(int todoID) {
        System.out.println("toggling todo with ID " + todoID);
        ToDoItem todo = todos.findOne(todoID);
        todo.isDone = !todo.isDone;
        todos.save(todo);


        return getToDos();
    }

    @RequestMapping(path = "/deleteToDoItem", method = RequestMethod.GET)
    public ArrayList<ToDoItem> deleteTodo(int todoID) {
        System.out.println("Deleting todo with ID " + todoID);
        ToDoItem todo = todos.findOne(todoID);
        todos.delete(todo);

        return getToDos();
    }
}

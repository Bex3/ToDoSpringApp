package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bearden-tellez on 9/15/16.
 */
@Controller
public class ToDoSpringController {
    @Autowired
    ToDoRepository todos;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            session.invalidate();
//            user = new User(userName, password);
//            users.save(user);
        } else if (!password.equals(user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("user", user);
        return "redirect:/todos";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String todotext, Boolean isdone) {

        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        List<ToDoItem> toDoItemList = new ArrayList<>();

            User savedUser = (User)session.getAttribute("user");
            if (savedUser != null) {
                toDoItemList = todos.findByUser(savedUser);
            } else {
                Iterable<ToDoItem> allItems = todos.findAll();
                for (ToDoItem thisItem : allItems) {
                    toDoItemList.add(thisItem);
                }
            }
        model.addAttribute("todos",toDoItemList);
        return "home";
    }

    @RequestMapping(path = "/addtodos", method = RequestMethod.POST)
    public String addToDoText(HttpSession session, String todotext, Boolean isDone) {
        User user = (User) session.getAttribute("user");
        ToDoItem todo = new ToDoItem(todotext, isDone, user);
        System.out.println("My runtime repo: " + todo.toString());
        todos.save(todo); //uses the repo to save it
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deletetodo(Model model, Integer todoID) {
        if (todoID != null) {
            todos.delete(todoID);
        }

        return "redirect:/";
    }
    @RequestMapping(path = "/todos", method = RequestMethod.GET)
    public String todo(String todotext, Boolean isDone) {
        return "todos";
    }

}

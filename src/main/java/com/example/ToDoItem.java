package com.example;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

/**
 * Created by bearden-tellez on 9/15/16.
 */

@Entity
@Table(name = "todos")

public class ToDoItem {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false)
    String todotext;

    @Column(nullable = false)
    boolean isDone;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTodotext() {
        return todotext;
    }

    public void setTodotext(String todotext) {
        this.todotext = todotext;
    }

//    public Boolean getDone() {
//        return isDone;
//    }
//
//    public void setDone(Boolean done) {
//        isDone = done;
//    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public ToDoItem(String todotext, boolean isDone, User user) {
        System.out.println("building a new todoitem with text = " + todotext);
        this.todotext = todotext;
        this.isDone = isDone;
        this.user = user;
    }

    public ToDoItem() {
    }
}
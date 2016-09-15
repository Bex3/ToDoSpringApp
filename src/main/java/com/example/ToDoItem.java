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
    Boolean isDone;


    public ToDoItem(String todotext, boolean isDone) {
        System.out.println("building a new todoitem with text = " + todotext);
        this.todotext = todotext;
        this.isDone = isDone;
    }

    public ToDoItem() {
    }
}
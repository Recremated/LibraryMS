package com.librarysystem.model;

import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public abstract void whoYouAre();
}
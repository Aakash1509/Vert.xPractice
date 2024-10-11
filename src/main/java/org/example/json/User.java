package org.example.json;

public class User
{
    private String name;
    private int age;

    public User(){}

    public User(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    public String getName()
    {
        return name;
    }
}

package org.example.json;

import io.vertx.core.json.JsonObject;

public class RunUser
{
    public static void main(String[] args)
    {
        User user =  new User("Aakash",21);

        //Convert user object to json object
        JsonObject jsonObject = JsonObject.mapFrom(user);

        System.out.println("User as JSON : "+jsonObject.encodePrettily());

        //Convert jsonObject back to User object
        User mappedUser = jsonObject.mapTo(User.class);

        System.out.println(mappedUser.getName()+" "+mappedUser.getAge());
    }
}

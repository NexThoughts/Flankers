package com.model

import io.vertx.core.json.JsonObject

class User {
    String uuid
    String dateCreated


    String id
    String email
    String password
    String firstname
    String lastname
    String dateOfBirth
    String location
    String gender

    User() {
        this.id = ""
    }

    User(String email, String password, String firstname, String lastname, String dateOfBirth, String location, String gender) {
        this.email = email
        this.password = password
        this.firstname = firstname
        this.lastname = lastname
        this.dateOfBirth = dateOfBirth
        this.location = location
        this.gender = gender
        this.id = ""
        this.dateCreated = new Date()
        this.uuid = UUID.randomUUID().toString()
    }

    User(JsonObject object) {
        this.email = object.getString("email")
        this.password = object.getString("password")
        this.firstname = object.getString("firstname")
        this.lastname = object.getString("lastname")
        this.dateOfBirth = dateOfBirth
        this.location = object.getString("location")
        this.gender = object.getString("gender")
        this.dateCreated = object.getString("dateCreated")
        this.uuid = object.getString("uuid")
    }

    User(String id, String email, String password, String firstname, String lastname, String dateOfBirth, String location, String gender) {
        this.id = id
        this.email = email
        this.password = password
        this.firstname = firstname
        this.lastname = lastname
        this.dateOfBirth = dateOfBirth
        this.location = location
        this.gender = gender
        this.dateCreated = new Date()
        this.uuid = UUID.randomUUID().toString()


    }

    JsonObject jsonObject() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("firstname", firstname)
        jsonObject.put("dateOfBirth", dateOfBirth)
        jsonObject.put("location", location)
        jsonObject.put("gender", gender)
        jsonObject.put("uuid", uuid)

        if (id) {
            jsonObject.put("_id", id)
        }

        return jsonObject
    }

}
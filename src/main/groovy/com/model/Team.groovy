package com.model

import io.vertx.core.json.JsonObject

class Team {

    String dateCreated
    String uuid

    String id

    String name
    List<String> users

    Team() {
        this.id = ""
    }

    void addUser(String userId, JsonObject object) {
        this.users = object.getJsonArray("users") as List<String>
        this.users.add(userId)
    }

    Team(String name, List<String> users) {
        this.name = name
        this.users = users
        this.id = ""
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }

    Team(JsonObject object) {
        this.users = object.getJsonArray("users") as List<String>
        this.name = object.getString("name")
        this.dateCreated = object.getString("dateCreated")
        this.uuid = object.getString("uuid")

    }

    Team(String id, String name, List<String> users) {
        this.id = id
        this.name = name
        this.users = users
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }


    JsonObject jsonObject() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.put("users", users.toArray())
        jsonObject.put("name", name)
        jsonObject.put("uuid", uuid)
        jsonObject.put("dateCreated", dateCreated)

        if (id) {
            jsonObject.put("_id", id)
        }

        return jsonObject
    }

}
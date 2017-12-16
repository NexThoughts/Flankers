package com.model

import io.vertx.core.json.JsonObject

class Comment {

    String dateCreated
    String uuid

    String id

    String message
    String createdBy
    String todoId

    Comment() {
        this.id = ""
    }

    Comment(String message, String createdBy, String todoId) {
        this.message = message
        this.createdBy = createdBy
        this.todoId = todoId
        this.id = ""
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }

    Comment(JsonObject object) {
        this.createdBy = object.getString("createdBy")
        this.message = object.getString("message")
        this.todoId = object.getString("todoId")
        this.dateCreated = object.getString("dateCreated")
        this.uuid = object.getString("uuid")

    }

    Comment(String id, String message, String createdBy, String todoId) {
        this.message = message
        this.createdBy = createdBy
        this.todoId = todoId
        this.id = id
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }

    JsonObject jsonObject() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.put("createdBy", createdBy)
        jsonObject.put("message", message)
        jsonObject.put("todoId", todoId)
        jsonObject.put("uuid", uuid)
        jsonObject.put("dateCreated", dateCreated)

        if (id) {
            jsonObject.put("_id", id)
        }

        return jsonObject
    }

}
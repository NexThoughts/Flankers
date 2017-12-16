package com.model

import io.vertx.core.json.JsonObject

class Todo {
    String dateCreated
    String uuid

    String id

    String title
    String createdBy
    String assignedTo

    String dateOfCompletion
    String deadLineDate

    Todo() {
        this.id = ""
    }

    Todo(String title, String createdBy, String assignedTo, String dateOfCompletion, String deadLineDate) {
        this.title = title
        this.createdBy = createdBy
        this.assignedTo = assignedTo
        this.dateOfCompletion = dateOfCompletion
        this.deadLineDate = deadLineDate
        this.id = ""
        this.uuid = UUID.randomUUID().toString()
    }

    Todo(JsonObject object) {
        this.title = object.getString("title")
        this.createdBy = object.getString("createdBy")
        this.assignedTo = object.getString("assignedTo")
        this.dateOfCompletion = object.getString("dateOfCompletion")
        this.deadLineDate = object.getString("deadLineDate")
        this.uuid = object.getString("uuid")
        this.dateCreated = object.getString("dateCreated")
        this.uuid = object.getString("uuid")

    }

    Todo(String id, String title, String createdBy, String assignedTo, String dateOfCompletion, String deadLineDate) {
        this.id = id
        this.title = title
        this.createdBy = createdBy
        this.assignedTo = assignedTo
        this.dateOfCompletion = dateOfCompletion
        this.deadLineDate = deadLineDate
        this.uuid = UUID.randomUUID().toString()

    }

    JsonObject jsonObject() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.put("title", title)
        jsonObject.put("createdBy", createdBy)
        jsonObject.put("assignedTo", assignedTo)
        jsonObject.put("deadLineDate", deadLineDate)
        jsonObject.put("uuid", uuid)
        jsonObject.put("dateCreated", dateCreated)

        if (id) {
            jsonObject.put("_id", id)
        }

        return jsonObject
    }

}
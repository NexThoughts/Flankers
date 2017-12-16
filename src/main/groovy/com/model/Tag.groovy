package com.model

import io.vertx.core.json.JsonObject

class Tag {

    String dateCreated
    String uuid = UUID.randomUUID().toString()

    String id

    String name
    String createdBy

    Tag() {
        this.id = ""
    }

    Tag(String name, String createdBy) {
        this.name = name
        this.createdBy = createdBy
        this.id = ""
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }

    Tag(JsonObject object) {
        this.createdBy = object.getString("createdBy")
        this.name = object.getString("name")
        this.dateCreated = object.getString("dateCreated")
        this.uuid = object.getString("uuid")

    }

    Tag(String id, String name, String createdBy) {
        this.name = name
        this.createdBy = createdBy
        this.id = id
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }


    JsonObject jsonObject() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.put("createdBy", createdBy)
        jsonObject.put("name", name)
        jsonObject.put("uuid", uuid)
        jsonObject.put("dateCreated", dateCreated)

        if (id) {
            jsonObject.put("_id", id)
        }

        return jsonObject
    }

}
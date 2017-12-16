package com.model

import com.utils.Enums
import io.vertx.core.json.JsonObject

class Notification {

    String dateCreated
    String uuid

    String id

    String type
    String message
    String emailSubject
    String sendTo
    String status
    String event


    Enums.NotificationType getNotificationType() {
        Enums.NotificationType.valueOf(this.type)
    }

    Enums.NotificationStatus getNotificationStatus() {
        Enums.NotificationStatus.valueOf(this.status)
    }

    Notification() {
        this.id = ""
    }

    Notification(String type, String event, String message, String emailSubject, String sendTo, String status) {
        this.type = type
        this.event = event
        this.message = message
        this.emailSubject = emailSubject
        this.sendTo = sendTo
        this.status = status
        this.id = ""
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }

    Notification(JsonObject object) {
        this.type = object.getString("type")
        this.message = object.getString("message")
        this.emailSubject = object.getString("emailSubject")
        this.sendTo = object.getString("sendTo")
        this.status = object.getString("status")


        this.dateCreated = object.getString("dateCreated")
        this.uuid = object.getString("uuid")

    }

    Notification(String id, String type, String event, String message, String emailSubject, String sendTo, String status) {
        this.id = id
        this.type = type
        this.event = event
        this.message = message
        this.emailSubject = emailSubject
        this.sendTo = sendTo
        this.status = status
        this.id = ""
        this.uuid = UUID.randomUUID().toString()
        this.dateCreated = new Date()

    }

    JsonObject jsonObject() {
        JsonObject jsonObject = new JsonObject()

        jsonObject.put("type", type)
        jsonObject.put("message", message)
        jsonObject.put("emailSubject", emailSubject)
        jsonObject.put("sendTo", sendTo)
        jsonObject.put("status", status)

        jsonObject.put("uuid", uuid)
        jsonObject.put("dateCreated", dateCreated)

        if (id) {
            jsonObject.put("_id", id)
        }

        return jsonObject
    }

}
package com.utils


class Enums {


    public enum NotificationStatus {
        SENT, PENDING, FAILED
    }

    public enum NotificationType {
        SMS,EMAIL
    }

    public enum NotificationEvent {
        USER_SIGNUP
    }


    public enum test {
        XYZ('xyz')

        public String name

        public test(String name) {
            this.name = name
        }
    }

}

package com.utils


class Enums {


    public enum NotificationStatus {
        SENT, PENDING, FAILED
    }

    public enum NotificationType {
        SMS,EMAIL
    }

    public enum EmailCategory {
        REGISTRATION
    }

    public enum test {
        XYZ('xyz')

        public String name

        public test(String name) {
            this.name = name
        }
    }

}

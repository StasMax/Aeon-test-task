package org.example.common;

public interface Urls {
    String VERSION = "v1";
    String ROOT = "/api/" + VERSION;

    String LOGIN = "/login";
    String LOGOUT = "/logout";
    String REGISTRATION = "/registration";

    interface Users {
        String ALL = ROOT + "/users";
    }

    interface Payments {
        String ALL = ROOT + "/payments";
    }

}

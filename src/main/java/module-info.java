module at.gotzi.itsmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires eu.mihosoft.monacofx;
    requires javafx.web;
    requires json.simple;

    exports at.gotzi.itsmanager.controller.panelcontroll;
    exports at.gotzi.itsmanager.handler;
    exports at.gotzi.itsmanager.helper;
    exports at.gotzi.itsmanager;
    opens at.gotzi.itsmanager.controller to javafx.fxml;
    opens at.gotzi.itsmanager.controller.panelcontroll to javafx.fxml;
    opens at.gotzi.itsmanager to javafx.fxml;
    exports at.gotzi.itsmanager.helper.controller;
    exports at.gotzi.itsmanager.api;
    exports at.gotzi.itsmanager.api.node;
    exports at.gotzi.itsmanager.view;
    exports at.gotzi.itsmanager.api.workspace;
    exports at.gotzi.itsmanager.api.workspace.question;
    opens at.gotzi.itsmanager.helper.controller to javafx.fxml;
}
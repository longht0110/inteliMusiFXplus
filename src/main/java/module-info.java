module org.intelimusi.intelimusifxplus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires javafx.media;
    requires java.net.http;

    opens org.intelimusi.intelimusifxplus to javafx.fxml;
    exports org.intelimusi.intelimusifxplus;
}
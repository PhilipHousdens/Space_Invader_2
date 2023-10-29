module com.opcode.spaceinvader2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.apache.logging.log4j;
    requires javafx.media;

    opens com.opcode.spaceinvader2 to javafx.fxml;
    exports com.opcode.spaceinvader2;
}
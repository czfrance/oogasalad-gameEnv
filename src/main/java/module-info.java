open module oogasalad_app {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
    requires org.json;
    //requires java.datatransfer;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires io.vavr;
    requires org.jooq.jool;

    // allow other classes to access listed packages in your project
    exports oogasalad;
}

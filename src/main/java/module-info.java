module gr.azormpas.cn5004 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.jetbrains.annotations;

    exports gr.azormpas.cn5004;
    opens gr.azormpas.cn5004 to javafx.fxml;
    exports gr.azormpas.cn5004.model;
    exports gr.azormpas.cn5004.controller;
    opens gr.azormpas.cn5004.controller to javafx.fxml;
    exports gr.azormpas.cn5004.controller.product;
    opens gr.azormpas.cn5004.controller.product to javafx.fxml;
    exports gr.azormpas.cn5004.controller.purchase;
    opens gr.azormpas.cn5004.controller.purchase to javafx.fxml;
    exports gr.azormpas.cn5004.controller.user;
    opens gr.azormpas.cn5004.controller.user to javafx.fxml;
}

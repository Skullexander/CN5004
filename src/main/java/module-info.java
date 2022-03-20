module gr.azormpas.cn5004 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens gr.azormpas.cn5004 to javafx.fxml;
    exports gr.azormpas.cn5004;
}

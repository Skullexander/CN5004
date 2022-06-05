package gr.azormpas.cn5004.controller.purchase;

import gr.azormpas.cn5004.Main;
import gr.azormpas.cn5004.model.Purchase;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;

public class ListController
{
    @FXML
    private TableView<Purchase> table;
    @FXML
    private TableColumn<Purchase, String> tableID, tableName, tableDate, tableCost, tableState;
    @FXML
    private TableColumn<Purchase, Purchase> tableItems;
    @FXML
    private Button btnAdd;

    public void initialize()
    {
        ObservableList<Purchase> list = FXCollections.observableArrayList();
        setCellValues();
        if (Main.data.getLoggedUserType() == 1)
        {
            setStateIncrement();
            btnAdd.setDisable(true);
        }
        list.addAll(Main.data.getPurchases());
        table.setItems(list);
    }

    private void setCellValues()
    {
        tableID.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%d",cellData.getValue().getID())));
        tableName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        tableDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        tableItems.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        tableCost.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f €", cellData.getValue().getCost())));
        tableState.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        tableItems.setCellFactory(cellData -> new TableCell<>()
                                  {
                                      private final Label itemLabel = new Label();

                                      @Override
                                      protected void updateItem(Purchase purchase, boolean empty)
                                      {
                                          super.updateItem(purchase, empty);

                                          if (purchase == null)
                                          {
                                              setGraphic(null);
                                              return;
                                          }


                                          Tooltip tooltip = new Tooltip(purchase.toString());
                                          tooltip.setShowDelay(Duration.millis(2));
                                          itemLabel.setText(purchase.getTotalItems());
                                          itemLabel.setTooltip(tooltip);
                                          setGraphic(itemLabel);
                                      }
                                  });
    }

    private void setStateIncrement()
    {
        TableColumn<Purchase, Purchase> tableOptions = new TableColumn<>("Options");
        tableOptions.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        tableOptions.setCellFactory(cellData -> new TableCell<>()
        {
            private final Button btnDec = new Button("➖");
            private final Button btnInc = new Button("➕");
            private final HBox box = new HBox(btnInc, btnDec);

            @Override
            protected void updateItem(Purchase purchase, boolean empty)
            {
                super.updateItem(purchase, empty);

                if (purchase == null)
                {
                    setGraphic(null);
                    return;
                }

                btnInc.setOnAction(event ->
                                   {
                                       purchase.updateStatus("+");
                                       table.refresh();
                                   });
                btnDec.setOnAction(event ->
                                   {
                                       purchase.updateStatus("-");
                                       table.refresh();
                                   });
                setGraphic(box);
            }
        });
        table.getColumns().add(tableOptions);
    }

    public void viewAdd(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("purchase/Add");
    }

    public void exit(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("Home");
    }
}

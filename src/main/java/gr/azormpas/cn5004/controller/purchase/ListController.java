package gr.azormpas.cn5004.controller.purchase;

import gr.azormpas.cn5004.Main;
import gr.azormpas.cn5004.model.Product;
import gr.azormpas.cn5004.model.Purchase;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ListController
{
    @FXML
    private TableView<Purchase> table;
    @FXML
    private TableColumn<Purchase, String> tableName, tableInfo, tableCost;
    @FXML
    private TableColumn<Purchase, Boolean> tableAvailable;
    @FXML
    private TableColumn<Purchase, Purchase> tableOptions;

    public void initialize()
    {
        ObservableList<Product> list = FXCollections.observableArrayList();
        setCellValues();
        list.addAll(Main.data.getProducts());
        table.setItems(list);
    }

    private void setCellValues()
    {
        tableName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableInfo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));
        tableCost.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getCost())));
        tableAvailable.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isAvailable()));
        tableOptions.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        setCellEditFields();
    }

    private void setCellEditFields()
    {
        tableName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableInfo.setCellFactory(TextFieldTableCell.forTableColumn());
        tableCost.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAvailable.setCellFactory(ChoiceBoxTableCell.forTableColumn(Boolean.TRUE, Boolean.FALSE));
        tableOptions.setCellFactory(cellData -> new TableCell<>()
        {
            private final Button btnDelete = new Button("Delete");

            @Override
            protected void updateItem(Product product, boolean empty)
            {
                super.updateItem(product, empty);

                if (product == null)
                {
                    setGraphic(null);
                    return;
                }

                setGraphic(btnDelete);
                btnDelete.setOnAction(ignoredEvent ->
                                      {
                                          getTableView().getItems().remove(product);
                                          deleteRow(product);
                                      });
            }
        });
    }

    private void deleteRow(Product toRemove)
    {
        Main.data.getProducts()
                 .removeIf(product -> product.equals(toRemove));
        save();
    }

    public void saveNameChange(TableColumn.@NotNull CellEditEvent<Product, String> productStringCellEditEvent)
    {
         Main.data.getProducts()
                  .get(productStringCellEditEvent.getTablePosition().getRow())
                  .setName(productStringCellEditEvent.getNewValue());
        save();
    }

    public void saveInfoChange(TableColumn.@NotNull CellEditEvent<Product, String> productStringCellEditEvent)
    {
        Main.data.getProducts()
                 .get(productStringCellEditEvent.getTablePosition().getRow())
                 .setInfo(productStringCellEditEvent.getNewValue());
        save();
    }

    public void saveCostChange(TableColumn.@NotNull CellEditEvent<Product, String> productStringCellEditEvent)
    {
        if(productStringCellEditEvent.getNewValue().matches("^\\d{1,8}\\.\\d{2}$"))
        {
            Main.data.getProducts()
                     .get(productStringCellEditEvent.getTablePosition().getRow())
                     .setCost(Double.parseDouble(productStringCellEditEvent.getNewValue()));
            save();
        }
        else
        {
            tableCost.getCellFactory().call(productStringCellEditEvent.getTableColumn()).cancelEdit();
            table.refresh();
        }
    }

    public void saveAvailableChange(TableColumn.@NotNull CellEditEvent<Product, Boolean> productBooleanCellEditEvent)
    {
        Main.data.getProducts()
                 .get(productBooleanCellEditEvent.getTablePosition().getRow())
                 .setAvailable(productBooleanCellEditEvent.getNewValue());
        save();
    }

    private void save()
    {
        try
        {
            Main.data.saveShops();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void viewAdd(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("product/Add");
    }

    public void exit(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("Home");
    }
}

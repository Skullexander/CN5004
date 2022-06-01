package gr.azormpas.cn5004.controller.product;

import gr.azormpas.cn5004.Main;
import gr.azormpas.cn5004.model.Product;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ListController
{
    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, String> tableName, tableInfo, tableCost;
    @FXML
    private TableColumn<Product, Boolean> tableAvailable;
    @FXML
    private TableColumn<Product, Product> tableOptions;

    public void initialize()
    {
        ObservableList<Product> list = FXCollections.observableArrayList();
        setCellValues();
        list.addAll(Main.data.getShops().get(Main.data.getLoggedUserLocation()).getInventory());
        table.setItems(list);
    }

    private void setCellValues()
    {
        tableName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableInfo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInfo()));
        tableInfo.setCellFactory(TextFieldTableCell.forTableColumn());
        tableCost.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getCost())));
        tableCost.setCellFactory(TextFieldTableCell.forTableColumn());
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
        Main.data.getShops()
                 .get(Main.data.getLoggedUserLocation())
                 .getInventory()
                 .get(productStringCellEditEvent.getTablePosition().getRow())
                 .setName(productStringCellEditEvent.getNewValue());
        save();
    }

    public void saveInfoChange(TableColumn.@NotNull CellEditEvent<Product, String> productStringCellEditEvent)
    {
        Main.data.getShops()
                 .get(Main.data.getLoggedUserLocation())
                 .getInventory()
                 .get(productStringCellEditEvent.getTablePosition().getRow())
                 .setInfo(productStringCellEditEvent.getNewValue());
        save();
    }

    public void saveCostChange(TableColumn.@NotNull CellEditEvent<Product, String> productStringCellEditEvent)
    {
        if(productStringCellEditEvent.getNewValue().matches("^\\d{1,8}\\.\\d{2}$"))
        {
            Main.data.getShops()
                     .get(Main.data.getLoggedUserLocation())
                     .getInventory()
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
        Main.data.getShops()
                 .get(Main.data.getLoggedUserLocation())
                 .getInventory()
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

    public void exit(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("Home");
    }
}

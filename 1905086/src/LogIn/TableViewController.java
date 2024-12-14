package LogIn;

import Search.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TableViewController {
    private boolean init= true;

    public TableView getTableView() {
        return tableView;
    }

    @FXML
    private TableView tableView;

    ObservableList<CountryInfo> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<CountryInfo, String> CountryNameColumn;

    @FXML
    private TableColumn<CountryInfo, String> PlayerCountColumn;

    private void initializeColumns() {
        CountryNameColumn.setCellValueFactory(new PropertyValueFactory<>("CountryName"));
        PlayerCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

//        tableView.getColumns().addAll(PlayerNameColumn, SellButtonColumn, ViewButtonColumn);
    }

    public void loadTable(List<Country> country) {
        if (init) {
            initializeColumns();
            init = false;
        }

        data.clear();

        for (Country desh: country) {
            data.add(new CountryInfo(desh));
        }
        country.clear();

        tableView.setEditable(true);
        tableView.setItems(data);
    }
}

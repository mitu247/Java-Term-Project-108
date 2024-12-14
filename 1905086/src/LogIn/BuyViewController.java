package LogIn;
import Search.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class BuyViewController {
    private Main main;
    @FXML
    private Label BuyClubName;

    @FXML
    private ImageView image;

    @FXML
    private TableView tableView;


    ObservableList<PlayerInfoBuyPage> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<PlayerInfoBuyPage, String> PlayerNameColumn;

    @FXML
    private TableColumn<PlayerInfoBuyPage, String> PriceColumn;

    @FXML
    private TableColumn<PlayerInfoBuyPage, Button> BuyButtonColumn;

    @FXML
    private TableColumn<PlayerInfoBuyPage, Button> ViewButtonColumn;

    private boolean init = true;

    private boolean isRunning = true;

    public void initialize() {
        Thread t = new Thread(this::refresh);
        t.start();
    }

    private void refresh() {
        while (isRunning) {
            Platform.runLater(() -> {
                try {
                    // data is modified if isRefresh() returns true
                    // It is true whenever a client clicks  the buy or sell button
                    // If an user clicks the sell button that player will appear the Buy & sell page's table
                    // Whenever an user clicks the buy button that player will get removed from the Buy & Sell page's table
                    if (main.getRefreshList().isRefresh()) {
                        data.clear();
                        for (Player player : main.getRefreshList().getPlayers()) {
                            data.add(new PlayerInfoBuyPage(player, main.getNetworkUtil(), main.getClubName()));
                            // NetWorkUtil is passed to send request to the server to transfer that player to the requested club
                            // ClubName is passed to save the info about the requested club so that the server knows where
                            // to send the requested player and which club should get it
                        }
                        main.getRefreshList().setRefresh(false);
                        tableView.refresh();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                Thread.sleep(2000);
                // Thread sleeps for every two 2s
                // So, after clicking sell or buy it takes two second to dynamically appear in buy & sell page or homepage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void initializeColumns() {

//        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        BuyButtonColumn.setCellValueFactory(new PropertyValueFactory<>("BuyButton"));
        ViewButtonColumn.setCellValueFactory(new PropertyValueFactory<>("ViewButton"));

//        tableView.getColumns().addAll(PlayerNameColumn, SellButtonColumn, ViewButtonColumn);
    }

    public void load(List<Player> showPlayers) {
        BuyClubName.setText(main.getClubName());
        if (init) {
            initializeColumns();
            init = false;
        }
//        initializeColumns();

        data.clear();

        for (Player player : showPlayers) {
            data.add(new PlayerInfoBuyPage(player, main.getNetworkUtil(), main.getClubName()));
        }
        tableView.setEditable(true);
        tableView.setItems(data);
        tableView.refresh();
    }
    public void setMain(Main main) {
        this.main=main;
    }

    @FXML
    void backToHomePage(ActionEvent event) throws Exception {
        main.showHomePage(main.getClubName());
        isRunning = false;
    }

    public void loadImage() {
        Image img = new Image(Main.class.getResourceAsStream("HomePageBackground.png"));
        image.setImage(img);
    }
}

package LogIn;
import Search.Country;
import Search.PlaySearches;
import Search.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.GetBuyPlayerList;
import util.LoginDTO;
import util.Notify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePageController {
    private String clubName;
    private PlaySearches playSearches;
    private List<Player> players = new ArrayList<>();
    private boolean isRunning = true;
    @FXML
    private Label ClubName;

    // --------------- search bar and clear searches button

    @FXML
    private TextField searchBar;

    @FXML
    private Label NotFound;

    @FXML
    private ImageView image;
    // ---------------------------------------

    //-----------------------------------------
    public void setMain(Main main) {
        this.main = main;
    }

    private Main main;

    private boolean[] pressed = new boolean[9];

    public void playerNameSearch(ActionEvent actionEvent) {
        pressed[0] = true;
        for (int i=0; i<9; i++) {
            if (i != 0) pressed[i] = false;
        }
    }

    public void playerCountrySearch(ActionEvent actionEvent) {
        pressed[1] = true;
        for (int i=0; i<9; i++) {
            if (i != 1) pressed[i] = false;
        }
    }

    public void playerPositionSearch(ActionEvent actionEvent) {
        pressed[2] = true;
        for (int i=0; i<9; i++) {
            if (i != 2) pressed[i] = false;
        }
    }

    public void playerSalaryRangeSearch(ActionEvent actionEvent) {
        pressed[3] = true;
        for (int i=0; i<9; i++) {
            if (i != 3) pressed[i] = false;
        }
    }

    public void countryWisePlayer(ActionEvent actionEvent) throws IOException {
        pressed[4] = true;
        for (int i=0; i<9; i++) {
            if (i != 4) pressed[i] = false;
        }
        List<Country> country = playSearches.countryWisePlayer(clubName);
        if (country.isEmpty()) NotFound.setText("Empty Club");
        else {
            main.NewTableLoader(country);
        }
    }



    public void MaximumHeight(ActionEvent actionEvent) {
        pressed[5] = true;
        for (int i=0; i<9; i++) {
            if (i != 5) pressed[i] = false;
        }
        players = playSearches.MaximumHeight(clubName);

        if (players.isEmpty()) NotFound.setText("Empty Club");
        else {
            load(players);
        }

    }

    public void MaximumAge(ActionEvent actionEvent) {
        pressed[6] = true;
        for (int i=0; i<9; i++) {
            if (i != 6) pressed[i] = false;
        }
        players = playSearches.MaximumAge(clubName);

        if (players.isEmpty()) NotFound.setText("Empty Club");
        else {
            load(players);
        }
    }
    public void MaximumSalary(ActionEvent actionEvent) {
        pressed[7] = true;
        for (int i=0; i<9; i++) {
            if (i != 7) pressed[i] = false;
        }
        players = playSearches.MaximumSalary(clubName);

        if (players.isEmpty()) NotFound.setText("Empty Club");
        else {
            load(players);
        }
    }

    public void TotalSalary(ActionEvent actionEvent) {
        pressed[8] = true;
        for (int i=0; i<9; i++) {
            if (i != 8) pressed[i] = false;
        }
        double salary = playSearches.TotalSalary(clubName);
        main.viewTotalSalary(salary);
    }
    public void marketAction(ActionEvent actionEvent) {
        try{
            isRunning = false;
            // isRunning is set to false to stop the thread when user leaves the homepage and goes to Buy & Sell page
            // GetBuyPlayerList obj is created to let the server know to start the thread of Buy & Sell page and show that page
            // ClubName is set to let the server know which club requested to open the Buy & Sell page
            GetBuyPlayerList ob = new GetBuyPlayerList();
            ob.setClubName(clubName);
            main.getNetworkUtil().write(ob);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LogOut(ActionEvent actionEvent) {
        try {
            isRunning = false;
            Notify ob = new Notify();
            ob.setClubName(clubName);
            main.getNetworkUtil().write(ob);
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void searchPlayer(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) && pressed[0]) {
            String playerName = searchBar.getText();
            players = playSearches.playerNameSearch(clubName, playerName);

            if (players.isEmpty()) NotFound.setText("No player with this Name");
            else {
                load(players);
            }
        }
        if (event.getCode().equals(KeyCode.ENTER) && pressed[1]) {
            String countryName = searchBar.getText();
            players = playSearches.playerCountrySearch(clubName, countryName);

            if (players.isEmpty()) NotFound.setText("No player with this Country");
            else {
                load(players);
            }
        }
        if (event.getCode().equals(KeyCode.ENTER) && pressed[2]) {
            String position = searchBar.getText();
            players = playSearches.playerPositionSearch(clubName,position);

            if (players.isEmpty()) NotFound.setText("No player with this position");
            else {
                load(players);
            }
        }
        if (event.getCode().equals(KeyCode.ENTER) && pressed[3]) {
            String[] tokens = searchBar.getText().split("-");
            players = playSearches.playerSalaryRangeSearch(clubName,Double.parseDouble(tokens[0]),Double.parseDouble(tokens[1]));

            if (players.isEmpty()) NotFound.setText("No player with this salary-range");
            else {
                load(players);
            }
        }
    }

    @FXML
    void clearSearches(ActionEvent event) {
        searchBar.setText(null);
        NotFound.setText(null);
    }

    @FXML
    void refreshHomePage(ActionEvent event) {
        players = playSearches.getAllPlayers(clubName);
        load(players);
    }

    // ----------------------------- Table -----------------------
    @FXML
    private TableView tableView;

    ObservableList<PlayerInfo> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<CountryInfo, String> PlayerNameColumn;

    @FXML
    private TableColumn<PlayerInfo, ShowPlayerProperty> SellButtonColumn;

    @FXML
    private TableColumn<PlayerInfo, ShowPlayerProperty> ViewButtonColumn;

    private boolean init = true;

    private void initializeColumns() {
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        SellButtonColumn.setCellValueFactory(new PropertyValueFactory<>("Sell"));
        ViewButtonColumn.setCellValueFactory(new PropertyValueFactory<>("View"));
    }

    public void load(List<Player> players) {
        if (init) {
            initializeColumns();
            init = false;
        }

        data.clear();

        for (Player player : players) {
            data.add(new PlayerInfo(player, main.getNetworkUtil()));
        }
        players.clear();

        tableView.setEditable(true);
        tableView.setItems(data);
    }


    // ----------------- Refreshing table view dynamically -------------------------

    public void initialize() {
        Thread t = new Thread(this::refresh);
        t.start();
    }

    private void refresh() {
        while (isRunning) {
            Platform.runLater(() -> {
                try {
                    if (main != null && main.getRefreshList() != null) {
                        // This thread is used for refreshing the table in the homepage
                        // At first main & main.getRefreshList is null
                        // main &7 main.getRefreshList is not null whenever a client clicks the buy or sell button
                        // isRefresh() is true whenever a client clicks the buy or sell button
                        if (main.getRefreshList().isRefresh()) {
                            players = playSearches.getAllPlayers(clubName);
                            data.clear();
                            for (Player player : players) {
                                data.add(new PlayerInfo(player, main.getNetworkUtil()));
                            }
                            players.clear();
                            main.getRefreshList().setRefresh(false);
                            tableView.refresh();
                            // It is set false to prevent data from being modified every time
                            // Though data.clear() prevents duplicate data, but setting false saves some memory space and time
                        }
                        // Table is refreshed continuously
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void setClubName(String clubName) {
        this.clubName = clubName;
        ClubName.setText(clubName);
        players = playSearches.getAllPlayers(clubName);
        load(players);
    }

    public void loadimage() {
        Image img = new Image(Main.class.getResourceAsStream("HomePageBackground.png"));
        image.setImage(img);
    }

    public void setPlaySearches(PlaySearches playSearches) {
        this.playSearches = playSearches;
    }

}

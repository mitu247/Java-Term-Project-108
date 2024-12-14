package LogIn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import Search.Player;
import util.NetworkUtil;

import java.io.IOException;

public class setPriceController {
    private NetworkUtil networkUtil;
    private Player player;
    @FXML
    private TextField price;

    @FXML
    void savePrice(ActionEvent event) throws IOException {
        String fixedPrice = price.getText();
        player.setPrice("$ " + fixedPrice);
        networkUtil.write(player);
    }

    public void setNetworkUtilandPlayer(NetworkUtil networkUtil, Player player) {
        this.networkUtil = networkUtil;
        this.player = player;
    }
}

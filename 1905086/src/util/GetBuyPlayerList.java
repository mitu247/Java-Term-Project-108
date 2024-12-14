package util;

import Search.Club;
import Search.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetBuyPlayerList implements Serializable {
    private String clubName;
    private List<Player> players;
    private List<Club> centralClubList;
    private boolean buyButtonClicked;
    private boolean refresh = true;

    public void setBuyButtonClicked(boolean buyButtonClicked) {
        this.buyButtonClicked = buyButtonClicked;
    }
    public boolean isBuyButtonClicked() {
        return buyButtonClicked;
    }

    public GetBuyPlayerList() {
        players = new ArrayList<>();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Player ob) {
        players.add(ob);
    }

    public void setModifiedClubList(List<Club> centralClubList) {
        this.centralClubList = centralClubList;
    }
    public List<Club> getCentralClubList() {
        return centralClubList;
    }

    public void setRefresh(boolean b) {
        this.refresh = b;
    }
    public boolean isRefresh() {
        return refresh;
    }

}

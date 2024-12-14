package Search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Club implements Serializable {
    private int playerCount;
    private String clubName;
    private double maxSalary;
    private double maxHeight;
    private double totalSalary;
    private int maxAge;
    private List<Player> players;
    public Club(){
        playerCount=0;
        players = new ArrayList<>();
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount += playerCount;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public List<Player> getPlayer() {
        return players;
    }
    public void setPlayer(Player ob) {
        players.add(ob);
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void updateProperties() {
        maxHeight=0.0;
        maxSalary=0.0;
        totalSalary=0.0;
        maxAge=0;

        for(Player player:players){
            if(maxAge<player.getAge()){
              maxAge=player.getAge();
            }

            if(maxHeight<player.getHeight()){
               maxHeight=player.getHeight();
            }

            if(maxSalary<player.getWeeklySalary()){
               maxSalary=player.getWeeklySalary();
            }
           totalSalary+=player.getWeeklySalary();
        }
        totalSalary*=52.0;
    }
}

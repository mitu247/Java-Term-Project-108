package Search;

import util.GetBuyPlayerList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlaySearches implements Serializable {
    public List<Player> playerList;
    public List<Club> clubList;
    public PlaySearches(){
        playerList = new ArrayList<>();
        clubList = new ArrayList<>();
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }
    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    public List<Player> playerNameSearch(String clubName,String name){
        List<Player> players=new ArrayList<>();
        for(Club club: clubList){
          if(club.getClubName().equalsIgnoreCase(clubName)) {
              for(Player player:club.getPlayer()){
                if(player.getName().equalsIgnoreCase(name)){
                    players.add(player);
                }
              }
          }
      }
        return players;
    }
    public List<Player> playerCountrySearch(String clubName,String countryName){
        List<Player> players=new ArrayList<>();
        for(Club club : clubList){
        if(club.getClubName().equalsIgnoreCase(clubName)) {
            for(Player player:club.getPlayer()){
                if(player.getCountry().equalsIgnoreCase(countryName)){
                    players.add(player);
                }
            }
        }
    }
        return players;
    }
    public List<Player> playerPositionSearch(String clubName, String position){
        List<Player> players=new ArrayList<>();
        for(Club club: clubList){
            if(club.getClubName().equalsIgnoreCase(clubName)) {
                for(Player player:club.getPlayer()){
                    if(player.getPosition().equalsIgnoreCase(position)){
                        players.add(player);
                    }
                }
            }
        }
        return players;
    }
    public List<Player> playerSalaryRangeSearch(String clubName, double from, double to){
        List<Player> players=new ArrayList<>();
        for(Club club: clubList){
            if(club.getClubName().equalsIgnoreCase(clubName)) {
                for(Player player:club.getPlayer()){
                    if(player.getWeeklySalary()>=from && player.getWeeklySalary()<=to){
                        players.add(player);
                    }
                }
            }
        }
        return players;
    }
    public List<Country> countryWisePlayer(String clubName){
        List<Country>countryList = new ArrayList();
        for(Club club: clubList){
            if(club.getClubName().equalsIgnoreCase(clubName)) {
                for(Player player:club.getPlayer()){
                    boolean newCountry=true;
                    for (Country country : countryList) {
                        if (player.getCountry().equalsIgnoreCase(country.getName())) {
                            country.setCount(1);
                            newCountry = false;
                        }
                    }
                    if(newCountry) {
                        Country country = new Country();
                        country.setName(player.getCountry());
                        country.setCount(1);
                        countryList.add(country);
                    }
                }
            }
        }
      return countryList;
    }

    public List<Player> MaximumSalary(String clubName){
        List<Player> players=new ArrayList<>();
        for(Club club: clubList) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                for (Player player:club.getPlayer()) {
                    if (player.getWeeklySalary() == club.getMaxSalary())
                        players.add(player);
                }
            }
        }
        return players;
    }
    public List<Player> MaximumAge(String clubName){
        List<Player> players=new ArrayList<>();
        for(Club club: clubList) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                for (Player player:club.getPlayer()) {
                    if (player.getAge() == club.getMaxAge())
                        players.add(player);
                }
            }
        }
        return players;
    }
    public List<Player> MaximumHeight(String clubName){
        List<Player> players=new ArrayList<>();
        for(Club club: clubList) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                for (Player player:club.getPlayer()) {
                    if (player.getHeight() == club.getMaxHeight())
                        players.add(player);
                }
            }
        }
        return players;
    }
    public double TotalSalary(String clubName){
        double salary=0.0;
        for(Club club: clubList){
            if(club.getClubName().equalsIgnoreCase(clubName)) {
                for (Player player:club.getPlayer()) {
                    salary+=player.getWeeklySalary();
                }
            }
        }
        salary=(salary * 52.0); // 1 Year = 52 weeks
        return salary;
    }

    public List<Player> getAllPlayers(String clubName) {
        List<Player> players=new ArrayList<>();
        for(Club club:clubList){
            if(club.getClubName().equalsIgnoreCase(clubName)){
                players.addAll(club.getPlayer());
                break;
            }
        }
        return players;
    }

    public void modifyClubList(GetBuyPlayerList ob) {
        this.clubList = ob.getCentralClubList();
    }
}

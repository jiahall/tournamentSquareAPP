package com.hall.jia.tournamentsquareapp.model;

public class Tournament {

    private String name;
    private int maxEntrants;
    private String postcode;
    private String enterAble;
    private String game;
    private int currEntrants;
    private String host;
    private int entryFee;
    private String prize;
    private String signIn;
    private String location;

    public Tournament(String name, int maxEntrants, String postcode, String location, String enterAble, String game, int currEntrants,
                      String host, int entryFee, String prize, String signIn) {
        super();
        this.name = name;
        this.maxEntrants = maxEntrants;
        this.postcode = postcode;
        this.enterAble = enterAble;
        this.game = game;
        this.currEntrants = currEntrants;
        this.host = host;
        this.entryFee = entryFee;
        this.prize = prize;
        this.signIn = signIn;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxEntrants() {
        return maxEntrants;
    }

    public void setMaxEntrants(int maxEntrants) {
        this.maxEntrants = maxEntrants;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEnterAble() {
        return enterAble;
    }

    public void setEnterAble(String enterAble) {
        this.enterAble = enterAble;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getCurrEntrants() {
        return currEntrants;
    }

    public void setCurrEntrants(int currEntrants) {
        this.currEntrants = currEntrants;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getSignIn() {
        return signIn;
    }

    public void setSignIn(String signIn) {
        this.signIn =signIn;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "name='" + name + '\'' +
                ", maxEntrants=" + maxEntrants +
                ", postcode='" + postcode + '\'' +
                ", enterAble='" + enterAble + '\'' +
                ", game='" + game + '\'' +
                ", currEntrants=" + currEntrants +
                ", host='" + host + '\'' +
                ", entryFee=" + entryFee +
                ", prize=" + prize +
                ", signIn='" + signIn + '\'' +
                ", location='" + location + '\'' +
                '}';
    }


}
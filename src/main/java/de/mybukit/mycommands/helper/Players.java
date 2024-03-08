package de.mybukkit.mycommands.helper;


public class Players {

    private String playername;
    private String uuid;
    private String nickName;
    private boolean nickname_enabled;
    private long last_update;
    private int balance = 0;

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPlayerName() {
        return this.playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public long getLastUpdate() {
        return this.last_update;
    }

    public void setLastUpdate(long logout) {
        this.last_update = logout;
    }

    public boolean isNicknameEnabled() {
        return this.nickname_enabled;
    }

    public void enableNickname(boolean nickname_enabled) {
        this.nickname_enabled = nickname_enabled;
    }
}

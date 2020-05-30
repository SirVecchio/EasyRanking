package me.kaotich00.easyranking.data;

import me.kaotich00.easyranking.api.data.UserData;

import java.util.UUID;

public class ERUserData implements UserData {

    private UUID uuid;
    private String nickname;
    private int score;

    public ERUserData(UUID uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.score = 0;
    }

    public ERUserData(UUID uuid, String nickname, int score) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.score = score;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public int getScore() {
        return this.score;
    }
}

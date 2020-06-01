package me.kaotich00.easyranking.data;

import me.kaotich00.easyranking.api.data.UserData;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ERUserData implements UserData {

    private UUID uuid;
    private String nickname;
    private float score;

    public ERUserData(Player player) {
        this.uuid = player.getUniqueId();
        this.nickname = player.getDisplayName();
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
    public float getScore() {
        return this.score;
    }

    @Override
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public void addScore(float score) {
        this.score += score;
    }

    @Override
    public void subtractScore(float score) {
        this.score -= score;
        if(this.score < 0)
            this.score = 0;
    }
}

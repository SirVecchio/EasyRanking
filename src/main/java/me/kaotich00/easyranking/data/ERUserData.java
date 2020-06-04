package me.kaotich00.easyranking.data;

import me.kaotich00.easyranking.api.data.UserData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class ERUserData implements UserData {

    private UUID uuid;
    private String nickname;
    private float score;

    public ERUserData(Player player) {
        this.uuid = player.getUniqueId();
        this.nickname = player.getPlayerListName();
        this.score = 0;
    }

    public ERUserData(OfflinePlayer player, Float score) {
        this.uuid = player.getUniqueId();
        this.nickname = player.getName();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ERUserData that = (ERUserData) o;
        return Float.compare(that.getScore(), getScore()) == 0 &&
                getUniqueId().equals(that.getUniqueId()) &&
                getNickname().equals(that.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniqueId(), getNickname(), getScore());
    }
}

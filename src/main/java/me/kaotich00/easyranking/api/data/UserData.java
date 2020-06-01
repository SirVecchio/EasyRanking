package me.kaotich00.easyranking.api.data;

import java.util.UUID;

public interface UserData {

    UUID getUniqueId();

    String getNickname();

    float getScore();

    void setScore(float score);

    void addScore(float score);

    void subtractScore(float score);

}

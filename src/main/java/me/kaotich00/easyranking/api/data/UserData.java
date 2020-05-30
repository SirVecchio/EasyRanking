package me.kaotich00.easyranking.api.data;

import java.util.UUID;

public interface UserData {

    UUID getUniqueId();

    String getNickname();

    int getScore();

}

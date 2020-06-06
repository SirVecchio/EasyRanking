package me.kaotich00.easyranking.utils;

import me.kaotich00.easyranking.api.data.UserData;

import java.util.Comparator;

public class RankPositionComparator implements Comparator<UserData> {

    @Override
    public int compare(UserData o1, UserData o2) {
        int score1 = (int)o1.getScore();
        int score2 = (int)o2.getScore();
        return score1 > score2 ? -1 : (score1 < score2 ? 1 : 0);
    }
}

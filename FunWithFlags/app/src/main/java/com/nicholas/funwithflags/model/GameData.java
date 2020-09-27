package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

public class GameData implements Parcelable {
    public static final String COLNUM = "com.nicholas.funwithflags.colnum";
    public static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    public static final String GAMEDATA = "com.nicholas.funwithflags.gdata";
    public static final String FLAG = "com.nicholas.funwithflags.flag";
    public static final String QUESTION = "com.nicholas.funwithflags.question";

    public static final String F_LAYOUT = "com.nicholas.funwithflags.f_layout";
    public static final String F_POINTS = "com.nicholas.funwithflags.f_points";
    public static final String F_FLAG = "com.nicholas.funwithflags.f_flag";
    public static final String F_QUESTION = "com.nicholas.funwithflags.f_question";
    public static final String F_BUTTON = "com.nicholas.funwithflags.f_button";
    public static final String F_ANSWER = "com.nicholas.funwithflags.f_answer";
    public static final String F_SPECIAL = "com.nicholas.funwithflags.f_special";


    private int target, start, current, won, lost, special;
    private Random rand;

    public GameData() {
        rand = new Random();
        start = rand.nextInt((10 - 1) +1 ) + 1;
        target  = rand.nextInt((30 - start+1) + 1) + start+1;
        current = start;
        won = 0;
        lost = 0;
        special = 0;
    }



    public int getCurrent() {
        return current;
    }

    public int getStart() {
        return start;
    }

    public int getTarget() {
        return target;
    }

    public int getWon() {
        return won;
    }

    public int getLost() {
        return lost;
    }

    public void correctAnswer(int points) {
        if(special == 1) {
            points += 10;
        }
        if((current + points) >= target) {
            won = 1;
        }
        current += points;
        special = 0;
    }

    public void incorrectAnswer(int points) {
        if((current - points) <= 0) {
            lost = 1;
        }
        current -= points;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    //Parcelling

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public GameData createFromParcel(Parcel in) {
            return new GameData(in);
        }

        public GameData[] newArray(int size) {
            return new GameData[size];
        }
    };

    public GameData(Parcel in)
    {
        this.target = in.readInt();
        this.start = in.readInt();
        this.current = in.readInt();
        this.won = in.readInt();
        this.lost = in.readInt();
        this.special = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.target);
        dest.writeInt(this.start);
        dest.writeInt(this.current);
        dest.writeInt(this.won);
        dest.writeInt(this.lost);
        dest.writeInt(this.special);
    }
}

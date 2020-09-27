package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/*
 * File: GameData.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Model class for the general game data objects (including current state)
 * Reference:
 */

public class GameData implements Parcelable {
    //Naming for data that is sent in bundles and such
    public static final String COLNUM = "com.nicholas.funwithflags.colnum";
    public static final String COLORIENT = "com.nicholas.funwithflags.colorientation";
    public static final String GAMEDATA = "com.nicholas.funwithflags.gdata";
    public static final String FLAG = "com.nicholas.funwithflags.flag";
    public static final String QUESTION = "com.nicholas.funwithflags.question";

    //Naming for layouts when they are created, found, replaced etc.
    public static final String F_LAYOUT = "com.nicholas.funwithflags.f_layout";
    public static final String F_POINTS = "com.nicholas.funwithflags.f_points";
    public static final String F_FLAG = "com.nicholas.funwithflags.f_flag";
    public static final String F_QUESTION = "com.nicholas.funwithflags.f_question";
    public static final String F_BUTTON = "com.nicholas.funwithflags.f_button";
    public static final String F_ANSWER = "com.nicholas.funwithflags.f_answer";
    public static final String F_SPECIAL = "com.nicholas.funwithflags.f_special";

    //Can't be a boolean because of the parcelling
    private int target, start, current, won, lost, special;
    private Random rand;

    public GameData() {
        rand = new Random();
        //This is the random starting point
        start = rand.nextInt((10 - 1) +1 ) + 1;
        ////This is the random Target point
        target  = rand.nextInt((100 - start+1) + 1) + start+1;
        current = start; //Current points start equal to the starting point

        won = 0; //Shoudln't be won by default
        lost = 0; //Shoudln't be lost by default
        special = 0; //Shoudln't be in special question mode by default
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

    /* SUBMODULE: correctAnswer
     * IMPORT: points(int)
     * EXPORT:
     * ASSERTION: Logic associated when a correct answer is chosen (like updating current points)
     */
    public void correctAnswer(int points) {
        //If the special question event is current, then add 10 extra points
        if(special == 1) {
            points += 10;
        }
        //If the player will win with the new points, set state to won
        if((current + points) >= target) {
            won = 1;
        }
        current += points;
        special = 0; //Reset special to off state
    }

    /* SUBMODULE: incorrectAnswer
     * IMPORT: points(int)
     * EXPORT:
     * ASSERTION: Logic associated when an incorrect answer is chosen (like updating current points)
     */
    public void incorrectAnswer(int points) {
        //If player will lose with the penalty points taken change state to lost
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


    //Parcelling Methods -> based on documentation from
    //https://developer.android.com/reference/android/os/Parcelable

    //For creating the bundle

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public GameData createFromParcel(Parcel in) {
            return new GameData(in);
        }

        public GameData[] newArray(int size) {
            return new GameData[size];
        }
    };

    //When reading from a parcel into current data
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

    //When writing to a parcel, collects the current data
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

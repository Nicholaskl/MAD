package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

public class GameData implements Parcelable {
    private int target, start, current, won;
    private Random rand;

    public GameData() {
        rand = new Random();
        start = rand.nextInt((10 - 0) +1 );
        target  = rand.nextInt((30 - start+1) + 1) + start+1;
        current = start;
        won = 0;
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

    public int correctAnswer(int points) {
        if((current + points) >= target) {
            won = 1;
        }
            current += points;

        return won;
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
    }
}

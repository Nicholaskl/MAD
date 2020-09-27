package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * File: Answer.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Model class for the answer objects (contains answers to the question)
 * Reference:
 */

public class Answer implements Parcelable {
    private int correct;
    private String text;
    private int answered; //Can't be a boolean because of the parcelling

    public Answer(int correct, String text) {
        this.correct = correct;
        this.text = text;
        answered = 0; //not answered by default
    }

    public int getCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }

    public void setAnswered() {
        this.answered = 1;
    }


    //Parcelling Methods -> based on documentation from
    //https://developer.android.com/reference/android/os/Parcelable

    //For creating the bundle
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    //When reading from a parcel into current data
    public Answer(Parcel in)
    {
        this.correct = in.readInt();
        this.text = in.readString();
        this.answered = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //When writing to a parcel, collects the current data
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.correct);
        dest.writeString(this.text);
        dest.writeInt(this.answered);
    }
}

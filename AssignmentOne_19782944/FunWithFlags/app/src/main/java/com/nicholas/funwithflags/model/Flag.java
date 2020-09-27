package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/*
 * File: Flag.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Model class for the flag objects and associated data (Contains list of questions)
 * Reference:
 */

public class Flag implements Parcelable
{
    private String name;
    private int location;
    private List<Question> questions;
    private int answered; //Can't be a boolean because of the parcelling

    public Flag(String name, int location, List<Question> questions) {
        this.name = name;
        this.location = location;
        this.questions = questions;
        this.answered = 0; //Shouldn't be answered by default
    }

    public int getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }


    //Parcelling Methods -> based on documentation from
    //https://developer.android.com/reference/android/os/Parcelable

    //For creating the bundle
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Flag createFromParcel(Parcel in) {
            return new Flag(in);
        }

        public Flag[] newArray(int size) {
            return new Flag[size];
        }
    };

    //When reading from a parcel into current data
    public Flag(Parcel in)
    {
        this.name = in.readString();
        this.location = in.readInt();
        in.readTypedList(questions, Question.CREATOR);
        this.answered = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //When writing to a parcel, collects the current data
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.location);
        dest.writeTypedList(this.questions);
        dest.writeInt(this.answered);
    }
}

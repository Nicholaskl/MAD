package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private int correct;
    private String text;
    private int answered;

    public Answer(int correct, String text) {
        this.correct = correct;
        this.text = text;
        answered = 0;
    }

    public int getCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }

    public String export() {
        return text;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered() {
        this.answered = 1;
    }

    //Parcelling

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.correct);
        dest.writeString(this.text);
        dest.writeInt(this.answered);
    }
}

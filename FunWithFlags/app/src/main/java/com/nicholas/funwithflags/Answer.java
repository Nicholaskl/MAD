package com.nicholas.funwithflags;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    public int correct;
    public String text;

    public Answer(int correct, String text) {
        this.correct = correct;
        this.text = text;
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.correct);
        dest.writeString(this.text);
    }
}

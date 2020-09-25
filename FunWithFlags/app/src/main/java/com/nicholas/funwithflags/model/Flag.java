package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Flag implements Parcelable
{
    private String name;
    private int location;
    private List<Question> questions;
    private int answered;

    public Flag(String name, int location, List<Question> questions) {
        this.name = name;
        this.location = location;
        this.questions = questions;
        this.answered = 0;
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

    public void addQuest(Question quest) {
        questions.add(quest);
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    //Parcelling

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Flag createFromParcel(Parcel in) {
            return new Flag(in);
        }

        public Flag[] newArray(int size) {
            return new Flag[size];
        }
    };

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.location);
        dest.writeTypedList(this.questions);
        dest.writeInt(this.answered);
    }
}

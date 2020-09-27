package com.nicholas.funwithflags.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/*
 * File: Question.java
 * File Created: Friday, 25th Setpember 2020
 * Author: Nicholas Klvana-Hooper
 * -----
 * Last Modified: Sunday, 27th Setpember 2020
 * Modified By: Nicholas Klvana-Hooper
 * -----
 * Purpose: Model class for the question objects (contains question for the list in flags)
 * and has list of answers for the question
 * Reference:
 */

public class Question implements Parcelable{
    private int special;
    private int points;
    private int penalty;
    private int index;
    private String text;
    private List<Answer> answers;
    private int answered; //Can't be a boolean because of the parcelling

    public Question(int special, int points, int penalty, int index, String text, List<Answer> answers) {
        this.special = special;
        this.points = points;
        this.penalty = penalty;
        this.index = index;
        this.text = text;
        this.answers = answers;
        this.answered = 0; //question shouldn't be answered be default
    }

    public int getSpecial() {
        return special;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getPoints() {
        return points;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    /* SUBMODULE: export
     * IMPORT:
     * EXPORT: export(String)
     * ASSERTION: Exports the values of the question in a string
     */
    public String export()
    {
        String export;
        if(special == 1) { //If special add the special name
            export = String.format("Q%d(S)\n" + "Point: %d\n" + "Fine: %d\n",
                    index, points, penalty);
        }
        else {
            export = String.format("Q%d\n" + "Point: %d\n" + "Fine: %d\n",
                    index, points, penalty);
        }
        return export;
    }

    /* SUBMODULE: exportSpecial
     * IMPORT:
     * EXPORT: export(String)
     * ASSERTION: Exports the values of the question in a string, adding the 10 points
     *            To every points number
     */
    public String exportSpecial()
    {
        String export;
        if(special == 1) { //If special add the special name
            export = String.format("Q%d(S)\n" + "Point: %d\n" + "Fine: %d\n",
                    index, points+10, penalty);
        }
        else {
            export = String.format("Q%d\n" + "Point: %d\n" + "Fine: %d\n",
                    index, points+10, penalty);
        }
        return export;
    }


    //Parcelling Methods -> based on documentation from
    //https://developer.android.com/reference/android/os/Parcelable

    //For creating the bundle
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    //When reading from a parcel into current data
    public Question(Parcel in)
    {
        this.special = in.readInt();
        this.points = in.readInt();
        this.penalty = in.readInt();
        this.index = in.readInt();
        this.text = in.readString();
        in.readTypedList(answers, Answer.CREATOR);
        this.answered = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //When writing to a parcel, collects the current data
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.special);
        dest.writeInt(this.points);
        dest.writeInt(this.penalty);
        dest.writeInt(this.index);
        dest.writeString(this.text);
        dest.writeTypedList(this.answers);
        dest.writeInt(this.answered);
    }
}

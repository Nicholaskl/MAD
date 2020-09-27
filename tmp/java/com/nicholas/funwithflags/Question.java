package com.nicholas.funwithflags;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable{
    private int special;
    private int points;
    private int penalty;
    private int index;
    private String text;
    private List<Answer> answers;

    public Question(int special, int points, int penalty, int index, String text, List<Answer> answers) {
        this.special = special;
        this.points = points;
        this.penalty = penalty;
        this.index = index;
        this.text = text;
        this.answers = answers;
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

    public int getIndex() {
        return index;
    }

    public String export()
    {
        String export = "";
        if(special == 1) {
            export = String.format("Q%d(Special)\n" + "Point: %d\n" + "Penalty: %d\n",
                    index, points, penalty);
        }
        else {
            export = String.format("Q%d\n" + "Point: %d\n" + "Penalty: %d\n",
                    index, points, penalty);
        }
        return export;
    }

    //Parcelling

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public Question(Parcel in)
    {
        this.special = in.readInt();
        this.points = in.readInt();
        this.penalty = in.readInt();
        this.index = in.readInt();
        this.text = in.readString();
        in.readTypedList(answers, Answer.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.special);
        dest.writeInt(this.points);
        dest.writeInt(this.penalty);
        dest.writeInt(this.index);
        dest.writeString(this.text);
        dest.writeTypedList(this.answers);
    }
}

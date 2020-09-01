package com.nicholas.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;

public class Equipment extends Item implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }
        public Equipment[] newArray(int size) { return new Equipment[size]; }
    };

    private double mass;

    public Equipment(String inDescription, int inValue, double inMass) {
        super(inDescription, inValue);
        this.mass = inMass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    //Parcelling

    public Equipment(Parcel in) {
        super(in.readString(), in.readInt());
        this.mass = in.readDouble();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(super.getDescription());
        dest.writeInt(super.getValue());
        dest.writeDouble(this.mass);
    }
}
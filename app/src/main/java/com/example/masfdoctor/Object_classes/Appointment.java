package com.example.masfdoctor.Object_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by be on 11/16/2017.
 */

public class Appointment implements Parcelable {
private String  Disease_name,Description,  date ,time ;

    public Appointment() {
    }

    @Override
    public String toString() {
        return "Appointment{" +

                ", Disease_name='" + Disease_name + '\'' +
                ", Description='" + Description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +

                '}';
    }
    public String getDisease_name() {
        return Disease_name;
    }
    public void setDisease_name(String Disease_name) {
        this.Disease_name = Disease_name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Creator<Appointment> getCREATOR() {
        return CREATOR;
    }

    public Appointment(  String Disease_name, String Description,   String date, String time) {


        this.Disease_name = Disease_name;
        this.Description = Description;
        this.date = date ;
        this.time = time ;
    }

    protected Appointment(Parcel in) {

        Disease_name = in.readString();
        Description = in.readString();
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(Disease_name);
        dest.writeString(Description);

        dest.writeString(date);

        dest.writeString(time);
    }
}

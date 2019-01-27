package com.example.panpa.bonplan.Plan;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String place;
    private String startTime;
    private String endTime;
    private String frequency;
    private String recallTime;
    private String descrip;
    private String pathImg;
    private String pathAudio="";

    public Note(String title,String place, String startTime, String endTime, String frequency, String recallTime, String descrip,String pathImg){
        this.title=title;
        this.place=place;
        this.startTime=startTime;
        this.endTime=endTime;
        this.frequency=frequency;
        this.recallTime=recallTime;
        this.descrip=descrip;
        this.pathImg=pathImg;
    }

    /*protected Note(Parcel in) {
        this.title=in.readString();
        this.place=in.readString();
        this.startTime=in.readString();
        this.endTime=in.readString();
        this.frequency=in.readString();
        this.recallTime=in.readString();
        this.descrip=in.readString();
        this.pathImg=in.readString();
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequence) {
        this.frequency = frequence;
    }

    public String getRecallTime() {
        return recallTime;
    }

    public void setRecallTime(String recallTime) {
        this.recallTime = recallTime;
    }

    public String getPathImg() {
        return this.pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public String getPathAudio() {
        return pathAudio;
    }

    public void setPathAudio(String pathAudio) {
        this.pathAudio = pathAudio;
    }

    /*public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.place);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.frequency);
        dest.writeString(this.descrip);
        dest.writeString(this.pathImg);
    }*/
}

package com.pinch.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class SearchFilters implements Parcelable {

    Calendar fromCalendar;
    Calendar toCalendar;
    int distanceSpinnerIndex;
    int eventTypeSpinnerIndex;
    int skillsRequiredSpinnerIndex;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.fromCalendar);
        dest.writeSerializable(this.toCalendar);
        dest.writeInt(this.distanceSpinnerIndex);
        dest.writeInt(this.eventTypeSpinnerIndex);
        dest.writeInt(this.skillsRequiredSpinnerIndex);
    }

    public SearchFilters() {
    }

    public SearchFilters(Calendar fromCalendar, Calendar toCalendar, int distanceSpinnerIndex, int eventTypeSpinnerIndex, int skillsRequiredSpinnerIndex) {
        this.fromCalendar = fromCalendar;
        this.toCalendar = toCalendar;
        this.distanceSpinnerIndex = distanceSpinnerIndex;
        this.eventTypeSpinnerIndex = eventTypeSpinnerIndex;
        this.skillsRequiredSpinnerIndex = skillsRequiredSpinnerIndex;
    }

    protected SearchFilters(Parcel in) {
        this.fromCalendar = (Calendar) in.readSerializable();
        this.toCalendar = (Calendar) in.readSerializable();
        this.distanceSpinnerIndex = in.readInt();
        this.eventTypeSpinnerIndex = in.readInt();
        this.skillsRequiredSpinnerIndex = in.readInt();
    }

    public static final Parcelable.Creator<SearchFilters> CREATOR = new Parcelable.Creator<SearchFilters>() {
        public SearchFilters createFromParcel(Parcel source) {
            return new SearchFilters(source);
        }

        public SearchFilters[] newArray(int size) {
            return new SearchFilters[size];
        }
    };

    public Calendar getFromCalendar() {
        return fromCalendar;
    }

    public void setFromCalendar(Calendar fromCalendar) {
        this.fromCalendar = fromCalendar;
    }

    public Calendar getToCalendar() {
        return toCalendar;
    }

    public void setToCalendar(Calendar toCalendar) {
        this.toCalendar = toCalendar;
    }

    public int getDistanceSpinnerIndex() {
        return distanceSpinnerIndex;
    }

    public void setDistanceSpinnerIndex(int distanceSpinnerIndex) {
        this.distanceSpinnerIndex = distanceSpinnerIndex;
    }

    public int getEventTypeSpinnerIndex() {
        return eventTypeSpinnerIndex;
    }

    public void setEventTypeSpinnerIndex(int eventTypeSpinnerIndex) {
        this.eventTypeSpinnerIndex = eventTypeSpinnerIndex;
    }

    public int getSkillsRequiredSpinnerIndex() {
        return skillsRequiredSpinnerIndex;
    }

    public void setSkillsRequiredSpinnerIndex(int skillsRequiredSpinnerIndex) {
        this.skillsRequiredSpinnerIndex = skillsRequiredSpinnerIndex;
    }

}

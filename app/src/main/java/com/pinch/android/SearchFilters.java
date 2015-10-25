package com.pinch.android;

import com.google.api.client.util.DateTime;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class SearchFilters implements Parcelable {

    String keyword;
    Calendar fromCalendar;
    Calendar toCalendar;
    int distanceSpinnerIndex;
    int eventTypeSpinnerIndex;
    int skillsRequiredSpinnerIndex;

    private int[] distanceArray = {-1, 5, 10, 15, 20};
    private String[] eventTypeArray = {"", "EventType1", "EventType2", "EventType3", "EventType4"};
    private String[] skillsArray = {"", "Skill1", "Skill2", "Skill3", "Skill4"};

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.keyword);
        dest.writeSerializable(this.fromCalendar);
        dest.writeSerializable(this.toCalendar);
        dest.writeInt(this.distanceSpinnerIndex);
        dest.writeInt(this.eventTypeSpinnerIndex);
        dest.writeInt(this.skillsRequiredSpinnerIndex);
    }

    public SearchFilters() {
        keyword = "";

        this.fromCalendar = Calendar.getInstance();
        fromCalendar.set(Calendar.HOUR, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);

        this.toCalendar = Calendar.getInstance();
        toCalendar.set(Calendar.YEAR, toCalendar.get(Calendar.YEAR) + 1);
        toCalendar.set(Calendar.HOUR, 23);
        toCalendar.set(Calendar.MINUTE, 59);
        toCalendar.set(Calendar.SECOND, 59);

        distanceSpinnerIndex = 0;
        eventTypeSpinnerIndex = 0;
        skillsRequiredSpinnerIndex = 0;
    }

    public SearchFilters(String keyword, Calendar fromCalendar, Calendar toCalendar, int distanceSpinnerIndex, int eventTypeSpinnerIndex, int skillsRequiredSpinnerIndex) {
        this.keyword = keyword;
        this.fromCalendar = fromCalendar;
        this.toCalendar = toCalendar;
        this.distanceSpinnerIndex = distanceSpinnerIndex;
        this.eventTypeSpinnerIndex = eventTypeSpinnerIndex;
        this.skillsRequiredSpinnerIndex = skillsRequiredSpinnerIndex;
    }

    protected SearchFilters(Parcel in) {
        this.keyword = in.readString();
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

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


    public int getDistance() {
        return distanceArray[distanceSpinnerIndex];
    }

    public String getEventType() {
        return eventTypeArray[eventTypeSpinnerIndex];
    }

    public String getSkill() {
        return skillsArray[skillsRequiredSpinnerIndex];
    }

    public DateTime getFromDateTime() {
        return new DateTime(fromCalendar.getTime());
    }

    public DateTime getToDateTime() {
        return new DateTime(toCalendar.getTime());
    }

}

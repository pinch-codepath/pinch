package com.pinch.android;

import com.google.api.client.util.DateTime;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class SearchFilters implements Parcelable {

    public static final Parcelable.Creator<SearchFilters> CREATOR = new Parcelable.Creator<SearchFilters>() {
        public SearchFilters createFromParcel(Parcel source) {
            return new SearchFilters(source);
        }

        public SearchFilters[] newArray(int size) {
            return new SearchFilters[size];
        }
    };
    String keyword;
    Calendar fromCalendar;
    Calendar toCalendar;
    int distanceSpinnerIndex;
    private int[] distanceArray = {-1, 5, 10, 15, 20};

    public SearchFilters() {
        keyword = "";

        this.fromCalendar = Calendar.getInstance();
        fromCalendar.set(Calendar.HOUR, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 1);

        this.toCalendar = Calendar.getInstance();
        toCalendar.add(Calendar.DATE, 14);
        toCalendar.set(Calendar.HOUR, 23);
        toCalendar.set(Calendar.MINUTE, 59);
        toCalendar.set(Calendar.SECOND, 59);

        distanceSpinnerIndex = 0;
    }

    public SearchFilters(String keyword, Calendar fromCalendar, Calendar toCalendar, int distanceSpinnerIndex) {
        this.keyword = keyword;
        this.fromCalendar = fromCalendar;
        this.toCalendar = toCalendar;
        this.distanceSpinnerIndex = distanceSpinnerIndex;
    }

    protected SearchFilters(Parcel in) {
        this.keyword = in.readString();
        this.fromCalendar = (Calendar) in.readSerializable();
        this.toCalendar = (Calendar) in.readSerializable();
        this.distanceSpinnerIndex = in.readInt();
    }

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
    }

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

    public int getDistance() {
        return distanceArray[distanceSpinnerIndex];
    }

    public DateTime getFromDateTime() {
        return new DateTime(fromCalendar.getTime());
    }

    public DateTime getToDateTime() {
        return new DateTime(toCalendar.getTime());
    }

}

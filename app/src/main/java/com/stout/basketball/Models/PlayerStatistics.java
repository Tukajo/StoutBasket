package com.stout.basketball.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TJ Frisch on 9/25/2016.
 */

public class PlayerStatistics implements Parcelable {
    private int misses;
    private int hits;
    private double shotAccuracyPercent;
    private int passes;
    private int wins;
    private int losses;
    private int pointsScored;

    public PlayerStatistics(int misses, int hits, int passes, int wins, int losses, int pointsScored){
        this.misses = misses;
        this.hits = hits;
        setShotAccuracyPercent(hits/(misses+hits));
        this.passes = passes;
        this.wins = wins;
        this.losses = losses;
        this.pointsScored = pointsScored;
    }
    protected PlayerStatistics(Parcel in){
        misses = in.readInt();
        hits = in.readInt();
        //Calculate accuracy
        setShotAccuracyPercent(hits/(misses+hits));
        passes = in.readInt();
        wins = in.readInt();
        losses = in.readInt();
        pointsScored = in.readInt();
    }
    public static final Creator<PlayerStatistics> CREATOR = new Creator<PlayerStatistics>() {
        @Override
        public PlayerStatistics createFromParcel(Parcel source) {
            return new PlayerStatistics(source);
        }

        @Override
        public PlayerStatistics[] newArray(int size) {
            return new PlayerStatistics[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(misses);
        dest.writeInt(hits);
        dest.writeInt(passes);
        dest.writeInt(wins);
        dest.writeInt(losses);
        dest.writeInt(pointsScored);
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public double getShotAccuracyPercent() {
        return shotAccuracyPercent;
    }

    public void setShotAccuracyPercent(double shotAccuracyPercent) {
        this.shotAccuracyPercent = shotAccuracyPercent;
    }

    public int getPasses() {
        return passes;
    }

    public void setPasses(int passes) {
        this.passes = passes;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(int pointsScored) {
        this.pointsScored = pointsScored;
    }
}

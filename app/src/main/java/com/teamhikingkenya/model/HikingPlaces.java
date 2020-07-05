package com.teamhikingkenya.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HikingPlaces")
public class HikingPlaces implements Parcelable {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "price")
    private String price;

    public HikingPlaces(){}

    protected HikingPlaces(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
        location = in.readString();
        price = in.readString();
    }

    public static final Creator<HikingPlaces> CREATOR = new Creator<HikingPlaces>() {
        @Override
        public HikingPlaces createFromParcel(Parcel in) {
            return new HikingPlaces(in);
        }

        @Override
        public HikingPlaces[] newArray(int size) {
            return new HikingPlaces[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(price);
    }
}

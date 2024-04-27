package com.eat.maroc.promo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Item implements Parcelable, Serializable {
    String title;
    String type;
    String prix;
    String image;
    String whatsapp;
    String description;
    String adress;
    String ville;
    String quartier;
    String location;

    public Item(String title, String type, String prix, String image, String whatsapp, String description, String adress, String ville, String quartier,String location){
        this.title=title;
        this.type=type;
        this.prix=prix;
        this.image=image;
        this.whatsapp=whatsapp;
        this.description=description;
        this.adress=adress;
        this.ville = ville;
        this.quartier = quartier;
        this.location=location;
    }

    protected Item(Parcel in) {
        title = in.readString();
        type = in.readString();
        prix = in.readString();
        image = in.readString();
        whatsapp = in.readString();
        description = in.readString();
        adress = in.readString();
        ville = in.readString();
        quartier = in.readString();
        location=in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(prix);
        dest.writeString(image);
        dest.writeString(whatsapp);
        dest.writeString(description);
        dest.writeString(adress);
        dest.writeString(ville);
        dest.writeString(quartier);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }
}

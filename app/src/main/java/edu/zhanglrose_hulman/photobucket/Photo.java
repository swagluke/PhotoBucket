package edu.zhanglrose_hulman.photobucket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

/**
 * Created by lukezhang on 7/20/16.
 */
public class Photo implements Parcelable {

    private String caption;
    private String url;
    private String key;


    public Photo(){
        // Need to have the empty one.
    }

    public Photo(String name, String url){
        this.caption = name;
        this.url = url;
    }

    protected Photo(Parcel in) {
        caption = in.readString();
        url = in.readString();
        key = in.readString();
    }


    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValues(Photo newWeatherpic) {
        caption = newWeatherpic.caption;
        url = newWeatherpic.url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(caption);
        dest.writeString(url);
        dest.writeString(key);
    }
}

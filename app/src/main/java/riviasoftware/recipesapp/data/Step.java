package riviasoftware.recipesapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergiolizanamontero on 10/5/17.
 */

public class Step implements Parcelable {
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private String shortDescription;

    public String getShortDescription() { return this.shortDescription; }

    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    private String description;

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    private String videoURL;

    public String getVideoURL() { return this.videoURL; }

    public void setVideoURL(String videoURL) { this.videoURL = videoURL; }

    private String thumbnailURL;

    public String getThumbnailURL() { return this.thumbnailURL; }

    public void setThumbnailURL(String thumbnailURL) { this.thumbnailURL = thumbnailURL; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
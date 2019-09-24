package com.stac.hanghangtwo;

public class ImageUploadInfo {
    public String imageName;

    public String imageURL;

    public int imageId;

    public boolean imageSign;

    public ImageUploadInfo() {

    }

    public ImageUploadInfo(String name, String url, int id, boolean sign) {

        this.imageName = name;
        this.imageURL = url;
        this.imageId = id;
        this.imageSign = sign;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getImageId() {
        return imageId;
    }

    public boolean getImageSign() {
        return imageSign;
    }
}

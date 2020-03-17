package com.example.multiplerecycler.add_Note;

public class spinnerClass {
    String colorCode;
    int drawableFile;
    String priorityName;

    public spinnerClass(String colorCode, int drawableFile, String priorityName) {
        this.colorCode = colorCode;
        this.drawableFile = drawableFile;
        this.priorityName = priorityName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getDrawableFile() {
        return drawableFile;
    }

    public void setDrawableFile(int drawableFile) {
        this.drawableFile = drawableFile;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }
}

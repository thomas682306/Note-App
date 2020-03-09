package com.example.multiplerecycler;

public class modelClass_note {
    String Heading;
    String Date;
    String Description;
    int priority;
    String HexCode;
    String day;

    public modelClass_note() {
    }

    public modelClass_note(String heading, String date, String description, int priority, String hexCode, String day) {
        Heading = heading;
        Date = date;
        Description = description;
        this.priority = priority;
        HexCode = hexCode;
        this.day = day;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getHexCode() {
        return HexCode;
    }

    public void setHexCode(String hexCode) {
        HexCode = hexCode;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

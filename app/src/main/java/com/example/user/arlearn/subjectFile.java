package com.example.user.arlearn;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;
import com.example.user.arlearn.ImageAdapter.*;
/**
 * Created by user on 12/21/2017.
 */

public class subjectFile {

    private String subjectID;
    private String name;
    private String image;
    private String type;
    private String Description1;
    private String Description2;
    private String version;
    private ImageAdapter imageAdapter;

    public subjectFile() {

    }

    public subjectFile(String subjectID, String image)
    {
        this.subjectID = subjectID;
        this.image = image;
    }

    public subjectFile(String name, String image, String Description1, String Description2)
    {
        this.name = name;
        this.image = image;
        this.Description1 = Description1;
        this.Description2 = Description2;
    }

    public subjectFile(String subjectID, String name, String image, String type, String Description1, String Description2, String version)
    {
        this.subjectID = subjectID;
        this.name = name;
        this.image = image;
        this.type = type;
        this.Description1 = Description1;
        this.Description2 = Description2;
        this.version = version;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription1() {
        return Description1;
    }

    public void setDescription1(String description1) {
        Description1 = description1;
    }

    public String getDescription2() {
        return Description2;
    }

    public void setDescription2(String description2) {
        Description2 = description2;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

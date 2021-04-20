package com.dgl114.assignment4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//***********************************************************************************************
// Card.java            Author: Unknown         Modified by: zakacat
//
//This class includes attributes for the card object. It has a default constructor and an
//overloaded constructor that will accept the card title as parameters. It also has getters
//and setters to access these attributes.
//
//* I will be keeping all of the instructor's comments as I will probably be referring to this
//in -depth at some point later.
//***********************************************************************************************

/**
 * 1. Add Room annotations as necessary to this file.
 */
@Entity
public class Card {
    //****************************************************************************************
    //These are the attributes of the Card object. They have also been marked with the Room
    //annotations necessary for Room to recognize them. Each Card represents a row and each card's
    //attribute represent a column as seen below.
    //****************************************************************************************
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "detail")
    private String mDetail;

    @ColumnInfo(name = "# of variables")
    private int mVariables;

    //*********************************************************************************************
    //Constructors, Getters, and Setters.
    //*********************************************************************************************
    public Card() {
    }

    public Card(@NonNull String title) {
        mTitle = title;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String text) {
        mTitle = text;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public int getVariables() {
        return mVariables;
    }

    public void setVariables(int numOfVariables) {
        mVariables = numOfVariables;
    }

}

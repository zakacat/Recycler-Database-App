package com.dgl114.assignment4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
/**
 import androidx.room.Update;
 */
//************************************************************************************************
//CardDao.java      Author: Unknown         Modified by: zakacat
//
// The card data access object (Dao) has methods that are used to interact with the rows
// of the Room table. This class is set up as an interface with various static methods.
//************************************************************************************************
import java.util.List;

/**
 * 1. Add additional Room annotations as necessary to this file.
 */
@Dao
public interface CardDao {
    /**
     * 2. Include @Query methods here (you will need at least two:
     * - One to get a single Card
     * - One to get a List<Card>
     * Use StudyHelper as a guide - you can use nearly identical SELECT queries,
     * but you will need to change parameter names.
     */
    //*********************************************************************************************
    //Methods to retrieve card objects from the database.
    //*********************************************************************************************
    @Query("SELECT * FROM Card WHERE id = :id")
    public Card getCard(long id);

    @Query("SELECT * FROM Card ORDER BY title")
    public List<Card> getCards();

    /**
     * 3. Include an @Insert method - don't forget the OnConflictStrategy!
     */
    //*********************************************************************************************
    //Method for adding a card to the database. If there is another card with the same ID, it
    //will replace it.
    //*********************************************************************************************
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertCard(Card Card);


    /**
     * 4. You may include @Update and @Delete methods,
     * though they are not required for a minimum submission
     * //   @Update
     * //   public void updateCard(Card card);
     */
    //*********************************************************************************************
    //Method for deleting a card. Most of the logic must be handled through the annotation.
    //*********************************************************************************************
    @Delete
    public void deleteCard(Card card);

}

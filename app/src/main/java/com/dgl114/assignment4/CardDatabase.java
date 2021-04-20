package com.dgl114.assignment4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//*************************************************************************************************
//CardDatabase.java         Author: Unknown     Modified by: zakacat
//
//This class contains methods and information needed to properly create a data base.
//*************************************************************************************************

/**
 * 1. Add Room annotations here.
 */
@Database(entities = {Card.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {
    //*********************************************************************************************
    //Here we are assigning a name to the database and also declaring an instance of Card Database
    //*********************************************************************************************
    private static final String DATABASE_NAME = "card.db";
    private static CardDatabase mCardDatabase;

    /**
     * Singleton
     */
    //*********************************************************************************************
    //To avoid accidentally creating or referencing more than one database, a singleton is used for
    //only if there is no prior instantiation of the CardDatabase, will the code execute a build
    //process.
    //*********************************************************************************************
    public static CardDatabase getInstance(Context context) {
        if (mCardDatabase == null) {
            mCardDatabase = Room.databaseBuilder(context, CardDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
            mCardDatabase.addStarterData();

            /** 2. Complete this method. Consider the approach used in StudyHelper.
             Don't neglect to handle database schema changes,
             and don't forget to add starter data!
             */

        }

        return mCardDatabase;
    }

    //*********************************************************************************************
    //This abstract method returns a Card Dao and allows for access to the Dao methods in
    //CardDao.java
    //*********************************************************************************************
    public abstract CardDao cardDao();

    /**
     * 3. Complete the implementation of addStarterData.
     * You can follow StudyHelper very closely here,
     * but! the specific model methods will differ.
     * Don't forget that in StudyHelper both Subject and Questions were handled here
     * you only need to handle Cards, but you will need to use runInTransaction()
     */
    //*********************************************************************************************
    //If there are no cards in the database, run the logic to create starter data. runInTransaction()
    //utilizes a Runnable() that bypasses the need to sub-class a thread.
    //I have added lots of start data to show off my color gradient and to make sure that the scroll
    //and gesture detection works properly. Starter data is not necessary for the function of the app.
    //*********************************************************************************************
    private void addStarterData() {

        if (cardDao().getCards().size() == 0) {

            runInTransaction(new Runnable() {
                @Override
                public void run() {
                    Card card = new Card();
                    card.setTitle("Area of a circle \n A = πr2");
                    card.setId(1);
                    card.setDetail("This formula can take the radius of a circle and produce the" +
                            " area, or it can take the area and produce the radius.");
                    card.setVariables(2);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Average velocity \n Vavg = Δv / 2");
                    card.setId(2);
                    card.setDetail("This formula can take the difference in velocity and produce the average" +
                            " velocity, or it can take the average velocity and produce the difference in velocity.");
                    card.setVariables(2);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Molarity \n M = n / v");
                    card.setId(3);
                    card.setDetail("This formula can take the number of moles and divide it by the volume of solution" +
                            " to produce the Molarity, or it can take the number of moles and molarity to produce volume, " +
                            "or it can take the volume and molarity and produce the amount of moles.");
                    card.setVariables(3);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Horsepower \n hp = Fd / t");
                    card.setId(4);
                    card.setDetail("This formula takes the force (lbs), distance(feet), and time(minutes) " +
                            "and produces horsepower. The derivations are also possible.");
                    card.setVariables(4);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Power \n P = W / Δt");
                    card.setId(5);
                    card.setDetail("This formula takes the work and change in time and produces power. " +
                            "The derivations are also possible.");
                    card.setVariables(3);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Current \n I = V / R");
                    card.setId(6);
                    card.setDetail("This formula takes the voltage and the resistence and produces the " +
                            "current. Derivations are also possible.");
                    card.setVariables(3);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Orbital Speed \n v ≈  2πa / T");
                    card.setId(7);
                    card.setDetail("This formula takes the length of the semimajor axis and the orbital period" +
                            " and produces the orbital speed. Derivations are also possible.");
                    card.setVariables(3);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Tangential Velocity \n Vt = wr");
                    card.setId(8);
                    card.setDetail("This formula takes the angular velocity and radius to produce tangential velocity." +
                            " Derivations are also possible.");
                    card.setVariables(3);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Stress \n t = F / A");
                    card.setId(9);
                    card.setDetail("This formula takes the force and cross-section area and produces stress. " +
                            "Derivations are also possible.");
                    card.setVariables(3);
                    cardDao().insertCard(card);

                    card = new Card();
                    card.setTitle("Strain \n E = ΔL / Lo");
                    card.setId(10);
                    card.setDetail("This formula takes the change in length and the original length and produces" +
                            " strain. Derivations are also possible.");
                    card.setVariables(3);
                    cardDao().insertCard(card);

                }

            });
        }
    }
}

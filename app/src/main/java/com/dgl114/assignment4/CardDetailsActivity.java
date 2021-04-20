package com.dgl114.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

//*************************************************************************************************
//CardDetailsActivity.java      Author: Unknown       Modified by: zakacat
//
//The class represents the secondary activity for the app. It presents the user with a
// a display of all the information in the card object (Title, Variables, and Details). This
//activity receives the intent that was started by clicking on a card in main activity. The card Id
//is passed through and all the card attributes referenced here are for that one card that was
//passed in specifically.
//*************************************************************************************************
public class CardDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_ID = "com.dgl114.assignment4.recyclerview.card_id";
    private long mCardId;
    private CardDatabase mCardDb;
    private Card mCard;
    private TextView mCardTitle, mCardDetail, mCardVar;

    //*********************************************************************************************
    //onCreate() receives he intent and assigns the card id to a class variable.
    //The database singleton is referenced and the relative card is called using the card id.
    //The view objects are coordinated appropriately and then finally there is a call to showCard().
    //*********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        /**1. Get the Intent that brought you here - don't create a new Intent!
         */

        Intent intent = getIntent();
        mCardId = intent.getLongExtra(CardDetailsActivity.EXTRA_CARD_ID, 1);


        /**2. Get a single card - use the CardDao and make sure you get the right Card!
         */
        mCardDb = CardDatabase.getInstance(getApplicationContext());
        mCard = mCardDb.cardDao().getCard(mCardId);


        /** 3. Get references to your view widgets so that the Card data can be displayed.
         */
        mCardTitle = findViewById(R.id.cardTitle);
        mCardDetail = findViewById(R.id.cardDetail);
        mCardVar = findViewById(R.id.numOfVariables);
        /** 4. Show the card by calling showCard()
         Unlike in StudyHelper there is no need for showCard to have a parameter here,
         since you've already picked the right card above.
         (Remember that in StudyHelper choosing a Subject displayed numerous Questions;
         in this app choosing a card displays more details about *that* card).
         */
        showCard();
    }

    //*********************************************************************************************
    //showCard() retrieves the card attribute info from the database and assigns it to the textViews,
    //visible for the user to see.
    //*********************************************************************************************
    private void showCard() {
        /** 5. Set values as appropriate for the views, based on the Card instance
         */
        mCardTitle.setText(mCard.getTitle());

        String output = "# of variables : " + mCard.getVariables();
        mCardVar.setText(output);

        mCardDetail.setText(mCard.getDetail());
    }


}



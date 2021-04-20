package com.dgl114.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//**************************************************************************************************
//MainActivity.java         Author: Unknown         Modified by: zakacat
//
//Main activity contains all the logic (and there is a fair bit) for the user to interact with and
//create cards. In this app, the are akin to flash cards for formulas.
//There are several different, notable sections of this activity:
//- There are the onCreate() and onResume() lifecycle components.
//- There is the custom menu which displays an action to add cards
//- There is the CardHolder which coordinates the card objects with the recycler view and contains
// the logic for the UI
//- There is the ActionMode.Callback which handles the logic for the delete and context menu.
//- There is the CardAdapter which contains the interaction logic for adding, removing, and binding
// cards in the Recycler View
//-There is also the overridden onCardEntered Method which handles the interpretation of user input
// from the dialog and interacts with the database accordingly.
//**************************************************************************************************
public class MainActivity extends AppCompatActivity implements CardDialogFragment.OnCardEnteredListener {

    private CardAdapter mCardAdapter;
    private RecyclerView mRecyclerView;
    private CardDatabase mCardDb;
    private int[] mCardColors;
    private ActionMode mActionMode = null;
    private Card mSelectedCard;
    private int mSelectedCardPosition = RecyclerView.NO_POSITION;

    //**********************************************************************************************
    //The onCreate() refers to the singleton of the database, instantiates a recycler view and sets
    //the number of columns to two. It also instantiates the CardAdapter with the cards from the
    //database.
    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Singleton
         */
        mCardDb = CardDatabase.getInstance(getApplicationContext());

        mCardColors = getResources().getIntArray(R.array.cardColors);

        /**1. Instantiate the RecyclerView object and set layout
         */
        mRecyclerView = findViewById(R.id.cardRecyclerView);
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        /** 2. Instantiate the CardAdapter from the CardAdapater constructor
         set it to the RecyclerView adapter
         */
        mCardAdapter = new CardAdapter(loadCards());
        mRecyclerView.setAdapter(mCardAdapter);

    }

    //**********************************************************************************************
    //onResume() will load the cards from the database and display them
    //**********************************************************************************************
    @Override
    protected void onResume() {
        super.onResume();

        /** Load subjects here in case settings changed
         */
        mCardAdapter = new CardAdapter(loadCards());
        mRecyclerView.setAdapter(mCardAdapter);
    }

    /**
     * 3. Write a private method (similar to loadSubjects from StudyHelper)
     * that returns a List<Card> from the database (use the CardDao!)
     */
    private List<Card> loadCards() {
        return mCardDb.cardDao().getCards();
    }


    /**
     * 4. Complete the CardHolder class
     */
    //**********************************************************************************************
    //This CardHolder class is an internal class. It inflates the recycler view and handles gesture
    //detection for onClick and onLongClick. It also binds the appropriate
    //**********************************************************************************************
    private class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private Card mCard;
        private TextView mTextView;

        //******************************************************************************************
        //The CardHolder constructor inflates the recycler view, set the gesture detection listeners
        //and assigns the card text view.
        //******************************************************************************************
        public CardHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_view_items, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mTextView = itemView.findViewById(R.id.cardTextView);
        }

        /**
         * 5. Complete the bind method. Use this method to ensure mCard and mTextView
         * both have appropriate values.
         * You may also use this space to distinguish each Card using a method similar
         * to that in StudyHelper - or of your own design.
         */
        //******************************************************************************************
        //bind() receives the card and postion of the card in parameters and assigns the appropriate
        //colours for the componenets. It also includes setting the card color to something
        //eye catching when it has been selected by long click.
        //******************************************************************************************
        public void bind(Card card, int position) {
            mCard = card;
            mTextView.setText(mCard.getTitle());
            mTextView.setTextColor(getResources().getColor(R.color.white));

            if (mSelectedCardPosition == position) {
                mTextView.setBackgroundColor(getResources().getColor(R.color.sand));
            } else {
                if (position < 10) {
                    mTextView.setBackgroundColor(mCardColors[position]);
                } else {
                    mTextView.setBackgroundColor(mCardColors[9]);
                }
            }
        }

        //******************************************************************************************
        //When onClick is called, a new intent is created the opens the CardDetailsActivity. It also
        //passed in the card id of the card that was clicked to be handled by the new activity.
        //******************************************************************************************
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CardDetailsActivity.class);
            intent.putExtra(CardDetailsActivity.EXTRA_CARD_ID, mCard.getId());
            startActivity(intent);
        }

        //******************************************************************************************
        //When onLongClick() is called, the card that was long clicked becomes the selected card,
        //and startActionMode() is called to inflate the context menu for deleting the card.
        //******************************************************************************************
        @Override
        public boolean onLongClick(View view) {
            if (mActionMode != null) {
                return false;
            }

            mSelectedCard = mCard;
            mSelectedCardPosition = getAdapterPosition();

            /** Re-bind the selected item
             */
            mCardAdapter.notifyItemChanged(mSelectedCardPosition);

            /** Show the CAB
             */
            mActionMode = MainActivity.this.startActionMode(mActionModeCallback);

            return true;
        }
    }

    //**********************************************************************************************
    //ActionMode.Callback() contains methods for handling the delete card context menu as well as
    // as handling what happens when the user decides to delete the card.
    //**********************************************************************************************
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        //******************************************************************************************
        //onCreateActionMode() inflates the custom sontext_delete_menu
        //******************************************************************************************
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            /** Provide context menu for CAB
             */
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_delete_menu, menu);
            return true;
        }

        //******************************************************************************************
        //onPrepareActionMode() is necessary to implemnet but returns nothing.
        //******************************************************************************************
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        //******************************************************************************************
        //onActionItemClicked() handles the context menu functions much like an onOptionsItemSelected()
        //method.
        //******************************************************************************************
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            /** Process action item selection
             */
            switch (item.getItemId()) {
                case R.id.delete:
                    /** Delete from the database and remove from the RecyclerView
                     */
                    mCardDb.cardDao().deleteCard(mSelectedCard);
                    mCardAdapter.removeCard(mSelectedCard);

                    /** Close the CAB
                     */
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        //******************************************************************************************
        //onDestroyActionMode() is called either when a card is delted or the user escapes the
        //context menu. The Recycler view is then updated appropriately.
        //******************************************************************************************
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;

            /** CAB closing, need to deselect item if not deleted
             */
            mCardAdapter.notifyItemChanged(mSelectedCardPosition);
            mSelectedCardPosition = RecyclerView.NO_POSITION;
        }
    };

    /**
     * 6. Complete the CardAdapter class
     */
    //**********************************************************************************************
    //CardAdapter is an internal class with methods to interact and update the recycler view.
    //**********************************************************************************************
    private class CardAdapter extends RecyclerView.Adapter<CardHolder> {

        private List<Card> mCardList;

        /**
         * 7. Write the CardAdapter constructor. Look to StudyHelper for inspiration.
         */
        //******************************************************************************************
        //The CardAdapter constructor passes in the list of card objects.
        //******************************************************************************************
        public CardAdapter(List<Card> cards) {
            mCardList = cards;
        }

        //******************************************************************************************
        //onCreateViewHolder() inflates the layout from this context and returns a CardHolder object.
        //******************************************************************************************
        @NonNull
        @Override
        public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new CardHolder(layoutInflater, parent);
        }

        /**
         * 8. Complete the onBindViewHolder method. Look to StudyHelper for inspiration.
         */
        //******************************************************************************************
        //onBincViewHolder() binds the cards via their position, by calling bind() from CardHolder.
        //******************************************************************************************
        @Override
        public void onBindViewHolder(CardHolder holder, int position) {
            holder.bind(mCardList.get(position), position);
        }

        //******************************************************************************************
        //getItemCount() returns the Card list size.
        //******************************************************************************************
        @Override
        public int getItemCount() {
            return mCardList.size();
        }

        //******************************************************************************************
        //addCard() adds the new card to the beginning, updates the adapter, and scrolls the recycler
        //view back to the top.
        //******************************************************************************************
        public void addCard(Card card) {
            /** Add the new card at the beginning of the list
             */
            mCardList.add(0, card);

            /** Notify the adapter that item was added to the beginning of the list
             */
            notifyItemInserted(0);

            /** Scroll to the top
             */
            mRecyclerView.scrollToPosition(0);
        }

        //******************************************************************************************
        //removeCard() removes the card from the list and updates the recycler view to remove the
        //card.
        //******************************************************************************************
        public void removeCard(Card card) {
            /** Find card in the list
             */
            int index = mCardList.indexOf(card);
            if (index >= 0) {
                /** Remove the card
                 */
                mCardList.remove(index);

                /** Notify adapter of card removal
                 */
                notifyItemRemoved(index);
            }
        }
    }

    //**********************************************************************************************
    //onCreateOptionsMenu() inflates the customs card_stack_menu
    //**********************************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.card_stack_menu, menu);
        return true;
    }

    //**********************************************************************************************
    //onOptionsItemSelected() handles the buttons within the menu, of which there is only one.
    //The add button initiates the CardDialogFragment.
    //**********************************************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /** Handle item selection
         */
        switch (item.getItemId()) {
            case R.id.addButton:
                FragmentManager manager = getSupportFragmentManager();
                CardDialogFragment dialog = new CardDialogFragment();
                dialog.show(manager, "subjectDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    //**********************************************************************************************
    //onCardEntered() is referred to by the CardDialogFragment class and handles the user input.
    //If a title has been input, then a new card can be created and added to the database using
    //the appropriate Card object attributes and cardDao() method. It also updates the adapter and
    //creates a toast to inform the user that their card has been added.
    //**********************************************************************************************
    @Override
    public void onCardEntered(String cardTitle, int cardVar, String cardDetail) {
        if (cardTitle.length() > 0) {
            Card card = new Card(cardTitle);
            card.setVariables(cardVar);
            if (cardDetail.length() > 0) {
                card.setDetail(cardDetail);
            }
            long subjectId = mCardDb.cardDao().insertCard(card);
            card.setId(subjectId);
            mCardAdapter.addCard(card);
            Toast.makeText(this, "Added " + cardTitle, Toast.LENGTH_SHORT).show();
        }
    }
}
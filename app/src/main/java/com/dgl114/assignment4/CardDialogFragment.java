package com.dgl114.assignment4;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

//*************************************************************************************************
//CardDialogFragment.java       Author: Unknown         Modified by: zakacat
//
//This fragment class is called only from the main activity when the add card button is pressed.
//It inflates custom layout, assigns the corresponding views and reads the information from user
//input. The onCardEntered method can then be overwritten and used to pass user information into
//the database using the Dao.
//*************************************************************************************************
public class CardDialogFragment extends DialogFragment {

    public interface OnCardEnteredListener {
        void onCardEntered(String cardTitle, int cardVar, String cardDetail);
    }

    private OnCardEnteredListener mListener;

    //************************************************************************************************
    //The onCreateDialog() is uses AlertDialog.Builder to streamline some of the aspects for
    //constructing a dialog. It can take care of the title, view, and buttons. In this case though,
    //some of these tasks were handled through the custom layout.
    //************************************************************************************************
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        //*******************************************************************************************
        //As a personal note here, the take away is that I need to assign a view before I can
        // assign the different view widgets as I need to use the View as a vehicle and I cannot
        //assign these view widgets directly.
        //*******************************************************************************************
        final View addCardView = inflater.inflate(R.layout.add_card_dialog, null);

        final EditText titleEditText = addCardView.findViewById(R.id.editTextTitle);
        final EditText varEditText = addCardView.findViewById(R.id.editTextVariables);
        final EditText detailEditText = addCardView.findViewById(R.id.editTextDetails);


        return new AlertDialog.Builder(requireActivity())
                .setView(addCardView)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        /**Notify listener
                         */
                        int cardVar = 0;

                        String cardTitle = titleEditText.getText().toString();
                        cardVar = Integer.valueOf(varEditText.getText().toString());
                        String cardDetail = detailEditText.getText().toString();

                        mListener.onCardEntered(cardTitle.trim(), cardVar, cardDetail.trim());
                    }
                })
                .setNegativeButton("cancel", null)
                .create();
    }

    //************************************************************************************************
    //onAttach() is called before onCreate as assigns the context (cast as an OnCardEnteredListener)
    //onDetach() is called after the destruction of the dialog to re-assign the mListener variable as
    // it is context specific.
    //************************************************************************************************
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnCardEnteredListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
# **Zakacat's Formula App**

## **Purpose**

This is the source code necessary to make an app that utilizes both Recycler View and Room with android studio for android phones. This was an assignment for a college course DGL-114 Intro to App Development. The purpose of this assignment was to familiarize the student ("Hey, that's me.") with:
- Room persistent library, databases, and Daos
- Recycler View
- The MVC design model (Model, View, Controller... not necessarily in that order)
- Cards within a Recycler View
- Dialog Fragments
- Context menu
- Gesture detection/ listening

## **About The App**

This app will initially display 10 formula flash cards. The user can click one of the cards to be taken to a different screen which will display the information about the formula which includes the number of variables as well as a description, or the user can add a card to the deck (Recycler View). Within the add dialog, the user can customize the title, formula, amount of variables, and the details of the formula.

*As a side-note, I had aspirations of storing the formulas and derivations within the card objects somehow and calling them to be able to actually interact with the user for the appropriate formulas, but I found this not possible to implement. I figured that I could have used a reference of some sort within the Card object attributes and pointed to a method in a separate class to complete the computation, but I concluded that this was a bit outside of the scope of the assignment. The variable Card-attribute is residual from how I was going to constrain the amount of EditText fields being displayed.

*I also intentionally kept the notes/directions from the instructor in the program files as I will most likely be referring back to this assignment in the future.

## **Contributors**

The initial set of files was provided by the instructor of the course. Most of the files were taken from the text with our instructions being - to improve and modify the app. Outside of the initial file set, I worked solo, but several modifications were still made.

## **Modifications**

- Added appropriate Room annotations
- Added a ActionMode.Callback to handle deleting Cards
- Added neccesary drawables
- Created an app bar menu
- Created a context menu
- Updated the colour theme to something less... offensive
- Added a gradient in the form of an array of colours to display the cards with in the Recycler View
- Added Varaibles attribute (int type) to the Card class
- Created a dialog to handle creating cards from user input
- Commented code (in block code format) to further prove my understanding of the material
- And otherwise followed the directions provided by the instructor (which are delineated in the code with a number and a JavaDoc 'green-text' notation)

## **License**
MIT license

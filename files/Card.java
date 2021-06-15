package files;


import java.beans.EventHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Card implements Comparable<Card> {
    private int value;
    private String suit;
    private ImageView img;
    private boolean isFaceUp = true;
    public ImageView faceUp;
    public ImageView faceDown;

    public Card(int value, String suit) throws FileNotFoundException  {
        this.value = value;
        this.suit = suit;
        faceUp =  new ImageView(new Image(new FileInputStream("./cards/" + toString() + ".png")));
        faceDown = new ImageView(new Image(new FileInputStream("./cards/blue_back.png")));
        faceUp.setFitWidth(75);
        faceUp.setFitHeight(100);
        img = faceUp;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public String toString() {
        if (value == 11)
            return "J" + suit;
        else if (value == 12)
            return "Q" + suit;
        else if (value == 13)
            return "K" + suit;
        else
            return value + suit;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip() {
        if (isFaceUp)
            img = faceDown;
        else
            img = faceUp;

        isFaceUp = !isFaceUp;

    }

    public void resize(int width, int height) {
        faceUp.setFitWidth(width);
        faceUp.setFitHeight(height);
    }

    @Override
    public int compareTo(Card o) {
        Card c = (Card) o;

        if (getValue() == c.getValue())
            return 0;
        else if (getValue() < c.getValue())
            return -1;
        else
            return 1;
        
    }

    @Override
    public boolean equals(Object o) {
        Card c = (Card) o;

        return (this.compareTo(c) == 0 && getSuit().equals(c.getSuit()));

    }

}

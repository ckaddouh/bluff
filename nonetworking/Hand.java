package nonetworking;

import java.util.ArrayList;

public class Hand {
  private ArrayList<Card> hand = new ArrayList<Card>();

  public Hand(ArrayList<Card> hand) {
    this.hand = hand;
  }

  public Hand(Hand handOther) {
    for (int i = 0; i < handOther.getHand().size(); i++) {
      hand.add(handOther.getHand().get(i));
    }
  }

  public ArrayList<Card> getHand() {
    return hand;
  }

  private ArrayList<Card> merge(ArrayList<Card> listA, ArrayList<Card> listB) {
    ArrayList<Card> merged = new ArrayList<>();

    int a = 0;
    int b = 0;

    while (a < listA.size() && b < listB.size()) {
      if (listA.get(a).compareTo(listB.get(b)) < 0) {
        merged.add(listA.get(a++));
      } else {
        merged.add(listB.get(b++));
      }
    }

    while (a < listA.size()) {
      merged.add(listA.get(a++));
    }
    while (b < listB.size()) {
      merged.add(listB.get(b++));
    }

    return merged;
  }

  public ArrayList<Card> mergeSort(ArrayList<Card> list) {
    int s = list.size();
    if (s == 1) {
      return list;
    }

    ArrayList<Card> halfA = new ArrayList<>();
    ArrayList<Card> halfB = new ArrayList<>();
    for (int i = 0; i < s / 2; i++) {
      halfA.add(list.get(i));
    }
    for (int i = s / 2; i < s; i++) {
      halfB.add(list.get(i));
    }

    halfA = mergeSort(halfA);
    halfB = mergeSort(halfB);

    list = merge(halfA, halfB);

    return list;
  }

}

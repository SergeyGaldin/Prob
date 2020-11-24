package com.example.probsession.Adapters.Ad.CardAdapter;

public class Card {
    String num_card, money_card;

    public Card() {
    }

    public Card(String num_card, String money_card) {
        this.num_card = num_card;
        this.money_card = money_card;
    }

    public String getNum_card() {
        return num_card;
    }

    public void setNum_card(String num_card) {
        this.num_card = num_card;
    }

    public String getMoney_card() {
        return money_card;
    }

    public void setMoney_card(String money_card) {
        this.money_card = money_card;
    }
}

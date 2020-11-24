package com.example.probsession.Adapters.Ad.CardProfileAdapter;

public class CardProfile {
    String num_card, money_card, block;

    public CardProfile() {
    }

    public CardProfile(String num_card, String money_card, String block) {
        this.num_card = num_card;
        this.money_card = money_card;
        this.block = block;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
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

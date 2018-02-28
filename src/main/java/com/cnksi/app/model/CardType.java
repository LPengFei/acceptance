package com.cnksi.app.model;

import java.util.List;

@SuppressWarnings("serial")
public class CardType extends KBaseModel<CardType> {

    public static final CardType me = new CardType();


    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }

    public List<CardType> findAllForDropDown() {
        String sql = "select name, `key` as id, `key` as `key` from " + tableName + " where enabled=0 order by sort";
        return find(sql);
    }

    public CardType findCardTypeByKey(String key) {
        String sql = "select * from " + tableName + " where enabled = 0 and " + tableName + ".key = ? order by id";
        return (CardType)(find(sql, key).get(0));
    }
}

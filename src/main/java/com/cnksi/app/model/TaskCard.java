package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class TaskCard extends KBaseModel<TaskCard> {

    public static final TaskCard me = new TaskCard();
    public static final String[] mergeColumns = new String[]{"status", "cname", "flow", "type", "dtid", "gcmc", "sjdw", "ysdw", "ysrq", "sccj", "sbxh", "scgh", "ccbh", "bdsmc", "sbmcbh", "jldw", "sgdw", "citids"};

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }


    public List<CardItemType> getCardItemTypes() {
        Set<String> set = Optional.ofNullable(this.getStr("citids"))
                .filter(x -> x.length() > 0)
                .map(x -> new HashSet<>(Arrays.asList(x.split(","))))
                .orElseGet(HashSet::new);

        return CardItemType.me.find("select * from " + CardItemType.me.getTableName() + " where cid = ? and enabled=0", this.getStr("cid"))
                .stream()
                .filter(x -> set.isEmpty() || set.contains(x.getStr("id")))
                .collect(Collectors.toList());
    }
}

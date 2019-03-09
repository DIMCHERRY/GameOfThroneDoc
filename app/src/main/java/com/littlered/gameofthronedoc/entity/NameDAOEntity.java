package com.littlered.gameofthronedoc.entity;

import org.greenrobot.greendao.annotation.Entity;

import me.yokeyword.indexablerv.IndexableEntity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NameDAOEntity implements IndexableEntity {
    private String name;
    private String pinyin;

    @Generated(hash = 686766037)
    public NameDAOEntity(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    @Generated(hash = 922974538)
    public NameDAOEntity() {
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}

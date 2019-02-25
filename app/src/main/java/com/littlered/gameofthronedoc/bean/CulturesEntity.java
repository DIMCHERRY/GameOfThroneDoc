package com.littlered.gameofthronedoc.bean;

import me.yokeyword.indexablerv.IndexableEntity;

public class CulturesEntity implements IndexableEntity {
    /**
     * _id : 56fa992326c647376404c927
     * name : Northmen
     * __v : 0
     */

    public CulturesEntity() {
    }

    public CulturesEntity(String name) {
        this.name = name;
    }

    private String _id;
    private String name;
    private int __v;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    private String pinyin;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
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
}

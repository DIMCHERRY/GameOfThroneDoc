package com.littlered.gameofthronedoc.bean;

import java.util.List;

import me.yokeyword.indexablerv.IndexableEntity;

public class NamesEntity implements IndexableEntity {
    /**
     * _id : 56ffc5be043244081938576d
     * male : true
     * house : House Hightower
     * slug : Abelar_Hightower
     * name : Abelar Hightower
     * __v : 0
     * pageRank : 2.5
     * books : ["The Hedge Knight"]
     * updatedAt : 2016-04-02T13:14:38.834Z
     * createdAt : 2016-04-02T13:14:38.834Z
     * titles : ["Ser"]
     */

    private String _id;
    private boolean male;
    private String house;
    private String slug;
    private String name;
    private int __v;
    private double pageRank;
    private String updatedAt;
    private String createdAt;
    private List<String> books;
    private List<String> titles;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public double getPageRank() {
        return pageRank;
    }

    public void setPageRank(double pageRank) {
        this.pageRank = pageRank;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    private long id;
    private String pinyin;

    public NamesEntity() {
    }

    public NamesEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexByField) {
        this.name = indexByField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        this.pinyin = pinyin;
    }
}

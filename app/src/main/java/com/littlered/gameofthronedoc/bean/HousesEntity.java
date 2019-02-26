package com.littlered.gameofthronedoc.bean;

import java.util.List;

import me.yokeyword.indexablerv.IndexableEntity;

public class HousesEntity implements IndexableEntity {
    /**
     * _id : 56fa9ae5c9e167a3645523d1
     * imageLink : /misc/images/houses/House_Fell.png
     * overlord : House Baratheon
     * title : Felwood
     * region : the Stormlands
     * coatOfArms : Per fess, a white crescent moon on a black field above a green field, a sprucetree line between(Per fess sapinagé vert and sable, in dexter chief a moon increscent argent)
     * name : House Fell
     * __v : 0
     * updatedAt : 2016-03-29T15:10:29.573Z
     * createdAt : 2016-03-29T15:10:29.573Z
     * ancestralWeapon : []
     */

    private String _id;
    private String imageLink;
    private String overlord;
    private String title;
    private String region;
    private String coatOfArms;
    private String name;
    private int __v;
    private String updatedAt;
    private String createdAt;
    private List<?> ancestralWeapon;
    private String pinyin;

    public HousesEntity() {
    }

    public HousesEntity(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getOverlord() {
        return overlord;
    }

    public void setOverlord(String overlord) {
        this.overlord = overlord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCoatOfArms() {
        return coatOfArms;
    }

    public void setCoatOfArms(String coatOfArms) {
        this.coatOfArms = coatOfArms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
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

    public List<?> getAncestralWeapon() {
        return ancestralWeapon;
    }

    public void setAncestralWeapon(List<?> ancestralWeapon) {
        this.ancestralWeapon = ancestralWeapon;
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

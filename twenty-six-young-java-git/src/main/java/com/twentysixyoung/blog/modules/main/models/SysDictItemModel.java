package com.twentysixyoung.blog.modules.main.models;

import com.twentysixyoung.blog.modules.main.entity.SysDictItem;
import lombok.Data;

@Data
public class SysDictItemModel {

    /**
     * <p>
     * the short code or abbreviated English words
     * that stands for the dictionary-type Text Data
     * </p>
     */
    private String itemValue;

    /**
     * <p>
     *     the dictionary-type Text Data
     * </p>
     */
    private String itemText;

    /**
     * <p>
     *     this method functionally picks some fields value from the given param sysDictItem
     *     and then set them into this Class itself correspondingly
     * </p>
     * @param sysDictItem
     */
    public SysDictItemModel convertToModel(SysDictItem sysDictItem){
        if (null != sysDictItem){
            this.itemValue = sysDictItem.getItemValue();
            this.itemText = sysDictItem.getItemText();
            return this;
        }
        return this;
    }
}

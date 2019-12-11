package com.clown.sell.domain;

/**
 **类目
 *Create By Clown
 *2019-11-26 14:28
 */
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import lombok.Data;

@Entity
@Proxy(lazy = false)
@Data
@DynamicUpdate//动态更新
public class ProductCategory {
    
    /**类目id.*/
    @Id
    @GeneratedValue
    private Integer categoryId; 
    
    /**类目名字.*/
    private String categoryName;
    
    /**类目编号.*/
    private Integer categoryType;
    
    /**创建时间.*/
    private Date createTime;
    
    /**修改时间.*/
    private Date updateTime;
    
    
    /*
     * public Integer getCategoryId() { return categoryId; } public void
     * setCategoryId(Integer categoryId) { this.categoryId = categoryId; } public
     * String getCategoryName() { return categoryName; } public void
     * setCategoryName(String categoryName) { this.categoryName = categoryName; }
     * public Integer getCategoryType() { return categoryType; } public void
     * setCategoryType(Integer categoryType) { this.categoryType = categoryType; }
     * public Date getCreateTime() { return createTime; } public void
     * setCreateTime(Date createTime) { this.createTime = createTime; } public Date
     * getUpdateTime() { return updateTime; } public void setUpdateTime(Date
     * updateTime) { this.updateTime = updateTime; }
     */
    
//    @Override
//    public String toString() {
//	return "ProductCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryType="
//		+ categoryType + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
//    }    
}

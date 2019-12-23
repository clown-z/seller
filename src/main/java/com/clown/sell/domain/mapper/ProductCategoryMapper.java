package com.clown.sell.domain.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.clown.sell.domain.ProductCategory;

public interface ProductCategoryMapper {
    
    @Insert("INSERT INTO product_category(category_name, category_type, create_time, update_time) values(#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER}, #{createTime, jdbcType=TIMESTAMP}, #{updateTime, jdbcType=TIMESTAMP})")
    int insertByMap(Map<String, Object> map);
    
    @Insert("INSERT INTO product_category(category_name, category_type, create_time, update_time) "
    	+ "values(#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER}, #{createTime, jdbcType=TIMESTAMP}, #{updateTime, jdbcType=TIMESTAMP})")
    int insertByObject(ProductCategory productCategory);
    
    @Select("SELECT * FROM sell.product_category where category_type = #{categoryType, jdbcType=INTEGER}")
    @Results({
	@Result(column = "category_id", property = "categoryId"),
	@Result(column = "category_name", property = "categoryName"),
	@Result(column = "category_type", property = "categoryType"),
	@Result(column = "create_time", property = "createTime"),
	@Result(column = "update_time", property = "updateTime")
    })
    ProductCategory findByCategoryType(Integer categoryType);
    
    @Select("SELECT * FROM sell.product_category where category_id = #{categoryId}")
    @Results({
	@Result(column = "category_id", property = "categoryId"),
	@Result(column = "category_name", property = "categoryName"),
	@Result(column = "category_type", property = "categoryType"),
	@Result(column = "create_time", property = "createTime"),
	@Result(column = "update_time", property = "updateTime")
    })
    ProductCategory findByCategoryId(Integer categoryId);
    
    
    @Select("SELECT * FROM sell.product_category where category_name = #{categoryName}")
    @Results({
	@Result(column = "category_id", property = "categoryId"),
	@Result(column = "category_name", property = "categoryName"),
	@Result(column = "category_type", property = "categoryType"),
	@Result(column = "create_time", property = "createTime"),
	@Result(column = "update_time", property = "updateTime")
    })
    List<ProductCategory> findByCategoryName(String categoryName);
    
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByCategoryType(@Param("categoryName") String categoryName, 
	    @Param("categoryType") Integer categoryType);
    
    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByCategoryObject(ProductCategory productCategory);
    
    @Delete("DELETE FROM product_category where category_type = #{categoryType}")
    int deleteByCategoryType(Integer categoryType);
    
    
    ProductCategory selectByCategoryType(Integer categoryType);
}

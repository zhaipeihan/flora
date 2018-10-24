package cc.peihan.flora.demo.dao;

import cc.peihan.flora.demo.model.Item;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface ItemDao {
    @Select("select * from item where id = #{id}")
    @Results(id = "itemResult", value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "price", column = "price", javaType = Double.class, jdbcType = JdbcType.DOUBLE),
            @Result(property = "isValid", column = "is_valid", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
    })
    Item getById(Long id);
}

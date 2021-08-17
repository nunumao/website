package com._365d1.dao;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/3 05:59
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.model.SqlField;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ContentDao extends BaseMapper {

    @MapKey(value = "Field")
    Map<String, SqlField> showColumn(@Param(value = "table") String table);

    List<String> table4category(@Param(value = "list") List<Integer> list);

    IPage<HashMap> selectPage(
            @Param(value = "table") String table,
            @Param(value = "categoryId") Integer categoryId,
            @Param(value = "title") String title,
            IPage page
    );

    IPage<HashMap> selectAllPage(
            @Param(value = "list") List<Integer> list,
            @Param(value = "tables") String tables,
            IPage page
    );

    IPage<HashMap> select(
            @Param(value = "table") String table,
            @Param(value = "categoryId") String categoryId,
            @Param(value = "title") String title,
            IPage page
    );

    HashMap<String, Object> selectOne(
            @Param(value = "id") Integer id,
            @Param(value = "table") String table
    );

    Integer insert(
            @Param(value = "table") String table,
            @Param(value = "data") Map<String, Object> data
    );

    Integer update(
            @Param(value = "id") Integer id,
            @Param(value = "primary") String primary,
            @Param(value = "table") String table,
            @Param(value = "data") Map<String, Object> data
    );

    List<HashMap> top();

    @Select(value = "SELECT * FROM article WHERE `status` = 1 ORDER BY create_time DESC LIMIT 5")
    List<HashMap> sidebarNewList();

}

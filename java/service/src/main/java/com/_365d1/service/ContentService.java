package com._365d1.service;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/2 18:55
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ContentService {

    IPage<HashMap> queryPage(Integer categoryId, String title, Integer page);

    IPage<HashMap> queryAllPage(Integer categoryId, String title, Integer page);

    IPage<HashMap> queryPage(String table, String category, String title, Integer page);

    HashMap<String, Object> queryDetail(Integer categoryId, Integer id);

    boolean create(Integer modelId, Map<String, Object> contentMap);

    boolean modify(Integer id, Integer modelId, Map<String, Object> contentMap);

    List<HashMap> top();

    List<HashMap> sidebarNewList();

}

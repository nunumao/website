package com._365d1.service.impl;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 
// +----------------------------------------------------------------------
// | 时　　间: 2021/7/3 05:57
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V1.0.0
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import cn.hutool.core.util.StrUtil;
import com._365d1.common.utils.Pages;
import com._365d1.dao.*;
import com._365d1.model.*;
import com._365d1.service.ContentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ModelFieldDao modelFieldDao;

    @Autowired
    private TopDao topDao;

    @Override
    public IPage<HashMap> queryPage(Integer categoryId, String title, Integer page) {
        Category category = categoryDao.selectById(categoryId);
        // 栏目不存在
        if (ObjectUtils.isEmpty(category)) {
            return null;
        }
        Model model = modelDao.selectById(category.getModelId());
        if (ObjectUtils.isEmpty(model)) {
            return null;
        }
        return contentDao.selectPage(model.getTableName(), categoryId, title, new Page<>(page, 10));
    }

    @Override
    public IPage<HashMap> queryAllPage(Integer categoryId, String title, Integer page) {
        Category category = categoryDao.selectById(categoryId);
        // 栏目不存在
        if (ObjectUtils.isEmpty(category)) {
            return null;
        }
        // 栏目无下级
        List<Category> child = categoryDao.selectList(new QueryWrapper<Category>().eq(Category.PARENT_ID, category.getId()));
        if (child.size() <= 0) {
            return null;
        }
        List<Integer> childList = child.stream().map(Category::getId).collect(Collectors.toList());
        List<String> tableList = contentDao.table4category(childList);
        // 构建查询表
        StringBuilder sb = new StringBuilder();
        for (String table : tableList) {
            sb.append(String.format(" SELECT id,category_id,title,thumb,description,view_num,create_time FROM %s UNION", table));
        }
        String tableStr = StrUtil.removeSuffix(sb.toString(), "UNION");
        IPage<HashMap> list = contentDao.selectAllPage(childList, tableStr, new Page<>(page, 10));

        return list;
    }

    @Override
    public IPage<HashMap> queryPage(String table, String category, String title, Integer page) {
        IPage<HashMap> list = contentDao.select(table, category, title, new Pages(page, 10));
        return list;
    }

    @Override
    public HashMap<String, Object> queryDetail(Integer categoryId, Integer id) {
        Category category = categoryDao.selectById(categoryId);
        if (ObjectUtils.isEmpty(category)) {
            throw new RuntimeException("栏目不存在");
        }
        Model model = modelDao.selectById(category.getModelId());
        if (ObjectUtils.isEmpty(model)) {
            throw new RuntimeException("模型不存在");
        }
        HashMap<String, Object> detail = contentDao.selectOne(id, model.getTableName());
        if (!ObjectUtils.isEmpty(detail)) {
            return detail;
        }
        return null;
    }

    @Transactional
    @Override
    public boolean create(Integer modelId, Map<String, Object> contentMap) {

        Model model = modelDao.selectById(modelId);
        QueryWrapper<ModelField> fieldQuery = new QueryWrapper<>();
        fieldQuery.eq(ModelField.MODEL_ID, model.getId());
        List<ModelField> fieldList = modelFieldDao.selectList(fieldQuery);
        // 查询字段类型
        Map<String, SqlField> tableColumn = contentDao.showColumn(model.getTableName());
        Map<String, SqlField> extendMap = contentDao.showColumn(model.getTableName() + "_data");
        tableColumn.putAll(extendMap);

        Map<String, Object> main = new HashMap<>();
        main.put("id", "null");
        Map<String, Object> extend = new HashMap<>();
        for (String key : contentMap.keySet()) {
            ModelField field = fieldList.stream().filter(modelField -> modelField.getField().equals(key)).findAny().orElse(null);
            if (ObjectUtils.isEmpty(field)) {
                continue;
            }

            Object value;

            if (isQuoteField(key, tableColumn)) {
                value = StringUtils.quote(contentMap.get(key).toString());
            } else {
                value = contentMap.get(key);
            }

            if (field.getIsCore().equals(0)) {
                main.put(key, value);
            } else {
                extend.put(key, value);
            }
        }

        contentDao.insert(model.getTableName(), main);
        extend.put(model.getTableName() + "_id", main.get("id"));
        contentDao.insert(model.getTableName() + "_data", extend);

        // 推荐
        if (main.get("is_top").equals(1)) {
            Top top = new Top();
            top.setCategoryId(Integer.parseInt(main.get("category_id").toString()));
            top.setDetailId(Integer.parseInt(main.get("id").toString()));
            topDao.insert(top);
        }

        return true;
    }

    @Transactional
    @Override
    public boolean modify(Integer id, Integer modelId, Map<String, Object> contentMap) {

        Model model = modelDao.selectById(modelId);
        QueryWrapper<ModelField> fieldQuery = new QueryWrapper<>();
        fieldQuery.eq(ModelField.MODEL_ID, model.getId());
        List<ModelField> fieldList = modelFieldDao.selectList(fieldQuery);

        // 查询字段类型
        Map<String, SqlField> tableColumn = contentDao.showColumn(model.getTableName());
        Map<String, SqlField> extendMap = contentDao.showColumn(model.getTableName() + "_data");
        tableColumn.putAll(extendMap);

        Map<String, Object> main = new HashMap<>();
        Map<String, Object> extend = new HashMap<>();
        for (String key : contentMap.keySet()) {
            ModelField field = fieldList.stream().filter(modelField -> modelField.getField().equals(key)).findAny().orElse(null);
            if (ObjectUtils.isEmpty(field)) {
                continue;
            }

            Object value;

            if (isQuoteField(key, tableColumn)) {
                value = StringUtils.quote(contentMap.get(key).toString());
            } else {
                value = contentMap.get(key);
            }

            if (field.getIsCore().equals(0)) {
                main.put(key, value);
            } else {
                extend.put(key, value);
            }
        }

        contentDao.update(id, "id", model.getTableName(), main);

        contentDao.update(id, model.getTableName() + "_id", model.getTableName() + "_data", extend);

        return true;
    }

    @Override
    public List<HashMap> top() {
        return contentDao.top();
    }

    @Override
    public List<HashMap> sidebarNewList() {
        return contentDao.sidebarNewList();
    }

    private boolean isQuoteField(String column, Map<String, SqlField> tableColumn) {
        String type = tableColumn.get(column).getType().toLowerCase();
        if (StringUtils.countOccurrencesOf(type, "varchar") > 0) {
            return true;
        }
        if (StringUtils.countOccurrencesOf(type, "datetime") > 0) {
            return true;
        }
        if (StringUtils.countOccurrencesOf(type, "text") > 0) {
            return true;
        }
        return false;
    }

}

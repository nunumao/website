package com._365d1.service.impl;
// +----------------------------------------------------------------------
// | 官方网站: www.c2b666.com
// +----------------------------------------------------------------------
// | 功能描述: 西途比科技代码生成器
// +----------------------------------------------------------------------
// | 创建时间: 2021-07-11 13:15
// +----------------------------------------------------------------------
// | 代码创建: 朱荻 <292018748@qq.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com._365d1.dao.ModelDao;
import com._365d1.dao.ModelFieldDao;
import com._365d1.model.Model;
import com._365d1.model.ModelField;
import com._365d1.model.enums.FieldType;
import com._365d1.service.ModelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelDao, Model> implements ModelService {

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ModelFieldDao modelFieldDao;

    @Override
    public Integer create(Model model) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 插入模型数据
            int result = modelDao.insert(model);
            // 插入模型字段数据
            List<ModelField> list = new ArrayList<ModelField>() {{
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("栏目")
                        .setField("category_id")
                        .setType(FieldType.CATEGORY.getId())
                        .setMaxLength(11)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("标题")
                        .setField("title")
                        .setType(FieldType.TITLE.getId())
                        .setMaxLength(255)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("缩略图")
                        .setField("thumb")
                        .setType(FieldType.THUMB.getId())
                        .setMaxLength(500)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("关键词")
                        .setField("keywords")
                        .setType(FieldType.KEYWORDS.getId())
                        .setMaxLength(255)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("简介")
                        .setField("description")
                        .setType(FieldType.DESCRIPTION.getId())
                        .setMaxLength(255)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("内容")
                        .setField("content")
                        .setType(FieldType.EDITOR.getId())
                        .setMaxLength(0)
                        .setIsCore(1)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("源内容")
                        .setField("origin")
                        .setType(FieldType.ORIGIN.getId())
                        .setMaxLength(0)
                        .setIsCore(1)
                        .setIsSystem(1)
                        .setIsUnique(1)
                        .setIsHidden(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("浏览")
                        .setField("view_num")
                        .setType(FieldType.VIEW_NUM.getId())
                        .setMaxLength(11)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1)
                        .setIsHidden(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("推荐")
                        .setField("is_top")
                        .setType(FieldType.IS_TOP.getId())
                        .setMaxLength(1)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("创建时间")
                        .setField("create_time")
                        .setType(FieldType.CREATE_TIME.getId())
                        .setMaxLength(0)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
                add(new ModelField()
                        .setModelId(model.getId())
                        .setName("状态")
                        .setField("status")
                        .setType(FieldType.STATUS.getId())
                        .setMaxLength(1)
                        .setIsCore(0)
                        .setIsSystem(1)
                        .setIsUnique(1));
            }};

            for (ModelField modelField : list) {
                modelFieldDao.insert(modelField);
            }


            // 删除已存在的数据表
            modelDao.deleteModelTable(model.getTableName());
            modelDao.deleteModelExtendTable(model.getTableName());
            // 创建新数据表
            modelDao.insertModelTable(model.getName(), model.getTableName());
            modelDao.insertModelExtendTable(model.getName(), model.getTableName());

            transactionManager.commit(status);
            return model.getId();
        } catch (Exception e) {
            transactionManager.rollback(status);
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public boolean removeSingle(String id) {
        Model model = modelDao.selectById(id);
        if (!ObjectUtils.isEmpty(model)) {
            modelDao.deleteModelTable(model.getTableName());
            modelDao.deleteModelExtendTable(model.getTableName());
        }
        modelFieldDao.delete(new QueryWrapper<ModelField>().eq(ModelField.MODEL_ID, model.getId()));
        int result = modelDao.deleteById(id);
        return result > 0;
    }
}
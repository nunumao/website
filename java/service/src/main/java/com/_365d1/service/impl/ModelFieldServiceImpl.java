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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com._365d1.dao.ModelFieldDao;
import com._365d1.model.ModelField;
import com._365d1.service.ModelFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelFieldServiceImpl extends ServiceImpl<ModelFieldDao, ModelField> implements ModelFieldService {

    @Autowired
    private ModelFieldDao modelFieldDao;

    @Override
    public Integer create(ModelField modelField) {
        int result = modelFieldDao.insert(modelField);
        if (result > 0) {
            return modelField.getId();
        }
        return null;
    }

}
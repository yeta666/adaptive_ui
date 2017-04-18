package com.adaptive.ui.service;

import com.adaptive.ui.domain.Model;
import com.adaptive.ui.repositary2.ModelRepositary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模型的逻辑处理类
 * Created by yeta on 2017/4/17/017.
 */
@Service
public class ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    @Autowired
    private ModelRepositary modelRepositary;

    /**
     * 增加一个模型的方法
     * @param model
     * @return
     */
    public Model save(Model model){
        return modelRepositary.save(model);
    }

    /**
     * 通过id查询一个模型的方法
     * @param id
     * @return
     */
    public Model findOne(Integer id){
        return modelRepositary.findOne(id);
    }

    /**
     * 查询全部模型的方法
     * @return
     */
    public List<Model> findAll(){
        return modelRepositary.findAll();
    }

    /**
     * 通过type查询一类模型的方法
     * @param type
     * @return
     */
    public List<Model> findByType(String type){
        return modelRepositary.findByType(type);
    }
}

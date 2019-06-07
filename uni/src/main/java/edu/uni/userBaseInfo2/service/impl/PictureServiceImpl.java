package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.Picture;
import edu.uni.userBaseInfo2.mapper.PictureMapper;
import edu.uni.userBaseInfo2.service.PictureService;
import edu.uni.userBaseInfo2.service.model.PictureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public boolean insert(Picture picture) {
        return pictureMapper.insert(picture)>0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        return pictureMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean update(Picture picture) {
        return pictureMapper.updateByPrimaryKeySelective(picture) > 0 ? true : false;
    }

    @Override
    public Picture select(long type) {
        return null;
    }

    @Override
    public List<PictureModel> selectAll(long id) {
        List<Picture> pictures = pictureMapper.selectByUserId(id);
        List<PictureModel> pictureModels = convertFromBean(pictures);
        System.out.println(pictureModels);
        return pictureModels;
    }

    private List<PictureModel> convertFromBean(List<Picture> pictures){
        List<PictureModel> pictureModels = new ArrayList<>();
        for(int i = 0 ;i < pictureModels.size();i++){
            PictureModel pictureModel = new PictureModel();
            pictureModel.setUniversityId(pictureModels.get(i).getUniversityId().toString());
            pictureModel.setPictureName(pictureModels.get(i).getPictureName());
            pictureModel.setFlag(pictureModels.get(i).getFlag());
            pictureModels.add(pictureModel);
        }
        return pictureModels;
    }
}

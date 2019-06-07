package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.Ecomm;
import edu.uni.userBaseInfo2.mapper.EcommMapper;
import edu.uni.userBaseInfo2.service.EcommService;
import edu.uni.userBaseInfo2.service.model.EcommModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EcommServiceImpl implements EcommService {
    @Autowired
    private EcommMapper ecommMapper;

    @Override
    public boolean insert(Ecomm ecomm) {
        return ecommMapper.insert(ecomm) > 0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        return ecommMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean update(Ecomm ecomm) {
        return ecommMapper.updateByPrimaryKeySelective(ecomm) > 0 ? true : false;
    }

    @Override
    public boolean updateByUserIdAndFlag(long id, int flag) {
        return ecommMapper.updateByUserIdAndFlag(id,flag) > 0 ? true : false;
    }

    @Override
    public boolean updateTrueById(long id) {
        return ecommMapper.updateTrueById(id) > 0 ? true : false;
    }

    @Override
    public Ecomm select(long id) {
        return ecommMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据user_id 查找用户所有有效电子通讯方式信息
     * @param id
     * @return
     */
    @Override
    public List<EcommModel> selectAll(long id) {
        List<Ecomm> ecomm = ecommMapper.selectByUserIdAndDelete(id);
        List<EcommModel> ecommModela = convertFromBean(ecomm);
        System.out.println(ecommModela);

        return ecommModela;
    }
    private List<EcommModel> convertFromBean(List<Ecomm> ecomm) {
        List<EcommModel> ecommModels = new ArrayList<>();

        int flag = 6;   //通讯方式有6种
        for(int i=0; i<flag; i++) {
            EcommModel ecommModel = new EcommModel();
            ecommModel.setFlag(i);
            for (int j=0; j<ecomm.size(); j++) {
                if(i == ecomm.get(j).getFlag()){
                    ecommModel.setId(ecomm.get(j).getId());
                    ecommModel.setContent(ecomm.get(j).getContent());
                    break;
                }
            }
            ecommModels.add(ecommModel);
        }
        return ecommModels;
    }
}

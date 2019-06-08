package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.Address;
import edu.uni.userBaseInfo2.mapper.*;
import edu.uni.userBaseInfo2.service.AddressService;
import edu.uni.userBaseInfo2.service.model.AddressModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper ;
    @Autowired
    private AddrCountryMapper addrCountryMapper;
    @Autowired
    private AddrStateMapper addrStateMapper;
    @Autowired
    private AddrCityMapper addrCityMapper;
    @Autowired
    private AddrAreaMapper addrAreaMapper;
    @Autowired
    private AddrStreetMapper addrStreetMapper;

    @Override
    public boolean insert(Address address) {
        return addressMapper.insert(address) > 0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        return addressMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean update(Address address) {
        return addressMapper.updateByPrimaryKeySelective(address) > 0 ? true : false;
    }

    @Override
    public boolean updateTrueById(long id) {
        return addressMapper.updateTrueById(id) > 0 ? true : false;
    }

    @Override
    public boolean updateById(long id) {
        return addressMapper.updateById(id) > 0 ? true : false;
    }

//    @Override
//    public boolean updateByUserIdAndFlag(long id, int flag) {
//        return addressMapper.updateByUserIdAndFlag(id,flag) > 0 ? true : false;
//    }

    @Override
    public Address select(long id) {
        return addressMapper.selectByPrimaryKey(id);
    }


    @Override
    public Address selectByUserIdAndFlag(long userId, int flag) {

        return addressMapper.selectByUserIdAndFlag(userId,flag);
    }

    @Override
    public AddressModel selectByFlag(int flag) {
        //保留。
        return null;
    }

    /**
     * 根据user_id 查找用户所有有效地址信息
     * @param id
     * @return
     */
    @Override
    public List<AddressModel> selectAll(long id) {
        List<Address> address = addressMapper.selectByUserIdAndDelete(id);
        List<AddressModel> addressModela = convertFromBean(address);

        return addressModela;
    }
    private List<AddressModel> convertFromBean(List<Address> address) {
        List<AddressModel> addressModels = new ArrayList<>();

        int flag = 5;   //地址有5种类型
        for(int i=0;i<flag;i++){
            AddressModel addressModel = new AddressModel();
            addressModel.setFlag(i);
            for(int j=0;j<address.size();j++){
                if(address.get(j).getFlag() == i) {
                    addressModel.setId(address.get(j).getId());
                    addressModel.setCountry(addrCountryMapper.selectByPrimaryKey(address.get(j).getCountry()).getCountryZh());
                    addressModel.setState(addrStateMapper.selectByPrimaryKey(address.get(j).getState()).getStateZh());
                    addressModel.setCity(addrCityMapper.selectByPrimaryKey(address.get(j).getCity()).getCityZh());
                    addressModel.setArea(addrAreaMapper.selectByPrimaryKey(address.get(j).getArea()).getAreaZh());
                    addressModel.setStreet(addrStreetMapper.selectByPrimaryKey(address.get(j).getStreet()).getStreetZh());
                    addressModel.setDetail(address.get(j).getDetail());
                    addressModel.setZipCode(address.get(j).getZipCode());
                    addressModel.setTelephone(address.get(j).getTelephone());
                    break;
                }
        }
            addressModels.add(addressModel);
        }

//        for(int i=0;i<address.size();i++){
//
//            AddressModel addressModel = new AddressModel();
//            addressModel.setId(address.get(i).getId());
//            addressModel.setCountry(addrCountryMapper.selectByPrimaryKey(address.get(i).getCountry()).getCountryZh());
//            addressModel.setState(addrStateMapper.selectByPrimaryKey(address.get(i).getState()).getStateZh());
//            addressModel.setCity(addrCityMapper.selectByPrimaryKey(address.get(i).getCity()).getCityZh());
//            addressModel.setArea(addrAreaMapper.selectByPrimaryKey(address.get(i).getArea()).getAreaZh());
//            addressModel.setStreet(addrStreetMapper.selectByPrimaryKey(address.get(i).getStreet()).getStreetZh());
//            addressModel.setDetail(address.get(i).getDetail());
//            addressModel.setZipCode(address.get(i).getZipCode());
//            addressModel.setTelephone(address.get(i).getTelephone());
//            addressModel.setFlag(address.get(i).getFlag());
//            addressModels.add(addressModel);
//        }
        return addressModels;
    }
}

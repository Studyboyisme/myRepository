package com.itheima.mp.mapper;

import com.itheima.mp.entity.Address;
import com.itheima.mp.service.IAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressMapperTest {
    @Resource
    private AddressMapper addressMapper;

    @Resource
    private IAddressService addressService;

    @Test
    void testDeleteByLogic() {
        // 删除方法与以前没有区别
        addressService.removeById(59L);
    }

    @Test
    void testQuery() {
        List<Address> list = addressService.list();
        list.forEach(System.out::println);
    }


}
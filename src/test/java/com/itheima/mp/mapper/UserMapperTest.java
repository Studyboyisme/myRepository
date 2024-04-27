package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
//@MapperScan("com.itheima.mp.mapper")
class UserMapperTest {

    @Resource
    private UserMapper userMapper;
    @Resource
    private IUserService userService;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(4L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }

    /**
     *  条件构造器QueryWrapper
     */
    @Test
    void testQueryWrapper(){
        //1 构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);
        //2 查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateByQueryWrapper(){
        //1 要更新的数据
        User user = new User();
        user.setBalance(2000);
        //2 更新的条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username", "jack");
        //3 执行更新
        userMapper.update(user, wrapper);
    }

    /**
     * LambdaQueryWrapper避免硬编码
     */
    @Test
    void testLambdaQueryWrapper(){
        //1 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getInfo, User::getBalance)
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000);
        //2 查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * UpdateWrapper在set语句比较特殊的情况下使用
     */
    @Test
    void testUpdateWrapper(){
        List<Long> ids = List.of(1L, 2L, 3L);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id", ids);
        userMapper.update(null,wrapper);
    }

    /**
     * 自定义sql
     */
    @Test
    void testCustomSqlUpdate(){
        //1 更新条件
        List<Long> ids = List.of(1L, 2L, 3L);
        int amount = 200;
        //2 定义条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().in("id",ids);
        //3 调用自定义的SQL方法
        userMapper.updateBalanceByIds(wrapper, amount);
    }

    @Test
    void testService() {
        List<User> list = userService.list();
        list.forEach(System.out::println);
    }

    /**
     * 分页查询测试
     */
    @Test
    void testPageQuery() {
        // 1.分页查询，new Page()的两个参数分别是：页码、每页大小
        int pageNo = 2, pageSize = 2;
        Page<User> p = userService.page(new Page<>(pageNo, pageSize));

        p.addOrder(new OrderItem("balance", true));
        p.addOrder(new OrderItem("id", true));

        // 2.总条数
        System.out.println("total = " + p.getTotal());
        // 3.总页数
        System.out.println("pages = " + p.getPages());
        // 4.数据
        List<User> records = p.getRecords();
        records.forEach(System.out::println);
    }
}
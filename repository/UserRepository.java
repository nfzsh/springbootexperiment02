package com.example.springbootexperiment02.repository;

import com.example.springbootexperiment02.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional
@Slf4j
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * 添加用户，并返回包括数据库时间戳的用户对象
     * @param user
     * @return
     */
    public User addUser(User user) {
        em.persist(user);
        em.refresh(user);
        return user;
    }

    /**
     * 添加地址，并指定地址对应的用户
     * @param address
     * @param uid
     * @return
     */
    public Address addAddress(Address address, int uid) {

        User u = em.find(User.class, uid);
        //em.refresh(u);
        address.setUser(u);
        em.persist(address);
        em.refresh(address);
        return address;
    }
    /**
     * 更新指定ID用户的姓名
     * @param uid
     * @param newName
     * @return
     */
    public User updateUser(int uid, String newName) {
        User u1 = em.find(User.class, uid);
        u1.setName(newName);
        em.flush();
        return u1;
    }

    /**
     * 尝试使用merge()，以及find()2种方法分别实现
     * 更新指定地址为指定用户
     * @param aid
     * @param uid
     * @return
     */
    public Address updateAddress(int aid, int uid) {
        User u2 = em.find(User.class, uid);
        Address a1 = new Address();
        a1.setId(aid);
        Address a2 = em.merge(a1);
        em.refresh(a2);
        a2.setUser(u2);
        return null;
    }

    /**
     * 返回指定用户的全部地址，没有返回空集合，而非null
     * @param uid
     * @return
     */
    public List<Address> listAddresses(int uid) {
        return List.of();
    }

    public void removeAddress(int aid) {

    }

    /**
     * 删除用户，设置级联操作或手动删除相关地址
     * @param uid
     */
    public void remaveUser(int uid) {

    }
}

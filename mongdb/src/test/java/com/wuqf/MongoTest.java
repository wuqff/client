package com.wuqf;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.WriteResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config-mongodb.xml")
public class MongoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private MongoTemplate mongoTemplate;

//    @Test
//    public void testAddDoc() {
//        Person p = new Person("Joe", 34);
//        Person p2 = new Person("1001", "Joe", 34);
//        Person p11 = new Person("1011", "zhangsan", 20);
//        Person p12 = new Person("1012", "zhangsan2", 21);
//        Person p13 = new Person("1013", "zhangsan3", 23);
//        List<Person> list = new ArrayList<Person>();
//        list.add(p11);
//        list.add(p12);
//        list.add(p13);
//        this.mongoTemplate.insert(p);// 默认保存在person集合中(与类名称一致)
//        this.mongoTemplate.insert(p2, "person2");// 指定保存在person2集合中
//        this.mongoTemplate.insertAll(list);// 默认保存在person集合中(与类名称一致)
//        // mongoTemplate.insert(list, collectionName);//指定保存的集合
//        // mongoTemplate.insert(list, Person.class);// 默认保存在person集合中(与类名称一致)
//    }

    @Test
    public void testFindDoc() {
        // 根据id查询,此id为mongo生成的id
        Person person = this.mongoTemplate.findById("1011", Person.class);
        System.out.println(person);

        // 使用query对象查询
        Query query = new Query(Criteria.where("age").is(34));
        person = this.mongoTemplate.findOne(query, Person.class);
        System.out.println(person);

        // 使用query对象查询列表
        query = new Query(Criteria.where("age").lt(30)).// age小于30
                with(new Sort(Direction.ASC, "age"));// age 升序
        // query.with(Pageable );//可分页查询

        List<Person> list = this.mongoTemplate.find(query.with(new Sort(Direction.ASC, "age")), Person.class);
        System.out.println(list);
    }

    @Test
    public void testUpdateDoc() {
        // age为34的person，age加1
        Query query = new Query(Criteria.where("id").is(1011));
        Update update = new Update().inc("age", 1);
        Person p = this.mongoTemplate.findAndModify(query, update, Person.class);
        System.out.println(p);

        p = this.mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Person.class);// returnNew(true)将更新后的对象返回
        System.out.println(p);

        // 将age为21的name改成zhangsan22，如果有多个age为21的，则只改变第一个
        query = new Query(Criteria.where("age").is(21));
        update = new Update().set("name", "zhangsan22");
        WriteResult result = this.mongoTemplate.updateFirst(query, update, Person.class);
        System.out.println(result);

        // 将age为21的name改成zhangsan22，如果有多个age为21的，全部更新
        result = mongoTemplate.upsert(query, update, Person.class);
        System.out.println(result);
    }


    @Test
    public void testRemoveDoc() {
        Query query = new Query(Criteria.where("age").is(35));
        // mongoTemplate.findAndRemove(query, Person.class);//删除文档
        // mongoTemplate.findAndRemove(query, Person.class,
        // collectionName)//删除指定集合内的文档
        Person person = this.mongoTemplate.findOne(query, Person.class);
        WriteResult result = this.mongoTemplate.remove(person);
        System.out.println(result);
        this.mongoTemplate.remove(query, Person.class);// 根据query对象删除文档

        // mongoTemplate.remove(person, collection);//从指定的集合中删除文档
        // mongoTemplate.remove(query, collectionName);//从指定的集合中根据query对象删除文档
        // mongoTemplate.remove(query, entityClass, collectionName)//从指定的集合中根据query对象和class类型删除文档

    }

}
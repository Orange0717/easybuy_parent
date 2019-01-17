package cn.itsource.easybuy.service.impl;

import cn.itsource.easybuy.domain.ProductType;
import cn.itsource.easybuy.mapper.ProductTypeMapper;
import cn.itsource.easybuy.server.RedisServer;
import cn.itsource.easybuy.server.StaticPageServer;
import cn.itsource.easybuy.service.IProductTypeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author easybuy_editor
 * @since 2019-01-14
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private StaticPageServer staticPageServer;

    private final String TREEDATAKEY = "treeDataKey";

    @Override
    public List<ProductType> getTreeData(Long pid) {
        //获取treeData：先从RedisServer中获取
        String redisCache = redisServer.getRedisCache(TREEDATAKEY);
        //缓存查询到的字符串不为空，那么就转换为对象，返回
        if(StringUtils.isNotBlank(redisCache)){
            System.out.println("这个数据从缓存获取11111111111111111111");
            return JSONArray.parseArray(redisCache, ProductType.class);
        }
        //缓存查询到的为空，那么就从数据库查询

        /*
        //获取treeData方式1：使用递归方式获取自己的子类型，会发送多条sql，不采用，时间过长超过断路器熔断时间
        List<ProductType> children = getChildren(pid);
        //递归循环获取子类型的子类型...（递归：①方法内部调用方法自己；②必须有一个出口）
        if(children==null || children.size()<1){
            return null;
        }
        for (ProductType child : children) {
            //自己的id作为pid，调用自己获取子类型
            getTreeData(child.getId());
        }
        return children;
        */

        //获取treeData的方式2：先查询出所有的类型，遍历，有父类型，就加入到父类型集合中
        List<ProductType> treeData = new ArrayList<>();
        //①获取所有的类型
        List<ProductType> allType = productTypeMapper.selectList(null);
        //②准备一个集合，以id为key，productType为value
        Map<Long,ProductType> map = new HashMap<>();
        for (ProductType productType : allType) {
            map.put(productType.getId(), productType);
        }
        //这个两次循环要分开，注意10+10与10*10的区别，提高性能
        for (ProductType productType : allType) {
            if(productType.getPid().longValue()==0){
                //pid为0，表示这个是顶级类型
                treeData.add(productType);
            }else {
                //pid不是0、那么根据pid拿到父类类型，将自己加入到父类类型的子类集合中
                //【注意的是：通过pid拿父类不是通过数据库查询，而是在事先准备好的集合中直接获取】
                map.get(productType.getPid()).getChildren().add(productType);
            }
        }
        //将从数据库查询到的数据存入缓存，然后返回
        redisServer.setRedisCache(TREEDATAKEY, JSONObject.toJSONString(treeData));
        System.out.println("这个数据从数据库获取222222222222222222222222");
        return treeData;
    }

    private List<ProductType> getChildren(long pid) {
        EntityWrapper<ProductType> wrapper = new EntityWrapper<>();
        wrapper.eq("pid", pid);
        return productTypeMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean insert(ProductType entity) {
            super.insert(entity);
            synchronizeCacheAndPage();
            return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
            super.deleteById(id);
            //int i = 1/0;  测试事务
            synchronizeCacheAndPage();
            return true;
    }

    @Override
    @Transactional
    public boolean updateById(ProductType entity) {
            super.updateById(entity);
            synchronizeCacheAndPage();
            return true;
    }

    /**
     * 覆写基本的增删改方法：增删改之后需要对缓存与静态主页进行更新
     */


    //在完成数据的增删改后，需要更新缓存数据与静态页面，因此抽取一个更新缓存与静态页面的方法
    public void synchronizeCacheAndPage(){
        //①更新缓存：先清空缓存，再重新查询数据，设置到缓存
        redisServer.setRedisCache(TREEDATAKEY, "");
        List<ProductType> treeData = getTreeData(0L);
        redisServer.setRedisCache(TREEDATAKEY, JSONObject.toJSONString(treeData));
        //②重新生成静态页面
        //2.1 先将类型数据treedata进行静态化，生成类型模板页面
        //model数据是根据模板需要而传入的，不固定类型与值，具体看模板需要
        Map<String,Object> typeModel = new HashMap<>();
        typeModel.put("model", treeData);
        typeModel.put("templatePath", "D:\\idea-workspace\\easybuy_parent\\easybuy06_product\\easybuy_product_server_8002\\src\\main\\resources\\template\\product.type.vm");
        typeModel.put("staticPagePath", "D:\\idea-workspace\\easybuy_parent\\easybuy06_product\\easybuy_product_server_8002\\src\\main\\resources\\template\\product.type.vm.html");
        staticPageServer.createStaticPage(typeModel);
        //2.2 再在主页的模板，通过导入生成的静态类型模板页面，生成静态主页
        Map<String,Object> homeModel = new HashMap<>();
        Map home = new HashMap();
        home.put("staticRoot", "D:\\idea-workspace\\easybuy_parent\\easybuy06_product\\easybuy_product_server_8002\\src\\main\\resources\\");
        homeModel.put("model", home);
        homeModel.put("templatePath", "D:\\idea-workspace\\easybuy_parent\\easybuy06_product\\easybuy_product_server_8002\\src\\main\\resources\\template\\home.vm");
        homeModel.put("staticPagePath", "D:\\idea-workspace\\easybuy_web_parent\\easybuy_web_mall\\home.html");
        staticPageServer.createStaticPage(homeModel);
    }
}

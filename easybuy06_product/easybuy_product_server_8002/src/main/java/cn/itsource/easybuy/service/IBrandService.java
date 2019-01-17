package cn.itsource.easybuy.service;

import cn.itsource.easybuy.domain.Brand;
import cn.itsource.easybuy.query.BrandQuery;
import cn.itsource.easybuy.util.PageList;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author easybuy_editor
 * @since 2019-01-14
 */
public interface IBrandService extends IService<Brand> {

    //我们自己定义的对分页查询与高级查询结合的方法
    PageList<Brand> selectPageList(BrandQuery query);
}

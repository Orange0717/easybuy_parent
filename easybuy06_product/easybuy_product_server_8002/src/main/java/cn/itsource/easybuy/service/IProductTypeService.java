package cn.itsource.easybuy.service;

import cn.itsource.easybuy.domain.ProductType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author easybuy_editor
 * @since 2019-01-14
 */
public interface IProductTypeService extends IService<ProductType> {

    //获取商品类型tree数据
    List<ProductType> getTreeData(Long pid);
}

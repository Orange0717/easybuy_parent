package cn.itsource.easybuy.mapper;

import cn.itsource.easybuy.domain.Brand;
import cn.itsource.easybuy.query.BrandQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author easybuy_editor
 * @since 2019-01-14
 */
public interface BrandMapper extends BaseMapper<Brand> {

    //自定义的分页查询+高级查询方法：page只是让插件mybatisplus知道是分页查询，query才是真实参数
    List<Brand> getPageList(Page<Brand> page, BrandQuery query);
}

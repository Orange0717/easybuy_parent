package cn.itsource.easybuy.service.impl;

import cn.itsource.easybuy.domain.Brand;
import cn.itsource.easybuy.mapper.BrandMapper;
import cn.itsource.easybuy.query.BrandQuery;
import cn.itsource.easybuy.service.IBrandService;
import cn.itsource.easybuy.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author easybuy_editor
 * @since 2019-01-14
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageList<Brand> selectPageList(BrandQuery query) {
        //1、创建page对象
        Page<Brand> page = new Page<>(query.getPage(),query.getRows());
        //2、将page与query传入我们定义好的brandMapper中的方法进行查询
        //【page只是让插件知道是分页查询，会将total自动封装到page中，query是高级查询条件】
        List<Brand> rows = brandMapper.getPageList(page,query);
        //3、从page获取total，将total与rows一起封装到PageList对象返回
        long total = page.getTotal();

        return new PageList<>(total,rows);
    }
}

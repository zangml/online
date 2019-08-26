package com.koala.learn.service;

import com.koala.learn.dao.CatalogMapper;
import com.koala.learn.entity.Catalog;
import com.koala.learn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Catalog 服务.
 * 
 * @since 1.0.0 2017年4月10日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class CatalogServiceImpl implements CatalogService{

	@Autowired
	private CatalogMapper catalogMapper;
	
	@Override
	public int saveCatalog(Catalog catalog) {
		// 判断重复
		Catalog catalog1 = catalogMapper.findByUserIdAndName(catalog.getUserId(), catalog.getName());
		if(catalog1 !=null) {
			throw new IllegalArgumentException("该分类已经存在了");
		}
		return catalogMapper.save(catalog);
	}

	@Override
	public void removeCatalog(Integer id) {
		catalogMapper.deleteById(id);
	}

	@Override
	public Catalog getCatalogById(Integer id) {
		return catalogMapper.getOne(id);
	}

	@Override
	public List<Catalog> listCatalogs(User user) {
		return catalogMapper.findAllByUserId(user.getId());
	}

}

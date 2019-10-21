package com.koala.learn.service;


import com.koala.learn.entity.Catalog;
import com.koala.learn.entity.User;

import java.util.List;

/**
 * Catalog 服务接口.
 * 
 * @since 1.0.0 2017年4月10日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface CatalogService {
	/**
	 * 保存Catalog
	 * @param catalog
	 * @return
	 */
	int saveCatalog(Catalog catalog);
	
	/**
	 * 删除Catalog
	 * @param id
	 * @return
	 */
	void removeCatalog(Integer id);

	/**
	 * 根据id获取Catalog
	 * @param id
	 * @return
	 */
	Catalog getCatalogById(Integer id);
	
	/**
	 * 获取Catalog列表
	 * @return
	 */
	List<Catalog> listCatalogs(User user);
}

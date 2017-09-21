package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	TbItemMapper tbItemMapper;
	 
	@Override
	public TbItem queryTbItemByid(Long id) {
	/*	return tbItemMapper.selectByPrimaryKey(id);*/
		
		TbItemExample tbItemExample = new TbItemExample();
		Criteria criteria = tbItemExample.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> resultItems = tbItemMapper.selectByExample(tbItemExample);
		if(resultItems!=null&&resultItems.size()>0) {
			return resultItems.get(0);
		}
		return null;
	}
	

}
	
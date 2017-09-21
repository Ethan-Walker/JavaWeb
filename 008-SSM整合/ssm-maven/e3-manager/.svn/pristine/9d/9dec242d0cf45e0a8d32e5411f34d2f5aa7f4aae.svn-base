package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@RequestMapping("/item/{itemId}") /* url 模板映射，将其赋值给参数*/
	@ResponseBody
	public TbItem queryItemById(@PathVariable Long itemId) {
		TbItem item = itemService.queryTbItemByid(itemId);
		System.out.println(item);
		return item;
	}
	
}

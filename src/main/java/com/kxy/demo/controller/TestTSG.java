package com.kxy.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxy.demo.model.User;
import com.kxy.demo.util.TreeNode;
import com.kxy.demo.util.TreeUtils;

@Controller
@RequestMapping("api/test")
public class TestTSG {

	@RequestMapping("test")
	@ResponseBody
	private List<TreeNode<User>> test() throws Exception{
		List<User> list = new ArrayList<>();
		list.add(new User("1","11","0",1));
		list.add(new User("2","12","0",1));
		list.add(new User("3","13","1",2));
		list.add(new User("4","14","2",2));
		list.add(new User("5","15","3",3));
		list.add(new User("6","16","3",3));
		List<TreeNode<User>> treeList = TreeUtils.getTreeNode(list, "parentId", null, "level", 1, "id");
		return treeList;
	}
}

package com.kxy.demo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TreeUtils {

	/**
	 * 获取树形结构数据，传值示例：getTreeNode(soures, "parentId", "0", null, null, "id"),
	 * 				 或    者：getTreeNode(soures, "parentId", null, "level", 1, "id"),
	 * @param soures	数据源
	 * @param parentField	公共字段名，例如：parentId,通过parentId来分级，not null
	 * @param rooParentId	第一级parentId的值，通过该值来获取第一级，可传递null,第一级可通过level值取
	 * @param levelFiled	公共字段名，例如：level，通过level来分级，此为null时，rootParentId不能为null，不然无法分级
	 * @param rootLevel		第一级level的值，levelFiled不为null时，该值不为null
	 * @param idField		公共字段名，例如：id，主键id，不为null
	 * @return
	 * @throws Exception
	 */
	public static <T> List<TreeNode<T>> getTreeNode(List<T> soures, String parentField, String rooParentId, 
			String levelFiled, Integer rootLevel, String idField) throws Exception{
		List<TreeNode<T>> treeNodes = new ArrayList<>();
		if(null == soures || soures.size() == 0){
			return treeNodes;
		}
		if(isBlank(parentField)){
			throw new Exception("父级节点字段不能为空");
		}
		if(isBlank(idField)){
			throw new Exception("主键id字段不能为空");
		}
		if(isBlank(levelFiled)){
			if(null == rooParentId || rooParentId.equals("")){
				throw new Exception("等级字段值为空时，root级节点的值不能为空");
			}
		}else{
			if(null == rootLevel){
				throw new Exception("第一级等级的值不能为空");
			}
		}
		//提取parentId,避免取值时重复提取
		List<Object> parentIds = getAllParentIdFromSoures(soures, parentField);
		for(T t: soures){
			TreeNode<T> node = new TreeNode<>();
			@SuppressWarnings("rawtypes")
			Class c = t.getClass();
			
			//有level时,先用level判断
			if(null != levelFiled && !levelFiled.equals("")){
				
				//通过等级字段反射获取等级的值
				Field lFile =  c.getDeclaredField(levelFiled);
				lFile.setAccessible(true);
				Object level= lFile.get(t);
				
				//获取第一级
				if(null != level && level.equals(rootLevel)){
					node.setNode(t);
					
					//获取子节点
					getChildNode(soures, parentIds, node, parentField, idField);
					treeNodes.add(node);
				}
			}else{
				//通过父级节点字段获取父级节点
				Field pFile =  c.getDeclaredField(parentField);
				pFile.setAccessible(true);
				Object parentId= pFile.get(t);
				
				//获取第一级
				if(null != rooParentId && parentId.equals(rooParentId)){
					node.setNode(t);
					getChildNode(soures, parentIds, node, parentField, idField);
					treeNodes.add(node);
				}
			}
		}
		return treeNodes;
	}

	/**
	 * 通过递归的方式获取子节点
	 * @param soures
	 * @param node
	 * @param parentField
	 * @param idField
	 * @throws Exception
	 */
	private static<T> void getChildNode(List<T> soures, List<Object> parentIds, TreeNode<T> node, String parentField,
			String idField) throws Exception{
		List<TreeNode<T>> firstChild = getChild(soures, parentIds, node.getNode(), parentField, idField);
		node.setChidNode(firstChild);
		
		for(TreeNode<T> tNode : firstChild){
			if(ishaveChild(tNode.getNode(), idField, parentIds)){
				getChildNode(soures, parentIds, tNode, parentField, idField);
			}
		}
	}


	/**
	 * 通过父节点来获取子节点
	 * @param soures
	 * @param pt
	 * @param parentField
	 * @param idField
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static<T> List<TreeNode<T>> getChild(List<T> soures, List<Object> parentIds, T pt, 
			String parentField, String idField) throws Exception {
		List<TreeNode<T>> chidNodes = new ArrayList<>();
		
		Class pCt = pt.getClass();
		Field ptField = pCt.getDeclaredField(idField);
		ptField.setAccessible(true);
		Object id = ptField.get(pt);
		
		for(int i = 0; i < parentIds.size(); i++){
			if(parentIds.get(i).equals(id)){
				TreeNode<T> chidNode = new TreeNode<>();
				chidNode.setNode(soures.get(i));
				chidNodes.add(chidNode);
			}
		}
		return chidNodes;
	}
	
	/**
	 * 判断是否有子节点
	 * @param pt
	 * @param idField
	 * @param parentIds
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static<T> boolean ishaveChild(T pt, String idField, List<Object> parentIds) throws Exception{
		Class pCt = pt.getClass();
		Field ptField = pCt.getDeclaredField(idField);
		ptField.setAccessible(true);
		Object id = ptField.get(pt);
		if(parentIds.contains(id)){
			return true;
		}
		return false;
	}
	
	/**
	 * 提取parentId,避免重复提取
	 * @param soures
	 * @param parentField
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static<T> List<Object> getAllParentIdFromSoures(List<T> soures, String parentField) throws Exception {
		List<Object> parentIds = new ArrayList<>();
		if(null != soures && soures.size() > 0){
			for(T t : soures){
				Class ct = t.getClass();
				Field pField = ct.getDeclaredField(parentField);
				pField.setAccessible(true);
				Object pId = pField.get(t);
				//无论没有有parentId，都要添加一个值，使其位置对应source中的位置，通过这个位置，获取source中的资源
				if(null != pId){
					parentIds.add(pId);
				}else{
					parentIds.add("");
				}
			}
		}
		return parentIds;
	}
	
	public static boolean isBlank(final String str) {
        int strLen;
        if ((null == str) || ((strLen = str.length()) == 0))
            return true;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

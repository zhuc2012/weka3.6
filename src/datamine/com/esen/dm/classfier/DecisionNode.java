package com.esen.dm.classfier;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weka.core.Instances;

import com.esen.util.GUID;

/**
 * 决策树的节点对象
 * 节点树对象是这样一个对象
 *                   ----------------------------
 *                   |属性名称(正确的的节点个数，错误的节点个数)|
 *                   ----------------------------
 *                   /                        \  
 *                  /                          \
 *                 /                            \  
 *                /                              \
 * -------------------------------------      ---------------------------------
 * |分类标签(分裂条件)(正确的的节点个数，错误的训练数据条数)|      |属性名称(分裂条件)(正确的分类个数，错误的分类个数)|
 * -------------------------------------      --------------------------------- 
 *                                                /               \
 *                                               /                 \
 *                                              /                   \
 *                                             /                     \
 *                               ---------------------------------   --------------------------------- 
 *                               |分类标签(分裂条件)(正确的分类个数，错误的分类个数)|  |分类标签(分裂条件)(正确的分类个数，错误的分类个数)|  
 *                               ---------------------------------   ---------------------------------
 *这种对象可以描述为：
 *根节点是属性名称（除非所有的都是一个分类，那根节点就是分类标签）
 *父节点根据根据分裂条件分裂成子节点，并将分裂条件保存到子节点中，实际上这个子节点的条件可以表述为：父节点属性名称+自己的条件
 *所有的叶子节点都是分类节点，对应一个label                               
 * @author weishuang
 */
public class DecisionNode {
	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 是否是叶子节点
	 */
	private boolean leaf;

	/**
	 * 节点实例个数
	 */
	private int numCorrect;

	/**
	 * 错误的分类个数
	 */
	private int numIncorrect;

	/**
	 * 如果是叶节点那它一定对应一个分类名称
	 */
	private String label;

	/**
	 * 属性名称,label和attrName只能二选一
	 */
	private String attrName;

	/**
	 *只保存当前节点被它的父节点分裂的 条件，不保存它分裂成子节点的条件,最上层的父节点是没有条件的
	 */
	private String condition;

	/**
	 * 父节点
	 */
	private DecisionNode parent;

	/**
	 * 子节点
	 */
	private List<DecisionNode> children;

	/**
	 * 在这个节点上保存的数据
	 */
	private Instances data;

	/**
	 * 添加一个子节点
	 * @param node
	 */
	public void addChild(DecisionNode node) {
		if (this.children == null) {
			this.children = new ArrayList<DecisionNode>();
		}
		this.children.add(node);
	}

	/**
	 * 添加一个条件
	 * @param condition
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getNodeId() {
		if (nodeId == null) {
			nodeId = GUID.makeGuid();
		}
		return nodeId;
	}

	public String getAttrName() {
		return attrName;
	}

	public String getCondition() {

		return this.condition;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public int getNumCorrect() {
		return numCorrect;
	}

	public int getNumIncorrect() {
		return numIncorrect;
	}

	/**
	 * 这个节点的类标签，如果是叶节点就有值，否则为空值
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	public List<DecisionNode> getChildren() {
		return children;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public void setNumCorrect(int numCorrect) {
		this.numCorrect = numCorrect;
	}

	public void setNumIncorrect(int numIncorrect) {
		this.numIncorrect = numIncorrect;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public DecisionNode getParent() {
		return this.parent;
	}

	public void setParent(DecisionNode parent) {
		this.parent = parent;
	}

	public void setData(Instances data) {
		this.data = data;
	}

	/**
	 * 返回在这个节点上的数据
	 * @return
	 */
	public Instances getData() {
		return this.data;
	}

	/**
	 * 字段转为JSON字符串对象
	 * @return
	 */
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("leaf", this.isLeaf());
			result.put("nodeid", this.getNodeId());
			if (this.parent != null) {//根节点没有条件
				result.put("condition", this.getCondition());
			}
			if (this.isLeaf()) {
				result.put("name", this.getLabel());
			} else {
				result.put("name", this.getAttrName());
				JSONArray array = new JSONArray();
				result.put("children", array);
				List<DecisionNode> children = this.getChildren();
				for (int i = 0, l = children.size(); i < l; i++) {
					DecisionNode node = children.get(i);
					JSONObject obj = node.toJSON();
					array.put(obj);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}
}

package com.esen.dm.classfier;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 决策树类
 * @author weishuang
 */
public class EDecisionTree {
	/**
	 * 存放所有叶子节点，实际上是叶子节点的一个索引
	 */
	private List<DecisionNode> leaves = new ArrayList<DecisionNode>();

	/**
	 * 存放根节点
	 */
	private DecisionNode root;

	/**
	 * 添加一个叶子节点
	 * @param leaf
	 */
	public void addLeaf(DecisionNode leaf) {
		this.leaves.add(leaf);
	}

	/**
	 * 获取所有叶子节点
	 * @return
	 */
	public List<DecisionNode> getLeaves() {
		return this.leaves;
	}

	/**
	 * 获取根节点
	 * @return
	 */
	public DecisionNode getTree() {
		return this.root;
	}

	/**
	 * 设置根节点
	 * @param root
	 */
	public void setRoot(DecisionNode root) {
		this.root = root;
	}

	/**
	 * 生成IF THEN结构的决策表，以字符串形式输出
	 * 遍历所有的leaves节点找到各个父节点的判断条件
	 * 输出结果如下格式：
	 * [   tear-prod-rate = reduced   ]===>[   none   ]
		 [   tear-prod-rate = normal   &&   astigmatism = no   ]===>[   soft   ]
		 [   tear-prod-rate = normal   &&   astigmatism = yes   &&   spectacle-prescrip = myope   ]===>[   hard   ]
		 [   tear-prod-rate = normal   &&   astigmatism = yes   &&   spectacle-prescrip = hypermetrope   ]===>[   none   ]
	 * @return
	 */
	public String toDecisionRuleAsTable() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, l = leaves.size(); i < l; i++) {
			sb.append(createRule(leaves.get(i)));
			sb.append("\r\n");
		}
		return sb.toString();
	}

	private String createRule(DecisionNode decisionNode) {
		if (!decisionNode.isLeaf()) {
			throw new RuntimeException("不能从非叶子节点创建规则!");
		}
		StringBuffer sb = new StringBuffer();
		List<DecisionNode> parents = new ArrayList<DecisionNode>();
		DecisionNode temp = decisionNode.getParent();
		while (temp != null) {
			parents.add(temp);
			temp = temp.getParent();
		}
		sb.append("[   ");
		for (int i = parents.size() - 1; i >= 0; i--) {
			temp = parents.get(i);
			if (i != parents.size() - 1) {
				sb.append("   &&   ");
			}
			sb.append(temp.getAttrName());
			if (i - 1 >= 0) {
				DecisionNode child = parents.get(i - 1);
				sb.append(child.getCondition());
			}
		}
		sb.append(decisionNode.getCondition());
		sb.append("   ]===>[   ");
		sb.append(decisionNode.getLabel());
		sb.append("   ]");
		return sb.toString();
	}

	/**
	 * 生成JSON格式的字符串
	 * @return
	 */
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("leaf", root.isLeaf());
			result.put("nodeid", root.getNodeId());
			if (root.isLeaf()) {
				result.put("name", root.getLabel());
			} else {
				result.put("name", root.getAttrName());
				JSONArray array = new JSONArray();
				result.put("children", array);
				List<DecisionNode> children = root.getChildren();
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

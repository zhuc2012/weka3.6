package com.esen.dm.classfier;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ��������
 * @author weishuang
 */
public class EDecisionTree {
	/**
	 * �������Ҷ�ӽڵ㣬ʵ������Ҷ�ӽڵ��һ������
	 */
	private List<DecisionNode> leaves = new ArrayList<DecisionNode>();

	/**
	 * ��Ÿ��ڵ�
	 */
	private DecisionNode root;

	/**
	 * ���һ��Ҷ�ӽڵ�
	 * @param leaf
	 */
	public void addLeaf(DecisionNode leaf) {
		this.leaves.add(leaf);
	}

	/**
	 * ��ȡ����Ҷ�ӽڵ�
	 * @return
	 */
	public List<DecisionNode> getLeaves() {
		return this.leaves;
	}

	/**
	 * ��ȡ���ڵ�
	 * @return
	 */
	public DecisionNode getTree() {
		return this.root;
	}

	/**
	 * ���ø��ڵ�
	 * @param root
	 */
	public void setRoot(DecisionNode root) {
		this.root = root;
	}

	/**
	 * ����IF THEN�ṹ�ľ��߱����ַ�����ʽ���
	 * �������е�leaves�ڵ��ҵ��������ڵ���ж�����
	 * ���������¸�ʽ��
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
			throw new RuntimeException("���ܴӷ�Ҷ�ӽڵ㴴������!");
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
	 * ����JSON��ʽ���ַ���
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

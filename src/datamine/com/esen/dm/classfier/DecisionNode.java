package com.esen.dm.classfier;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weka.core.Instances;

import com.esen.util.GUID;

/**
 * �������Ľڵ����
 * �ڵ�������������һ������
 *                   ----------------------------
 *                   |��������(��ȷ�ĵĽڵ����������Ľڵ����)|
 *                   ----------------------------
 *                   /                        \  
 *                  /                          \
 *                 /                            \  
 *                /                              \
 * -------------------------------------      ---------------------------------
 * |�����ǩ(��������)(��ȷ�ĵĽڵ�����������ѵ����������)|      |��������(��������)(��ȷ�ķ������������ķ������)|
 * -------------------------------------      --------------------------------- 
 *                                                /               \
 *                                               /                 \
 *                                              /                   \
 *                                             /                     \
 *                               ---------------------------------   --------------------------------- 
 *                               |�����ǩ(��������)(��ȷ�ķ������������ķ������)|  |�����ǩ(��������)(��ȷ�ķ������������ķ������)|  
 *                               ---------------------------------   ---------------------------------
 *���ֶ����������Ϊ��
 *���ڵ����������ƣ��������еĶ���һ�����࣬�Ǹ��ڵ���Ƿ����ǩ��
 *���ڵ���ݸ��ݷ����������ѳ��ӽڵ㣬���������������浽�ӽڵ��У�ʵ��������ӽڵ���������Ա���Ϊ�����ڵ���������+�Լ�������
 *���е�Ҷ�ӽڵ㶼�Ƿ���ڵ㣬��Ӧһ��label                               
 * @author weishuang
 */
public class DecisionNode {
	/**
	 * �ڵ�id
	 */
	private String nodeId;

	/**
	 * �Ƿ���Ҷ�ӽڵ�
	 */
	private boolean leaf;

	/**
	 * �ڵ�ʵ������
	 */
	private int numCorrect;

	/**
	 * ����ķ������
	 */
	private int numIncorrect;

	/**
	 * �����Ҷ�ڵ�����һ����Ӧһ����������
	 */
	private String label;

	/**
	 * ��������,label��attrNameֻ�ܶ�ѡһ
	 */
	private String attrName;

	/**
	 *ֻ���浱ǰ�ڵ㱻���ĸ��ڵ���ѵ� �����������������ѳ��ӽڵ������,���ϲ�ĸ��ڵ���û��������
	 */
	private String condition;

	/**
	 * ���ڵ�
	 */
	private DecisionNode parent;

	/**
	 * �ӽڵ�
	 */
	private List<DecisionNode> children;

	/**
	 * ������ڵ��ϱ��������
	 */
	private Instances data;

	/**
	 * ���һ���ӽڵ�
	 * @param node
	 */
	public void addChild(DecisionNode node) {
		if (this.children == null) {
			this.children = new ArrayList<DecisionNode>();
		}
		this.children.add(node);
	}

	/**
	 * ���һ������
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
	 * ����ڵ�����ǩ�������Ҷ�ڵ����ֵ������Ϊ��ֵ
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
	 * ����������ڵ��ϵ�����
	 * @return
	 */
	public Instances getData() {
		return this.data;
	}

	/**
	 * �ֶ�תΪJSON�ַ�������
	 * @return
	 */
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("leaf", this.isLeaf());
			result.put("nodeid", this.getNodeId());
			if (this.parent != null) {//���ڵ�û������
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

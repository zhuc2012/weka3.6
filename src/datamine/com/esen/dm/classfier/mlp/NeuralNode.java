package com.esen.dm.classfier.mlp;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.functions.neural.NeuralConnection;

import com.esen.util.Point;

/**
 * 这个类是Weka中的NeuralNode对象的简化版
 * @author weishuang
 */
public class NeuralNode {
	/**
	 * 节点名称，如果是分类属性，应该是分类名称，
	 * 如果是特征属性，
	 */
	private String id;

	/**
	 * 输入节点列表
	 */
	private List<NeuralNode> inputList;

	/**
	 * 输出节点列表
	 */
	private List<NeuralNode> outputList;

	/**
	 * 由MultilayerPerceptron算法计算的节点位置
	 */
	private double m_x;

	/**
	 * 由MultilayerPerceptron算法计算的节点位置
	 */
	private double m_y;

	/**
	 * 节点类型
	 * 对应NeuralConnection中的节点类型
	 */
	private int type;

	/**
	 * weight，对每一条输入线，应该都有一个weight
	 */
	private double weight;

	public NeuralNode(String id, double m_x, double m_y, int type) {
		this.id = id;
		this.m_x = m_x;
		this.m_y = m_y;
		this.type = type;
	}

	/**
	 * 添加一个输入节点
	 * @param node
	 */
	public void addInput(NeuralNode node) {
		if (this.inputList == null) {
			this.inputList = new ArrayList<NeuralNode>();
		}
		this.inputList.add(node);
	}

	/**
	 * 添加一个输出节点
	 * @param node
	 */
	public void addOutput(NeuralNode node) {
		if (this.outputList == null) {
			this.outputList = new ArrayList<NeuralNode>();
		}
		this.outputList.add(node);
	}

	/**
	 * 计算第I个输入节点的位置
	 * @param i 输入的索引
	 * @param width 画布的宽度
	 * @param height 画布的高度
	 * @return
	 */
	public Point getStartPoint(int i, int width, int height) {
		NeuralNode node = inputList.get(i);
		Point point = node.getPosition(width, height);
		return point;
	}

	/**
	 * 返回自己在画布中的位置
	 * @return
	 */
	public Point getPosition(int width, int height) {
		int x = (int) (m_x * width);
		int y = (int) (m_y * height);
		Point point = new Point(x, y);
		return point;
	}

	public double getMx() {
		return this.m_x;
	}

	public double getMy() {
		return this.m_y;
	}

	public String getId() {
		return this.id;
	}

	/**
	 * 计算第I个输出节点的位置
	 * @param i 输出的索引
	 * @param width 画布的宽度
	 * @param height 画布的高度
	 * @return
	 */
	public Point getEndPoint(int i, int width, int height) {
		NeuralNode node = outputList.get(i);
		Point point = node.getPosition(width, height);
		return point;
	}

	/**
	 * 返回输入节点的个数
	 * @return
	 */
	public int getInputCount() {
		if (this.inputList == null) {
			return 0;
		}
		return this.inputList.size();
	}

	public NeuralNode getInput(int index) {
		if (index < getInputCount()) {
			return this.inputList.get(index);
		}
		return null;
	}

	/**
	 * 返回输出节点的个数
	 * @return
	 */
	public int getOutputCount() {
		if (this.outputList == null) {
			return 0;
		}
		return this.outputList.size();
	}

	public NeuralNode getOutput(int index) {
		if (index < getOutputCount()) {
			return this.outputList.get(index);
		}
		return null;
	}

	public int getType() {
		return this.type;
	}

	/**
	 * 是否是纯输出节点
	 * @return
	 */
	public boolean isPureOut() {
		return (type & NeuralConnection.PURE_INPUT) == NeuralConnection.PURE_INPUT;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * 获取节点的权重
	 * @return
	 */
	public double getWeight() {
		return this.weight;
	}

}

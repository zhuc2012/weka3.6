package com.esen.dm.classfier.mlp;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.functions.neural.NeuralConnection;

import com.esen.util.Point;

/**
 * �������Weka�е�NeuralNode����ļ򻯰�
 * @author weishuang
 */
public class NeuralNode {
	/**
	 * �ڵ����ƣ�����Ƿ������ԣ�Ӧ���Ƿ������ƣ�
	 * ������������ԣ�
	 */
	private String id;

	/**
	 * ����ڵ��б�
	 */
	private List<NeuralNode> inputList;

	/**
	 * ����ڵ��б�
	 */
	private List<NeuralNode> outputList;

	/**
	 * ��MultilayerPerceptron�㷨����Ľڵ�λ��
	 */
	private double m_x;

	/**
	 * ��MultilayerPerceptron�㷨����Ľڵ�λ��
	 */
	private double m_y;

	/**
	 * �ڵ�����
	 * ��ӦNeuralConnection�еĽڵ�����
	 */
	private int type;

	/**
	 * weight����ÿһ�������ߣ�Ӧ�ö���һ��weight
	 */
	private double weight;

	public NeuralNode(String id, double m_x, double m_y, int type) {
		this.id = id;
		this.m_x = m_x;
		this.m_y = m_y;
		this.type = type;
	}

	/**
	 * ���һ������ڵ�
	 * @param node
	 */
	public void addInput(NeuralNode node) {
		if (this.inputList == null) {
			this.inputList = new ArrayList<NeuralNode>();
		}
		this.inputList.add(node);
	}

	/**
	 * ���һ������ڵ�
	 * @param node
	 */
	public void addOutput(NeuralNode node) {
		if (this.outputList == null) {
			this.outputList = new ArrayList<NeuralNode>();
		}
		this.outputList.add(node);
	}

	/**
	 * �����I������ڵ��λ��
	 * @param i ���������
	 * @param width �����Ŀ��
	 * @param height �����ĸ߶�
	 * @return
	 */
	public Point getStartPoint(int i, int width, int height) {
		NeuralNode node = inputList.get(i);
		Point point = node.getPosition(width, height);
		return point;
	}

	/**
	 * �����Լ��ڻ����е�λ��
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
	 * �����I������ڵ��λ��
	 * @param i ���������
	 * @param width �����Ŀ��
	 * @param height �����ĸ߶�
	 * @return
	 */
	public Point getEndPoint(int i, int width, int height) {
		NeuralNode node = outputList.get(i);
		Point point = node.getPosition(width, height);
		return point;
	}

	/**
	 * ��������ڵ�ĸ���
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
	 * ��������ڵ�ĸ���
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
	 * �Ƿ��Ǵ�����ڵ�
	 * @return
	 */
	public boolean isPureOut() {
		return (type & NeuralConnection.PURE_INPUT) == NeuralConnection.PURE_INPUT;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * ��ȡ�ڵ��Ȩ��
	 * @return
	 */
	public double getWeight() {
		return this.weight;
	}

}

package com.esen.dm.classfier;

import weka.classifiers.trees.SimpleCart;
import weka.classifiers.trees.j48.ClassifierTree;

public class ClassfierUtils {
	/**
	 * ��ģ��ת��JAVA������Ϊweka������㷨��Ҫ�ǻ����ַ����ģ���������Ҫ�������Լ��Ķ���
	 * @param tree
	 * @return
	 * @throws Exception
	 */
	public static EDecisionTree createDecisionTree(ClassifierTree tree) throws Exception {
		if (tree == null)
			return null;
		EDecisionTree eTree = new EDecisionTree();
		DecisionNode root = new DecisionNode();
		root.setAttrName(tree.getAttrName());
		root.setLeaf(tree.isLeaf());
		root.setLabel(tree.getLabel());
		root.setNodeId("N"+tree.getNodeId());
		root.setNumCorrect(tree.getCorrectCount());
		root.setNumIncorrect(tree.getInCorrectCount());
		root.setData(tree.getTrainInstances());
		eTree.setRoot(root);
		if (tree.isLeaf()) {
			eTree.addLeaf(root);
		}
		if (!tree.isLeaf()) {
			for (int i = 0, l = tree.getChildCount(); i < l; i++) {
				ClassifierTree child = tree.getChild(i);
				createChildDecisionTree(eTree, root, tree, child, i);
			}
		}
		return eTree;
	}

	/**
	 * ��������
	 * @param parent
	 * @param child
	 */
	private static void createChildDecisionTree(EDecisionTree eTree, DecisionNode parent, ClassifierTree wekaParent,
			ClassifierTree child, int childIndex) throws Exception {
		DecisionNode node = new DecisionNode();
		node.setParent(parent);
		parent.addChild(node);
		node.setAttrName(child.getAttrName());
		node.setLeaf(child.isLeaf());
		node.setLabel(child.getLabel());
		node.setNodeId("N"+child.getNodeId());
		node.setNumCorrect(child.getCorrectCount());
		node.setNumIncorrect(child.getInCorrectCount());
		node.setData(child.getTrainInstances());
		node.setCondition(wekaParent.getChildCondition(childIndex));
		if (child.isLeaf()) {
			eTree.addLeaf(node);
		}
		if (!child.isLeaf()) {
			for (int i = 0, l = child.getChildCount(); i < l; i++) {
				ClassifierTree child1 = child.getChild(i);
				createChildDecisionTree(eTree, node, child, child1, i);
			}
		}
	}

	/**
	 * ������ع���ת��EDecisionTree����
	 * @param cartTree
	 * @return
	 * @throws Exception
	 */
	public static EDecisionTree createSimpleCartTree(SimpleCart cartTree) throws Exception {
		EDecisionTree eTree = new EDecisionTree();
		DecisionNode root = new DecisionNode();
		root.setAttrName(cartTree.getAttrName());
		root.setLeaf(cartTree.isLeaf());
		root.setLabel(cartTree.getLabel());
		root.setNodeId(null);
		root.setNumCorrect(cartTree.getCorrectCount());
		root.setNumIncorrect(cartTree.getInCorrectCount());
		root.setData(cartTree.getTrainInstances());
		eTree.setRoot(root);
		if (cartTree.isLeaf()) {
			eTree.addLeaf(root);
		}
		if (!cartTree.isLeaf()) {
			for (int i = 0, l = cartTree.getChildCount(); i < l; i++) {
				SimpleCart child = cartTree.getChild(i);
				createChildSimpleCartTree(eTree, root, cartTree, child, i);
			}
		}
		return eTree;
	}

	private static void createChildSimpleCartTree(EDecisionTree eTree, DecisionNode parent, SimpleCart wekaParent,
			SimpleCart child, int childIndex) throws Exception{
		DecisionNode node = new DecisionNode();
		node.setParent(parent);
		parent.addChild(node);
		node.setAttrName(child.getAttrName());
		node.setLeaf(child.isLeaf());
		node.setLabel(child.getLabel());
		node.setNodeId(null);
		node.setNumCorrect(child.getCorrectCount());
		node.setNumIncorrect(child.getInCorrectCount());
		node.setData(child.getTrainInstances());
		node.setCondition(wekaParent.getChildCondition(childIndex));
		if (child.isLeaf()) {
			eTree.addLeaf(node);
		}
		if (!child.isLeaf()) {
			for (int i = 0, l = child.getChildCount(); i < l; i++) {
				SimpleCart child1 = child.getChild(i);
				createChildSimpleCartTree(eTree, node, child, child1, i);
			}
		}
	}
}

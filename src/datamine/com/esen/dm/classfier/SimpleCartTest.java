package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.SimpleCart;
import weka.classifiers.trees.j48.ClassifierTree;
import weka.core.Instances;

public class SimpleCartTest {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\contact-lenses.arff"));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		SimpleCart tree = new SimpleCart();
		tree.buildClassifier(data);

		for (int i = 0, l = data.numInstances(); i < l; i++) {
			tree.classifyInstance(data.instance(i));
		}
		EDecisionTree eTree = ClassfierUtils.createSimpleCartTree(tree);
		System.out.println(eTree.toJSON());
		System.out.println(eTree.toDecisionRuleAsTable());
		//		System.out.println(tree);
		//	���ﴫ���data��ѵ���������ݣ�������ȡһЩ��Ϣ�������������۷�����
		Evaluation eval = new Evaluation(data);
		//	���ﴫ����������������Ե�data��Ϊ�˼򵥣����ǲ�����ѵ����ͬ��������������
		eval.evaluateModel(tree, data);
		//		eval.crossValidateModel(tree, data, 3, new Random());
		System.out.print(eval.toClassDetailsString());

		System.out.println(eval.toMatrixString());
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	}
}

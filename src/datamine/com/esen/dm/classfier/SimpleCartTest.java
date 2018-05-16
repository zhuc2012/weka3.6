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
		//	这里传入的data是训练集的数据，用来获取一些信息，并不用来评价分类器
		Evaluation eval = new Evaluation(data);
		//	这里传入的是真正用来测试的data，为了简单，我们采用与训练集同样的数据作测试
		eval.evaluateModel(tree, data);
		//		eval.crossValidateModel(tree, data, 3, new Random());
		System.out.print(eval.toClassDetailsString());

		System.out.println(eval.toMatrixString());
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	}
}

package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.j48.ClassifierTree;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

public class J48Test {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\contact-lenses.arff"));
		Instances data = new Instances(reader);
		
		
		Instances testData = DataSource.read("D:\\Weka-3-6\\data\\contact-lenses-no.arff");
		testData.setClassIndex(testData.numAttributes()-1);
		
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		//	��dataѵ��һ��J48������
		J48 tree = new J48();
		tree.setOptions(Utils.splitOptions("-C 0.25 -M 2 -B"));
		tree.buildClassifier(data);
		ClassifierTree model = tree.getModel();

		System.out.println(tree.graph());
		for (int i = 0, l = testData.numInstances(); i < l; i++) {
			System.out.println(tree.classifyInstance(testData.instance(i)));
		}
		EDecisionTree eTree = ClassfierUtils.createDecisionTree(tree.getModel());
		System.out.println(eTree.toJSON());
		System.out.println(eTree.toDecisionRuleAsTable());
		//		System.out.println(tree);
		//	���ﴫ���data��ѵ��������ݣ�������ȡһЩ��Ϣ�������������۷�����
		Evaluation eval = new Evaluation(data);
		//	���ﴫ����������������Ե�data��Ϊ�˼򵥣����ǲ�����ѵ����ͬ������������
		eval.evaluateModel(tree, data);
		//		eval.crossValidateModel(tree, data, 3, new Random());
		System.out.print(eval.toClassDetailsString());

		System.out.println(eval.toMatrixString());
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
	}
}

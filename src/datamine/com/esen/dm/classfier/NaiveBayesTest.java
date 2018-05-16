package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.Utils;

public class NaiveBayesTest {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\contact-lenses.arff"));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		//	��dataѵ��һ��J48������
		NaiveBayes tree = new NaiveBayes();
//		tree.setOptions(Utils.splitOptions("-D"));
		tree.buildClassifier(data);

		System.out.println(tree);
		for (int i = 0, l = data.numInstances(); i < l; i++) {
			tree.classifyInstance(data.instance(i));
		}
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

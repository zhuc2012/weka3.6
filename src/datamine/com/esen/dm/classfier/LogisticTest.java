package com.esen.dm.classfier;

import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class LogisticTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Instances data = DataSource.read("D:\\Weka-3-6\\data\\iris.arff");
		data.setClassIndex(data.numAttributes() - 1);
		Logistic log = new Logistic();
		log.buildClassifier(data);
		//System.out.println(log);
		for (int i = 0; i < data.numInstances(); i++) {
			System.out.println(log.classifyInstance(data.instance(i)));
		}
	}

}

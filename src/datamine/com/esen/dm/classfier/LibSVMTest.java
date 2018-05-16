package com.esen.dm.classfier;

import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class LibSVMTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Instances data = DataSource.read("D:\\Weka-3-6\\data\\iris.arff");
		data.setClassIndex(data.numAttributes() - 1);
		LibSVM svm  = new LibSVM();
		svm.buildClassifier(data);
		System.out.println(svm);
		for (int i = 0; i < data.numInstances(); i++) {
			System.out.println(svm.classifyInstance(data.instance(i)));
		}
	}

}

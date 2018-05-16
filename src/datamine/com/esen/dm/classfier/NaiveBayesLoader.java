package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectInputStream;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class NaiveBayesLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream(new File("C:\\Users\\weishuang\\Desktop\\naivebayes.model"));
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			Classifier classifier = (Classifier) objectInputStream.readObject();
			Instances trainHeader = (Instances) objectInputStream.readObject();
			BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\contact-lenses2.arff"));
			Instances data = new Instances(reader);
			data.setClassIndex(trainHeader.classIndex());
			reader.close();
			data.setClassIndex(data.numAttributes()-1);
			for (int i = 0, l = data.numInstances(); i < l; i++) {
				int idx = (int)classifier.classifyInstance(data.instance(i));
				System.out.println(data.classAttribute().value(idx));
			}
		} finally {
			is.close();
		}

	}

}

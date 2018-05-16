package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

/**
 * 存储J48模型对象
 * 
 *
 * @author weishuang
 */
public class NaiveBayesSaver {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\contact-lenses.arff"));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		//	用data训练一个J48分类器
		NaiveBayes tree = new NaiveBayes();
		tree.buildClassifier(data);

		OutputStream os = new FileOutputStream(new File("C:\\Users\\weishuang\\Desktop\\naivebayes.model"));
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
		objectOutputStream.writeObject(tree);
		Instances trainHeader = data.stringFreeStructure();
		if (trainHeader != null) {
			objectOutputStream.writeObject(trainHeader);
		}
		objectOutputStream.flush();
		objectOutputStream.close();

	}

}

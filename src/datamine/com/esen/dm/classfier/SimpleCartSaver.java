package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import weka.classifiers.trees.SimpleCart;
import weka.core.Instances;

public class SimpleCartSaver {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\contact-lenses.arff"));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		//	用data训练一个J48分类器
		SimpleCart tree = new SimpleCart();
		tree.buildClassifier(data);

		OutputStream os = new FileOutputStream(new File("C:\\Users\\weishuang\\Desktop\\simplecart.model"));
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

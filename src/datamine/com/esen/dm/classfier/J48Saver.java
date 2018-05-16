package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;

/**
 * 存储J48模型对象
 * 
 *
 * @author weishuang
 */
public class J48Saver {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\contact-lenses.arff"));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		//	用data训练一个J48分类器
		J48 tree = new J48();
		tree.setOptions(Utils.splitOptions("-C 0.25 -M 2"));
		tree.buildClassifier(data);

		OutputStream os = new FileOutputStream(new File("C:\\Users\\weishuang\\Desktop\\j48.model"));
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

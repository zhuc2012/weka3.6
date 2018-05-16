package com.esen.dm.classfier;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

import com.esen.dm.classfier.mlp.NeuralNet;

public class MLPTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("D:\\Weka-3-6\\data\\iris.arff"));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		MultilayerPerceptron mlp = new MultilayerPerceptron();
		mlp.setHiddenLayers("a,a");
		mlp.buildClassifier(data);
		NeuralNet net = new NeuralNet(mlp, data.numAttributes() - 1, data.numClasses());
		System.out.println(net.toJSON().toString());
		System.out.println(mlp.classifyInstance(data.firstInstance()));
	}

}

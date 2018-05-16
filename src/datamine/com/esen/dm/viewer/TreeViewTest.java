package com.esen.dm.viewer;

import javax.swing.JFrame;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class TreeViewTest {
	
	public J48 getJ48(String location) throws Exception {
		Instances data = DataSource.read(location);
		data.setClassIndex(data.numAttributes() - 1 );
		
		System.out.println(data.toSummaryString());
		
//		Instances small = new Instances(data, 80, 50);
		
		J48 classifier = new J48();
		classifier.buildClassifier(data);
		System.out.println(classifier.toString());
		System.out.println(classifier.toSummaryString());
//		System.out.println(classifier.graph());
		
		return classifier;
	}
	
	public static void main(String[] args) throws Exception {
		TreeViewTest test = new TreeViewTest();
		J48 classifier = test.getJ48("D:/weka-3-6/data/iris.arff");
		
		
		
		TreeVisualizer tree = new TreeVisualizer(null, classifier.graph(), new PlaceNode2());
			
		JFrame frame = new JFrame();

		frame.add(tree);
		
		
		frame.setSize(500, 500);
		frame.setLocation(450, 150);
		frame.setVisible(true);
	
		tree.fitToScreen();
	}

}

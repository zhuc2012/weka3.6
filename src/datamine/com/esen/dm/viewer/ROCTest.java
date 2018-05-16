package com.esen.dm.viewer;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.gui.visualize.Plot2D;
import weka.gui.visualize.PlotData2D;

public class ROCTest {

	public static void main(String[] args) throws Exception {
		Instances data = DataSource.read("C:/Users/zhucong/Desktop/incomeData/income_all.csv");
		
		//删除含有空值的实例
		for(int i = 0; i < data.numAttributes(); i++) {
		data.deleteWithMissing(i);
		}
		
		System.out.println(data.toSummaryString());
		
		data.randomize(new Random(1l));
		
		int trainSize = (int) (data.numInstances() * 0.66) + 1;
		int testSize = data.numInstances() - trainSize;
		
		Instances trainData = new Instances(data, 0 , trainSize);
		Instances testData = new Instances(data, trainSize, testSize);
		
		trainData.setClassIndex(trainData.numAttributes() - 1);
		testData.setClassIndex(testData.numAttributes() - 1);
		
		Logistic classifier = new Logistic();
		classifier.buildClassifier(trainData);
		
		Evaluation eval = new Evaluation(trainData);
		
		eval.evaluateModel(classifier, testData);
		
		System.out.println(eval.toSummaryString());
		
		
		System.out.println(eval.toClassDetailsString());
		
//		Attribute classAttribute = trainData.attribute(data.numAttributes() - 1);
		
		
//		System.out.println(eval.areaUnderROC(classAttribute.indexOfValue(" <=50K")));
//		System.out.println(eval.avgCost());
		
//		System.out.println(eval.areaUnderPRC(0));
//		System.out.println(eval.areaUnderPRC(1));
		
//		System.out.println(classAttribute.toString());
		
//		ArrayList<Prediction> predictions = eval.predictions();
		
//		ThresholdCurve curve = new ThresholdCurve();
		
//		Instances insts = curve.getCurve(predictions);
//		  Instances insts =null;
//		System.out.println(insts.toSummaryString());
//		System.out.println(insts.toString());
		
//		PlotData2D plotData = new PlotData2D(insts);
		
//		plotData.setXindex(insts.attribute("False Positive Rate").index());
//		plotData.setYindex(insts.attribute("True Positive Rate").index());
//		plotData.setCindex(insts.attribute("Threshold").index());
		
		
		Plot2D plot = new Plot2D();
			
//		plot.setMasterPlot(plotData);
		
//		plot.setInstances(insts);
//		plot.setXindex(insts.attribute("False Positive Rate").index());
//		plot.setYindex(insts.attribute("True Positive Rate").index());
//		plot.setCindex(insts.attribute("Threshold").index());
		
		JFrame jf = new JFrame();
		jf.add(plot);
		
		jf.setSize(500,500);
		jf.setVisible(true);

	}

}

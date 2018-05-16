package com.esen.dm.clusters;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class EMTest {

	public static void main(String[] args) throws Exception{
		Instances data = DataSource.read("D:\\Weka-3-6\\data\\iris.arff");
		data.setClassIndex(data.numAttributes() -1);
		
		Remove filter = new Remove();
		filter.setAttributeIndices("" + (data.classIndex() + 1));
		filter.setInputFormat(data);
		Instances dataCluster = Filter.useFilter(data, filter);
		
		EM clusterer = new EM();
		clusterer.buildClusterer(dataCluster);
		
		ClusterEvaluation eval = new ClusterEvaluation();
		
		eval.setClusterer(clusterer);
		eval.evaluateClusterer(data);
		
		System.out.println(eval.clusterResultsToString());
	}
}

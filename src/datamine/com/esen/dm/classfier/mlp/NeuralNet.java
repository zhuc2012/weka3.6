package com.esen.dm.classfier.mlp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.neural.NeuralConnection;

/**
 * 神经网络对象,
 * 是Weka中MultilayerPerceptron对象的结果
 *MultilayerPerceptron将网状的结构拉平了，分成了输入，输出，和中间的连接节点
 * @author weishuang
 */
public class NeuralNet {
	/**
	 * 输入节点，相当于特征属性名节点
	 */
	private NeuralNode[] inputs;

	/**
	 * 输出节点，相当于分类值节点
	 */
	private NeuralNode[] outputs;

	/**
	 * 中间层节点
	 */
	private NeuralNode[] connections;

	public NeuralNet(MultilayerPerceptron perceptron, int attrCount, int classCount) {
		NeuralConnection[] mInputs = perceptron.getInputs();
		NeuralConnection[] mOutputs = perceptron.getOutputs();
		NeuralConnection[] mConnections = perceptron.getConnections();
		inputs = new NeuralNode[attrCount];
		outputs = new NeuralNode[classCount];
		connections = new NeuralNode[perceptron.getConnectionCount()];
		for (int i = 0; i < inputs.length; i++) {
			if (mInputs[i] != null) {
				inputs[i] = parseInput(mInputs[i], true);
			}
		}
		for (int i = 0; i < outputs.length; i++) {
			if (mOutputs[i] != null) {
				outputs[i] = parseOutput(mOutputs[i], true);
			}
		}
		for (int i = 0; i < this.connections.length; i++) {
			if (mConnections[i] != null) {
				connections[i] = parseConnection(mConnections[i], true);
			}
		}
	}

	/**
	 * 中间节点只有输入没有输出
	 * @param neuralConnection
	 * @param parseSub
	 * @return
	 */
	private NeuralNode parseConnection(NeuralConnection neuralConnection, boolean parseSub) {
		NeuralNode node = new NeuralNode(neuralConnection.getId(), neuralConnection.getX(), neuralConnection.getY(),
				neuralConnection.getType());
		if (parseSub) {
			double[] weights = null;
			NeuralConnection[] nInputs = neuralConnection.getInputs();
			if (neuralConnection instanceof weka.classifiers.functions.neural.NeuralNode) {
				weka.classifiers.functions.neural.NeuralNode wekaNode = (weka.classifiers.functions.neural.NeuralNode) neuralConnection;
				weights = wekaNode.getWeights();
			}
			if (nInputs != null && nInputs.length > 0) {
				for (int i = 0; i < nInputs.length; i++) {
					if (nInputs[i] != null) {
						NeuralNode eNode = parseInput(nInputs[i], false);
						if (weights != null) {
							eNode.setWeight(weights[i + 1]);
						}
						node.addInput(eNode);
					}
				}
			}
		}
		return node;
	}

	private NeuralNode parseOutput(NeuralConnection neuralConnection, boolean parseSub) {
		NeuralNode node = new NeuralNode(neuralConnection.getId(), neuralConnection.getX(), neuralConnection.getY(),
				neuralConnection.getType());
		if (parseSub) {
			double[] weights = null;
			if (neuralConnection instanceof weka.classifiers.functions.neural.NeuralNode) {
				weka.classifiers.functions.neural.NeuralNode wekaNode = (weka.classifiers.functions.neural.NeuralNode) neuralConnection;
				weights = wekaNode.getWeights();
			}
			NeuralConnection[] nInputs = neuralConnection.getInputs();
			if (nInputs != null && nInputs.length > 0) {
				for (int i = 0; i < nInputs.length; i++) {
					if (nInputs[i] != null) {
						NeuralNode eNode = parseInput(nInputs[i], false);
						if (weights != null) {
							eNode.setWeight(weights[i + 1]);
						}
						node.addInput(eNode);
					}
				}
			}
			NeuralConnection[] nOutputs = neuralConnection.getOutputs();
			if (nOutputs != null && nOutputs.length > 0) {
				for (int i = 0; i < nOutputs.length; i++) {
					if (nOutputs[i] != null) {
						node.addOutput(parseOutput(nOutputs[i], false));
					}
				}
			}
		}
		return node;
	}

	private NeuralNode parseInput(NeuralConnection neuralConnection, boolean parseSub) {
		NeuralNode node = new NeuralNode(neuralConnection.getId(), neuralConnection.getX(), neuralConnection.getY(),
				neuralConnection.getType());
		if (parseSub) {
			NeuralConnection[] nInputs = neuralConnection.getInputs();
			if (nInputs != null && nInputs.length > 0) {
				for (int i = 0; i < nInputs.length; i++) {
					if (nInputs[i] != null) {
						node.addInput(parseInput(nInputs[i], false));
					}
				}
			}
		}
		return node;
	}

	/**
	 * 将网络转成JSON字符串
	 * @return
	 */
	public JSONObject toJSON() throws JSONException {
		JSONObject obj = new JSONObject();
		JSONArray inputArray = new JSONArray();
		for (int i = 0; i < inputs.length; i++) {
			JSONObject input = new JSONObject();
			input.put("text", this.inputs[i].getId());
			input.put("mx", this.inputs[i].getMx());
			input.put("my", this.inputs[i].getMy());
			inputArray.put(input);
			/**
			 * input节点有输入没有输出
			 */
			int subInputCount = inputs[i].getInputCount();
			JSONArray subArray = new JSONArray();
			for (int m = 0; m < subInputCount; m++) {
				NeuralNode sInput = inputs[i].getInput(m);
				JSONObject subInput = new JSONObject();
				subInput.put("text", sInput.getId());
				subInput.put("mx", sInput.getMx());
				subInput.put("my", sInput.getMy());
				subArray.put(subInput);
			}
			input.put("inputs", subArray);
		}
		obj.put("attrs", inputArray);

		/**
		 * 输出节点
		 */
		JSONArray outputArray = new JSONArray();
		for (int i = 0; i < outputs.length; i++) {
			JSONObject output = new JSONObject();
			output.put("text", this.outputs[i].getId());
			output.put("mx", this.outputs[i].getMx());
			output.put("my", this.outputs[i].getMy());
			outputArray.put(output);
			/**
			 * 分类节点有输入也有输出
			 */
			int subInputCount = outputs[i].getInputCount();
			JSONArray subInputArray = new JSONArray();
			for (int m = 0; m < subInputCount; m++) {
				NeuralNode sInput = outputs[i].getInput(m);
				JSONObject subInput = new JSONObject();
				subInput.put("text", sInput.getId());
				subInput.put("mx", sInput.getMx());
				subInput.put("my", sInput.getMy());
				subInput.put("weight", sInput.getWeight());
				subInputArray.put(subInput);
			}
			output.put("inputs", subInputArray);

			int subOutputCount = outputs[i].getOutputCount();
			JSONArray subOutputArray = new JSONArray();
			for (int m = 0; m < subOutputCount; m++) {
				NeuralNode sOutput = outputs[i].getOutput(m);
				JSONObject subOutput = new JSONObject();
				subOutput.put("text", sOutput.getId());
				subOutput.put("mx", sOutput.getMx());
				subOutput.put("my", sOutput.getMy());
				subOutputArray.put(subOutput);
			}
			output.put("outputs", subOutputArray);
		}
		obj.put("classes", outputArray);

		/**
		 * 中间节点
		 */
		JSONArray connectionArray = new JSONArray();
		for (int i = 0; i < this.connections.length; i++) {
			JSONObject connection = new JSONObject();
			connection.put("text", this.connections[i].getId());
			connection.put("mx", this.connections[i].getMx());
			connection.put("my", this.connections[i].getMy());
			connectionArray.put(connection);
			/**
			 * 中间节点只有输入没有输出
			 */
			int subInputCount = this.connections[i].getInputCount();
			JSONArray subInputArray = new JSONArray();
			for (int m = 0; m < subInputCount; m++) {
				NeuralNode sInput = connections[i].getInput(m);
				JSONObject subInput = new JSONObject();
				subInput.put("text", sInput.getId());
				subInput.put("mx", sInput.getMx());
				subInput.put("my", sInput.getMy());
				subInput.put("weight", sInput.getWeight());
				subInputArray.put(subInput);
			}
			connection.put("inputs", subInputArray);
		}
		obj.put("nodes", connectionArray);
		return obj;
	}
}

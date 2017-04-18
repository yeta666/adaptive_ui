package com.adaptive.ui.id3Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by yeta on 2017/4/13/013.
 * 训练模型的类
 */
public class TrainModel {
    //根节点
    private TreeNode root;
    //属性集
    private String[] attributesArray;
    //训练集
    private Object[] trainArrays;
    //最后结果的索引
    private int resultIndex;
    //是否处理训练集的标志
    private boolean[] flag;

    //构造方法
    public TrainModel(String[] attributesArray, Object[] trainArrays, int resultIndex){
        //初始化属性集
        this.attributesArray = attributesArray;
        //初始化训练集
        this.trainArrays = trainArrays;
        //初始化最后结果的索引
        this.resultIndex = resultIndex;
        //初始化标志
        this.flag = new boolean[this.attributesArray.length];
        for(int i = 0; i < this.flag.length; i++){
            if(i == this.resultIndex){
                this.flag[i] = true;
            }else{
                this.flag[i] = false;
            }
        }
    }

    //训练方法
    public TreeNode train(){
        //创建决策树
        this.createTree(this.trainArrays);
        //打印决策树
        this.printlnTree(this.root);

        return this.root;
    }

    /**
     * 打印决策树的方法
     * @param rootNode
     */
    public void printlnTree(TreeNode rootNode){
        System.out.println(rootNode.nodeName);
        TreeNode[] childNodes = rootNode.childNodes;
        for(int i = 0; i < childNodes.length; i++){
            if(childNodes[i] != null){
                System.out.println("如果：" + childNodes[i].parentAttribute);
                printlnTree(childNodes[i]);
            }
        }
    }

    /**
     * 创建决策树的方法
     * @param arrays
     */
    public void createTree(Object[] arrays){
        //获取最大增益的属性作为根节点
        Object[] maxGain = this.getMaxGain(arrays);
        //创建根节点，并根据根节点插入其他节点
        if(this.root == null){
            this.root = new TreeNode();
            this.root.parentNode = null;
            this.root.parentAttribute = null;
            this.root.nodeName = this.getAttributeNameByIndex(((Integer) maxGain[1]).intValue());
            this.root.attributes = getAttributes(((Integer) maxGain[1]).intValue());
            this.root.childNodes = new TreeNode[this.root.attributes.length];
            insertTreeNode(arrays, this.root);
        }
    }

    /**
     * 插入决策树节点的方法
     * @param arrays
     * @param parentNode
     */
    public void insertTreeNode(Object[] arrays, TreeNode parentNode){
        //获取父节点的属性
        String[] attributes = parentNode.attributes;
        //遍历父节点的属性
        for(int i = 0; i < attributes.length; i++){
            //根据该属性获取数据
            Object[] newArrays = this.getNewArraysByAttribute(arrays, attributes[i], getIndexByAttributeName(parentNode.nodeName));
            //获取最大增益
            Object[] maxGain = this.getMaxGain(newArrays);
            double gain = ((Double)maxGain[0]).doubleValue();
            if(gain == 0){
                //最大增益为0，则表示该父节点的属性下面是一个叶子节点
                TreeNode newNode = new TreeNode();
                newNode.parentNode = parentNode;
                newNode.parentAttribute = attributes[i];
                newNode.nodeName = this.getLeafNodeName(newArrays);
                newNode.attributes = new String[0];
                newNode.childNodes = new TreeNode[0];
                parentNode.childNodes[i] = newNode;
            }else{
                //最大增益不为0，则表示该父节点的属性下面不是一个叶子节点，所以在插入了当前节点之后还要继续调用插入方法
                int index = ((Integer)maxGain[1]).intValue();
                TreeNode newNode = new TreeNode();
                newNode.parentNode = parentNode;
                newNode.parentAttribute = attributes[i];
                newNode.nodeName = this.getAttributeNameByIndex(index);
                newNode.attributes = this.getAttributes(index);
                newNode.childNodes = new TreeNode[newNode.attributes.length];
                parentNode.childNodes[i] = newNode;
                this.insertTreeNode(newArrays, newNode);
            }
        }
    }

    /**
     * 获取最大增益的方法
     * @param arrays 源数据
     * @return 最大增益
     */
    public Object[] getMaxGain(Object[] arrays) {
        //初始化返回对象
        Object[] maxGain = new Object[2];
        double gain = 0; //gain
        int index = -1;    //index
        //获取每一个“信息属性”的增益，如果比初始化的值大，则更新
        for(int i = 0; i < this.flag.length; i++){
            if(!this.flag[i]){
                double value = gain(arrays, i);
                if(value > gain){
                    gain = value;
                    index = i;
                }
            }
        }
        maxGain[0] = gain;
        maxGain[1] = index;
        //如果最大增益为0，则代表当前“信息属性”要作为叶子节点
        if(index != -1){
            this.flag[(int)maxGain[1]] = true;
        }
        return maxGain;
    }

    /**
     * 计算增益的方法
     * @param arrays 源数据
     * @param index 要计算增益的“某一个信息属性”的索引
     * @return
     */
    public double gain(Object[] arrays, int index){
        //获取最后结果的取值
        String[] results = this.getAttributes(this.resultIndex);
        //统计最后的结果
        //初始化
        int[] counts = new int[results.length];
        for(int i = 0; i < counts.length; i++){
            counts[i] = 0;
        }
        //统计
        for(int i = 0; i < arrays.length; i++){
            //获取一条传来的数据
            String[] strs = (String[])arrays[i];
            //遍历最后的结果
            for(int j = 0; j < results.length; j++){
                //如果最后的结果中的某一个等于这一条传来的数据的结果，则数目加一
                if(results[j].equals(strs[this.resultIndex])){
                    counts[j]++;
                }
            }
        }
        //根据历史记录（最后的结果）获取信息熵
        double entropy1 = 0;
        for(int i = 0; i < counts.length; i++){
            entropy1 += Entropy.getEntropy(counts[i], arrays.length);
        }

        //获取要计算信息熵的“信息属性”的取值
        String[] attributes = getAttributes(index);
        //“信息属性”的每一个取值都对应了多个最后的结果，所以遍历“信息属性”的每一个取值，计算“信息属性”的每一个取值的信息熵，最后加起来就是该“信息属性”的信息熵
        double entropy2 = 0;
        for(int i = 0; i < attributes.length; i++){
            entropy2 += this.getAttributeEntropy(arrays, index, attributes[i]);
        }

        return entropy1 - entropy2;
    }

    /**
     * 计算“信息属性”的某一个取值的信息熵的方法
     * @param arrays 源数据
     * @param index 该“信息属性”的索引
     * @param attribute 该“信息属性”的某一个取值
     * @return
     */
    public double getAttributeEntropy(Object[] arrays, int index, String attribute){
        //获取最后结果的取值
        String[] results = this.getAttributes(this.resultIndex);
        //统计最后的结果
        //初始化
        int[] counts = new int[results.length];
        for(int i = 0; i < counts.length; i++){
            counts[i] = 0;
        }
        //统计
        for(int i = 0; i < arrays.length; i++){
            //获取一条数据
            String[] strs = (String[])arrays[i];
            //如果这一条数据和“信息属性”的取值有关
            if(strs[index].equals(attribute)){
                //遍历最后的结果
                for(int j = 0; j < results.length; j++){
                    //如果最后的结果中的某一个等于这一条传来的数据的结果，则数目加一
                    if(results[j].equals(strs[this.resultIndex])){
                        counts[j]++;
                    }
                }
            }
        }
        //获取统计的总数
        int total = 0;
        for(int i = 0; i < counts.length; i++){
            total += counts[i];
        }
        //计算“信息属性”的某一个取值的信息熵
        double attributeEntropy = 0;
        for(int i = 0; i < counts.length; i++){
            attributeEntropy += Entropy.getEntropy(counts[i], total);
        }

        return attributeEntropy * (total * Double.parseDouble("1.0") / arrays.length);
    }

    /**
     * 获取“信息属性”的取值的方法
     * @param index 要获取的“信息属性”的索引
     * @return “信息属性”的取值
     */
    public String[] getAttributes(int index){
        TreeSet<String> treeSet = new TreeSet<String>(new Comparisons());
        for (int i = 0; i < this.trainArrays.length; i++) {
            String[] strs = (String[])this.trainArrays[i];
            treeSet.add(strs[index]);
        }
        String[] result = new String[treeSet.size()];
        return treeSet.toArray(result);
    }

    /**
     * 根据“某一信息属性”的“某一取值”剪取源数据的方法
     * @param arrays 源数据
     * @param attribute “某一取值”
     * @param index “某一信息属性”的索引
     * @return 和“某一属性”有关的数据
     */
    public Object[] getNewArraysByAttribute(Object[] arrays, String attribute, int index){
        List<String[]> list = new ArrayList<String[]>();
        for(int i = 0; i < arrays.length; i++){
            String[] strs = (String[])arrays[i];
            if(strs[index].equals(attribute)){
                list.add(strs);
            }
        }
        return list.toArray();
    }

    /**
     * 通过“信息属性”的索引获取“信息属性”的名称的方法
     * @param index
     * @return
     */
    public String getAttributeNameByIndex(int index){
        return this.attributesArray[index];
    }

    /**
     * 通过“信息属性”的名称获取“信息属性”的索引的方法
     * @param attribute
     * @return
     */
    public int getIndexByAttributeName(String attribute){
        for(int i = 0; i < this.attributesArray.length; i++){
            if(this.attributesArray[i].equals(attribute)){
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取叶子节点名称的方法，因为叶子节点的数据的最后结果必定只有一种可能，所以随便取一条数据，看它的结果
     * @param arrays
     * @return
     */
    public String getLeafNodeName(Object[] arrays){
        if(arrays != null && arrays.length > 0){
            String[] strs = (String[])arrays[0];
            return strs[this.resultIndex];
        }
        return null;
    }
}

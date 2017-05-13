package com.adaptive.ui.id3Tree;

import java.io.Serializable;

/**
 * 树节点类
 * Created by yeta on 2017/4/13/013.
 */
public class TreeNode implements Serializable {

    private static final long serialVersionUID = -8566061928745722367L;

    //父节点
    public TreeNode parentNode;
    //父节点的属性
    public String parentAttribute;
    //节点名
    public String nodeName;
    //属性
    public String[] attributes;
    //子节点
    public TreeNode[] childNodes;

    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public String getParentAttribute() {
        return parentAttribute;
    }

    public void setParentAttribute(String parentAttribute) {
        this.parentAttribute = parentAttribute;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    public TreeNode[] getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(TreeNode[] childNodes) {
        this.childNodes = childNodes;
    }
}

package com.adaptive.ui.id3Tree;

import java.io.Serializable;

/**
 * Created by yeta on 2017/4/13/013.
 * 树节点类
 */
public class TreeNode implements Serializable {
    //父节点
    TreeNode parentNode;
    //父节点的属性
    String parentAttribute;
    //节点名
    String nodeName;
    //属性
    String[] attributes;
    //子节点
    TreeNode[] childNodes;

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

package com.xzm.java.algorithm;

/**
 * Created by Bradish7Y on 15/10/21.
 */
public class BinaryTreeNode {
    private int value;
    private BinaryTreeNode leftNode;
    private BinaryTreeNode rightNode;

    public BinaryTreeNode(int value) {
        this.value = value;
    }

    int deep = 0;

    public void createTreeNode(BinaryTreeNode node) {

        if (deep++ > 3) {
            return;
        }

        node.leftNode = new BinaryTreeNode(deep);
        node.rightNode = new BinaryTreeNode(deep);

        createTreeNode(node.leftNode);
        createTreeNode(node.rightNode);

    }

    /**
     * 节点个数
     * @param node
     * @return
     */
    public int getNodeNum(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }

        return getNodeNum(node.leftNode) + getNodeNum(node.rightNode) + 1;
    }

    public static void main(String[] args) {
        BinaryTreeNode node = new BinaryTreeNode(0);
        node.createTreeNode(node);

        System.out.println(node.getNodeNum(node));
    }

}

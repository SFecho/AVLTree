package com.util.tree;

import java.util.Stack;

//利用红黑树实现set模板
public class RBTree<T extends Comparable<T>> {

    private final static int RED = 0;
    private final static int BLACK = 1;

    private class RBNode<T extends Comparable<T>> extends Node<T> {

        protected RBNode<T> left;
        protected RBNode<T> right;
        protected RBNode<T> parent;
        int color;

        public RBNode(T data) {
            super(data);
            this.color = RED;
            this.left = this.right = this.parent = null;
        }
    }

    private RBNode<T> root;

    public RBTree() {
        this.root = null;
    }

    private int getColor(RBNode<T> node){
        return node == null ? BLACK : node.color;
    }

    private RBNode<T> getParent(RBNode<T> node){
        return node == null ? null : node.parent;
    }

    private RBNode<T> getLeft(RBNode<T> node){
        return node == null ? null : node.left;
    }

    private RBNode<T> getRight(RBNode<T> node){
        return node == null ? null : node.right;
    }

    private void setColor(RBNode<T> node, int color){
        if(node != null)
            node.color = color;
    }

    private void leftRotate(RBNode<T> node){
        if(node == null)
            return ;
        RBNode<T> rotateNode = node.right;
        node.right = rotateNode.left;
        if(rotateNode.left != null)
            rotateNode.left.parent = node;

        rotateNode.left = node;

        if(node.parent == null)
            this.root = rotateNode;
        else{
            if(node.parent.left == node)
                node.parent.left = rotateNode;
            else
                node.parent.right = rotateNode;

        }

        rotateNode.parent = node.parent;
        node.parent = rotateNode;

    }

    //平衡二叉树的右旋
    private void rightRotate(RBNode<T> node){
        if(node == null)
            return ;
        RBNode<T> rotateNode = node.left;
        node.left = rotateNode.right;
        if(rotateNode.right != null)
            rotateNode.right.parent = node;

        rotateNode.right = node;

        if(node.parent == null)
            this.root = rotateNode;
        else{
            if(node.parent.left == node)
                node.parent.left = rotateNode;
            else
                node.parent.right = rotateNode;

        }
        rotateNode.parent = node.parent;
        node.parent = rotateNode;
    }

    private void transplant(RBNode<T> deleteNode, RBNode<T> nextNode) {
        if (deleteNode == null)
            return;
        if (deleteNode.parent == null) {
            this.root = nextNode;
        } else {
            if (deleteNode == deleteNode.parent.left) {
                deleteNode.parent.left = nextNode;
            } else if (deleteNode == deleteNode.parent.right)
                deleteNode.parent.right = nextNode;
        }
    }

    public void insert(T data){
        RBNode<T> newNode = new RBNode<>(data);
        RBNode<T> prev = null;
        RBNode<T> search = this.root;
        if(this.root == null)
            this.root = newNode;
        else{
            while(search != null){
                prev = search;
                if(search.data.compareTo(data) > 0)
                    search = search.left;
                else if(search.data.compareTo(data) < 0)
                    search = search.right;
                else
                    return ;
            }

            if(prev.data.compareTo(data) > 0)
                prev.left = newNode;
            else
                prev.right = newNode;

            newNode.parent = prev;
        }
        insertFixUp(newNode);
    }

    private void insertFixUp(RBNode<T> node){
        while(node != null && node != this.root && getColor(node.parent) == RED){
            RBNode<T> parentNode = getParent(node);
            RBNode<T> grandParentNode = getParent(parentNode);
            RBNode<T> uncleNode;
            if(getLeft(grandParentNode) == parentNode){
                uncleNode = getRight(grandParentNode);
                if(getColor(uncleNode) == RED){
                    setColor(uncleNode, BLACK);
                    setColor(parentNode, BLACK);
                    setColor(grandParentNode, RED);
                    node = grandParentNode;
                }else{
                    if(node == getRight(parentNode)){
                        node = parentNode;
                        leftRotate(node);
                        parentNode = getParent(node);
                        grandParentNode = getParent(parentNode);
                    }
                    setColor(parentNode, BLACK);
                    setColor(grandParentNode, RED);
                    rightRotate(grandParentNode);
                }
            }
            else{
                uncleNode = getLeft(grandParentNode);
                if(getColor(uncleNode) == RED){
                    setColor(uncleNode, BLACK);
                    setColor(parentNode, BLACK);
                    setColor(grandParentNode, RED);
                    node = grandParentNode;
                }else{
                    if(node == getLeft(parentNode)){
                        node = parentNode;
                        rightRotate(node);
                        parentNode = getParent(node);
                        grandParentNode = getParent(parentNode);
                    }
                    setColor(parentNode, BLACK);
                    setColor(grandParentNode, RED);
                    leftRotate(grandParentNode);
                }
            }
        }
        setColor(this.root, BLACK);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        Stack<RBNode<T>> stack = new Stack<>();

        RBNode<T> p = this.root;

        while(p != null || stack.isEmpty() == false){
            while(p != null){
                stack.push(p);
                p = p.left;
            }

            if(stack.isEmpty() == false){
                RBNode<T>  top = stack.pop();

                stringBuffer.append(top.data  + " ");
                p = top.right;
            }
        }
        return stringBuffer.toString();
    }

    public void preInorder(){
        Stack<RBNode> stack = new Stack<>();
        RBNode<T> p = this.root;

        while(p != null || stack.isEmpty() == false){
            while(p != null){
                String color = p.color == RED ? "R" :"B";
                System.out.print(p.data + color + " ");
                stack.push(p);
                p = p.left;

            }

            if(stack.isEmpty() == false){
                p = stack.pop();
                p = p.right;
            }
        }
        System.out.println();
    }

    public void postInorder(){
        RBNode<T> p = this.root;
        RBNode<T> prev = null;
        Stack<RBNode<T>> stack = new Stack<>();

        while(p != null || stack.empty() == false){
            if(p != null){
                stack.push(p);
                p = p.left;
            }
            else{
                p = stack.peek();
                if(p.right != null && p.right != prev){
                    p = p.right;
                }else{
                    p = stack.pop();
                    String color = p.color == RED ? "R" :"B";
                    System.out.print(p.data + color + " ");
                    prev = p;
                    p = null;
                }
            }
        }
        System.out.println();
    }

}

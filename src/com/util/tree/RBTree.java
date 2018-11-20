package com.util.tree;

import java.util.Stack;

//利用红黑树实现set模板
public class RBTree<T extends Comparable<T>> {

    private final static boolean RED = false;
    private final static boolean BLACK = true;

    private class RBNode<T extends Comparable<T>> extends Node<T> {

        protected RBNode<T> left;
        protected RBNode<T> right;
        protected RBNode<T> parent;
        protected boolean color;

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

    private void setColor(RBNode<T> node, boolean color){
        if(node != null)
            node.color = color;
    }

    private boolean getColor(RBNode<T> node){
        return node == null ? BLACK : node.color;
    }

    private RBNode<T> getParent(RBNode<T> node){
        return node == null ? null : node.parent;
    }

    private void setParent(RBNode<T> node, RBNode<T> newParent){
        if(node != null)
            node.parent = newParent;

    }

    private RBNode<T> getLeft(RBNode<T> node){
        return node == null ? null : node.left;
    }

    private void setLeft(RBNode<T> node, RBNode<T> newLeft){
        if(node != null)
            node.left = newLeft;
    }

    private RBNode<T> getRight(RBNode<T> node){
        return node == null ? null : node.right;
    }

    private void setRight(RBNode<T> node, RBNode<T> newRight){
        if(node != null)
            node.right = newRight;
    }

    private void setData(RBNode<T> node, T newData){
        if(node != null)
            node.data = newData;
    }

    private T getData(RBNode<T> node){
        return node == null ? null : node.data;
    }

    private void leftRotate(RBNode<T> node){
        if(node == null)
            return ;
        RBNode<T> rotateNode = getRight(node);
        setRight(node, getLeft(rotateNode));
        if(getLeft(rotateNode) != null)
            setParent(getLeft(rotateNode), node);

        setLeft(rotateNode, node);

        if(getParent(node) == null)
            this.root = rotateNode;
        else{
            if(getLeft(getParent(node)) == node)
                setLeft(getParent(node), rotateNode);
            else
                setRight(getParent(node), rotateNode);
        }

        setParent(rotateNode, getParent(node));
        setParent(node, rotateNode);
    }

    //平衡二叉树的右旋
    private void rightRotate(RBNode<T> node){
        if(node == null)
            return ;
        RBNode<T> rotateNode = getLeft(node);
        setLeft(node, getRight(rotateNode));
        if(getRight(rotateNode) != null)
            setParent(getRight(rotateNode), node);

        setRight(rotateNode, node);

        if(getParent(node) == null)
            this.root = rotateNode;
        else{
            if(getLeft(getParent(node)) == node)
                setLeft(getParent(node), rotateNode);
            else
                setRight(getParent(node), rotateNode);
        }
        setParent(rotateNode, getParent(node));
        setParent(node, rotateNode);
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

    private RBNode<T> findMinNode(RBNode<T> node){
        RBNode<T> prev = null;
        while(node != null){
            prev = node;
            node = node.left;
        }
        return prev;
    }

    private RBNode<T> findNode(T data){
        RBNode<T> p = this.root;
        while(p != null){
            if(data.compareTo(p.data) < 0)
                p = p.left;
            else if(data.compareTo(p.data) > 0)
                p = p.right;
            else
                break;
        }
        return p;
    }

    public boolean contain(T data){
        return findNode(data) != null ? true : false;
    }

    public void delete(T data){
        RBNode<T> delNode = findNode(data);
        if(delNode == null)
            return ;

        if(getLeft(delNode) != null && getRight(delNode) != null){
            RBNode<T> min = findMinNode(getRight(delNode));
            setData(delNode, getData(min));
            delNode = min;
        }
        RBNode<T> fixUpNode = getLeft(delNode) != null ? getLeft(delNode) : getRight(delNode);

        if(fixUpNode != null){
            setParent(fixUpNode, getParent(delNode));
            if(getParent(delNode) == null)
                this.root = fixUpNode;
            else{
                if(getLeft(getParent(delNode)) == delNode)
                    setLeft(getParent(delNode), fixUpNode);
                else
                    setRight(getParent(delNode), fixUpNode);
            }
            setLeft(delNode, null);
            setRight(delNode, null);
            setParent(delNode, null);
            if(getColor(delNode) == BLACK)
                deleteFixUp(fixUpNode);
        }
        else if(getParent(delNode) == null)
            this.root = null;
        else{
            //待删除的节点没儿子
            if(getColor(delNode) == BLACK)
                deleteFixUp(delNode);
            if(getParent(delNode) != null){
                if(getLeft(getParent(delNode)) == delNode)
                    setLeft(getParent(delNode), null);
                else if(getRight(getParent(delNode)) == delNode)
                    setRight(getParent(delNode), null);

                setParent(delNode, null);
            }
        }
    }

    private void deleteFixUp(RBNode<T> node) {
        while (node != this.root && getColor(node) == BLACK) {
            if (node == getLeft(getParent(node))) {
                RBNode<T> sib = getRight(getParent(node));

                if (getColor(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(getParent(node), RED);
                    leftRotate(getParent(node));
                    sib = getRight(getParent(node));
                }

                if (getColor(getLeft(sib))  == BLACK &&
                        getColor(getRight(sib)) == BLACK) {
                    setColor(sib, RED);
                    node = getParent(node);
                } else {
                    if (getColor(getRight(sib)) == BLACK) {
                        setColor(getLeft(sib), BLACK);
                        setColor(sib, RED);
                        rightRotate(sib);
                        sib = getRight(getParent(node));
                    }
                    setColor(sib, getColor(getParent(node)));
                    setColor(getParent(node), BLACK);
                    setColor(getRight(sib), BLACK);
                    leftRotate(getParent(node));
                    node = root;
                }
            } else { // symmetric
                RBNode<T> sib = getLeft(getParent(node));

                if (getColor(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(getParent(node), RED);
                    rightRotate(getParent(node));
                    sib = getLeft(getParent(node));
                }

                if (getColor(getRight(sib)) == BLACK &&
                        getColor(getLeft(sib)) == BLACK) {
                    setColor(sib, RED);
                    node = getParent(node);
                } else {
                    if (getColor(getLeft(sib)) == BLACK) {
                        setColor(getRight(sib), BLACK);
                        setColor(sib, RED);
                        leftRotate(sib);
                        sib = getLeft(getParent(node));
                    }
                    setColor(sib, getColor(getParent(node)));
                    setColor(getParent(node), BLACK);
                    setColor(getLeft(sib), BLACK);
                    rightRotate(getParent(node));
                    node = root;
                }
            }
        }
        setColor(node, BLACK);
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

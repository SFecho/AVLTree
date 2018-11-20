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

    private void transplant(RBNode<T> deleteNode, RBNode<T> nextNode) {

        if (deleteNode == null)
            return;
        if (getParent(deleteNode) == null) {
            this.root = nextNode;
        } else {
            if (deleteNode == getLeft(getParent(deleteNode))) {
                setLeft(getParent(deleteNode), nextNode);
            } else if (deleteNode == getRight(getParent(deleteNode)))
                setRight(getParent(deleteNode), nextNode);
        }

        if(nextNode != null)
            setParent(nextNode, getParent(deleteNode));
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

    public void delete(T data){
        RBNode<T> delNode = findNode(data);

        if(delNode == null)
            return ;

        RBNode<T> fixUpNode = delNode;
        boolean originColor = getColor(delNode);

        if(getLeft(delNode) == null){
            fixUpNode = getRight(delNode);
            transplant(delNode, getRight(delNode));
        }else if(getRight(delNode) == null){
            fixUpNode = getLeft(delNode);
            transplant(delNode, getLeft(delNode));
        }else{
            RBNode<T> tmpMinNode = findMinNode(delNode.right);
            originColor = getColor(tmpMinNode);
            fixUpNode = getRight(tmpMinNode);

            if(delNode == this.root)
                this.root = tmpMinNode;

            if(getRight(delNode) != tmpMinNode){
                transplant(tmpMinNode, getRight(tmpMinNode));
                setRight(tmpMinNode, getRight(delNode));
                setParent(getRight(delNode), tmpMinNode);
            }
            delNode.left.parent = tmpMinNode;
            tmpMinNode.left = delNode.left;
            transplant(delNode, tmpMinNode);
            setColor(tmpMinNode, getColor(delNode));
        }
        if(originColor == BLACK)
            deleteFixUp(fixUpNode);
    }

//    private void fixAfterDeletion(RBNode<T> x) {
//        while (x != root && getColor(x) == BLACK) {
//            if (x == getLeft(getParent(x))) {
//                RBNode<T> sib = getRight(getParent(x));
//
//                if (getColor(sib) == RED) {
//                    setColor(sib, BLACK);
//                    setColor(getParent(x), RED);
//                    leftRotate(getParent(x));
//                    sib = getRight(getParent(x));
//                }
//
//                if (getColor(getLeft(sib))  == BLACK &&
//                        getColor(getRight(sib)) == BLACK) {
//                    setColor(sib, RED);
//                    x = getParent(x);
//                } else {
//                    if (getColor(getRight(sib)) == BLACK) {
//                        setColor(getLeft(sib), BLACK);
//                        setColor(sib, RED);
//                        rightRotate(sib);
//                        sib = getRight(getParent(x));
//                    }
//                    setColor(sib, getColor(getParent(x)));
//                    setColor(getParent(x), BLACK);
//                    setColor(getRight(sib), BLACK);
//                    leftRotate(getParent(x));
//                    x = root;
//                }
//            } else { // symmetric
//                RBNode<T> sib = getLeft(getParent(x));
//
//                if (getColor(sib) == RED) {
//                    setColor(sib, BLACK);
//                    setColor(getParent(x), RED);
//                    rightRotate(getParent(x));
//                    sib = getLeft(getParent(x));
//                }
//
//                if (getColor(getRight(sib)) == BLACK &&
//                        getColor(getLeft(sib)) == BLACK) {
//                    setColor(sib, RED);
//                    x = getParent(x);
//                } else {
//                    if (getColor(getLeft(sib)) == BLACK) {
//                        setColor(getRight(sib), BLACK);
//                        setColor(sib, RED);
//                        rightRotate(sib);
//                        sib = getLeft(getParent(x));
//                    }
//                    setColor(sib, getColor(getParent(x)));
//                    setColor(getParent(x), BLACK);
//                    setColor(getLeft(sib), BLACK);
//                    rightRotate(getParent(x));
//                    x = root;
//                }
//            }
//        }
//        setColor(x, BLACK);
//    }

    private void deleteFixUp(RBNode<T> node){
        while(node != this.root && getColor(node) == BLACK){
            RBNode<T> brother;
            RBNode<T> parent = getParent(node);
            //如果当前节点是它父亲的左儿子
            if(node == getLeft(parent)){
                brother = getRight(parent);
                //情况1：兄弟是红色，则需将父亲置红，兄弟置黑，进行一次左旋
                if(getColor(brother) == RED){
                    setColor(brother, BLACK);
                    setColor(parent, RED);
                    leftRotate(parent);
                    parent = getParent(node);
                    brother = getRight(parent);
                }
                //情况2：兄弟是黑色，两个侄子也都是黑色，将兄弟置红，node转至其父亲节点
                if(getColor(getLeft(brother)) == BLACK && getColor(getRight(brother)) == BLACK){
                    setColor(brother, RED);
                    node = getParent(node);
                    //情况3：如果左侄子为红，右侄子为黑，将左侄子置黑，兄弟置红，进行右旋
                }else{
                    if(getColor(getRight(brother)) == BLACK){
                        setColor(getLeft(brother), BLACK);
                        setColor(brother, RED);
                        rightRotate(brother);
                        parent = getParent(node);
                        brother = getRight(parent);
                    }
                    //情况4：如果右侄子为红，左侄子任意，则将父亲的颜色赋给兄弟，父亲置黑，左旋，同时node回到根节点，退出循环
                    parent = getParent(node);

                    setColor(brother, getColor(parent));
                    setColor(parent, BLACK);
                    setColor(getRight(brother), BLACK);
                    leftRotate(parent);
                    node = this.root;
                }
            }else if(node == getRight(parent)){
                brother = getLeft(parent);
                //情况1：兄弟是红色，则需将父亲置红，兄弟置黑，进行一次左旋
                if(getColor(brother) == RED){
                    setColor(brother, BLACK);
                    setColor(parent, RED);
                    rightRotate(parent);
                    parent = getParent(node);
                    brother = getLeft(parent);
                }
                //情况2：兄弟是黑色，两个侄子也都是黑色，将兄弟置红，node转至其父亲节点
                if(getColor(getLeft(brother)) == BLACK && getColor(getRight(brother)) == BLACK){
                    setColor(brother, RED);
                    node = getParent(node);
                    //情况3：如果左侄子为红，右侄子为黑，将左侄子置黑，兄弟置红，进行右旋
                }else{
                    if(getColor(getLeft(brother)) == BLACK){
                        setColor(getRight(brother), BLACK);
                        setColor(brother, RED);
                        leftRotate(brother);
                        parent = getParent(node);
                        brother = getLeft(parent);
                    }
                    //情况4：如果右侄子为红，左侄子任意，则将父亲的颜色赋给兄弟，父亲置黑，左旋，同时node回到根节点，退出循环
                    parent = getParent(node);
                    setColor(brother, getColor(parent));
                    setColor(parent, BLACK);
                    setColor(getLeft(brother), BLACK);
                    rightRotate(parent);
                    node = this.root;
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

package com.util.tree;

import java.util.*;

//利用AVL树实现set模板
public class AVLTree<T extends Comparable<T>> {
    AVLNode<T> root;

    public AVLTree() {
        this.root = null;
    }

    //树的节点，内部类
    private class AVLNode<T extends Comparable<T>> extends Node<T> {

        protected AVLNode<T> left;
        protected AVLNode<T> right;
        protected AVLNode<T> parent;
        int height;

        public AVLNode(T data) {
            super(data);
            this.height = 0;
            this.left = this.right = this.parent = null;
        }
    }

    //得到当前节点的高度
    private int getHeight(AVLNode node) {
        return node == null ? -1 : node.height;
    }

    //折半查找
    private int binSearch(T o[], T data){
        int count = 1;
        int left = 0, right = o.length - 1;
        int mid;
        while(left <= right){
            mid = (left + right) >> 1;
            if(o[mid].compareTo(data) == 0)
                break;
            else if(o[mid].compareTo(data) < 0)
                left = mid + 1;
            else
                right = mid - 1;

            count++;
        }
        return count;
    }

    //平衡二叉树的左旋
    private void leftRotate(AVLNode<T> node){
        if(node == null)
            return ;
        AVLNode<T> rotateNode = node.right;
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

        //修正树高
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        node.parent.height = node.height + 1;
        if(node.parent.parent != null)
            node.parent.parent.height = Math.max(getHeight(node.parent.parent.left), getHeight(node.parent.parent.right)) + 1;
    }

    //平衡二叉树的右旋
    private void rightRotate(AVLNode<T> node){
        if(node == null)
            return ;
        AVLNode<T> rotateNode = node.left;
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

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        node.parent.height = node.height + 1;
        if(node.parent.parent != null)
            node.parent.parent.height = Math.max(getHeight(node.parent.parent.left), getHeight(node.parent.parent.right)) + 1;
    }

    //AVL的单变量插入
    public void insert(T data){
        AVLNode newNode = new AVLNode(data);
        AVLNode prev = null;
        AVLNode search = this.root;
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

    //插入修复函数
    private void insertFixUp(AVLNode<T> node){
        while(node != null){
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            if(node.parent != null && node.parent.parent != null){
                AVLNode<T> nodeParent = node.parent;
                AVLNode<T> nodeGrandParent = node.parent.parent;
                nodeParent.height = Math.max(getHeight(nodeParent.left), getHeight(nodeParent.right)) + 1;
                nodeGrandParent.height = Math.max(getHeight(nodeGrandParent.left), getHeight(nodeGrandParent.right)) + 1;

                if(getHeight(nodeGrandParent.left) - getHeight(nodeGrandParent.right) == 2){
                    if(getHeight(nodeParent.left) < getHeight(nodeParent.right) ){
                        this.leftRotate(nodeParent);
                    }
                    this.rightRotate(nodeGrandParent);
                }
                else if(getHeight(nodeGrandParent.left) - getHeight(nodeGrandParent.right) == -2){
                    if(getHeight(nodeParent.left) > getHeight(nodeParent.right)){
                        this.rightRotate(nodeParent);
                    }
                    this.leftRotate(nodeGrandParent);
                }
            }
            node = node.parent;
        }
    }

    //
    public boolean contain(T data){
        return findNode(data)!= null ? true : false;
    }

    public void preInorder(){
        Stack<AVLNode> stack = new Stack<>();
        AVLNode<T> p = this.root;

        while(p != null || stack.isEmpty() == false){
            while(p != null){
                System.out.print(p.data + " ");
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
        AVLNode<T> p = this.root;
        AVLNode<T> prev = null;
        Stack<AVLNode<T>> stack = new Stack<>();

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
                    System.out.print(p.data + " ");
                    prev = p;
                    p = null;
                }
            }
        }
        System.out.println();
    }

    //插入有序数组
    public void insert(T o[]){
        if(o == null)
            return ;

        Arrays.sort(o);
        LinkedList<Pair<T,Integer>> pairList = new LinkedList<>();

        for(T tmp : o){
            Pair pa = new Pair(tmp, 0);
            pairList.addLast(pa);
        }

        for(Pair<T,Integer> tmp : pairList){
            //折半查找部分
            int count = binSearch(o, tmp.getFirst());
            tmp.setSecond(count);
        }

        pairList.sort((Pair<T,Integer> o1, Pair<T,Integer> o2)->o1.second - o2.second);

        for(Pair<T,Integer> tmp : pairList){
            System.out.println("[" + tmp.getFirst() + ", " + tmp.getSecond() + "]");
        }

        for(Pair<T,Integer> tmp : pairList){
            this.insert(tmp.getFirst());
        }
    }

    private AVLNode<T> findMinNode(AVLNode<T> node){
        AVLNode<T> prev = null;
        while(node != null){
            prev = node;
            node = node.left;
        }
        return prev;
    }

    private AVLNode<T> findNode(T data){
        AVLNode<T> p = this.root;
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

    //平衡二叉树的嫁接节点
    private void transplant(AVLNode<T> deleteNode, AVLNode<T> nextNode){
        if(deleteNode == null)
            return ;
        if(deleteNode.parent == null) {
            this.root = nextNode;
        }
        else{
            if(deleteNode == deleteNode.parent.left){
                deleteNode.parent.left = nextNode;
            }
            else if(deleteNode == deleteNode.parent.right)
                deleteNode.parent.right = nextNode;
        }

        if(nextNode != null)
            nextNode.parent = deleteNode.parent;
    }

    //删除指定节点
    public void delete(T data){
        AVLNode<T> delNode = findNode(data);

        if(delNode == null)
            return ;
        AVLNode<T> fixUpNode = delNode.parent;

        if(delNode.left == null)
            this.transplant(delNode, delNode.right);
        else if(delNode.right == null)
            this.transplant(delNode, delNode.left);
        else{
            AVLNode<T> tmpMinNode = this.findMinNode(delNode.right);

            if(delNode.right != tmpMinNode){
                transplant(tmpMinNode, tmpMinNode.right);
                tmpMinNode.right = delNode.right;
                delNode.right.parent = tmpMinNode;
                fixUpNode = tmpMinNode.parent;
            }else
                fixUpNode = tmpMinNode;

            tmpMinNode.left = delNode.left;
            delNode.left.parent = tmpMinNode;
            transplant(delNode, tmpMinNode);
        }
        deleteFixUp(fixUpNode);
    }

    //删除修复函数
    private void deleteFixUp(AVLNode<T> node){
        while(node != null){
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            AVLNode<T> child = null;
            if(getHeight(node.left) - getHeight(node.right) == 2){
                child = node.left;
                if(getHeight(child.left) < getHeight(child.right)){
                    leftRotate(child);
                }
                rightRotate(node);
            }
            else if(getHeight(node.left) - getHeight(node.right) == -2){
                child = node.right;
                if(getHeight(child.left) > getHeight(child.right)){
                    rightRotate(child);
                }
                leftRotate(node);
            }
            node = node.parent;
        }
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        Stack<AVLNode<T>> stack = new Stack<>();

        AVLNode p = this.root;

        while(p != null || stack.isEmpty() == false){
            while(p != null){
                stack.push(p);
                p = p.left;
            }

            if(stack.isEmpty() == false){
                AVLNode<T> top = stack.pop();
                stringBuffer.append(top.data + " ");
                p = top.right;
            }
        }
        return stringBuffer.toString();
    }
}

package com.util.tree;

import jdk.nashorn.api.tree.Tree;

import java.util.*;

public class AVLTree<K extends Comparable<K>> {
    TreeNode<K>  root;

    public AVLTree() {
        this.root = null;
    }

    //树的节点，内部类
    private class TreeNode<K extends Comparable<K>> extends Node<K> {

        protected TreeNode<K> left;
        protected TreeNode<K> right;
        protected TreeNode<K> parent;
        int height;

        public TreeNode(K key) {
            super(key);
            this.height = 0;
            this.left = this.right = this.parent = null;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    //得到当前节点的高度
    private int getHeight(TreeNode node) {
        return node == null ? -1 : node.height;
    }

    //折半查找
    private int binSearch(K o[], K key){
        int count = 1;
        int left = 0, right = o.length - 1;
        int mid;
        while(left <= right){
            mid = (left + right) >> 1;
            if(o[mid].compareTo(key) == 0)
                break;
            else if(o[mid].compareTo(key) < 0)
                left = mid + 1;
            else
                right = mid - 1;

            count++;
        }
        return count;
    }

    //平衡二叉树的左旋
    private void leftRotate(TreeNode<K> node){
        if(node == null)
            return ;
        TreeNode<K> rotateNode = node.right;
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
    private void rightRotate(TreeNode<K> node){
        if(node == null)
            return ;
        TreeNode<K> rotateNode = node.left;
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
    public void insert(K data){
        TreeNode newNode = new TreeNode(data);
        TreeNode prev = null;
        TreeNode search = this.root;
        if(this.root == null)
            this.root = newNode;
        else{

            while(search != null){
                prev = search;
                if(search.data.compareTo(data) > 0)
                    search = search.left;
                else
                    search = search.right;
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
    private void insertFixUp(TreeNode<K> node){
        while(node != null){
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            if(node.parent != null && node.parent.parent != null){
                TreeNode<K> nodeParent = node.parent;
                TreeNode<K> nodeGrandParent = node.parent.parent;
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
    public boolean contain(K key){
        TreeNode<K> p = this.root;

        while(p != null){
            if(key.compareTo(p.data) < 0){
                p = p.left;
            }
            else if(key.compareTo(p.data) > 0){
                p = p.right;
            }
            else
                break;
        }
        return p != null;
    }

    public void preInorder(){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode<K> p = this.root;

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
        TreeNode<K> p = this.root;
        TreeNode<K> prev = null;
        Stack<TreeNode<K>> stack = new Stack<>();

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
    public void insert(K o[]){
        if(o == null)
            return ;

        Arrays.sort(o);
        LinkedList<Pair<K,Integer>> pairList = new LinkedList<>();

        for(K tmp : o){
            Pair pa = new Pair(tmp, 0);
            pairList.addLast(pa);
        }

        for(Pair<K,Integer> tmp : pairList){
            //折半查找部分
            int count = binSearch(o, tmp.getFirst());
            tmp.setSecond(count);
        }

        pairList.sort((Pair<K,Integer> o1, Pair<K,Integer> o2)->o1.second - o2.second);

        for(Pair<K,Integer> tmp : pairList){
            System.out.println("[" + tmp.getFirst() + ", " + tmp.getSecond() + "]");
        }

        for(Pair<K,Integer> tmp : pairList){
            this.insert(tmp.getFirst());
        }
    }

    private TreeNode<K> findMinNode(TreeNode<K> node){
        TreeNode<K> prev = null;
        while(node != null){
            prev = node;
            node = node.left;
        }
        return prev;
    }

    private TreeNode<K> findNode(K data){
        TreeNode<K> p = this.root;
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
    private void transplant(TreeNode<K> deleteNode, TreeNode<K> nextNode){
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
    public void delete(K data){
        TreeNode<K> delNode = findNode(data);

        if(delNode == null)
            return ;

        TreeNode<K> fixUpNode = delNode.parent;

        if(delNode.left == null)
            this.transplant(delNode, delNode.right);
        else if(delNode.right == null)
            this.transplant(delNode, delNode.left);
        else{
            TreeNode<K> tmpMinNode = this.findMinNode(delNode.right);
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
    private void deleteFixUp(TreeNode<K> node){
        while(node != null){
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            TreeNode<K> child = null;
            if(getHeight(node.left) - getHeight(node.right) == 2){
                child = node.left;
                if(getHeight(child) < getHeight(child)){
                    leftRotate(child);
                }
                rightRotate(node);
            }
            else if(getHeight(node.left) - getHeight(node.right) == -2){
                child = node.right;
                if(getHeight(child) > getHeight(child)){
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

        Stack<TreeNode<K>> stack = new Stack<>();

        TreeNode p = this.root;

        while(p != null || stack.isEmpty() == false){
            while(p != null){
                stack.push(p);
                p = p.left;
            }

            if(stack.isEmpty() == false){
                TreeNode<K> top = stack.pop();
                stringBuffer.append(top.data + " ");
                p = top.right;
            }
        }
        return stringBuffer.toString();
    }
}

package com.test;

import com.util.tree.AVLTree;
import com.util.tree.RBTree;

import java.util.Random;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
	// write your code here

        AVLTree<Integer> avlTree = new AVLTree<>();
        Random random = new Random();
        int a[] = new int[12];
        for(int i = 0; i < a.length; i++) {
           a[i] = random.nextInt(100);
           avlTree.insert(a[i]);
        }
        System.out.println("===========AVLTree-set===========");

        System.out.println(avlTree);
        avlTree.preInorder();
        avlTree.postInorder();

        System.out.println("===========search-test===========");
        for(int i = 0 ;i < a.length; i++)
            System.out.println(a[i] + " is " + avlTree.contain(a[i]));
        System.out.println(20 + " is " + avlTree.contain(20));

        System.out.println("===========delete-test===========");
        System.out.println("delete " + a[2]);
        avlTree.delete(a[2]);
        System.out.println(avlTree);
        avlTree.preInorder();
        avlTree.postInorder();

        System.out.println("delete " + a[3]);
        avlTree.delete(a[3]);
        System.out.println(avlTree);
        avlTree.preInorder();
        avlTree.postInorder();


        System.out.println("delete " + a[4]);
        avlTree.delete(a[4]);
        System.out.println(avlTree);
        avlTree.preInorder();
        avlTree.postInorder();

        System.out.println("delete " + a[5]);
        avlTree.delete(a[5]);
        System.out.println(avlTree);
        avlTree.preInorder();
        avlTree.postInorder();

        System.out.println("delete " + a[6]);
        avlTree.delete(a[6]);
        System.out.println(avlTree);
        avlTree.preInorder();
        avlTree.postInorder();

        //RBTree测试部分
        RBTree<Integer> rbTree = new RBTree<>();
        System.out.println("===========RBTree-set===========");
        for(int i = 0; i < a.length; i++) {
            rbTree.insert(a[i]);
        }

        System.out.println(rbTree);
        rbTree.preInorder();
        rbTree.postInorder();

        System.out.println("===========search-test===========");
        for(int i = 0 ;i < a.length; i++)
            System.out.println(a[i] + " is " + rbTree.contain(a[i]));
        System.out.println(20 + " is " + rbTree.contain(20));

        System.out.println("===========delete-test===========");

        System.out.println("delete " + a[2]);
        rbTree.delete(a[2]);
        System.out.println(rbTree);
        rbTree.preInorder();
        rbTree.postInorder();

        System.out.println("delete " + a[3]);
        rbTree.delete(a[3]);
        System.out.println(rbTree);
        rbTree.preInorder();
        rbTree.postInorder();


        System.out.println("delete " + a[4]);
        rbTree.delete(a[4]);
        System.out.println(rbTree);
        rbTree.preInorder();
        rbTree.postInorder();

        System.out.println("delete " + a[5]);
        rbTree.delete(a[5]);
        System.out.println(rbTree);
        rbTree.preInorder();
        rbTree.postInorder();

        System.out.println("delete " + a[6]);
        rbTree.delete(a[6]);
        System.out.println(rbTree);
        rbTree.preInorder();
        rbTree.postInorder();
    }
}

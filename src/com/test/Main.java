package com.test;

import com.util.tree.AVLTree;
import com.util.tree.RBTree;

import java.util.Random;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        Integer arr[] = {15, 17, 18, 36, 45, 47, 48, 53, 72, 93, 95, 100};
//        BinTree<Integer> tree = new BinTree<>();
//        tree.insert(arr);
        AVLTree<Integer> avlTree = new AVLTree<>();
        Random random = new Random();
        int a[] = new int[12];
        for(int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(100);
            avlTree.insert(a[i]);
        }
        System.out.println("AVLTree-set:");

        System.out.println(avlTree);
        avlTree.preInorder();
        avlTree.postInorder();

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

        //RBTree
        RBTree<Integer> rbTree = new RBTree<>();
        System.out.println("RBTree-set:");
        for(int i = 0; i < a.length; i++) {
            rbTree.insert(a[i]);
        }
        System.out.println(rbTree);
        rbTree.preInorder();
        rbTree.postInorder();

    }
}

package com.test;

import com.util.tree.AVLTree;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        Integer arr[] = {15, 17, 18, 36, 45, 47, 48, 53, 72, 93, 95, 100};
//        BinTree<Integer> tree = new BinTree<>();
//        tree.insert(arr);
        AVLTree<Integer> tree = new AVLTree<>();
        Random random = new Random();
        int a[] = new int[12];
        for(int i = 0; i <= 11; i++) {
            a[i] = random.nextInt(100);
            tree.insert(a[i]);
        }


        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        System.out.println("delete " + a[2]);
        tree.delete(a[2]);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        System.out.println("delete " + a[3]);
        tree.delete(a[3]);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();


        System.out.println("delete " + a[4]);
        tree.delete(a[4]);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        System.out.println("delete " + a[5]);
        tree.delete(a[5]);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        System.out.println("delete " + a[6]);
        tree.delete(a[6]);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();
    }
}

package com.test;

import com.util.tree.AVLTree;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        Integer arr[] = {15, 17, 18, 36, 45, 47, 48, 53, 72, 93, 95, 100};
//        BinTree<Integer> tree = new BinTree<>();
//        tree.insert(arr);
        AVLTree<Integer> tree = new AVLTree<>();

        for(int i = 0; i <= 11; i++)
            tree.insert(i);


        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        tree.delete(3);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        tree.delete(2);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        tree.delete(1);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        tree.delete(0);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();

        tree.delete(7);
        System.out.println(tree);
        tree.preInorder();
        tree.postInorder();
    }
}

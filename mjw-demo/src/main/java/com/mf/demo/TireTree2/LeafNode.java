package com.mf.demo.TireTree2;

/**
 * Created by user on 2016/8/8.
 * Trie叶子结点
 */
public class LeafNode extends TrieNode{
    LeafNode(char k){
        super.key=k;
        super.kind=NodeKind.LN;
    }
}



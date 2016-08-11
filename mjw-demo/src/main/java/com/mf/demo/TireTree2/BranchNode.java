package com.mf.demo.TireTree2;

/**
 * Created by user on 2016/8/8.
 * Trie内部结点
 */
public class BranchNode extends TrieNode{
    BranchNode(char k){
        super.key=k;
        super.kind=NodeKind.BN;
        super.points=new TrieNode[27];
    }

}

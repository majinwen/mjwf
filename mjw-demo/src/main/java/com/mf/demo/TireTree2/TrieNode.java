package com.mf.demo.TireTree2;

/**
 * Created by user on 2016/8/8.
 *
 * Tire 节点
 */
public class TrieNode {

    char key;
    TrieNode[] points=null;
    NodeKind kind=null;

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public TrieNode[] getPoints() {
        return points;
    }

    public void setPoints(TrieNode[] points) {
        this.points = points;
    }

    public NodeKind getKind() {
        return kind;
    }

    public void setKind(NodeKind kind) {
        this.kind = kind;
    }




}

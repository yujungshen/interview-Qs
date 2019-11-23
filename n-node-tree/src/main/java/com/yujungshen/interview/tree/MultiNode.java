package com.yujungshen.interview.tree;

import java.util.ArrayList;
import java.util.List;

public class MultiNode {
    private int number;
    private List<MultiNode> children;
    private transient int count;
    private transient int sum;

    public MultiNode(int number) {
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public List<MultiNode> getChildren() {
        return children;
    }

    public void addChildren(MultiNode n) {
        if (children == null) {
            children = new ArrayList<MultiNode>();
        }
        children.add(n);
    }

    public float average() {
        count = 1;
        sum = 0;
        traverseAndSum(this);
        System.out.println("count= " + count + ", sum=" + sum);
        return sum/count;
    }

    private void traverseAndSum(MultiNode n) {
        sum += n.getNumber();
        if (n.getChildren() != null) {
            count += n.getChildren().size();
            for (MultiNode c : n.getChildren()) {
                traverseAndSum(c);
            }
        }
    }
}

package com.yujungshen.interview.tree;

import org.junit.Assert;
import org.junit.Test;

public class MultiNodeTest {

    @Test
    public void testSingleNode() {
        MultiNode n = new MultiNode(5);
        Assert.assertEquals(5, n.average(), 0);
    }

    @Test
    public void testLevel1Nodes() {
        MultiNode n = new MultiNode(10);
        n.addChildren(new MultiNode(20));
        n.addChildren(new MultiNode(30));
        Assert.assertEquals(20, n.average(), 20);
    }

    @Test
    public void testMultiLevelNodes() {
        MultiNode n = new MultiNode(2);
        MultiNode c1 = new MultiNode(4);
        n.addChildren(c1);
        n.addChildren(new MultiNode(6));
        n.addChildren(new MultiNode(8));
        c1.addChildren(new MultiNode(10));
        c1.addChildren(new MultiNode(12));
        c1.addChildren(new MultiNode(14));
        MultiNode c2 = new MultiNode(16);
        n.addChildren(c2);
        MultiNode c3 = new MultiNode(18);
        c2.addChildren(c3);
        c3.addChildren(new MultiNode(20));
        c3.addChildren(new MultiNode(22));
        c2.addChildren(new MultiNode(0));
        Assert.assertEquals(11, n.average(), 0);
    }
}


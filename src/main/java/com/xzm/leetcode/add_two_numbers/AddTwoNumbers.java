package com.xzm.leetcode.add_two_numbers;

import com.google.common.base.Preconditions;

/**
 * Created by Bradish7Y on 15/6/29.
 * <p/>
 * You are given two linked lists representing two non-negative numbers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * <p/>
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class AddTwoNumbers {
    public static void main(String[] args) {

        AddTwoNumbers add = new AddTwoNumbers();

        ListNode resp = add.addTwoNumbers(add.assembleLinkedNode(1, 9, 1), add.assembleLinkedNode(1, 1));
        add.print(resp);

    }

    /**
     * assemble a LinkedNode
     *
     * @param args
     * @return ListNode
     */
    public ListNode assembleLinkedNode(int... args) {
        Preconditions.checkArgument(args.length > 0);

        ListNode n = new ListNode();
        ListNode ret = n;
        for (int i : args) {
            n.next = new ListNode(i);
            n = n.next;
        }
        return ret.next;
    }

    /**
     * output the value of LinkedNode
     *
     * @param p
     */
    public void print(ListNode p) {
        Preconditions.checkNotNull(p);

        ListNode node = p;
        for (; ; ) {

            if (node == null) {
                break;
            }
            System.out.print(node.val);
            node = node.next;
        }
        System.out.println();
    }

    /**
     * add
     *
     * @param node1
     * @param node2
     * @return
     */
    public ListNode addTwoNumbers(ListNode node1, ListNode node2) {
        Preconditions.checkNotNull(node1);
        Preconditions.checkNotNull(node2);

        ListNode tail = new ListNode();
        ListNode head = tail;

        int exceed = 0;

        for (; ; ) {

            int n1 = node1 == null ? 0 : node1.val;
            int n2 = node2 == null ? 0 : node2.val;

            int sum = n1 + n2 + exceed;

            exceed = 0;

            if (sum > 9) {
                exceed = sum / 10;
                sum = sum % 10;
            }

            if (node1 != null) {
                node1 = node1.next;
            }

            if (node2 != null) {
                node2 = node2.next;
            }

            if (node1 != null || node2 != null || sum > 0) {
                tail.next = new ListNode();
                tail = tail.next;
                tail.val = sum;
            } else {
                break;
            }

        }

        return head.next;
    }
}

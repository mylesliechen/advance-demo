package cx.commom.sample.niuke;


//如果有环形，走得快的可以追上走得慢的
public class ListCycle {
    public boolean findCycle(ListNode head) {
        ListNode slower = head;
        ListNode faster = head;
        while (faster != null && faster.next != null) {
            slower = slower.next;
            faster = faster.next.next;
            if (slower == faster) {
                return true;
            }
        }
        return false;
    }
}

//*Definition for singly-linked list.
//
//class ListNode {
//    int val;
//    ListNode next;
//
//    ListNode(int x) {
//        val = x;
//        next = null;
//    }
//}
package cx.commom.sample.niuke;

public class reserveListNode {

    //{1,2,3} -> {3.,2,1}
    public ListNode ReverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pre = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
}

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

//public ListNode ReverseList1(ListNode head) {
//    if (head == null) return null;
//    ListNode pre = null;
//    ListNode cur = head;
//    while (cur != null) {
//        ListNode next = cur.next;
//        cur.next = pre;
//        pre = cur;
//        cur = next;
//    }
//    return pre;
//}
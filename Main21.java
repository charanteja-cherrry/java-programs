class Node{
    int data;
   Node next;
    Node(int n)
    {
        this.data=n;
        this.next= null;
    }
}
class linkedlist{
    Node head;
	linkedlist()
	{
		this.head=null;
	}
    void add(int n)
    {
        Node newnode=new Node(n);
        if(head==null)
        {
            head=newnode;
        }
        else{
            Node curr=head;
            while(curr.next!=null){
                curr=curr.next;
            }
            curr.next=newnode;
        }
    }
    void addf(int n)
    {
        Node newnode=new Node(n);
        newnode.next=head;
        this.head=newnode;
    }
    void count()
    {
        Node curr=this.head;
        int count=0;
        while(curr!=null)
        {
            count++;
            curr=curr.next;
        }
        System.out.println(count);
    }
        void display()
        {
            Node curr=head;
            while(curr!=null){
                System.out.println(curr.data);
                curr=curr.next;
            }
        }
}


public class Main21
{
	public static void main(String[] args) {
	      linkedlist l=new linkedlist();
	      l.add(1);
	      l.add(2);
	      l.add(3);
	      l.count();
	}
}
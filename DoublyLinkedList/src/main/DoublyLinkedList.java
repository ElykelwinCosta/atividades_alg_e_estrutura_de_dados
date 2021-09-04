package main;

import org.w3c.dom.Node;

import javax.swing.text.html.HTMLDocument;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class DoublyLinkedList<Item> implements Iterable<Item> {
    private int n;          //Number of elements on list
    private Node pre;       //Sentinel before first item
    private Node post;      //Sentinel after last item

    public DoublyLinkedList(){
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
    }

    //Linked list node helper data type
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public boolean isEmpty(){
        return n == 0;
    }

    public int size(){
        return n;
    }

    //Add item to end of list
    public void add(Item item){
        Node oldlast = post.prev;
        Node x = new Node();
        x.item = item;
        x.next = post;
        x.prev = oldlast;
        post.prev = x;
        oldlast.next = x;
        n++;
    }

    public ListIterator<Item> iterator(){
        return new DoublyLinkedListIterator();
    }

    //Assumes no calls to DoublyLinkedList.add() during iteration
    private class DoublyLinkedListIterator implements ListIterator<Item> {
        private Node current = pre.next; //The node that is returned by next()
        private Node lastAcessed = null; //The last node to be returned by prev() or next()

        //Reset to null upon intervening remove() or add()
        private int index = 0;
        public boolean hasNext(){
            return index < n;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        public  int previousIndex(){
            return index - 1;
        }

        public int nextIndex(){
            return index;
        }

        public Item next(){
            if(!hasNext()) throw  new NoSuchElementException();
            lastAcessed = current;
            Item item = current.item; //end the author's joke
            current = current.next; //Standard

        }
    }

}

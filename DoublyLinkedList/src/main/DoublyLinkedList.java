package main;
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
            index ++; //Padrão

            //Observem o Item retornado é do objeto anterior ao atual
            //O atual vai para o próximo e retorna o objeto que estava
            //guardado no elemento anterior da fila

            return item;
        }

        public Item previous(){
            if (!hasPrevious()) throw new NoSuchElementException();
            current = current.prev; //Padrão
            index--; //Padrão
            lastAcessed = current;
            //Observem o Item retornado é do objeto atual - o atual vai
            //para o anterior e retorna o objeto que está agora nele
            return current.item;
        }

        //Replace the item of the element that was last acessed by
        //next() or previous()
        //Condition: no calls to remove() or add() after last call to
        //next() or previous()

        public void set(Item item){
            if (lastAcessed == null) throw new IllegalStateException();
            lastAcessed.item = item;
        }

        //remove the element that was last acessed by next() ou previous()
        //condition: no calls to remove() or add() after last call to next()
        //or previous()

        public void remove(){
            if (lastAcessed == null) throw new IllegalStateException();
            Node x = lastAcessed.prev;
            Node y = lastAcessed.next;
            x.next = y;
            y.next = x;
            n--;
            if (current == lastAcessed){
                current = y;
            } else {
                index--;
            }
            lastAcessed = null;
        }

        //add element to list
        //Insere antes do nó antes do nó atual
        public void add(Item item){
            Node ant = current.prev;
            Node y = new Node();
            Node atual = current;
            y.item = item;
            //atualizando os campos Próximos
            ant.next = y;
            y.next = ant;

            n++;
            index ++;
            lastAcessed = null;
        }
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Item item : this){
            s.append((item + " "));
        }
        return s.toString();
    }

    //A test client
    public static void main(String[] args){
        Random rand = new Random();
        int n = Integer.parseInt(args[0]);
        //add elements 1, ....., n
        System.out.println(n + "Inteiros menores que 100");
        DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();

        for(int i = 0; i < n; i++){
            list.add(rand.nextInt(100));
        }
        System.out.println("Lista Original: ");
        System.out.println(list);
        System.out.println();

        ListIterator<Integer> iterator = list.iterator();

        //Go fowards with next() and set()
        System.out.println("Some 1 usando next e set");
        while (iterator.hasNext()){
            int x = iterator.next();
            iterator.set(x + 1);
            System.out.println("Inserindo ");
            System.out.println(x + 1);
        }

        System.out.println("Lista: ");
        System.out.println(list);

        //Go backwards with previous() and set()
        System.out.println("Multiplicando por 3 usando previous e set");
        while (iterator.hasPrevious()){
            int x = iterator.previous();
            iterator.set(x + x + x);
            System.out.println("Inserindo ");
            System.out.println(x + x + x);
        }
        System.out.println("Lista: ");
        System.out.println(list);
        System.out.println();

        //Remove all elements that are multiples of 4 via next() and previous
        System.out.println("Remove os elementos que são multiplos de 4 usando next e remove");
        while (iterator.hasNext()){
            int x = iterator.next();
            if (x % 4 == 0){
                iterator.remove();
            }
        }
        System.out.println("Lista: ");
        System.out.println(list);
        System.out.println();

        //Remove all even elements via previous() and remove()
        System.out.println("Remove os elementos pares usando next e remove");
        while (iterator.hasPrevious()){
            int x = iterator.previous();
            if (x % 2 == 0){
                iterator.remove();
            }
        }
        System.out.println("Lista: ");
        System.out.println(list);
        System.out.println();
    }


}


package arraylist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

public class MyArrayList <T> implements List<T>, Iterable<T> {

    private static final int TILE_SIZE = 1024;
    private int size = 0;

    private int tileCount = 0;
    private T[] data;

    class ListItr<t> implements ListIterator<t>{
        private int position;
        int lastElement;

        ListItr(int index){
            position = index;
        }

        ListItr(){
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public t next() {
            lastElement = position;
            return (t)MyArrayList.this.data[position++];
        }

        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public t previous() {
            lastElement = --position;
            return (t)MyArrayList.this.data[position];
        }

        @Override
        public int nextIndex() {
            return position;
        }

        @Override
        public int previousIndex() {
            return position - 1;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(t t) {
            Object[] arrData = MyArrayList.this.data;
            arrData[lastElement] = t;
        }

        @Override
        public void add(t t) {

        }
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int size) {
        this.size = size;
        data = (T[])new Object[size];
        tileCount = size / TILE_SIZE;
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(){
        data = (T[])new Object[TILE_SIZE];
        tileCount = 1;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for (T item : data){
            if (item.equals(o)) return true;
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int position = 0;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public T next() {
                return data[position++];
            }
        };
    }

    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        T[] arrayData = (T[])new Object[size];
        System.arraycopy(data, 0, arrayData, 0, size);
        return arrayData;
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public boolean add(T t) {
        size++;
        if (size > data.length){
            tileCount++;
            if (tileCount > 100) return false;
            T[] tempData = data.clone();
            data = (T[])new Object[tileCount * TILE_SIZE];
            System.arraycopy(tempData, 0, data, 0, size - 1);
        }

        data[size-1] = t;
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        if (size == 0) return false;

        int removeIndex = indexOf(o);
        if (removeIndex < 0) return false;

        remove(removeIndex);

        return true;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean addAll(Collection<? extends T> c) {
        T[] tempData = data.clone();

        int collectionSize = c.size();
        size += collectionSize;
        tileCount = (int)Math.ceil((double)size / TILE_SIZE);

        if (size > data.length){
            data = (T[])new Object[tileCount * TILE_SIZE];
            System.arraycopy(tempData, 0, data, 0, size - collectionSize);
        }

        System.arraycopy(c.toArray(), 0, data, size - collectionSize, collectionSize);

        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends T> c) {
        T[] tempData = data.clone();
        int collectionSize = c.size();

        size = index + 1 + collectionSize;
        tileCount = (int)Math.ceil((double)size / TILE_SIZE);

        if (size > data.length){
            data = (T[])new Object[tileCount * TILE_SIZE];
            System.arraycopy(tempData, 0, data, 0, index + 1);
        }

        System.arraycopy(c.toArray(), 0, data, index + 1, collectionSize);
        return true;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        size = 0;
        tileCount = 1;
        data = (T[]) new Object[TILE_SIZE];
    }

    public T get(int index) {
        return data[index];
    }

    public T set(int index, T element) {
        T elem = data[index];
        data[index] = element;
        return elem;
    }

    @SuppressWarnings("unchecked")
    public void add(int index, T element) {
        size++;
        T[] tempData = data.clone();
        if (size > data.length){
            tileCount++;
            if (tileCount > 100) return;
            data = (T[])new Object[tileCount * TILE_SIZE];
        }
        System.arraycopy(tempData, 0, data, 0, index);
        data[index] = element;
        System.arraycopy(tempData, index, data, index + 1, size - 1 - index);
    }

    @SuppressWarnings("unchecked")
    public T remove(int removeIndex) {
        T removeElement = data[removeIndex];

        T[] tempData = data.clone();
        size --;
        if (data.length - size >= TILE_SIZE){
            tileCount--;
            data = (T[])new Object[tileCount * TILE_SIZE];
        }

        System.arraycopy(tempData, 0, data, 0, removeIndex);
        System.arraycopy(tempData, removeIndex + 1, data, removeIndex, size - removeIndex);

        return removeElement;
    }

    public int indexOf(Object o) {
        int index = -1;
        for (int item = 0; item < size; item++){
            if (data[item].equals(o)){
                index = item;
                break;
            }
        }
        return index;
    }

    public int lastIndexOf(Object o) {
        int index = -1;
        for (int item = size - 1; item >= 0; item--){
            if (data[item].equals(o)){
                index = item;
                break;
            }
        }

        return index;
    }

    public ListIterator<T> listIterator() {
        return new ListItr<>();
    }

    public ListIterator<T> listIterator(int index) {
        return new ListItr<>(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (T item : this) {
            action.accept(item);
        }
    }
}

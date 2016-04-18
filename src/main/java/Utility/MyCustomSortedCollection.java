package Utility;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by utkarshc on 16/4/16.
 */
public class MyCustomSortedCollection<T> {
    private Map<Integer, T> theRepository;
    private Comparator<Integer> theComparator;
    private List<Integer> sortedElems;

    public MyCustomSortedCollection(Map<Integer, T> repo, Method method) {
        this.theRepository = repo;
        theComparator = new Comparator<Integer>() {
            private Map<Integer, T> theRepo;
            private Method method;
            @Override
            public int compare(Integer i, Integer t1) {
                try {
                    return (int) method.invoke(theRepo.get(i), theRepo.get(t1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            public Comparator<Integer> setParams(Map<Integer, T> theRepo, Method method) {
                this.theRepo = theRepo;
                this.method = method;
                return this;
            }
        }.setParams(theRepository, method);
        this.sortedElems = new ArrayList<>();
    }

    public void reinitializeList(List<Integer> elems) {
        this.sortedElems = elems;
//        this.sortedElems = new ArrayList<>();
//        this.sortedElems.addAll(elems);
//        this.sortedElems.sort(theComparator);
    }

    public boolean addElem(Integer val) {
        int idx = Collections.binarySearch(this.sortedElems, val, this.theComparator);
        if(idx < 0) {
            this.sortedElems.add(-idx-1, val);
            return true;
        }
        else if (val != this.sortedElems.get(idx)) {
            this.sortedElems.add(idx, val);
            return true;
        }
        return false;
    }

    public T getElem(T dummy) {
        this.theRepository.put(-1, dummy);
        int idx = Collections.binarySearch(this.sortedElems, -1, this.theComparator);
        if(idx < 0)
            return null;

        return this.theRepository.getOrDefault(this.sortedElems.get(idx), null);
    }

    public List<T> getAllElem(T dummy) {
        this.theRepository.put(-1, dummy);
        int idx = Collections.binarySearch(this.sortedElems, -1, this.theComparator);
        if(idx < 0)
            return null;
        int min = idx;
        while(min >0 && this.theComparator.compare(this.sortedElems.get(min-1), this.sortedElems.get(min)) == 0) min--;
        int max = idx;
        while(max < (this.sortedElems.size() - 1) && this.theComparator.compare(this.sortedElems.get(max + 1), this.sortedElems.get(max)) == 0) max++;

        List<T> ret = new ArrayList<>();
        for(int i = min; i <= max; i++) {
            ret.add(this.theRepository.getOrDefault(this.sortedElems.get(i), null));
        }
        return ret;
    }

    public T getMaxElem() {
        int idx = this.sortedElems.size() - 1;
        if(idx < 0)
            return null;

        return this.theRepository.getOrDefault(this.sortedElems.get(idx), null);
    }

    public T getMinElem() {
        if(this.sortedElems.size() == 0)
            return null;

        return this.theRepository.getOrDefault(this.sortedElems.get(0), null);
    }

    public List<T> getAllMaxElem() {
        int idx = this.sortedElems.size() - 1;
        if(idx < 0)
            return null;

        int min = idx;
        while(min >0 && this.theComparator.compare(this.sortedElems.get(min-1), this.sortedElems.get(min)) == 0) min--;
        int max = idx;

        List<T> ret = new ArrayList<>();
        for(int i = min; i <= max; i++) {
            ret.add(this.theRepository.getOrDefault(this.sortedElems.get(i), null));
        }
        return ret;
    }

    public List<T> getAllMinElem() {
        if(this.sortedElems.size() == 0)
            return null;

        int min = 0;
        int max = 0;
        while(max < (this.sortedElems.size() - 1) && this.theComparator.compare(this.sortedElems.get(max + 1), this.sortedElems.get(max)) == 0) max++;

        List<T> ret = new ArrayList<>();
        for(int i = min; i <= max; i++) {
            ret.add(this.theRepository.getOrDefault(this.sortedElems.get(i), null));
        }
        return ret;
    }

    public boolean removeElem(T dummy) {
        this.theRepository.put(-1, dummy);
        int idx = Collections.binarySearch(this.sortedElems, -1, this.theComparator);
        if(idx < 0)
            return false;

        this.sortedElems.remove(idx);
        return true;
    }

    public boolean removeExactElem(T dummy, int valInQueue) {
        this.theRepository.put(-1, dummy);
        int idx = Collections.binarySearch(this.sortedElems, -1, this.theComparator);
        if(idx < 0)
            return false;

        if(this.sortedElems.get(idx) == valInQueue) {
            this.sortedElems.remove(idx);
            return true;
        }

        int min = idx;
        while(min >0 && this.theComparator.compare(this.sortedElems.get(min-1), this.sortedElems.get(min)) == 0) min--;
        int max = idx;
        while(max < (this.sortedElems.size() - 1) && this.theComparator.compare(this.sortedElems.get(max + 1), this.sortedElems.get(max)) == 0) max++;

        for(int i = min; i <= max; i++) {
            if(this.sortedElems.get(i) == valInQueue) {
                this.sortedElems.remove(i);
                return true;
            }
        }
        return false;
    }
}

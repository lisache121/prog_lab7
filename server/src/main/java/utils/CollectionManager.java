package utils;


import data.Dragon;
import databaseUtils.DBDragon;
import exceptions.CommandExecutingException;
import exceptions.EmptyCollectionException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * class to work with collection's elements
 */
public class CollectionManager {
    private ArrayDeque<Dragon> dragons;
    private final Date time = new Date();
    private final Comparator COMPARATOR = new Comparator();
    private DBDragon dbDragon;
    private final Lock readLock;
    private final Lock writeLock;




    public CollectionManager (DBDragon dbDragon) {
        this.dbDragon = dbDragon;
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    public ArrayDeque<Dragon> getCollection() {
        readLock.lock();
        try {
            return dragons;
        } finally {
            readLock.unlock();
        }
    }

    public String getType(){
        return "ArrayDeque";
    }
    public int getArraySize(){
        return dragons.size();
    }
    public void addToCollection(Dragon dragon){
        writeLock.lock();
        updateCollection();
        dragons.add(dragon);
        writeLock.unlock();
    }

    public String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        return dateFormat.format(time);
    }


    public void setCollection(ArrayDeque<Dragon> dragons) {
        this.dragons = dragons;
    }


    public long makeID(){
        if (dragons.isEmpty()) return 1L;
        return dragons.getLast().getId() + 1;
    }


    public boolean addIfMin(Dragon dragon){
        readLock.lock();
        try {
            updateCollection();
            if (dragons.isEmpty()){ return true;}
            if (dragons.stream()
                    .min(Dragon::compareTo)
                    .filter(w->w.compareTo(dragon)==-1)
                    .isPresent())
            {
                return false;
            }

            return true;
        } finally {
            readLock.unlock();
        }
    }

    public boolean addIfMax(Dragon dragon) {

        updateCollection();
        if (dragons.isEmpty()){ return true;}
        if (dragons.stream()
                .max(Dragon::compareTo)
                .filter(w->w.compareTo(dragon)==1)
                .isPresent())
        {
            return false;
        }

        return true;
    }


    public Dragon getById(Long id){
        return dragons.stream().filter(dragon -> dragon.getId().equals(id)).findFirst().orElse(null);
    }


    public String removeFromCollection(Long id, Map.Entry<String, String> user){
        updateCollection();
        Optional<Dragon> dragon = dragons.stream()
                .filter(d -> d.getId().equals(id))
                .findAny();
        if (dragon.isPresent()) {
            if (dbDragon.removeDragon(id, user) > 0) {
                return "The element with id " + id + " has been removed.";
            }
            throw new CommandExecutingException("User " + user.getKey() + " can't remove element with id " + id + ".");
        }
        return "The element with id " + id + " doesn't exist.";
    }

    public String maxByDescription() throws EmptyCollectionException {
        updateCollection();
        if (dragons.isEmpty()) throw new EmptyCollectionException();
        Dragon d = dragons
                .stream()
                .max(Comparator::compareByDescription).get();
        return d.toConsole();
    }

    public List<Dragon> filterByAge(int age) throws EmptyCollectionException {
        updateCollection();
        if (dragons.isEmpty()) throw new EmptyCollectionException();
        List<Dragon> filteredList = dragons.stream().filter(w-> w.getAge()<age)
                .collect(Collectors.toList());;
        return filteredList;
    }


    public void sort() throws EmptyCollectionException {
        updateCollection();
        if (dragons.isEmpty()) throw new EmptyCollectionException();
        List<Dragon> list = dragons.stream().sorted(COMPARATOR).collect(Collectors.toList());
        dragons.clear();
        dragons.addAll(list);
    }


    public String showCollection() throws EmptyCollectionException {
        readLock.lock();
        try {
            updateCollection();
            if (dragons.isEmpty()) throw new EmptyCollectionException();
            sort();
            String res = dragons.stream()
                    .map(e -> e.toConsole()).reduce("", (a,b)->a + b + "\n");
            return res;
        } finally {
            readLock.unlock();
        }

    }

    public String clearCollection(Map.Entry<String, String> user){
        updateCollection();
        if (dragons.size() == 0) return "Cannot clear the collection: the collection is already empty.";
        if (dbDragon.clearCollection(user)) return "All elements from this collection created by " + user.getKey() + " have been removed.";
        if (!dbDragon.clearCollection(user)) {
            return "No elements in this collection were created by " + user.getKey();
        }
        throw new CommandExecutingException("SQL error — couldn't clear the collection.");
    }


    public void updateCollection() {
        ArrayDeque<Dragon> updatedCollection = dbDragon.getCollection();
        if (updatedCollection != null) {
            dragons = updatedCollection;
        } else throw new RuntimeException();
    }

}

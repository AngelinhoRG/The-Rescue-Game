package com.mycompany.a4;

import java.util.ArrayList;
import java.util.List;

// Collection class to manage game objects
public class GameObjectCollection implements ICollection<GameObject> {

    private List<GameObject> gameObjects;

    // Constructor
    public GameObjectCollection() {
        gameObjects = new ArrayList<>();
    }

    @Override
    public void add(GameObject obj) {
        gameObjects.add(obj);
    }

    @Override
    public IIterator<GameObject> getIterator() {
        return new GameObjectIterator();
    }

    // Remove all objects that are in the given list
    public void removeAll(List<GameObject> objectsToRemove) {
        gameObjects.removeAll(objectsToRemove);
    }

    // Custom Iterator class for GameObjectCollection
    private class GameObjectIterator implements IIterator<GameObject> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < gameObjects.size();
        }

        @Override
        public GameObject getNext() {
            return gameObjects.get(currentIndex++);
        }

        @Override
        public void remove() {
            if (currentIndex > 0) {
                gameObjects.remove(--currentIndex);
            }
        }
    }
}

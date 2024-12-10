package com.mycompany.a4;

import java.util.*;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

// Data Model
public class GameWorld extends Observable {
    
    private Spaceship spaceship;
    private int gameClock = 0;
    private int score = 0;
    private int astronautsRescued = 0;
    private int aliensSneakedIn = 0;
    private boolean soundOn = false; 
    private int worldWidth = 2048;
    private int worldHeight = 1534;
    
    private Sound alienAstronautCollisionSound;
    private Sound alienCollisionSound;
    private Sound doorOpenSound;
    private BGSound backgroundSound;

    
    private static final int NUM_ASTRONAUTS = 4;
    private static final int NUM_ALIENS = 3;
    //private static final int MAX_SCORE = NUM_ASTRONAUTS * 10;

    private GameObjectCollection gameObjectCollection;
    private boolean running = true;
    

    // Constructor
    public GameWorld() {
        gameObjectCollection = new GameObjectCollection();
    }
    
    

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public void restoreHealth(Astronaut astronaut) {
        astronaut.restoreHealth(); // Reset health, color, and speed
    }


    // Initialize the game objects
    public void init() {
        Point spaceshipLocation = generateRandomLocation();
        int spaceshipColor = ColorUtil.YELLOW;
        spaceship = Spaceship.getInstance(spaceshipLocation, spaceshipColor); // Singleton Pattern
        System.out.println("Spaceship initialized at: (" + spaceshipLocation.getX() + ", " + spaceshipLocation.getY() + ")");


        // Add spaceship to the collection
        gameObjectCollection.add(spaceship);

        // Spawn astronauts and aliens
        spawnAstronauts();
        spawnAliens();

        setChanged();
        notifyObservers(); // Notify observers of the initial state
        System.out.println("GameWorld dimensions: width=" + worldWidth + ", height=" + worldHeight);
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            System.out.println("Object in GameWorld: " + obj);
        }
        
        setChanged();
        notifyObservers();
        System.out.println("GameWorld dimensions: width=" + worldWidth + ", height=" + worldHeight);


    }

    private void spawnAstronauts() {
        for (int i = 0; i < NUM_ASTRONAUTS; i++) {
            Point randomLocation = generateRandomLocation();
            Astronaut astronaut = new Astronaut(randomLocation, ColorUtil.rgb(255, 0, 0));
            gameObjectCollection.add(astronaut);
            System.out.println("Astronaut added at: (" + randomLocation.getX() + ", " + randomLocation.getY() + "), size: " + astronaut.getSize());
        }
    }

    private void spawnAliens() {
        for (int i = 0; i < NUM_ALIENS; i++) {
            Point randomLocation = generateRandomLocation();
            Alien alien = new Alien(randomLocation, ColorUtil.rgb(0, 255, 0));
            gameObjectCollection.add(alien);
            System.out.println("Alien added at: (" + randomLocation.getX() + ", " + randomLocation.getY() + "), size: " + alien.getSize());
        }
    }


    // Manage sound state
    public void toggleSound() {
        soundOn = !soundOn;
        if (soundOn) {
            backgroundSound.play(); // Start or resume background sound
        } else {
            backgroundSound.pause(); // Pause background sound
        }
        setChanged();
        notifyObservers();
    }



    public boolean isSoundOn() {
        return soundOn;
    }

    // Expand the spaceship door
    public void expand() {
        int currentSize = spaceship.getSize();
        if (currentSize <= 990) {
            spaceship.setSize(currentSize + 10);
            System.out.println("Spaceship door expanded.");
            setChanged();
            notifyObservers();
        } else {
            System.out.println("Spaceship door is already at maximum size.");
        }
    }

    // Contract the spaceship door
    public void contract() {
        int currentSize = spaceship.getSize();
        if (currentSize >= 60) {
            spaceship.setSize(currentSize - 10);
            System.out.println("Spaceship door contracted.");
            setChanged();
            notifyObservers();
        } else {
            System.out.println("Spaceship door is already at minimum size.");
        }
    }

    /* Methods to move the spaceship */
    
    public void moveShipUp() {
        spaceship.moveUp();
        System.out.println("Spaceship moved up.");
        setChanged();
        notifyObservers();
    }

    public void moveShipDown() {
        spaceship.moveDown();
        System.out.println("Spaceship moved down.");
        setChanged();
        notifyObservers();
    }

    public void moveShipLeft() {
        spaceship.moveLeft();
        System.out.println("Spaceship moved left.");
        setChanged();
        notifyObservers();
    }

    public void moveShipRight() {
        spaceship.moveRight();
        System.out.println("Spaceship moved right.");
        setChanged();
        notifyObservers();
    }

    // Move the spaceship to a random astronaut

    public void moveShipToRandomAstronaut() {
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        Astronaut target = null;
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof Astronaut) {
                target = (Astronaut) obj;
                break; // stop once an astronaut is found
            } else {
                System.out.println("Skipping non-astronaut object at: " + obj.getLocation() + " of type " + obj.getClass().getSimpleName());
            }
        }
        if (target != null) {
            spaceship.jumpToLocation(target.getLocation());
            System.out.println("Spaceship moved to a random astronaut.");
        } else {
            System.out.println("No astronauts available.");
        }
        setChanged();
        notifyObservers();
    }

    public void moveShipToRandomAlien() {
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        Alien target = null;
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof Alien) {
                target = (Alien) obj;
                System.out.println("Found an alien at: " + obj.getLocation()); // Debugging line
                break; // stop once an alien is found
            } else {
                System.out.println("Skipping non-alien object at: " + obj.getLocation() + " of type " + obj.getClass().getSimpleName());
            }
        }
        if (target != null) {
            spaceship.jumpToLocation(target.getLocation());
            System.out.println("Spaceship moved to a random alien.");
        } else {
            System.out.println("No aliens available.");
        }
        setChanged();
        notifyObservers();
    }




    // Open the spaceship door and update score
    public void openDoor() {
        List<GameObject> objectsToRemove = new ArrayList<>();
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        System.out.println("Spaceship Door Opened");
        
        if (isSoundOn()) {
            doorOpenSound.play();
        }

        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof Astronaut && isAtSpaceshipDoor(obj)) {
                System.out.println("Astronaut Rescued!");
                Astronaut astronaut = (Astronaut) obj;
                score += (10 + astronaut.getHealth() - 5);
                objectsToRemove.add(astronaut);
                astronautsRescued++;
            } else if (obj instanceof Alien && isAtSpaceshipDoor(obj)) {
                System.out.println("Alien Snuck In!");
                score -= 10;
                objectsToRemove.add(obj);
                aliensSneakedIn++;
            }
        }

        // Remove rescued astronauts and sneaked-in aliens from the game world
        gameObjectCollection.removeAll(objectsToRemove);

        // Check if the game should end
        if (astronautsRescued >= NUM_ASTRONAUTS) {
            endGame(); // Only end the game when all astronauts are rescued
        } else {
            System.out.println("Astronauts Rescued: " + astronautsRescued + "/" + NUM_ASTRONAUTS);
        }

        setChanged();
        notifyObservers();
    }


    // Method to print the final game state and end the game
    private void endGame() {
        System.out.println("All astronauts have been rescued! The game has ended! CONGRATS!\n");
        printGameStateValues();
        System.exit(0);
    }

    /// Tick method to handle movement and collision detection
    public void tick(int elapsedTime) {
        gameClock++;

        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        ArrayList<GameObject> objects = new ArrayList<>();

        // Move objects
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof IMoving) {
                ((IMoving) obj).move(elapsedTime);
            }
            objects.add(obj);
        }

        // Detect and handle collisions
        for (int i = 0; i < objects.size(); i++) {
            GameObject obj1 = objects.get(i);
            if (obj1 instanceof ICollider) {
                for (int j = i + 1; j < objects.size(); j++) {
                    GameObject obj2 = objects.get(j);
                    if (obj2 instanceof ICollider) {
                        ICollider collider1 = (ICollider) obj1;
                        ICollider collider2 = (ICollider) obj2;

                        if (collider1.collidesWith(obj2) && !collider1.getCollisionVector().contains(obj2)) {
                            collider1.handleCollision(obj2, this); // Handle collision from obj1's perspective
                            collider2.getCollisionVector().add(obj1); // Add obj1 to obj2's vector
                            collider1.getCollisionVector().add(obj2); // Add obj2 to obj1's vector
                        } else if (!collider1.collidesWith(obj2)) {
                            // Remove stale collisions
                            collider1.getCollisionVector().remove(obj2);
                            collider2.getCollisionVector().remove(obj1);
                        }
                    }
                }
            }
        }

        setChanged();
        notifyObservers();
    }


    // Utility methods
    Point generateRandomLocation() {
        Random random = new Random();
        float x = random.nextFloat() * worldWidth;  // Use worldWidth as the upper bound for X
        float y = random.nextFloat() * worldHeight; // Use worldHeight as the upper bound for Y
        return new Point(x, y);
    }


    private boolean isAtSpaceshipDoor(GameObject obj) {
        int doorSize = spaceship.getSize();
        Point spaceshipLocation = spaceship.getLocation();
        Point objectLocation = obj.getLocation();
        float distanceX = Math.abs(spaceshipLocation.getX() - objectLocation.getX());
        float distanceY = Math.abs(spaceshipLocation.getY() - objectLocation.getY());
        return distanceX <= (doorSize / 2) && distanceY <= (doorSize / 2);
    }

    // Print game state
    public void printGameStateValues() {
        System.out.println("\nCurrent Game State:");
        System.out.println("Clock Time: " + gameClock);
        System.out.println("Score: " + score);
        System.out.println("Astronauts Rescued: " + astronautsRescued);
        System.out.println("Aliens Sneaked In: " + aliensSneakedIn);
        System.out.println("Sound State: " + (soundOn ? "ON" : "OFF"));
    }
    
    // Simulate a fight between an astronaut and an alien
    public void pretendFight() {
        // Check if there are enough astronauts and aliens for a fight
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        Astronaut astronaut = null;
        Alien alien = null;
        
        if (isSoundOn()) {
            System.out.println("Playing Alien Astro Collision Sound");
            getAlienAstronautCollisionSound().play(); // Play the sound
        }

        // Find an astronaut and an alien in the game world
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof Astronaut && astronaut == null) {
                astronaut = (Astronaut) obj;
            } else if (obj instanceof Alien && alien == null) {
                alien = (Alien) obj;
            }
            // Stop the loop if we have found both
            if (astronaut != null && alien != null) {
                break;
            }
        }

        if (astronaut != null && alien != null) {
            // Decrease the astronaut's health as a result of the fight
            astronaut.decreaseHealth();
            System.out.println("Alien and astronaut fought. Astronaut health: " + astronaut.getHealth());
            setChanged();
            notifyObservers();
        } else {
            System.out.println("Not enough aliens or astronauts for a fight.");
        }
    }

    // Simulate a collision between two aliens, resulting in the creation of a new alien
    public void pretendAlienCollision() {
        // Check if there are enough aliens for a collision
        List<Alien> alienList = new ArrayList<>();
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();

        // Collect all aliens in the game world
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof Alien) {
                alienList.add((Alien) obj);
            }
        }

        if (alienList.size() >= 2) {
            // Generate a new alien as a result of the collision
            Alien newAlien = new Alien(generateRandomLocation(), ColorUtil.rgb(0, 255, 0));
            gameObjectCollection.add(newAlien);
            System.out.println("Alien collision! New alien created.");
            setChanged();
            notifyObservers();
        } else {
            System.out.println("Not enough aliens for a collision.");
        }
    }
    
    // Method to print the current game map
    public void printMap() {
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        
        // Iterate over all game objects in the collection and print their details
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            System.out.println(obj.toString());
        }
    }
    
    public int astroRemaining() {
    	int astroRemaining = NUM_ASTRONAUTS - astronautsRescued;
		return astroRemaining;
    }
    
 // Method to get the number of remaining aliens
    public int getAliensRemaining() {
        int alienCount = 0;
        IIterator<GameObject> iterator = gameObjectCollection.getIterator();
        while (iterator.hasNext()) {
            if (iterator.getNext() instanceof Alien) {
                alienCount++;
            }
        }
        return alienCount;
    }
    
    public void createSounds() {
        setAlienAstronautCollisionSound(new Sound("alien_astro_collision.wav"));
        setAlienCollisionSound(new Sound("alien_collision.wav"));
        doorOpenSound = new Sound("open_door.wav");
        setBackgroundSound(new BGSound("BGSound.wav"));
    }


    public void markChanged() {
        super.setChanged(); // Call the protected method here
    }
    
    // Getter for gameClock
    public int getGameClock() {
        return gameClock;
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // Getter for astronauts rescued
    public int getAstronautsRescued() {
        return astronautsRescued;
    }

    // Getter for aliens sneaked in
    public int getAliensSneakedIn() {
        return aliensSneakedIn;
    }
    
    public void setWorldWidth(int width) {
        this.worldWidth = width;
        System.out.println("GameWorld width set to: " + this.worldWidth);
    }

    public void setWorldHeight(int height) {
        this.worldHeight = height;
        System.out.println("GameWorld height set to: " + this.worldHeight);
    }


    // Optional getters for debugging or access
    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
    
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

	public Sound getAlienCollisionSound() {
		return alienCollisionSound;
	}

	public void setAlienCollisionSound(Sound alienCollisionSound) {
		this.alienCollisionSound = alienCollisionSound;
	}

	public Sound getAlienAstronautCollisionSound() {
		return alienAstronautCollisionSound;
	}

	public void setAlienAstronautCollisionSound(Sound alienAstronautCollisionSound) {
		this.alienAstronautCollisionSound = alienAstronautCollisionSound;
	}

	public BGSound getBackgroundSound() {
		return backgroundSound;
	}

	public void setBackgroundSound(BGSound backgroundSound) {
		this.backgroundSound = backgroundSound;
	}

    public void aacs() {
    	if (isSoundOn()) {
            System.out.println("Playing Alien Astro Collision Sound");
            getAlienAstronautCollisionSound().play(); // Play the sound
        }
    }



	


    
}

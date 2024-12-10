package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class HealCommand extends Command {
    private GameWorld gw;

    public HealCommand(GameWorld gw) {
        super("Heal");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Check if the game is paused before proceeding
        if (gw.isRunning()) {
            IIterator<GameObject> iterator = gw.getGameObjectCollection().getIterator();
            boolean healed = false;

            // Iterate through game objects to find the selected astronaut
            while (iterator.hasNext()) {
                GameObject obj = iterator.getNext();
                if (obj instanceof Astronaut && ((Astronaut) obj).isSelected()) {
                    ((Astronaut) obj).restoreHealth(); // Heal the selected astronaut
                    healed = true;
                    System.out.println("Astronaut healed: " + obj);
                    break; // Heal only the first selected astronaut
                }
            }

            if (!healed) {
                System.out.println("No selected astronaut to heal.");
            }
        } else {
            System.out.println("Heal command ignored because the game is running.");
        }
    }
}

package com.mycompany.a4;

import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable{
    private GameWorld gw;
    private ScoreView scoreView;
    
    private NewMap mv;
    private UITimer timer;
    private final int TICK_RATE = 20; // Tick rate in milliseconds
    private boolean isRunning = true; // Default state is running
	private HealCommand healCommand;

    

    // Game class constructor in the Game.java file
    public Game() {
        // Set up GameWorld
        gw = new GameWorld();
        gw.init();
        

        
        // Set up the form's layout
        this.setLayout(new BorderLayout());

        // Create and add the views
        scoreView = new ScoreView();
        
        mv = new NewMap(gw);
     // Set mv dimensions
//        mv.setWidth(2048); 
//        mv.setHeight(1534); 
//        mv.setPreferredSize(new Dimension(1400, 1400));
        
     // Set GameWorld dimensions to match mv
        
        
        System.out.println("mv dimensions: width=" + this.getWidth() + ", height=" + this.getHeight());
        System.out.println("Game world dimensions: width=" + gw.getWorldWidth() + ", height=" + gw.getWorldHeight());
        System.out.println("GameWorld dimensions set to: width=" + gw.getWorldWidth() + ", height=" + gw.getWorldHeight());


        gw.addObserver(scoreView);
        gw.addObserver(mv);

        // Add views to the form
        this.add(BorderLayout.NORTH, scoreView);
        this.add(BorderLayout.CENTER, mv);
        
        // Set up animation timer
        timer = new UITimer(this);
        timer.schedule(TICK_RATE, true, this); // Schedule with a repeat interval of TICK_RATE milliseconds
        
        // Update setupControls method to exclude the TickCommand setup
        setupControlsWithoutTick();
        // Assign healCommand as a field in Game constructor
        healCommand = new HealCommand(gw);

        
        
        // Create command objects
        ExpandCommand expandCommand = new ExpandCommand(gw);
        ContractCommand contractCommand = new ContractCommand(gw);
        MoveRightCommand moveRightCommand = new MoveRightCommand(gw);
        MoveLeftCommand moveLeftCommand = new MoveLeftCommand(gw);
        MoveUpCommand moveUpCommand = new MoveUpCommand(gw);
        MoveDownCommand moveDownCommand = new MoveDownCommand(gw);
        MoveToAstroCommand moveToAstroCommand = new MoveToAstroCommand(gw);
        MoveToAlienCommand moveToAlienCommand = new MoveToAlienCommand(gw);
        //TickCommand tickCommand = new TickCommand(gw); removed because we have a timer now
        OpenDoorCommand openDoorCommand = new OpenDoorCommand(gw);
        PretendFightCommand pretendFightCommand = new PretendFightCommand(gw);
        PretendAlienCollisionCommand pretendAlienCollisionCommand = new PretendAlienCollisionCommand(gw);
        ToggleSoundCommand toggleSoundCommand = new ToggleSoundCommand(gw);
        AboutCommand aboutCommand = new AboutCommand();
        ExitCommand exitCommand = new ExitCommand();
        HelpCommand helpCommand = new HelpCommand();
        ScoreCommand scoreCommand = new ScoreCommand(gw);
        PauseCommand pauseCommand = new PauseCommand(this);
        HealCommand healCommand = new HealCommand(gw);





        // Set up controls and assign commands
        setupControls(
        	    pretendFightCommand, 
        	    pretendAlienCollisionCommand, 
        	    moveToAlienCommand, 
        	    moveToAstroCommand, 
        	    expandCommand, 
        	    contractCommand, 
        	    moveRightCommand, 
        	    moveLeftCommand, 
        	    moveUpCommand, 
        	    moveDownCommand,  
        	    openDoorCommand, 
        	    scoreCommand, 
        	    pauseCommand,
        	    healCommand
        	);
        
        
        
        // Call setupSideMenu with the correct commands
        setupSideMenu(toggleSoundCommand, aboutCommand, exitCommand, helpCommand, scoreCommand);

        // Set up key bindings and assign commands
        setupKeyBindings(moveToAlienCommand, moveToAstroCommand, pretendAlienCollisionCommand, pretendFightCommand, expandCommand, 
        		contractCommand, moveRightCommand, moveLeftCommand, moveUpCommand, moveDownCommand, openDoorCommand);
        
        this.show(); // Ensure form is shown before creating sounds
        
       
        mv.setWidth(mv.getParent().getWidth());
        mv.setHeight(mv.getParent().getHeight());
        gw.setWorldWidth(mv.getWidth());
        gw.setWorldHeight(mv.getHeight());

        System.out.println("GameWorld dimensions set to: width=" + gw.getWorldWidth() + ", height=" + gw.getWorldHeight());

        
        
        
        gw.createSounds();

        gw.getBackgroundSound().play(); // Start playing background sound if sound is on
        
        updateCommandState();
        
        revalidate(); // Ensure all components resize correctly


    }
    
    

    private void setupControlsWithoutTick() {
		// TODO Auto-generated method stub
		
	}

	private void setupControls(Command expandCommand, Command contractCommand, Command moveRightCommand, Command moveLeftCommand, 
            Command moveUpCommand, Command moveDownCommand, Command openDoorCommand, 
            Command moveToAstroCommand, Command moveToAlienCommand, Command pretendAlienCollisionCommand, 
            Command pretendFightCommand, Command scoreCommand, Command pauseCommand, Command healCommand) {

        // Create control containers for each side
    	this.setTitle("The Rescue Game");
        Container westContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS)); // Vertically align buttons on the left
        Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS)); // Vertically align buttons on the right
        Container southContainer = new Container(new FlowLayout(Component.CENTER)); // Center alignment for buttons at the bottom
        
        // Set visible borders for containers for debugging purposes (can be removed later)
        westContainer.getStyle().setBorder(Border.createLineBorder(2, ColorUtil.rgb(0, 0, 0)));
        eastContainer.getStyle().setBorder(Border.createLineBorder(2, ColorUtil.rgb(0, 0, 0)));
        southContainer.getStyle().setBorder(Border.createLineBorder(2, ColorUtil.rgb(0, 0, 0)));

        // Add command buttons to the west container
        addButtonToContainer(expandCommand, westContainer);
        addButtonToContainer(moveLeftCommand, westContainer);
        addButtonToContainer(moveUpCommand, westContainer);
        addButtonToContainer(moveToAstroCommand, westContainer);

        // Add command buttons to the east container
        addButtonToContainer(contractCommand, eastContainer);
        addButtonToContainer(moveRightCommand, eastContainer);
        addButtonToContainer(moveDownCommand, eastContainer);
        addButtonToContainer(moveToAlienCommand, eastContainer);
        addButtonToContainer(scoreCommand, eastContainer);

        // Add command buttons to the south container and center them
        addButtonToContainer(pretendAlienCollisionCommand, southContainer);
        addButtonToContainer(pretendFightCommand, southContainer);
        addButtonToContainer(pauseCommand, southContainer);
        addButtonToContainer(healCommand, southContainer);

        // Add containers to the form
        this.add(BorderLayout.WEST, westContainer);
        this.add(BorderLayout.EAST, eastContainer);
        this.add(BorderLayout.SOUTH, southContainer);
        //this.add(BorderLayout.CENTER, centerContainer);
        
        
        mv.setWidth(mv.getParent().getWidth());
        mv.setHeight(mv.getParent().getHeight());
        mv.setPreferredSize(new Dimension(mv.getParent().getHeight(), mv.getParent().getHeight()));
        mv.repaint();
        System.out.println("mv final dimensions: " + mv.getWidth() + "x" + mv.getHeight());


    }


    private void addButtonToContainer(Command command, Container container) {
        StyledButton button = new StyledButton(command.getCommandName());
        button.setCommand(command);
        container.add(button);
        //System.out.println("Adding button with command: " + command.getCommandName() + " to container: " + container);
    }


    private void setupSideMenu(Command toggleSoundCommand, Command aboutCommand, Command exitCommand, Command helpCommand, ScoreCommand scoreCommand) {
        // Create a toolbar for the side menu
        Toolbar toolbar = new Toolbar();
        this.setToolbar(toolbar);

        // Create a sound checkbox to toggle sound state
        CheckBox soundCheckBox = new CheckBox("Sound");
        soundCheckBox.setSelected(false);
        soundCheckBox.addActionListener(evt -> gw.toggleSound());

        // Add commands to the side menu and title bar
        toolbar.addComponentToSideMenu(soundCheckBox);
        toolbar.addCommandToSideMenu(toggleSoundCommand);
        toolbar.addCommandToSideMenu(aboutCommand);
        toolbar.addCommandToSideMenu(exitCommand);
        toolbar.addCommandToRightBar(helpCommand);
        toolbar.addCommandToSideMenu(scoreCommand);
    }

    private void setupKeyBindings(Command moveToAstroCommand, Command moveToAlienCommand, Command pretendAlienCollisionCommand, 
            Command pretendFightCommand, Command expandCommand, Command contractCommand, Command moveRightCommand, Command moveLeftCommand, 
            Command moveUpCommand, Command moveDownCommand, Command openDoorCommand) {

			// Assign key strokes to commands
			addKeyListener('e', expandCommand);
			addKeyListener('c', contractCommand);
			addKeyListener('r', moveRightCommand);
			addKeyListener('l', moveLeftCommand);
			addKeyListener('u', moveUpCommand);
			addKeyListener('d', moveDownCommand);
			//addKeyListener('t', tickCommand);
			addKeyListener('s', openDoorCommand);
			addKeyListener('w', pretendAlienCollisionCommand);
			addKeyListener('f', pretendFightCommand);
			addKeyListener('a', moveToAstroCommand);	//switched commands for a and o because they do the opposites.
			addKeyListener('o', moveToAlienCommand);
	}
    
    @Override
    public void run() {
        gw.tick(TICK_RATE); // Call GameWorld's tick method with elapsed time
    }

    // Stop the timer
    public void stopTimer() {
        timer.cancel();
    }

    // Restart the timer
    public void startTimer() {
        timer.schedule(TICK_RATE, true, this);
    }
    
    public void pauseGame() {
        stopTimer(); // Stop the game timer
        isRunning = false; // Set to paused


        // Pause background sound if it's enabled and playing
        if (gw.isSoundOn() && gw.getBackgroundSound() != null) {
            gw.getBackgroundSound().pause();
            System.out.println("Background sound paused.");
        }

        System.out.println("Game paused. isRunning: " + isRunning);

        // Update UI commands to reflect the paused state
        updateCommandState(); 
    }


    public void resumeGame() {
        startTimer(); // Restart the game timer

        // Resume background sound if it was toggled on
        if (gw.isSoundOn() && gw.getBackgroundSound() != null) {
            gw.getBackgroundSound().play();
            System.out.println("Background sound resumed.");
        } else {
            System.out.println("Background sound is off or not initialized.");
        }

        // Deselect all objects when resuming the game
        IIterator<GameObject> iterator = gw.getGameObjectCollection().getIterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.getNext();
            if (obj instanceof ISelectable) {
                ((ISelectable) obj).setSelected(false);
            }
        }

        isRunning = true; // Set the game state to running
        System.out.println("Game resumed. All objects deselected.");
        
        // Update UI commands to reflect the resumed state
        updateCommandState();
    }





    private void updateCommandState() {
        if (healCommand != null) {
            healCommand.setEnabled(!isRunning); // Enable heal in pause mode
            System.out.println("Heal command enabled: " + !isRunning);
        } else {
            System.out.println("HealCommand is not initialized.");
        }
        revalidate();
    }


    public boolean isPaused() {
        return !isRunning; // If the game is not running, it is paused
    }



    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public GameWorld getGameWorld() {
        return gw;
    }


}

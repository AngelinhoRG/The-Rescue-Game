package com.mycompany.a4;
//package com.mycompany.a2;
//
//import com.codename1.ui.Button;
//import com.codename1.ui.Container;
//import com.codename1.ui.Form;
//import com.codename1.ui.Toolbar;
//import com.codename1.ui.layouts.BorderLayout;
//import com.codename1.ui.layouts.FlowLayout;
//
//public class CommandPatternForm extends Form {
//    public CommandPatternForm(GameWorld gw) {
//        // Set up the form layout
//        this.setLayout(new BorderLayout());
//
//        // Create a toolbar for the form
//        Toolbar myToolbar = new Toolbar();
//        this.setToolbar(myToolbar);
//
//        // Create command objects
//        ExpandCommand expandCommand = new ExpandCommand(gw);
//        ContractCommand contractCommand = new ContractCommand(gw);
//        MoveUpCommand moveUpCommand = new MoveUpCommand(gw);
//        MoveDownCommand moveDownCommand = new MoveDownCommand(gw);
//        MoveLeftCommand moveLeftCommand = new MoveLeftCommand(gw);
//        MoveRightCommand moveRightCommand = new MoveRightCommand(gw);
//        TickCommand tickCommand = new TickCommand(gw);
//        OpenDoorCommand openDoorCommand = new OpenDoorCommand(gw);
//
//        // Create buttons and set their commands
//        Button expandButton = new Button(expandCommand); 
//        Button contractButton = new Button(contractCommand);
//        Button upButton = new Button(moveUpCommand);
//        Button downButton = new Button(moveDownCommand);
//        Button leftButton = new Button(moveLeftCommand);
//        Button rightButton = new Button(moveRightCommand);
//        Button tickButton = new Button(tickCommand);
//        Button openDoorButton = new Button(openDoorCommand);
//
//        // Add buttons to the container
//        Container buttonContainer = new Container(new FlowLayout());
//        buttonContainer.add(expandButton);
//        buttonContainer.add(contractButton);
//        buttonContainer.add(upButton);
//        buttonContainer.add(downButton);
//        buttonContainer.add(leftButton);
//        buttonContainer.add(rightButton);
//        buttonContainer.add(tickButton);
//        buttonContainer.add(openDoorButton);
//        this.add(BorderLayout.SOUTH, buttonContainer);
//
//        // Add commands to the toolbar
//        myToolbar.addCommandToRightBar(expandCommand);
//        myToolbar.addCommandToSideMenu(contractCommand);
//        myToolbar.addCommandToSideMenu(tickCommand);
//
//        // Bind key strokes to commands
//        addKeyListener('e', expandCommand);
//        addKeyListener('c', contractCommand);
//        addKeyListener('u', moveUpCommand);
//        addKeyListener('d', moveDownCommand);
//        addKeyListener('l', moveLeftCommand);
//        addKeyListener('r', moveRightCommand);
//        addKeyListener('t', tickCommand);
//        addKeyListener('s', openDoorCommand);
//
//        // Show the form
//        this.show();
//    }
//}

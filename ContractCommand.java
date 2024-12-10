package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ContractCommand extends Command {
    private GameWorld gw;

    public ContractCommand(GameWorld gw) {
        super("Contract"); // Command name
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gw.contract(); // Invoke the contract operation in GameWorld
    }
}



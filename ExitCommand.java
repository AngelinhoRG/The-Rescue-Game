package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {

    public ExitCommand() {
        super("Exit");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (Dialog.show("Exit", "Are you sure you want to exit?", "Yes", "No")) {
            System.exit(0);
        }
    }
}

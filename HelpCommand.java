package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("Help");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Dialog.show("Help", "Commands:\n"
                + "'Expand Door', 'Contract Door', 'Move Right', 'Move Left', 'Move Up', 'Move Down', 'Tick Clock', 'Open Door'\n"
                + "Use buttons or side menu to perform commands.", "OK", null);
    }
}

package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {

    public AboutCommand() {
        super("About");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Dialog.show("About", "Developer: Angel\nCourse: CSC 133\n", "OK", null);
    }
}

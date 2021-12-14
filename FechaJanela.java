package com.faculdade;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class FechaJanela extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}

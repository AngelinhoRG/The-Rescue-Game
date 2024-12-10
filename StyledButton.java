package com.mycompany.a4;

import com.codename1.ui.Button;
import com.codename1.ui.plaf.Style;
import com.codename1.charts.util.ColorUtil;

public class StyledButton extends Button {

    // Constructor that accepts a String for the button's text
    public StyledButton(String text) {
        super(text); // Pass the text to the Button superclass
        styleButton(); // Apply custom styles
    }

    private void styleButton() {
        // Get the button's style
        Style buttonStyle = this.getAllStyles();
        
        // Set the background color using RGB (light blue color)
        int lightBlueColor = ColorUtil.rgb(0, 0, 255); 
        buttonStyle.setBgColor(lightBlueColor);
        buttonStyle.setBgTransparency(255); // Fully opaque background

        // Set the front color (text color) using RGB (dark blue color)
        int textColor = ColorUtil.rgb(255, 255, 255); 
        buttonStyle.setFgColor(textColor);

        // Set padding and margin for visuals
        buttonStyle.setPadding(3, 3, 3, 3); // Reduced padding for uniformity
        buttonStyle.setMargin(1, 1, 1, 1); // Small margin for better alignment
    }
}

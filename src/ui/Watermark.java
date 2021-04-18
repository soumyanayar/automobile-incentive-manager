package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

//Add watermark on the editor panes in the description page
public class Watermark implements FocusListener {
    String prompt;
    JEditorPane pane;

    public Watermark (String prompt, JEditorPane jEditorPane) {
        this.prompt = prompt;
        this.pane = jEditorPane;
        pane.setText(prompt);
        pane.setForeground(Color.GRAY);
    }

    //Clear watermark text when focus gained
    @Override
    public void focusGained(FocusEvent e) {
        String temp = pane.getText();
        if(temp.equals(prompt)) {
            pane.setText("");
            pane.setForeground(Color.BLACK);
        }
    }

    //display watermark in gray when focus is lost
    @Override
    public void focusLost(FocusEvent e) {
        String temp = pane.getText();
        if(temp.equals("")) {
            pane.setForeground(Color.GRAY);
            pane.setText(prompt);
        }
    }
}

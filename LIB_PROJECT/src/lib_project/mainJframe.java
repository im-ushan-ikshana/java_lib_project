/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import lib_project.sql.crudSql;
import lib_project.sql.connSql;

/**
 *
 * @author ikush
 */
public class mainJframe extends JFrame {

    // Default width and height for the frame
    public final int width = 1280;
    public final int height = 720;

    /**
     * Configures the main properties of the JFrame.
     *
     * @param _name The JFrame to be configured
     */
    public static void MainFrame(JFrame _name) {
        // Set the title, disable resizing, set close operation, and apply a FlatLaf look and feel
        _name.setTitle("Library Management System");
        _name.setResizable(false);
        _name.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        FlatMacLightLaf.setup();
    }

    /**
     * Adds a window listener to prompt a confirmation dialog before closing the
     * JFrame.
     *
     * @param _name The JFrame to which the window listener is added
     */
    public void close_messege(JFrame _name) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Output dialog box confirming close action.
                int result = JOptionPane.showConfirmDialog(_name, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                // If it is yes, then the frame is disposed.
                if (result == JOptionPane.YES_OPTION) {
                    _name.dispose();
                }
            }
        });
    }
}

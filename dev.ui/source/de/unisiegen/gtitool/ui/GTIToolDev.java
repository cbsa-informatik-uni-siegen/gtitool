package de.unisiegen.gtitool.ui;


/**
 * The main starter class for the {@link GTITool} dev project.
 * 
 * @author Christian Fehler
 * @version $Id:Start.java 761 2008-04-10 22:22:51Z fehler $
 */
public final class GTIToolDev {

    /**
     * The main method.
     * 
     * @param arguments The command line arguments.
     */
    public final static void main(String[] arguments) {
        new GTIToolDev(arguments);
    }


    /**
     * Allocates a new {@link GTIToolDev}.
     * 
     * @param arguments The command line arguments.
     */
    public GTIToolDev(String[] arguments) {
        new GTITool(arguments);
    }
}

package de.unisiegen.gtitool.ui.swing;


import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JDialog;


/**
 * Special {@link JDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIDialog extends JDialog
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2462183199568686632L;


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog () throws HeadlessException
  {
    super ();
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Dialog owner ) throws HeadlessException
  {
    super ( owner );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner Th owner.
   * @param modal The modal value.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Dialog owner, boolean modal ) throws HeadlessException
  {
    super ( owner, modal );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @param title The title.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Dialog owner, String title ) throws HeadlessException
  {
    super ( owner, title );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @param title The title.
   * @param modal The modal value.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Dialog owner, String title, boolean modal )
      throws HeadlessException
  {
    super ( owner, title, modal );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @param title The title.
   * @param modal The modal value.
   * @param gc The {@link GraphicsConfiguration}.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Dialog owner, String title, boolean modal,
      GraphicsConfiguration gc ) throws HeadlessException
  {
    super ( owner, title, modal, gc );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Frame owner ) throws HeadlessException
  {
    super ( owner );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @param modal The modal value.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Frame owner, boolean modal ) throws HeadlessException
  {
    super ( owner, modal );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @param title The title.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Frame owner, String title ) throws HeadlessException
  {
    super ( owner, title );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @param title The title.
   * @param modal The modal value.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Frame owner, String title, boolean modal )
      throws HeadlessException
  {
    super ( owner, title, modal );
  }


  /**
   * Allocates a new {@link JGTIDialog}.
   * 
   * @param owner The owner.
   * @param title The title.
   * @param modal The modal value.
   * @param gc The {@link GraphicsConfiguration}.
   * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
   */
  public JGTIDialog ( Frame owner, String title, boolean modal,
      GraphicsConfiguration gc )
  {
    super ( owner, title, modal, gc );
  }
}

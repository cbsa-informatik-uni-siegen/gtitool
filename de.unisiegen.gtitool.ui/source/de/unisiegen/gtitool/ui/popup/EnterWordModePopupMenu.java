package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * The popup menu for the enter word and the word navigation mode.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class EnterWordModePopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 706578962258712542L;


  /**
   * The start item.
   */
  private JMenuItem jMenuItemStart;


  /**
   * The previous step item.
   */
  private JMenuItem jMenuItemPreviousStep;


  /**
   * The next step item.
   */
  private JMenuItem jMenuItemNextStep;


  /**
   * The stop item.
   */
  private JMenuItem jMenuItemStop;


  /**
   * The {@link MainWindowForm}
   */
  protected MainWindowForm mainWindowForm;


  /**
   * Allocates a new {@link EnterWordModePopupMenu}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   */
  public EnterWordModePopupMenu ( MainWindowForm mainWindowForm )
  {
    this.mainWindowForm = mainWindowForm;
    populateMenues ();
  }


  /**
   * Populates the menues of this popup menu.
   */
  private final void populateMenues ()
  {
    // start
    this.jMenuItemStart = new JMenuItem ( Messages
        .getString ( "MachinePanel.WordModeStart" ) ); //$NON-NLS-1$
    this.jMenuItemStart.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/navigation/small/start.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemStart.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordStart ();
      }
    } );
    this.jMenuItemStart.setEnabled ( this.mainWindowForm.getLogic ()
        .isEnabledStart () );
    add ( this.jMenuItemStart );

    // previous step
    this.jMenuItemPreviousStep = new JMenuItem ( Messages
        .getString ( "MachinePanel.WordModePreviousStep" ) ); //$NON-NLS-1$
    this.jMenuItemPreviousStep.setIcon ( new ImageIcon ( getClass ()
        .getResource (
            "/de/unisiegen/gtitool/ui/icon/navigation/small/backward.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemPreviousStep.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordPreviousStep ();
      }
    } );
    this.jMenuItemPreviousStep.setEnabled ( this.mainWindowForm.getLogic ()
        .isEnabledPreviousStep () );
    add ( this.jMenuItemPreviousStep );

    // next step
    this.jMenuItemNextStep = new JMenuItem ( Messages
        .getString ( "MachinePanel.WordModeNextStep" ) ); //$NON-NLS-1$
    this.jMenuItemNextStep.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/navigation/small/forward.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemNextStep.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordNextStep ();
      }
    } );
    this.jMenuItemNextStep.setEnabled ( this.mainWindowForm.getLogic ()
        .isEnabledNextStep () );
    add ( this.jMenuItemNextStep );

    // stop
    this.jMenuItemStop = new JMenuItem ( Messages
        .getString ( "MachinePanel.WordModeStop" ) ); //$NON-NLS-1$
    this.jMenuItemStop.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/navigation/small/stop.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemStop.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordStop ();
      }
    } );
    this.jMenuItemStop.setEnabled ( this.mainWindowForm.getLogic ()
        .isEnabledStop () );
    add ( this.jMenuItemStop );
  }
}

package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.logic.MachinePanel.MachineMode;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * A Popup Menu for {@link State}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class EnterWordModePopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 706578962258712542L;


  /**
   * The {@link MachinePanel}
   */
  MachinePanel machinePanel;


  /**
   * The start item
   */
  private JMenuItem jMenuItemStart;


  /**
   * The previous step item
   */
  private JMenuItem jMenuItemPreviousStep;


  /**
   * The next step item
   */
  private JMenuItem jMenuItemNextStep;


  /**
   * The rename checkbox item
   */
  private JMenuItem jMenuItemStop;


  /**
   * The {@link MainWindowForm}
   */
  private MainWindowForm mainWindowForm;


  /**
   * Allocates a new {@link StatePopupMenu}.
   * 
   * @param machinePanel The {@link MachinePanel}.
   * @param mainWindowForm The {@link MainWindow}.
   */
  public EnterWordModePopupMenu ( MachinePanel machinePanel,
      MainWindowForm mainWindowForm )
  {
    this.machinePanel = machinePanel;
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

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordStart ();
      }
    } );
    this.jMenuItemStart.setEnabled ( !this.machinePanel.getMachineMode ()
        .equals ( MachineMode.WORD_NAVIGATION ) );
    add ( this.jMenuItemStart );

    // previous step
    this.jMenuItemPreviousStep = new JMenuItem ( Messages
        .getString ( "MachinePanel.WordModePreviousStep" ) ); //$NON-NLS-1$
    this.jMenuItemPreviousStep.setIcon ( new ImageIcon ( getClass ()
        .getResource (
            "/de/unisiegen/gtitool/ui/icon/navigation/small/backward.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemPreviousStep.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordPreviousStep ();
      }
    } );
    this.jMenuItemPreviousStep.setEnabled ( this.machinePanel.getMachineMode ()
        .equals ( MachineMode.WORD_NAVIGATION ) );
    add ( this.jMenuItemPreviousStep );

    // next step
    this.jMenuItemNextStep = new JMenuItem ( Messages
        .getString ( "MachinePanel.WordModeNextStep" ) ); //$NON-NLS-1$
    this.jMenuItemNextStep.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/navigation/small/forward.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemNextStep.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordNextStep ();
      }
    } );
    this.jMenuItemNextStep.setEnabled ( this.machinePanel.getMachineMode ()
        .equals ( MachineMode.WORD_NAVIGATION ) );
    add ( this.jMenuItemNextStep );

    // stop
    this.jMenuItemStop = new JMenuItem ( Messages
        .getString ( "MachinePanel.WordModeStop" ) ); //$NON-NLS-1$
    this.jMenuItemStop.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/navigation/small/stop.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemStop.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        EnterWordModePopupMenu.this.mainWindowForm.getLogic ()
            .handleWordStop ();
      }
    } );
    this.jMenuItemStop.setEnabled ( this.machinePanel.getMachineMode ().equals (
        MachineMode.WORD_NAVIGATION ) );
    add ( this.jMenuItemStop );
  }
}

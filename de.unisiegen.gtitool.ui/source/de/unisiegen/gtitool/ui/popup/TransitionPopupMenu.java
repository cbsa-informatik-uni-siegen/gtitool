package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.logic.TransitionDialog;
import de.unisiegen.gtitool.ui.netbeans.MachinePanelForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * A Popup Menu for {@link Transition}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class TransitionPopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3541518527653662496L;


  /**
   * The {@link MachinePanelForm}.
   */
  private MachinePanelForm parent;


  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * The push down {@link Alphabet}.
   */
  private Alphabet pushDownAlphabet;


  /**
   * The {@link DefaultTransitionView}.
   */
  private DefaultTransitionView transition;


  /**
   * The delete item.
   */
  private JMenuItem delete;


  /**
   * The configure item.
   */
  private JMenuItem config;


  /**
   * Allocates a new {@link StatePopupMenu}.
   * 
   * @param parent The parent panel.
   * @param transition the transition to open the popup menu.
   * @param alphabet The {@link Alphabet}.
   * @param pushDownAlphabet The push down {@link Alphabet}.
   */
  public TransitionPopupMenu ( MachinePanelForm parent,
      DefaultTransitionView transition, Alphabet alphabet,
      Alphabet pushDownAlphabet )
  {
    this.parent = parent;
    this.alphabet = alphabet;
    this.pushDownAlphabet = pushDownAlphabet;
    this.transition = transition;
    populateMenues ();

    /*
     * Language changed listener
     */
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            TransitionPopupMenu.this.delete.setText ( Messages
                .getString ( "MachinePanel.Delete" ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Populates the menues of this popup menu.
   */
  private final void populateMenues ()
  {

    this.delete = new JMenuItem ( Messages.getString ( "MachinePanel.Delete" ) ); //$NON-NLS-1$
    this.delete.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TransitionPopupMenu.this.parent.getLogic ().deleteTransition (
            TransitionPopupMenu.this.transition );
      }
    } );
    add ( this.delete );

    this.config = new JMenuItem ( Messages
        .getString ( "MachinePanel.Properties" ) ); //$NON-NLS-1$
    this.config.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/rename.png" ) ) ); //$NON-NLS-1$
    this.config.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        JFrame window = ( JFrame ) SwingUtilities
            .getWindowAncestor ( TransitionPopupMenu.this.parent );
        TransitionDialog transitionDialog = new TransitionDialog ( window,
            TransitionPopupMenu.this.parent.getLogic (),
            TransitionPopupMenu.this.alphabet,
            TransitionPopupMenu.this.pushDownAlphabet,
            TransitionPopupMenu.this.transition.getTransition () );
        transitionDialog.show ();
        TransitionPopupMenu.this.parent.getLogic ().performCellsChanged ();
      }
    } );
    add ( this.config );
  }
}

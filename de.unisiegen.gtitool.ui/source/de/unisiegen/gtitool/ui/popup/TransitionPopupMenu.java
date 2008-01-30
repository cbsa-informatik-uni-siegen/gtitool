package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.jgraph.JGraph;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.logic.TransitionDialog;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


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
   * The {@link JGraph}.
   */
  private JGraph graph;


  /**
   * The {@link MachinesPanelForm}.
   */
  private MachinesPanelForm parent;


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
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The delete item.
   */
  private JMenuItem delete;


  /**
   * The configure item.
   */
  private JMenuItem config;


  /**
   * Allocates a new <code>StatePopupMenu</code>.
   * 
   * @param jGraph The {@link JGraph}.
   * @param parent The parent panel.
   * @param model the model containing the state.
   * @param transition the transition to open the popup menu.
   * @param alphabet The {@link Alphabet}.
   * @param pushDownAlphabet The push down {@link Alphabet}.
   */
  public TransitionPopupMenu ( JGraph jGraph, MachinesPanelForm parent,
      DefaultMachineModel model, DefaultTransitionView transition,
      Alphabet alphabet, Alphabet pushDownAlphabet )
  {
    this.graph = jGraph;
    this.parent = parent;
    this.alphabet = alphabet;
    this.pushDownAlphabet = pushDownAlphabet;
    this.model = model;
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
        int choice = JOptionPane.NO_OPTION;
        String message = Messages.getString (
            "TransitionDialog.DeleteTransitionQuestion", //$NON-NLS-1$
            TransitionPopupMenu.this.transition );
        choice = JOptionPane.showConfirmDialog ( null, message, Messages
            .getString ( "TransitionDialog.DeleteTransitionTitle" ), //$NON-NLS-1$
            JOptionPane.YES_NO_OPTION );
        if ( choice == JOptionPane.YES_OPTION )
        {
          TransitionPopupMenu.this.model
              .removeTransition ( TransitionPopupMenu.this.transition );
        }
      }
    } );
    add ( this.delete );

    this.config = new JMenuItem ( Messages
        .getString ( "MachinePanel.Properties" ) ); //$NON-NLS-1$
    this.config.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/properties.png" ) ) ); //$NON-NLS-1$
    this.config.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        JFrame window = ( JFrame ) SwingUtilities
            .getWindowAncestor ( TransitionPopupMenu.this.parent );
        TransitionDialog dialog = new TransitionDialog ( window,
            TransitionPopupMenu.this.alphabet,
            TransitionPopupMenu.this.pushDownAlphabet,
            TransitionPopupMenu.this.transition.getTransition ().getSymbol (),
            TransitionPopupMenu.this.transition.getSourceView ().getState (),
            TransitionPopupMenu.this.transition.getTargetView ().getState () );
        dialog.show ();
        if ( dialog.DIALOG_RESULT == TransitionDialog.DIALOG_CONFIRMED )
        {
          Transition newTransition = dialog.getTransition ();
          TransitionPopupMenu.this.graph.getGraphLayoutCache ()
              .valueForCellChanged ( TransitionPopupMenu.this.transition,
                  newTransition );
          Transition oldTransition = TransitionPopupMenu.this.transition
              .getTransition ();
          oldTransition.clear ();
          try
          {
            oldTransition.add ( newTransition );
          }
          catch ( TransitionException exc )
          {
            exc.printStackTrace ();
            System.exit ( 1 );
          }
        }
      }
    } );
    add ( this.config );
  }
}

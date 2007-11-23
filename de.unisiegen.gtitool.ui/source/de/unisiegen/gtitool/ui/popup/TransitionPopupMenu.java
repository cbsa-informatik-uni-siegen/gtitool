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
import org.jgraph.graph.GraphModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.TransitionDialog;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


/**
 * A Popup Menu for {@link Transition}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class TransitionPopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3541518527653662496L;


  /** The {@link JGraph} */
  private JGraph graph;


  /** The {@link MachinesPanelForm} */
  private MachinesPanelForm parent;
  
  /** The link {@link Machine} */
  private Machine machine;


  /** The {@link Alphabet} */
  private Alphabet alphabet;


  /** The {@link DefaultTransitionView} */
  private DefaultTransitionView transition;


  /** The model containing the transition view */
  private GraphModel model;


  /** The delete item */
  private JMenuItem delete;


  /** The configure item */
  private JMenuItem config;


  /**
   * Allocates a new <code>StatePopupMenu</code>.
   * 
   * @param pGraph The {@link JGraph}
   * @param pParent The parent panel
   * @param pModel the model containing the state
   * @param pTransition the transition to open the popup menu
   * @param pMachine The {@link Machine}
   * @param pAlphabet The {@link Alphabet}
   */
  public TransitionPopupMenu ( JGraph pGraph, MachinesPanelForm pParent,
      GraphModel pModel, DefaultTransitionView pTransition, Machine pMachine, Alphabet pAlphabet )
  {
    this.graph = pGraph;
    this.parent = pParent;
    this.machine = pMachine;
    this.alphabet = pAlphabet;
    this.model = pModel;
    this.transition = pTransition;
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
  protected void populateMenues ()
  {

    this.delete = new JMenuItem ( Messages.getString ( "MachinePanel.Delete" ) ); //$NON-NLS-1$
    this.delete.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent e )
      {

        int choice = JOptionPane.NO_OPTION;
        String message = "Soll die Transition \"" //$NON-NLS-1$
            + TransitionPopupMenu.this.transition.toString ()
            + "\" wirklich gelöscht werden?"; //$NON-NLS-1$
        choice = JOptionPane.showConfirmDialog ( null, message,
            "Transition löschen", JOptionPane.YES_NO_OPTION ); //$NON-NLS-1$
        if ( choice == JOptionPane.YES_OPTION )
        {
          TransitionPopupMenu.this.model.remove ( new Object []
          { TransitionPopupMenu.this.transition } );
          TransitionPopupMenu.this.machine.removeTransition ( TransitionPopupMenu.this.transition.getTransition () );
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
      ActionEvent e )
      {

        JFrame window = ( JFrame ) SwingUtilities
            .getWindowAncestor ( TransitionPopupMenu.this.parent );
        TransitionDialog dialog = new TransitionDialog ( window,
            TransitionPopupMenu.this.alphabet );
        dialog.setOverChangeSet ( TransitionPopupMenu.this.transition
            .getTransition ().getSymbolSet () );
        dialog.show ();
        TransitionPopupMenu.this.model.remove ( new Object []
        { TransitionPopupMenu.this.transition } );
        MachinePanel.createTransitionView ( TransitionPopupMenu.this.graph, TransitionPopupMenu.this.transition.getTransition (),
            TransitionPopupMenu.this.transition.getSourceView (),
            TransitionPopupMenu.this.transition.getTargetView (),
            TransitionPopupMenu.this.alphabet, dialog.getAlphabet () );
      }
    } );
    add ( this.config );

  }
}

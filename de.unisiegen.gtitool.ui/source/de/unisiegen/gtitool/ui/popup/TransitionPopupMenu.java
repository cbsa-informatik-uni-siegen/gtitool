package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.jgraph.graph.GraphModel;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;


/**
 * A Popup Menu for {@link Transition}s
 * 
 * @author Benjamin Mies
 * @version $Id: NewDialog.java 119 2007-11-10 12:07:30Z fehler
 */
public class TransitionPopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3541518527653662496L;


  /** The {@link DefaultTransitionView} */
  private DefaultTransitionView transition;


  /** The model containing the transition view */
  private GraphModel model;


  /** The delete item */
  private JMenuItem delete;


  /**
   * Allocates a new <code>StatePopupMenu</code>.
   * 
   * @param pModel the model containing the state
   * @param pTransition the transition to open the popup menu
   */
  public TransitionPopupMenu ( GraphModel pModel,
      DefaultTransitionView pTransition )
  {
    this.model = pModel;
    this.transition = pTransition;
    populateMenues ();
  }


  /**
   * Populates the menues of this popup menu.
   */
  protected void populateMenues ()
  {

    this.delete = new JMenuItem ( "Löschen" ); //$NON-NLS-1$
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
        }

      }
    } );
    add ( this.delete );

  }
}

package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFrame;
import javax.swing.JLabel;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ChooseNextActionDialogForm;


/**
 * Logic for the ChooseNextActionDialogForm
 */
public final class ChooseNextActionDialog implements
    LogicClass < ChooseNextActionDialogForm >
{

  /**
   * The {@link ChooseNextActionDialogForm}
   */
  private ChooseNextActionDialogForm gui;


  /**
   * The dialogs parent
   */
  private JFrame parent;


  /**
   * The {@link ActionSet}
   */
  private ActionSet actions;


  /**
   * True if dialog was confirmed
   */
  private boolean confirmed;


  /**
   * Allocates a new {@link ChooseNextActionDialog}
   * 
   * @param parent The parent {link JFrame}
   * @param actions The {@link Action}s
   */
  public ChooseNextActionDialog ( final JFrame parent, final ActionSet actions )
  {
    if ( parent == null )
      throw new NullPointerException ( "parent is null" ); //$NON-NLS-1$
    if ( actions == null )
      throw new NullPointerException ( "actions is null" ); //$NON-NLS-1$
    if ( actions.size () < 2 )
      throw new IllegalArgumentException ( "actions set size too small" ); //$NON-NLS-1$

    this.parent = parent;
    this.actions = actions;
    this.gui = new ChooseNextActionDialogForm ( parent, true );

    JLabel entry;
    for ( Action a : this.actions )
    {
      entry = new JLabel ( a.getReduceAction ().toPrettyString ()
          .toHTMLString () );
      this.gui.jGTIListActionList.add ( entry );
    }
  }


  /**
   * Returns the chosen {@link Action}
   * 
   * @return The chosen {@link Action}
   */
  public final Production getChosenAction ()
  {
    if ( this.gui.jGTIListActionList.isSelectionEmpty () )
      throw new RuntimeException ( "No action was chosen." ); //$NON-NLS-1$
    return this.actions.get ( this.gui.jGTIListActionList.getSelectedIndex () )
        .getReduceAction ();
  }


  /**
   * Handles cancel button pressed.
   */
  public final void handleCancel ()
  {
    this.confirmed = false;
    this.gui.dispose ();
  }


  /**
   * Handle ok button pressed.
   */
  public final void handleOk ()
  {
    this.confirmed = true;
    this.gui.dispose ();
  }


  /**
   * Returns whether the dialog was confirmed or not
   * 
   * @return true if the dialog was confirmed
   */
  public final boolean isConfirmed ()
  {
    return this.confirmed;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public ChooseNextActionDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Show the dialog for creating a new transition
   */
  public final void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}

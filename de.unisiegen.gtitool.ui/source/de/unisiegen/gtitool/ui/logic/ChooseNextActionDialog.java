package de.unisiegen.gtitool.ui.logic;


import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;

import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.DefaultActionSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.ChooseNextActionDialogForm;


/**
 * Logic for the ChooseNextActionDialogForm
 */
public final class ChooseNextActionDialog implements
    LogicClass < ChooseNextActionDialogForm >
{

  /**
   * In which mode do we want to select the listed {@link Action}s
   */
  public enum SelectionMode
  {
    /**
     * single selection
     */
    SINGLE_SELECTION,

    /**
     * selection of more than one {@link Action} at a time is possible
     */
    MULTIPLE_SELECTION;
  }


  /**
   * Shall we speak of Actions or Productions?
   */
  public enum TitleForm
  {
    /**
     * normal; means: Action
     */
    NORMAL,

    /**
     * Production; means: Production
     */
    PRODUCTION;
  }


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
   * The {@link DefaultListModel}
   */
  private DefaultListModel listModel;


  private TitleForm tf;


  /**
   * Allocates a new {@link ChooseNextActionDialog}
   * 
   * @param parent The parent {link JFrame}
   * @param actions The {@link Action}s
   * @param tf The {@link TitleForm}
   */
  public ChooseNextActionDialog ( final JFrame parent, final ActionSet actions,
      final TitleForm tf )
  {
    if ( parent == null )
      throw new NullPointerException ( "parent is null" ); //$NON-NLS-1$
    if ( actions == null )
      throw new NullPointerException ( "actions is null" ); //$NON-NLS-1$

    this.parent = parent;
    this.actions = actions;
    this.gui = new ChooseNextActionDialogForm ( this, parent );

    this.listModel = new DefaultListModel ();
    for ( Action a : this.actions )
      this.listModel.addElement ( a.toPrettyString () );

    this.gui.jGTIListActionList
        .setSelectionMode ( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
    this.gui.jGTIListActionList.setModel ( this.listModel );

    this.gui.jGTIListActionList.setSelectedIndex ( 0 );

    this.tf = tf;

    if ( tf == TitleForm.NORMAL )
    {
      getGUI ()
          .setTitle ( Messages.getString ( "ChooseNextActionDialog.Title" ) ); //$NON-NLS-1$
      getGUI ().jGTILabel1.setText ( Messages
          .getString ( "ChooseNextActionDialog.Header" ) ); //$NON-NLS-1$
    }
    else
    {
      getGUI ().setTitle (
          Messages.getString ( "ChooseNextActionDialog.Title2" ) ); //$NON-NLS-1$
      getGUI ().jGTILabel1.setText ( Messages
          .getString ( "ChooseNextActionDialog.Header2" ) ); //$NON-NLS-1$
    }
  }


  /**
   * TODO
   *
   * @param ns
   * @param ts
   */
  public void setTableEntry ( final NonterminalSymbol ns, final TerminalSymbol ts )
  {
    if ( this.tf == TitleForm.NORMAL )
    {
      getGUI ()
          .setTitle ( Messages.getString ( "ChooseNextActionDialog.Title" ) ); //$NON-NLS-1$
      getGUI ().jGTILabel1.setText ( Messages.getString (
          "ChooseNextActionDialog.Header", ns, ts ) ); //$NON-NLS-1$
    }
    else
    {
      getGUI ().setTitle (
          Messages.getString ( "ChooseNextActionDialog.Title2" ) ); //$NON-NLS-1$
      getGUI ().jGTILabel1.setText ( Messages
          .getString ( "ChooseNextActionDialog.Header2", ns, ts ) ); //$NON-NLS-1$
    }
  }


  /**
   * blub
   * 
   * @param le blub
   */
  public void setLastEntry ( final PrettyString le )
  {
    this.listModel.addElement ( le );
  }


  /**
   * blub
   * 
   * @return true
   */
  public boolean lastEntrySelected ()
  {
    return this.gui.jGTIListActionList.getSelectedIndex () == this.listModel
        .getSize () - 1;
  }


  /**
   * Sets the selection mode
   * 
   * @param mode the mode
   */
  public void setSelectionMode ( final SelectionMode mode )
  {
    if ( mode == SelectionMode.SINGLE_SELECTION )
      this.gui.jGTIListActionList
          .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    else
      this.gui.jGTIListActionList
          .setSelectionMode ( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
  }


  /**
   * Returns the chosen {@link Action}s
   * 
   * @return The chosen {@link Action}s
   * @throws ActionSetException
   */
  public final ActionSet getChosenAction () throws ActionSetException
  {
    if ( this.gui.jGTIListActionList.isSelectionEmpty () )
      throw new RuntimeException ( "No action was chosen." ); //$NON-NLS-1$
    final int [] chosenActionIndices = this.gui.jGTIListActionList
        .getSelectedIndices ();
    @SuppressWarnings ( "hiding" )
    final ActionSet actions = new DefaultActionSet ();
    for ( int idx : chosenActionIndices )
      actions.add ( this.actions.get ( idx ) );
    return actions;
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

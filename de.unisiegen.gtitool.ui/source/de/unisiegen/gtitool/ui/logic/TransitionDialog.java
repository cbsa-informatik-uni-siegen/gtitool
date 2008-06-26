package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringListCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.TransitionDialogForm;
import de.unisiegen.gtitool.ui.redoundo.TransitionChangedItem;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.swing.JGTIList;
import de.unisiegen.gtitool.ui.swing.dnd.JGTIListModelRows;
import de.unisiegen.gtitool.ui.swing.dnd.JGTIListTransferHandler;


/**
 * The logic class for the create new transition dialog.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class TransitionDialog implements
    LogicClass < TransitionDialogForm >
{

  /**
   * The {@link Symbol} {@link ListModel}.
   * 
   * @author Christian Fehler
   */
  public final class SymbolListModel extends AbstractListModel implements
      Iterable < Symbol >
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -4310954235069928120L;


    /**
     * The item list.
     */
    private ArrayList < Symbol > list;


    /**
     * Allocates a new {@link SymbolListModel}.
     */
    public SymbolListModel ()
    {
      this.list = new ArrayList < Symbol > ();
    }


    /**
     * Adds the given item.
     * 
     * @param item The item to add.
     */
    public final void add ( Symbol item )
    {
      this.list.add ( item );
      Collections.sort ( this.list );
      fireContentsChanged ( this, 0, this.list.size () - 1 );
    }


    /**
     * Clears the model.
     */
    public final void clear ()
    {
      int size = this.list.size ();
      this.list.clear ();
      fireIntervalRemoved ( this, 0, size - 1 < 0 ? 0 : size - 1 );
    }


    /**
     * Returns true, if the item is a member of this model, otherwise false.
     * 
     * @param item The item to check.
     * @return True, if the item is a member of this model, otherwise false.
     */
    public final boolean contains ( Symbol item )
    {
      return this.list.contains ( item );
    }


    /**
     * Returns the value at the specified index.
     * 
     * @param index The requested index.
     * @return The value at the specified index.
     * @see ListModel#getElementAt(int)
     */
    public final Object getElementAt ( int index )
    {
      if ( ( index < 0 ) || ( index >= this.list.size () ) )
      {
        throw new IllegalArgumentException ( "index incorrect" ); //$NON-NLS-1$
      }
      return this.list.get ( index );
    }


    /**
     * Returns the length of the list.
     * 
     * @return The length of the list.
     * @see ListModel#getSize()
     */
    public final int getSize ()
    {
      return this.list.size ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see Iterable#iterator()
     */
    public final Iterator < Symbol > iterator ()
    {
      return this.list.iterator ();
    }


    /**
     * Removes the item with the given index.
     * 
     * @param index The item index.
     */
    public final void remove ( int index )
    {
      this.list.remove ( index );
      fireIntervalRemoved ( this, index, index );
    }


    /**
     * Removes the given item.
     * 
     * @param item The item to remove.
     */
    public final void remove ( Symbol item )
    {
      int index = this.list.indexOf ( item );
      this.list.remove ( item );
      fireIntervalRemoved ( this, index, index );
    }
  }


  /**
   * The epsilon {@link Symbol}.
   */
  public final static String EPSILON = "\u03B5"; //$NON-NLS-1$


  /**
   * The {@link TransitionDialogForm}.
   */
  private TransitionDialogForm gui;


  /**
   * The parent frame.
   */
  private JFrame parent;


  /**
   * The {@link Alphabet} for this dialog.
   */
  private Alphabet alphabet;


  /**
   * The push down {@link Alphabet} for this dialog.
   */
  private Alphabet pushDownAlphabet;


  /**
   * The {@link Transition} for this dialog.
   */
  private Transition transition;


  /**
   * The model for the change over set {@link JGTIList}.
   */
  private SymbolListModel modelChangeOverSet;


  /**
   * The model for the alphabet {@link JGTIList}.
   */
  private SymbolListModel modelAlphabet;


  /**
   * The {@link State} where the {@link Transition} begins.
   */
  private State stateBegin;


  /**
   * The {@link State} where the {@link Transition} ends.
   */
  private State stateEnd;


  /**
   * The {@link Word} which is read from the {@link Stack}.
   */
  private Word pushDownWordRead = new DefaultWord ();


  /**
   * The {@link Word} which should be written on the {@link Stack}.
   */
  private Word pushDownWordWrite = new DefaultWord ();


  /**
   * True if this dialog was confirmed.
   */
  private boolean confirmed = false;


  /**
   * The {@link MachinePanel}.
   */
  private MachinePanel machinePanel;


  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The x value of the new {@link DefaultStateView}.
   */
  private double xPosition;


  /**
   * The y value of the new {@link DefaultStateView}.
   */
  private double yPosition;


  /**
   * The zoom factor of the {@link MachinePanel}.
   */
  private double zoomFactor;


  /**
   * The epsilon {@link Symbol}.
   */
  private Symbol epsilonSymbol = new DefaultSymbol ();


  /**
   * Creates a new {@link TransitionDialog}. This constructor is used for the
   * config case.
   * 
   * @param parent The parent frame.
   * @param machinePanel The {@link MachinePanel}.
   * @param alphabet The {@link Alphabet} available for the {@link Transition}.
   * @param pushDownAlphabet The push down {@link Alphabet} available for the
   *          {@link Transition}.
   * @param transition The {@link Transition}.
   */
  public TransitionDialog ( JFrame parent, MachinePanel machinePanel,
      Alphabet alphabet, Alphabet pushDownAlphabet, Transition transition )
  {
    init ( parent, machinePanel, alphabet, pushDownAlphabet, transition
        .getStateBegin (), transition.getStateEnd (), transition
        .getPushDownWordRead (), transition.getPushDownWordWrite (), transition
        .getSymbol () );

    this.transition = transition;
  }


  /**
   * Creates a new {@link TransitionDialog}. This constructor is used for the
   * add case.
   * 
   * @param parent The parent frame.
   * @param machinePanel The {@link MachinePanel}.
   * @param model The {@link DefaultMachineModel}.
   * @param alphabet The {@link Alphabet} available for the {@link Transition}.
   * @param pushDownAlphabet The push down {@link Alphabet} available for the
   *          {@link Transition}.
   * @param stateBegin The {@link State} where the {@link Transition} begins.
   * @param stateEnd The {@link State} where the {@link Transition} ends.
   * @param pushDownWordRead The push down word to read.
   * @param pushDownWordWrite The push down word to write.
   * @param symbols The Symbols of the {@link Transition}.
   * @param x The x value of the new {@link DefaultStateView}.
   * @param y The y value of the new {@link DefaultStateView}.
   * @param zoomFactor The zoom factor of the {@link MachinePanel}.
   */
  public TransitionDialog ( JFrame parent, MachinePanel machinePanel,
      DefaultMachineModel model, Alphabet alphabet, Alphabet pushDownAlphabet,
      State stateBegin, State stateEnd, Word pushDownWordRead,
      Word pushDownWordWrite, TreeSet < Symbol > symbols, double x, double y,
      double zoomFactor )
  {
    // find a transition
    Transition foundTransition = null;
    for ( Transition current : stateBegin.getTransitionBegin () )
    {
      if ( current.getStateEnd () == stateEnd )
      {
        foundTransition = current;
        break;
      }
    }

    // the transition will be added
    if ( foundTransition == null )
    {
      init ( parent, machinePanel, alphabet, pushDownAlphabet, stateBegin,
          stateEnd, pushDownWordRead, pushDownWordWrite, symbols );
      this.model = model;
      this.xPosition = x;
      this.yPosition = y;
      this.zoomFactor = zoomFactor;
    }
    // there is already a transition which is configured
    else
    {
      init ( parent, machinePanel, alphabet, pushDownAlphabet, foundTransition
          .getStateBegin (), foundTransition.getStateEnd (), foundTransition
          .getPushDownWordRead (), foundTransition.getPushDownWordWrite (),
          foundTransition.getSymbol () );
      this.transition = foundTransition;
    }
  }


  /**
   * Adds the given {@link Symbol}s to the change over set.
   * 
   * @param symbolList The {@link Symbol}s to add.
   */
  public final void addToChangeOver ( ArrayList < Symbol > symbolList )
  {
    for ( Symbol current : symbolList )
    {
      this.modelChangeOverSet.add ( current );
      this.modelAlphabet.remove ( current );
    }
    this.gui.jGTIListAlphabet.clearSelection ();
    this.gui.jGTIButtonMoveRight.setEnabled ( false );
    setButtonStatus ();
    updateResultingTransition ();
  }


  /**
   * Disposes the dialog.
   */
  public final void dispose ()
  {
    this.gui.dispose ();
  }


  /**
   * Returns the gui.
   * 
   * @return The gui.
   * @see #gui
   */
  public final TransitionDialogForm getGui ()
  {
    return this.gui;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final TransitionDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Handles cancel button pressed.
   */
  public final void handleCancel ()
  {
    this.gui.dispose ();
  }


  /**
   * Handles the list selection.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleListSelection ( ListSelectionEvent event )
  {
    JGTIList selectedList = ( JGTIList ) event.getSource ();
    Symbol selected = ( Symbol ) selectedList.getSelectedValue ();
    if ( selected == null )
    {
      return;
    }
    if ( selectedList.equals ( this.gui.jGTIListAlphabet ) )
    {
      this.gui.jGTIListChangeOverSet.clearSelection ();
      this.gui.jGTIButtonMoveLeft.setEnabled ( false );
      if ( selectedList.getSelectedValues ().length > 0 )
      {
        this.gui.jGTIButtonMoveRight.setEnabled ( true );
      }
    }
    else
    {
      this.gui.jGTIListAlphabet.clearSelection ();
      if ( selectedList.getSelectedValues ().length > 0 )
      {
        this.gui.jGTIButtonMoveLeft.setEnabled ( true );
      }
      this.gui.jGTIButtonMoveRight.setEnabled ( false );
    }
  }


  /**
   * Handles move left button pressed.
   */
  public final void handleMoveLeft ()
  {
    Object [] selectedValues = this.gui.jGTIListChangeOverSet
        .getSelectedValues ();
    ArrayList < Symbol > removeList = new ArrayList < Symbol > ();
    for ( Object current : selectedValues )
    {
      removeList.add ( ( Symbol ) current );
    }
    removeFromChangeOver ( removeList );
  }


  /**
   * Handles move right button pressed.
   */
  public final void handleMoveRight ()
  {
    Object [] selectedValues = this.gui.jGTIListAlphabet.getSelectedValues ();
    ArrayList < Symbol > addList = new ArrayList < Symbol > ();
    for ( Object current : selectedValues )
    {
      addList.add ( ( Symbol ) current );
    }
    addToChangeOver ( addList );
  }


  /**
   * Create a new {@link Transition}.
   */
  private final void handleNewTransition ()
  {
    try
    {
      ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
      for ( Symbol symbol : this.modelChangeOverSet )
      {
        symbols.add ( symbol );
      }
      this.transition = new DefaultTransition ( this.pushDownWordRead,
          this.pushDownWordWrite, symbols );
      this.transition.setAlphabet ( this.alphabet );
      this.transition.setPushDownAlphabet ( this.pushDownAlphabet );
      this.transition.setStateBegin ( this.stateBegin );

      boolean nullTarget = false;

      DefaultStateView target;

      if ( this.stateEnd != null )
      {
        this.transition.setStateEnd ( this.stateEnd );
        target = this.model.getStateById ( this.stateEnd.getId () );
      }
      else
      {
        State newState;
        newState = new DefaultState ( this.alphabet, this.pushDownAlphabet,
            false, false );
        target = this.model.createStateView ( this.xPosition / this.zoomFactor,
            this.yPosition / this.zoomFactor, newState, false );
        this.transition.setStateEnd ( target.getState () );
        nullTarget = true;
        this.stateEnd = target.getState ();

      }

      this.model.createTransitionView ( this.transition, this.model
          .getStateById ( this.stateBegin.getId () ), target, nullTarget, true,
          true );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StateException e1 )
    {
      e1.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Handle ok button pressed.
   */
  public final void handleOk ()
  {
    this.confirmed = true;
    this.gui.setVisible ( false );

    if ( this.transition == null )
    {
      handleNewTransition ();
    }
    else
    {
      handleUpdateTransition ();
    }
    this.gui.dispose ();
    this.machinePanel.getJGTIGraph ().repaint ();
  }


  /**
   * Update the {@link Transition}.
   */
  private final void handleUpdateTransition ()
  {
    TreeSet < Symbol > oldSymbols = new TreeSet < Symbol > ();
    oldSymbols.addAll ( this.transition.getSymbol () );

    Word oldPushDownWordRead = this.transition.getPushDownWordRead ();
    Word oldPushDownWordWrite = this.transition.getPushDownWordWrite ();

    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    for ( Symbol symbol : this.modelChangeOverSet )
    {
      symbols.add ( symbol );
    }
    try
    {
      this.transition.setPushDownWordRead ( this.pushDownWordRead );
      this.transition.setPushDownWordWrite ( this.pushDownWordWrite );
      this.transition.replace ( symbols );

      if ( !this.transition.getPushDownWordRead ()
          .equals ( oldPushDownWordRead )
          || !this.transition.getPushDownWordWrite ().equals (
              oldPushDownWordWrite )
          || !this.transition.getSymbol ().equals ( oldSymbols ) )
      {
        TransitionChangedItem item = new TransitionChangedItem (
            this.transition, oldPushDownWordRead, oldPushDownWordWrite,
            oldSymbols );
        this.machinePanel.getRedoUndoHandler ().addItem ( item );
      }
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Initializes the {@link TransitionDialog}.
   * 
   * @param initParent The parent frame.
   * @param initMachinePanel The {@link MachinePanel}.
   * @param initAlphabet The {@link Alphabet} available for the
   *          {@link Transition}.
   * @param initPushDownAlphabet The push down {@link Alphabet} available for
   *          the {@link Transition}.
   * @param initStateBegin The {@link State} where the {@link Transition}
   *          begins.
   * @param initStateEnd The {@link State} where the {@link Transition} ends.
   * @param initPushDownWordRead The push down word to read.
   * @param initPushDownWordWrite The push down word to write.
   * @param symbols The Symbols of the {@link Transition}.
   */
  private final void init ( JFrame initParent, MachinePanel initMachinePanel,
      Alphabet initAlphabet, Alphabet initPushDownAlphabet,
      State initStateBegin, State initStateEnd, Word initPushDownWordRead,
      Word initPushDownWordWrite, TreeSet < Symbol > symbols )
  {
    this.parent = initParent;
    this.machinePanel = initMachinePanel;
    this.alphabet = initAlphabet;
    this.pushDownAlphabet = initPushDownAlphabet;

    // PushDownWordRead
    setPushDownWordRead ( initPushDownWordRead );
    // PushDownWordWrite
    setPushDownWordWrite ( initPushDownWordWrite );
    this.stateBegin = initStateBegin;
    this.stateEnd = initStateEnd;
    this.gui = new TransitionDialogForm ( this, initParent );
    String targetName = this.stateEnd == null ? Messages
        .getString ( "TransitionDialog.NewState" ) : this.stateEnd //$NON-NLS-1$
        .getName ();
    this.gui.jGTILabelNonterminalSymbol.setText ( Messages.getString (
        "TransitionDialog.Header", this.stateBegin, targetName ) ); //$NON-NLS-1$

    this.gui.jGTIListChangeOverSet
        .setTransferHandler ( new JGTIListTransferHandler (
            TransferHandler.MOVE )
        {

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = 0L;


          @SuppressWarnings ( "synthetic-access" )
          @Override
          protected boolean importListModelRows ( JGTIList jGTIList,
              JGTIListModelRows rows, int targetIndex )
          {
            moveRowsToChangeOverSet ( jGTIList, rows, targetIndex );
            return true;
          }
        } );
    this.gui.jGTIListChangeOverSet.setDragEnabled ( true );
    this.gui.jGTIListChangeOverSet
        .addAllowedDndSource ( this.gui.jGTIListAlphabet );

    this.gui.jGTIListAlphabet.setTransferHandler ( new JGTIListTransferHandler (
        TransferHandler.MOVE )
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = 0L;


      @SuppressWarnings ( "synthetic-access" )
      @Override
      protected boolean importListModelRows ( JGTIList jGTIList,
          JGTIListModelRows rows, int targetIndex )
      {
        moveRowsToAlphabet ( jGTIList, rows, targetIndex );
        return true;
      }
    } );
    this.gui.jGTIListAlphabet.setDragEnabled ( true );
    this.gui.jGTIListAlphabet
        .addAllowedDndSource ( this.gui.jGTIListChangeOverSet );

    this.modelAlphabet = new SymbolListModel ();
    this.modelChangeOverSet = new SymbolListModel ();

    this.modelAlphabet.add ( this.epsilonSymbol );
    for ( Symbol symbol : this.alphabet )
    {
      this.modelAlphabet.add ( symbol );
    }

    for ( Symbol symbol : symbols )
    {
      this.modelAlphabet.remove ( symbol );
      this.modelChangeOverSet.add ( symbol );
    }

    try
    {
      this.gui.styledTransitionParserPanel.setText ( new DefaultTransition (
          this.pushDownWordRead, this.pushDownWordWrite, symbols ) );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }

    this.gui.jGTIListAlphabet.setModel ( this.modelAlphabet );
    this.gui.jGTIListChangeOverSet.setModel ( this.modelChangeOverSet );
    this.gui.styledTransitionParserPanel.setText ( new DefaultTransition () );

    // Set the push down alphabet
    this.gui.styledAlphabetParserPanelPushDownAlphabet
        .setText ( this.pushDownAlphabet );
    this.gui.styledWordParserPanelRead.setAlphabet ( this.pushDownAlphabet );
    this.gui.styledWordParserPanelWrite.setAlphabet ( this.pushDownAlphabet );

    // Set the push down read and write word
    if ( this.pushDownWordRead != null )
    {
      this.gui.styledWordParserPanelRead.setText ( this.pushDownWordRead );
    }
    this.gui.styledWordParserPanelRead.parse ();
    if ( this.pushDownWordWrite != null )
    {
      this.gui.styledWordParserPanelWrite.setText ( this.pushDownWordWrite );
    }
    this.gui.styledWordParserPanelWrite.parse ();

    /*
     * Word changed listener
     */
    this.gui.styledWordParserPanelRead
        .addParseableChangedListener ( new ParseableChangedListener < Word > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Word newWord )
          {
            if ( newWord != null )
            {
              setPushDownWordRead ( newWord );
              updateResultingTransition ();
            }
            else
            {
              TransitionDialog.this.gui.styledTransitionParserPanel
                  .setText ( null );
            }
            setButtonStatus ();
          }
        } );
    this.gui.styledWordParserPanelWrite
        .addParseableChangedListener ( new ParseableChangedListener < Word > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Word newWord )
          {
            if ( newWord != null )
            {
              setPushDownWordWrite ( newWord );
              updateResultingTransition ();
            }
            else
            {
              TransitionDialog.this.gui.styledTransitionParserPanel
                  .setText ( null );
            }
            setButtonStatus ();
          }
        } );

    this.gui.jGTIListAlphabet
        .setCellRenderer ( new PrettyStringListCellRenderer () );
    this.gui.jGTIListChangeOverSet
        .setCellRenderer ( new PrettyStringListCellRenderer () );

    setButtonStatus ();
  }


  /**
   * Returns the confirmed value.
   * 
   * @return The confirmed value.
   * @see #confirmed
   */
  public final boolean isConfirmed ()
  {
    return this.confirmed;
  }


  /**
   * Moves the rows.
   * 
   * @param jGTIList The {@link JGTIList} into which to import the rows.
   * @param rows The {@link JGTIListModelRows}.
   * @param targetIndex The target index.
   */
  private final void moveRowsToAlphabet (
      @SuppressWarnings ( "unused" ) JGTIList jGTIList, JGTIListModelRows rows,
      @SuppressWarnings ( "unused" ) int targetIndex )
  {
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( int index : rows.getRowIndices () )
    {
      Symbol symbol = ( Symbol ) rows.getSource ().getModel ().getElementAt (
          index );
      if ( !this.modelAlphabet.contains ( symbol ) )
      {
        symbolList.add ( symbol );
      }
    }

    removeFromChangeOver ( symbolList );
  }


  /**
   * Moves the rows.
   * 
   * @param jGTIList The {@link JGTIList} into which to import the rows.
   * @param rows The {@link JGTIListModelRows}.
   * @param targetIndex The target index.
   */
  private final void moveRowsToChangeOverSet (
      @SuppressWarnings ( "unused" ) JGTIList jGTIList, JGTIListModelRows rows,
      @SuppressWarnings ( "unused" ) int targetIndex )
  {
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( int index : rows.getRowIndices () )
    {
      Symbol symbol = ( Symbol ) rows.getSource ().getModel ().getElementAt (
          index );
      if ( !this.modelChangeOverSet.contains ( symbol ) )
      {
        symbolList.add ( symbol );
      }
    }
    addToChangeOver ( symbolList );
  }


  /**
   * Removes the given {@link Symbol}s from the change over set.
   * 
   * @param symbolList The {@link Symbol}s to remove.
   */
  public final void removeFromChangeOver ( ArrayList < Symbol > symbolList )
  {
    for ( Symbol current : symbolList )
    {
      this.modelAlphabet.add ( current );
      this.modelChangeOverSet.remove ( current );
    }
    this.gui.jGTIListChangeOverSet.clearSelection ();
    this.gui.jGTIButtonMoveLeft.setEnabled ( false );
    setButtonStatus ();
    updateResultingTransition ();
  }


  /**
   * Sets the status of the buttons.
   */
  private final void setButtonStatus ()
  {
    if ( ( this.gui.styledWordParserPanelRead.getParsedObject () == null )
        || ( this.gui.styledWordParserPanelWrite.getParsedObject () == null )
        || ( this.modelChangeOverSet.getSize () == 0 ) )
    {
      this.gui.jGTIButtonOk.setEnabled ( false );
    }
    else
    {
      this.gui.jGTIButtonOk.setEnabled ( true );
    }
  }


  /**
   * Sets the {@link Word} which is read from the {@link Stack}.
   * 
   * @param pushDownWordRead The {@link Word} to set.
   */
  public final void setPushDownWordRead ( Word pushDownWordRead )
  {
    if ( pushDownWordRead == null )
    {
      throw new NullPointerException ( "push down word read is null" ); //$NON-NLS-1$
    }
    this.pushDownWordRead = pushDownWordRead;
  }


  /**
   * The {@link Word} which should be written on the {@link Stack}.
   * 
   * @param pushDownWordWrite The {@link Word} to set.
   */
  public final void setPushDownWordWrite ( Word pushDownWordWrite )
  {
    if ( pushDownWordWrite == null )
    {
      throw new NullPointerException ( "push down word write is null" ); //$NON-NLS-1$
    }
    this.pushDownWordWrite = pushDownWordWrite;
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


  /**
   * Updates the current resulting {@link Transition}.
   */
  private final void updateResultingTransition ()
  {
    try
    {
      this.gui.styledTransitionParserPanel.setText ( new DefaultTransition (
          this.pushDownWordRead, this.pushDownWordWrite,
          this.modelChangeOverSet ) );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}

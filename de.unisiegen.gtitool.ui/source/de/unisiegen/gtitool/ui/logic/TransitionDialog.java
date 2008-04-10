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
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.listener.WordChangedListener;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.parser.style.PrettyStringListCellRenderer;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.TransitionDialogForm;
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
public final class TransitionDialog
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
   * Creates a new {@link TransitionDialog}.
   * 
   * @param parent The parent frame.
   * @param alphabet The {@link Alphabet} available for the {@link Transition}.
   * @param pushDownAlphabet The push down {@link Alphabet} available for the
   *          {@link Transition}.
   * @param stateBegin The {@link State} where the {@link Transition} begins.
   * @param stateEnd The {@link State} where the {@link Transition} ends.
   */
  public TransitionDialog ( JFrame parent, Alphabet alphabet,
      Alphabet pushDownAlphabet, State stateBegin, State stateEnd )
  {
    this ( parent, alphabet, pushDownAlphabet, new DefaultWord (),
        new DefaultWord (), new TreeSet < Symbol > (), stateBegin, stateEnd );
  }


  /**
   * Creates a new {@link TransitionDialog}.
   * 
   * @param parent The parent frame.
   * @param alphabet The {@link Alphabet} available for the {@link Transition}.
   * @param pushDownAlphabet The push down {@link Alphabet} available for the
   *          {@link Transition}.
   * @param pushDownWordRead The {@link Word} which is read from the
   *          {@link Stack}.
   * @param pushDownWordWrite The {@link Word} which should be written on the
   *          {@link Stack}.
   * @param overChangeSymbolSet The change over {@link Symbol} set.
   * @param stateBegin The {@link State} where the {@link Transition} begins.
   * @param stateEnd The {@link State} where the {@link Transition} ends.
   */
  public TransitionDialog ( JFrame parent, Alphabet alphabet,
      Alphabet pushDownAlphabet, Word pushDownWordRead, Word pushDownWordWrite,
      TreeSet < Symbol > overChangeSymbolSet, State stateBegin, State stateEnd )
  {
    this.parent = parent;
    this.alphabet = alphabet;
    this.pushDownAlphabet = pushDownAlphabet;
    // PushDownWordRead
    setPushDownWordRead ( pushDownWordRead );
    // PushDownWordWrite
    setPushDownWordWrite ( pushDownWordWrite );
    this.stateBegin = stateBegin;
    this.stateEnd = stateEnd;
    this.transition = null;
    this.gui = new TransitionDialogForm ( this, parent );
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
    this.modelAlphabet = new SymbolListModel ();
    for ( Symbol symbol : this.alphabet )
    {
      this.modelAlphabet.add ( symbol );
    }
    this.gui.jGTIListAlphabet.setModel ( this.modelAlphabet );
    this.modelChangeOverSet = new SymbolListModel ();
    this.gui.jGTIListChangeOverSet.setModel ( this.modelChangeOverSet );
    this.gui.styledTransitionParserPanel.setText ( new DefaultTransition () );
    setOverChangeSet ( overChangeSymbolSet );

    // Set the push down alphabet
    this.gui.styledAlphabetParserPanelPushDownAlphabet
        .setText ( this.pushDownAlphabet );
    this.gui.styledWordParserPanelRead.setAlphabet ( this.pushDownAlphabet );
    this.gui.styledWordParserPanelWrite.setAlphabet ( this.pushDownAlphabet );

    // Set the push down read and write word
    if ( pushDownWordRead != null )
    {
      this.gui.styledWordParserPanelRead.setText ( pushDownWordRead );
    }
    this.gui.styledWordParserPanelRead.parse ();
    if ( pushDownWordWrite != null )
    {
      this.gui.styledWordParserPanelWrite.setText ( pushDownWordWrite );
    }
    this.gui.styledWordParserPanelWrite.parse ();

    /*
     * Word changed listener
     */
    this.gui.styledWordParserPanelRead
        .addWordChangedListener ( new WordChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void wordChanged ( Word newWord )
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
        .addWordChangedListener ( new WordChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void wordChanged ( Word newWord )
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
   * Get the {@link Transition} of this dialog.
   * 
   * @return The {@link Transition}.
   */
  public final Transition getTransition ()
  {
    return this.transition;
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
   * Handle ok button pressed.
   */
  public final void handleOk ()
  {
    this.confirmed = true;
    this.gui.setVisible ( false );
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
      if ( this.stateEnd != null )
      {
        this.transition.setStateEnd ( this.stateEnd );
      }
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    this.gui.dispose ();
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
  private final void moveRowsToAlphabet ( JGTIList jGTIList,
      JGTIListModelRows rows, int targetIndex )
  {
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( int index : rows.getRowIndices () )
    {
      symbolList.add ( ( Symbol ) rows.getModel ().getElementAt ( index ) );
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
  private final void moveRowsToChangeOverSet ( JGTIList jGTIList,
      JGTIListModelRows rows, int targetIndex )
  {
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( int index : rows.getRowIndices () )
    {
      symbolList.add ( ( Symbol ) rows.getModel ().getElementAt ( index ) );
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
    updateResultingTransition ();
  }


  /**
   * Sets the status of the buttons.
   */
  private final void setButtonStatus ()
  {
    if ( ( this.gui.styledWordParserPanelRead.getWord () == null )
        || ( this.gui.styledWordParserPanelWrite.getWord () == null ) )
    {
      this.gui.jGTIButtonOk.setEnabled ( false );
    }
    else
    {
      this.gui.jGTIButtonOk.setEnabled ( true );
    }
  }


  /**
   * Sets the {@link Symbol}s of the over change set.
   * 
   * @param overChangeSymbolSet The {@link Symbol}s of the over change set.
   */
  private final void setOverChangeSet ( TreeSet < Symbol > overChangeSymbolSet )
  {
    if ( overChangeSymbolSet.size () > 0 )
    {
      this.modelChangeOverSet.clear ();
    }
    for ( Symbol symbol : overChangeSymbolSet )
    {
      this.modelAlphabet.remove ( symbol );
      this.modelChangeOverSet.add ( symbol );
    }
    try
    {
      this.gui.styledTransitionParserPanel.setText ( new DefaultTransition (
          this.pushDownWordRead, this.pushDownWordWrite, overChangeSymbolSet ) );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
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
  private void updateResultingTransition ()
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

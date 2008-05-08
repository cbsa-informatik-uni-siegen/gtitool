package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.HistoryPath;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.parser.style.renderer.HistoryPathTableCellRenderer;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.HistoryDialogForm;


/**
 * The {@link HistoryDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class HistoryDialog
{

  /**
   * The path.
   * 
   * @author Christian Fehler
   */
  private final class Path
  {

    /**
     * The {@link Transition} list.
     */
    private ArrayList < Transition > transitionList;


    /**
     * The readed {@link Symbol} list.
     */
    private ArrayList < Symbol > readedSymbolList;


    /**
     * The {@link State}.
     */
    private State state;


    /**
     * Allocates a new {@link Path}.
     * 
     * @param transitionList The {@link Transition} list.
     * @param readedSymbolList The readed {@link Symbol} list
     */
    public Path ( ArrayList < Transition > transitionList,
        ArrayList < Symbol > readedSymbolList )
    {
      this ( transitionList, readedSymbolList, null );
    }


    /**
     * Allocates a new {@link Path}.
     * 
     * @param transitionList The {@link Transition} list.
     * @param readedSymbolList The readed {@link Symbol} list
     * @param state The {@link State}.
     */
    public Path ( ArrayList < Transition > transitionList,
        ArrayList < Symbol > readedSymbolList, State state )
    {
      this.transitionList = transitionList;
      this.readedSymbolList = readedSymbolList;
      this.state = state;
    }


    /**
     * Returns the readed {@link Symbol} list.
     * 
     * @return The readed {@link Symbol} list.
     * @see #readedSymbolList
     */
    public final ArrayList < Symbol > getReadedSymbolList ()
    {
      return this.readedSymbolList;
    }


    /**
     * Returns the {@link State}.
     * 
     * @return The {@link State}.
     * @see #state
     */
    public final State getState ()
    {
      return this.state;
    }


    /**
     * Returns the {@link Transition} list.
     * 
     * @return The {@link Transition} list.
     * @see #transitionList
     */
    public final ArrayList < Transition > getTransitionList ()
    {
      return this.transitionList;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( HistoryDialog.class );


  /**
   * The {@link HistoryDialogForm}.
   */
  private HistoryDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link Machine}.
   */
  private Machine machine;


  /**
   * The remaining path list.
   */
  private ArrayList < Path > remainingPathList = new ArrayList < Path > ();


  /**
   * The history path list.
   */
  private ArrayList < ArrayList < Transition >> historyPathList = new ArrayList < ArrayList < Transition >> ();


  /**
   * Allocates a new {@link HistoryDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   * @param machine The {@link Machine}.
   */
  public HistoryDialog ( JFrame parent, Machine machine )
  {
    logger.debug ( "HistoryDialog", "allocate a new history dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    this.parent = parent;
    this.machine = machine;

    this.gui = new HistoryDialogForm ( this, parent );

    // Model
    DefaultTableModel historyModel = new DefaultTableModel ()
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = -5238984692740682487L;


      @Override
      public boolean isCellEditable ( @SuppressWarnings ( "unused" )
      int row, @SuppressWarnings ( "unused" )
      int column )
      {
        return false;
      }
    };

    historyModel.addColumn ( "history" ); //$NON-NLS-1$

    Path path = null;
    try
    {
      ArrayList < Symbol > inputList = new ArrayList < Symbol > ();
      inputList.addAll ( this.machine.getReadedSymbols () );

      path = new Path ( new ArrayList < Transition > (), inputList,
          this.machine.getActiveState ().first () );
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    this.remainingPathList.add ( path );

    calculate ();

    logger.debug ( "HistoryDialog", "result: " + this.historyPathList ); //$NON-NLS-1$//$NON-NLS-2$

    for ( ArrayList < Transition > currentTransitionList : this.historyPathList )
    {
      HistoryPath historyPath = new HistoryPath ();
      
      for ( int i =  currentTransitionList .size ()-1;i>=0;i--)
      {
        Transition currentTransition = currentTransitionList.get ( i );
        historyPath.add ( currentTransition.getStateBegin (), currentTransition,
            currentTransition.getStateEnd (), null );
      }
      historyModel.addRow ( new Object[]{historyPath} );
    }

    // ColumnModel
    DefaultTableColumnModel columnModel = new DefaultTableColumnModel ();
    TableColumn historyColumn = new TableColumn ( 0 );
    historyColumn.setHeaderValue ( Messages
        .getString ( "HistoryDialog.HistoryColumn" ) ); //$NON-NLS-1$
    historyColumn.setCellRenderer ( new HistoryPathTableCellRenderer () );
    columnModel.addColumn ( historyColumn );

    this.gui.jGTITableHistory.setModel ( historyModel );
    this.gui.jGTITableHistory.setColumnModel ( columnModel );
    this.gui.jGTITableHistory.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableHistory.getTableHeader ().setResizingAllowed ( false );
  }


  /**
   * Calculates the list of history paths.
   */
  private final void calculate ()
  {
    if ( this.remainingPathList.size () == 0 )
    {
      return;
    }

    Path path = this.remainingPathList.remove ( 0 );

    ArrayList < Transition > transitionList = path.getTransitionList ();
    ArrayList < Symbol > readedSymbolList = path.getReadedSymbolList ();

    State state;
    if ( path.getState () == null )
    {
      state = transitionList.get ( transitionList.size () - 1 )
          .getStateBegin ();
    }
    else
    {
      state = path.getState ();
    }

    if ( state.isStartState () && readedSymbolList.isEmpty () )
    {
      this.historyPathList.add ( transitionList );

      calculate ();
      return;
    }

    ArrayList < Transition > list = state.getTransitionEnd ();

    for ( Transition current : list )
    {
      if ( current.isEpsilonTransition () )
      {
        ArrayList < Transition > newTransitionList = new ArrayList < Transition > ();
        newTransitionList.addAll ( transitionList );
        newTransitionList.add ( current );

        ArrayList < Symbol > newReadedSymbolList = new ArrayList < Symbol > ();
        newReadedSymbolList.addAll ( readedSymbolList );

        Path newPath = new Path ( newTransitionList, newReadedSymbolList );
        this.remainingPathList.add ( newPath );
      }
      else if ( ( readedSymbolList.size () > 0 )
          && current.contains ( readedSymbolList
              .get ( readedSymbolList.size () - 1 ) ) )
      {
        ArrayList < Transition > newTransitionList = new ArrayList < Transition > ();
        newTransitionList.addAll ( transitionList );
        newTransitionList.add ( current );

        ArrayList < Symbol > newReadedSymbolList = new ArrayList < Symbol > ();
        newReadedSymbolList.addAll ( readedSymbolList );
        newReadedSymbolList.remove ( newReadedSymbolList.size () - 1 );

        Path newPath = new Path ( newTransitionList, newReadedSymbolList );
        this.remainingPathList.add ( newPath );
      }
    }

    calculate ();
  }


  /**
   * Closes the {@link HistoryDialogForm}.
   */
  public final void handleClose ()
  {
    logger.debug ( "handleClose", "handle close" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Shows the {@link HistoryDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the history dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}

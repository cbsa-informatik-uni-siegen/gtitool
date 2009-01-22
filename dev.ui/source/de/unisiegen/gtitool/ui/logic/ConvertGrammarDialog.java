package de.unisiegen.gtitool.ui.logic;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringListCellRenderer;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableColumnModel;
import de.unisiegen.gtitool.ui.model.ConvertMachineTableModel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
import de.unisiegen.gtitool.ui.netbeans.ConvertGrammarDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ConvertRegexToMachineDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.swing.JGTIList;
import de.unisiegen.gtitool.ui.swing.dnd.JGTIListModelRows;
import de.unisiegen.gtitool.ui.swing.dnd.JGTIListTransferHandler;


/**
 * The LogicClass for the RegexToMachine Converter
 * 
 * @author Simon Meurer
 */
public class ConvertGrammarDialog implements
    LogicClass < ConvertGrammarDialogForm >, Converter
{

  /**
   * Does the next step after a delay.
   * 
   * @author Simon Meurer
   */
  private final class AutoStepTimerTask extends TimerTask
  {

    /**
     * {@inheritDoc}
     * 
     * @see TimerTask#run()
     */
    @Override
    public final void run ()
    {
      SwingUtilities.invokeLater ( new Runnable ()
      {

        @SuppressWarnings ( "synthetic-access" )
        public void run ()
        {
          if ( ConvertGrammarDialog.this.endReached )
          {
            handleStop ();
          }
          else
          {
            performNextStep ();
          }
        }
      } );
    }
  }


  /**
   * The {@link StepItem}.
   * 
   * @author Simon Meurer
   */
  private class StepItem
  {

    private int iNow;


    private int jNow;


    private CFG oldCFG;


    private DefaultListModel oldNonterminalModel;


    /**
     * Allocates a new {@link StepItem}.
     * 
     * @param oldCFG
     * @param oldNonterminalModel
     * @param i
     * @param j
     */
    public StepItem ( CFG oldCFG, DefaultListModel oldNonterminalModel, int i,
        int j )
    {
      this.oldCFG = oldCFG;
      this.oldNonterminalModel = oldNonterminalModel;
      this.iNow = i;
      this.jNow = j;
    }


    /**
     * Returns the oldNonterminalModel.
     * 
     * @return The oldNonterminalModel.
     * @see #oldNonterminalModel
     */
    public DefaultListModel getOldNonterminalModel ()
    {
      return this.oldNonterminalModel;
    }


    /**
     * Returns the iNow.
     * 
     * @return The iNow.
     * @see #iNow
     */
    public int getINow ()
    {
      return this.iNow;
    }


    /**
     * Returns the jNow.
     * 
     * @return The jNow.
     * @see #jNow
     */
    public int getJNow ()
    {
      return this.jNow;
    }


    /**
     * Returns the oldCFG.
     * 
     * @return The oldCFG.
     * @see #oldCFG
     */
    public CFG getOldCFG ()
    {
      return this.oldCFG;
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( ConvertGrammarDialog.class );


  /**
   * The auto step {@link Timer}.
   */
  private Timer autoStepTimer = null;


  /**
   * The {@link ConvertMachineTableModel}.
   */
  private ConvertMachineTableModel convertMachineTableModel;


  /**
   * Flag that indicates if the end is reached.
   */
  private boolean endReached = false;


  /**
   * The {@link ConvertRegexToMachineDialogForm}
   */
  private ConvertGrammarDialogForm gui;


  /**
   * The parent {@link JFrame}
   */
  private JFrame parent;


  /**
   * The {@link StepItem} list.
   */
  private ArrayList < StepItem > stepItemList = new ArrayList < StepItem > ();


  /**
   * The {@link ConvertMachineTableColumnModel}.
   */
  private ConvertMachineTableColumnModel tableColumnModel = new ConvertMachineTableColumnModel ();


  /**
   * TODO
   */
  private GrammarPanel panel;


  /**
   * Creates new from {@link ConvertGrammarDialog}
   * 
   * @param parent The parent {@link JFrame}
   * @param panel The {@link RegexPanel}
   */
  public ConvertGrammarDialog ( JFrame parent, GrammarPanel panel )
  {
    this.parent = parent;
    this.panel = panel;
  }


  /**
   * Adds a outline comment.
   * 
   * @param prettyString The {@link PrettyString} to add.
   */
  private final void addOutlineComment ( PrettyString prettyString )
  {
    this.convertMachineTableModel.addRow ( prettyString );
    this.gui.jGTITableOutline.changeSelection ( this.convertMachineTableModel
        .getRowCount () - 1, ConvertMachineTableModel.OUTLINE_COLUMN, false,
        false );
  }


  /**
   * Cancels the auto step timer.
   */
  private final void cancelAutoStepTimer ()
  {
    if ( this.autoStepTimer != null )
    {
      this.autoStepTimer.cancel ();
      this.autoStepTimer = null;
    }
  }


  /**
   * TODO
   * 
   * @param fromEntityType
   * @param toEntityType
   * @param complete
   * @see de.unisiegen.gtitool.ui.convert.Converter#convert(de.unisiegen.gtitool.core.entities.InputEntity.EntityType,
   *      de.unisiegen.gtitool.core.entities.InputEntity.EntityType, boolean)
   */
  public void convert ( EntityType fromEntityType, EntityType toEntityType,
      boolean complete )
  {
    // TODO
  }


  /**
   * The {@link GrammarColumnModel}.
   */
  private GrammarColumnModel grammarColumnModelOriginal = new GrammarColumnModel ();


  /**
   * The {@link GrammarColumnModel}.
   */
  private GrammarColumnModel grammarColumnModelConverted = new GrammarColumnModel ();


  /**
   * TODO
   */
  public void convert ()
  {
    this.gui = new ConvertGrammarDialogForm ( this, this.parent );

    this.convertMachineTableModel = new ConvertMachineTableModel ();
    this.gui.jGTITableOutline.setModel ( this.convertMachineTableModel );
    this.modelOriginal = ( DefaultGrammarModel ) this.panel.getModel ();

    this.gui.jGTITableGrammarOriginal.setModel ( this.modelOriginal
        .getGrammar () );
    this.gui.jGTITableGrammarOriginal
        .setColumnModel ( this.grammarColumnModelOriginal );
    this.gui.jGTITableGrammarOriginal.getTableHeader ().setReorderingAllowed (
        false );

    updateOriginal ();
    createConverted ();

    this.gui.jGTITableGrammarConverted.setModel ( this.modelConverted
        .getGrammar () );
    this.gui.jGTITableGrammarConverted
        .setColumnModel ( this.grammarColumnModelConverted );
    this.gui.jGTITableGrammarConverted.getTableHeader ().setReorderingAllowed (
        false );

    this.gui.jGTIListNonterminalsConverted.setDndMode ( JGTIList.DROP_BETWEEN );
    this.gui.jGTIListNonterminalsConverted
        .setCellRenderer ( new PrettyStringListCellRenderer () );
    this.gui.jGTIListNonterminalsConverted
        .setTransferHandler ( new JGTIListTransferHandler (
            TransferHandler.MOVE )
        {

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = -6137236787378034581L;


          /**
           * {@inheritDoc}
           * 
           * @see de.unisiegen.gtitool.ui.swing.dnd.JGTIListTransferHandler#importListModelRows(de.unisiegen.gtitool.ui.swing.JGTIList,
           *      de.unisiegen.gtitool.ui.swing.dnd.JGTIListModelRows, int)
           */
          @Override
          protected boolean importListModelRows ( JGTIList list,
              JGTIListModelRows rows, int targetIndex )
          {
            moveNonterminals ( list, rows, targetIndex );
            return true;
          }
        } );
    this.gui.jGTIListNonterminalsConverted.setDragEnabled ( true );
    this.gui.jGTIListNonterminalsConverted
        .addAllowedDndSource ( this.gui.jGTIListNonterminalsConverted );

    this.modelConverted
        .addModifyStatusChangedListener ( new ModifyStatusChangedListener ()
        {

          /**
           * TODO
           * 
           * @param modified
           * @see de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener#modifyStatusChanged(boolean)
           */
          public void modifyStatusChanged ( @SuppressWarnings ( "unused" )
          boolean modified )
          {
            updateConverted ();
          }
        } );
    updateConverted ();
    setStatus ();
    addOutlineComment ( new PrettyString ( new PrettyToken ( "Initial: i: "
        + this.i + " j:" + this.j, Style.COMMENT ) ) );
    show ();
  }


  /**
   * TODO
   * 
   * @param list
   * @param rows
   * @param targetIndex
   */
  protected void moveNonterminals ( @SuppressWarnings ( "unused" )
  JGTIList list, JGTIListModelRows rows, int targetIndex )
  {
    ArrayList < NonterminalSymbol > oldNonterminals = new ArrayList < NonterminalSymbol > ();
    for ( int n = 0 ; n < this.nonterminals.getSize () ; n++ )
    {
      oldNonterminals.add ( ( NonterminalSymbol ) this.nonterminals.get ( n ) );
    }

    ArrayList < NonterminalSymbol > nonterminalList = new ArrayList < NonterminalSymbol > ();

    int [] indeces = rows.getRowIndices ();

    int newTargetIndex = targetIndex;

    if ( ( indeces.length > 0 ) && ( indeces [ 0 ] < targetIndex ) )
    {
      newTargetIndex++ ;
    }

    for ( int index : indeces )
    {
      nonterminalList.add ( ( NonterminalSymbol ) this.nonterminals
          .getElementAt ( index ) );

      if ( index < targetIndex )
      {
        newTargetIndex-- ;
      }
    }

    for ( int n = indeces.length - 1 ; n > -1 ; n-- )
    {
      this.nonterminals.remove ( indeces [ n ] );
    }

    newTargetIndex = Math.min ( newTargetIndex, this.nonterminals.getSize () );
    Collections.reverse ( nonterminalList );
    for ( NonterminalSymbol s : nonterminalList )
    {
      this.nonterminals.add ( newTargetIndex, s );
    }
    this.gui.jGTIListNonterminalsConverted.getSelectionModel ()
        .setSelectionInterval ( newTargetIndex,
            newTargetIndex + indeces.length - 1 );
  }


  /**
   * TODO
   */
  private void createConverted ()
  {
    NonterminalSymbol start = null;
    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();
    for ( NonterminalSymbol s : this.modelOriginal.getGrammar ()
        .getNonterminalSymbolSet () )
    {
      NonterminalSymbol c = new DefaultNonterminalSymbol ( new String ( s
          .getName () ) );
      try
      {
        nonterminalSet.add ( c );
      }
      catch ( NonterminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
      if ( s.equals ( this.modelOriginal.getGrammar ().getStartSymbol () ) )
      {
        start = c;
      }
    }
    TerminalSymbolSet terminals = new DefaultTerminalSymbolSet ();
    for ( TerminalSymbol s : this.modelOriginal.getGrammar ()
        .getTerminalSymbolSet () )
    {
      try
      {
        terminals
            .add ( new DefaultTerminalSymbol ( new String ( s.getName () ) ) );
      }
      catch ( TerminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
    }
    CFG newCFG = new DefaultCFG ( nonterminalSet, terminals, start );
    for ( Production p : this.modelOriginal.getGrammar ().getProduction () )
    {
      NonterminalSymbol nonterminal = null;
      for ( NonterminalSymbol s : nonterminalSet )
      {
        if ( p.getNonterminalSymbol ().getName ().equals (
            new String ( s.getName () ) ) )
        {
          nonterminal = s;
        }
      }
      ProductionWord word = new DefaultProductionWord ();
      for ( ProductionWordMember m : p.getProductionWord () )
      {
        for ( NonterminalSymbol n : nonterminalSet )
        {
          if ( m.getName ().equals ( n.getName () ) )
          {
            word.add ( n );
          }
        }
        for ( TerminalSymbol t : terminals )
        {
          if ( m.getName ().equals ( t.getName () ) )
          {
            word.add ( t );
          }
        }
      }
      newCFG.addProduction ( new DefaultProduction ( nonterminal, word ) );
    }
    for ( NonterminalSymbol n : nonterminalSet.get () )
    {
      this.nonterminals.addElement ( n );
    }
    this.gui.jGTIListNonterminalsConverted.setModel ( this.nonterminals );
    this.modelConverted = new DefaultGrammarModel ( newCFG );
  }


  /**
   * TODO
   * 
   * @return
   */
  private CFG cloneConverted ()
  {
    NonterminalSymbol start = null;
    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();
    for ( NonterminalSymbol s : this.modelConverted.getGrammar ()
        .getNonterminalSymbolSet () )
    {
      NonterminalSymbol c = new DefaultNonterminalSymbol ( s.getName () );
      try
      {
        nonterminalSet.add ( c );
      }
      catch ( NonterminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
      if ( s.equals ( this.modelConverted.getGrammar ().getStartSymbol () ) )
      {
        start = c;
      }
    }
    TerminalSymbolSet terminals = new DefaultTerminalSymbolSet ();
    for ( TerminalSymbol s : this.modelConverted.getGrammar ()
        .getTerminalSymbolSet () )
    {
      try
      {
        terminals.add ( new DefaultTerminalSymbol ( s.getName () ) );
      }
      catch ( TerminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
    }
    CFG newCFG = new DefaultCFG ( nonterminalSet, terminals, start );
    for ( Production p : this.modelConverted.getGrammar ().getProduction () )
    {
      NonterminalSymbol nonterminal = null;
      for ( NonterminalSymbol s : nonterminalSet )
      {
        if ( p.getNonterminalSymbol ().getName ().equals ( s.getName () ) )
        {
          nonterminal = s;
        }
      }
      ProductionWord word = new DefaultProductionWord ();
      for ( ProductionWordMember m : p.getProductionWord () )
      {
        for ( NonterminalSymbol n : nonterminalSet )
        {
          if ( m.getName ().equals ( n.getName () ) )
          {
            word.add ( n );
          }
        }
        for ( TerminalSymbol t : terminals )
        {
          if ( m.getName ().equals ( t.getName () ) )
          {
            word.add ( t );
          }
        }
      }
      newCFG.addProduction ( new DefaultProduction ( nonterminal, word ) );
    }
    return newCFG;
  }


  /**
   * TODO
   */
  private DefaultListModel nonterminals = new DefaultListModel ();


  /**
   * TODO
   */
  private void updateOriginal ()
  {
    this.gui.styledNonterminalSymbolSetParserPanelOriginal
        .setText ( this.modelOriginal.getGrammar ().getNonterminalSymbolSet () );
    this.gui.styledStartNonterminalSymbolParserPanelOriginal
        .setText ( this.modelOriginal.getGrammar ().getStartSymbol () );
    this.gui.styledTerminalSymbolSetParserPanelOriginal
        .setText ( this.modelOriginal.getGrammar ().getTerminalSymbolSet () );
  }


  /**
   * TODO
   */
  protected void updateConverted ()
  {
    this.gui.styledStartNonterminalSymbolParserPanelConverted
        .setText ( this.modelConverted.getGrammar ().getStartSymbol () );
    this.gui.styledTerminalSymbolSetParserPanelConverted
        .setText ( this.modelConverted.getGrammar ().getTerminalSymbolSet () );
  }


  /**
   * Returns the convertMachineTableModel.
   * 
   * @return The convertMachineTableModel.
   * @see #convertMachineTableModel
   */
  public ConvertMachineTableModel getConvertMachineTableModel ()
  {
    return this.convertMachineTableModel;
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public ConvertGrammarDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the tableColumnModel.
   * 
   * @return The tableColumnModel.
   * @see #tableColumnModel
   */
  public ConvertMachineTableColumnModel getTableColumnModel ()
  {
    return this.tableColumnModel;
  }


  /**
   * Handles the action on the auto step button.
   */
  public void handleAutoStep ()
  {
    logger.debug ( "handleAutoStep", "handle auto step" ); //$NON-NLS-1$ //$NON-NLS-2$

    setStatus ();

    startAutoStepTimer ();
  }


  /**
   * Handles the action on the begin step button.
   */
  public void handleBeginStep ()
  {
    logger.debug ( "handleBeginStep", "handle begin step" ); //$NON-NLS-1$ //$NON-NLS-2$
    while ( !this.stepItemList.isEmpty () )
    {
      handlePreviousStep ();
    }
  }


  /**
   * Handles the action on the cancel button.
   */
  public void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.dispose ();
  }


  /**
   * Handles the action on the end step button.
   */
  public void handleEndStep ()
  {
    logger.debug ( "handleEndStep", "handle end step" ); //$NON-NLS-1$ //$NON-NLS-2$
    while ( !this.endReached )
    {
      handleNextStep ();
    }
  }


  /**
   * Handles the action on the next step button.
   */
  public void handleNextStep ()
  {
    logger.debug ( "handleNextStep", "handle next step" ); //$NON-NLS-1$ //$NON-NLS-2$
    performNextStep ();
    setStatus ();
  }


  /**
   * TODO
   */
  private DefaultGrammarModel modelOriginal;


  /**
   * TODO
   */
  private DefaultGrammarModel modelConverted;


  /**
   * Handles the action on the ok button.
   */
  public void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();
    while ( !this.endReached )
    {
      performNextStep ();
    }
    this.gui.setVisible ( false );

    this.panel.getMainWindow ().handleNew ( this.modelConverted.getElement (),
        false );
    this.gui.dispose ();
  }


  /**
   * Handles the action on the previous step button.
   */
  public void handlePreviousStep ()
  {
    performPreviousStep ();
  }


  /**
   * Handles the action on the print button.
   */
  public void handlePrint ()
  {
    // PrintDialog dialog = new PrintDialog ( this.parent, this );
    // dialog.show ();
  }


  /**
   * Handles the action on the stop button.
   */
  public void handleStop ()
  {
    logger.debug ( "handleStop", "handle stop" ); //$NON-NLS-1$ //$NON-NLS-2$

    cancelAutoStepTimer ();

    this.gui.jGTIToolBarToggleButtonAutoStep.setSelected ( false );
    setStatus ();
  }


  /**
   * Performs previous step
   */
  private void performPreviousStep ()
  {
    StepItem item = this.stepItemList.remove ( this.stepItemList.size () - 1 );
    this.i = item.getINow ();
    this.j = item.getJNow ();
    CFG old = item.getOldCFG ();
    this.modelConverted = new DefaultGrammarModel ( old );

    this.gui.jGTITableGrammarConverted.setModel ( this.modelConverted
        .getGrammar () );

    this.gui.jGTITableGrammarConverted
        .setColumnModel ( this.grammarColumnModelConverted );
    this.gui.jGTITableGrammarConverted.getTableHeader ().setReorderingAllowed (
        false );

    this.nonterminals = item.getOldNonterminalModel ();
    this.gui.jGTIListNonterminalsConverted.setModel ( this.nonterminals );
    updateConverted ();
    this.convertMachineTableModel.removeLastRow ();
    this.endReached = false;
    setStatus ();
  }


  /**
   * Sets the button status.
   */
  private final void setStatus ()
  {
    if ( this.gui.jGTIToolBarToggleButtonAutoStep.isSelected () )
    {
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( false );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( false );
      this.gui.jGTIToolBarButtonStop.setEnabled ( true );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( false );
    }
    else
    {
      boolean beginReached = this.stepItemList.isEmpty ();
      this.gui.jGTIListNonterminalsConverted.setDragEnabled ( beginReached );
      this.gui.jGTIToolBarButtonBeginStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonPreviousStep.setEnabled ( !beginReached );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( !this.endReached );
      this.gui.jGTIToolBarButtonStop.setEnabled ( false );
      this.gui.jGTIToolBarButtonEndStep.setEnabled ( !this.endReached );
    }
  }


  /**
   * Shows the {@link ConvertMachineDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show", "show the convert machine dialog" ); //$NON-NLS-1$ //$NON-NLS-2$

    Rectangle rect = PreferenceManager.getInstance ()
        .getConvertMachineDialogBounds ();
    if ( ( rect.x == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_X )
        || ( rect.y == PreferenceManager.DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_Y ) )
    {
      rect.x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
          - ( this.gui.getWidth () / 2 );
      rect.y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
          - ( this.gui.getHeight () / 2 );
    }
    this.gui.setBounds ( rect );
    this.gui.setVisible ( true );
  }


  /**
   * Starts the auto step timer.
   */
  @SuppressWarnings ( "synthetic-access" )
  private final void startAutoStepTimer ()
  {
    cancelAutoStepTimer ();

    this.autoStepTimer = new Timer ();
    int time = PreferenceManager.getInstance ().getAutoStepItem ()
        .getAutoStepInterval ();
    this.autoStepTimer.schedule ( new AutoStepTimerTask (), time, time );
  }


  private int i = 1;


  private int j = 0;


  /**
   * Performs the next step.
   */
  @SuppressWarnings ( "unchecked" )
  private final void performNextStep ()
  {
    CFG oldCFG = cloneConverted ();
    DefaultListModel oldNonterminalModel = new DefaultListModel ();
    for ( int n = 0 ; n < this.nonterminals.size () ; n++ )
    {
      oldNonterminalModel.addElement ( this.nonterminals.getElementAt ( n ) );
    }
    int iNow = this.i;
    int jNow = this.j;

    addOutlineComment ( new PrettyString ( new PrettyToken ( "i: " + this.i
        + " j:" + this.j, Style.COMMENT ) ) );
    CFG cfg = ( CFG ) this.modelConverted.getGrammar ();
    // for ( int i = 1 ; i <= nonterminals.size () ; i++ )
    if ( this.i <= this.nonterminals.size () )
    {
      NonterminalSymbol ai = ( NonterminalSymbol ) this.nonterminals
          .getElementAt ( this.i - 1 );
      // for ( int j = 0 ; j < this.i - 1 ; j++ )
      if ( this.j < this.i )
      {
        NonterminalSymbol aj = ( NonterminalSymbol ) this.nonterminals
            .getElementAt ( this.j );
        this.j++ ;
        ArrayList < Production > productions = getProductionsForNonterminals (
            ai, aj, cfg );
        ArrayList < Production > productionsOfAj = cfg
            .getProductionForNonTerminal ( aj );
        for ( Production pi : productions )
        {
          cfg.getProduction ().remove ( pi );
          pi.getProductionWord ().get ().remove ( 0 );
          for ( Production pj : productionsOfAj )
          {
            ProductionWord word = new DefaultProductionWord ( pj
                .getProductionWord ().get () );
            word.add ( pi.getProductionWord ().get () );
            Production p = new DefaultProduction ( ai, word );
            this.modelConverted.addProduction ( p, false );
          }
        }
      }
      else
      {
        this.j = 0;
        this.i++ ;
        if ( this.i > this.nonterminals.size () )
        {
          this.endReached = true;
        }
      }
      try
      {
        eliminateDirectLeftRecursion ( ai, cfg );
      }
      catch ( NonterminalSymbolSetException exc )
      {
        exc.printStackTrace ();
      }
    }
    else
    {
      this.endReached = true;
    }
    this.stepItemList.add ( new StepItem ( oldCFG, oldNonterminalModel, iNow,
        jNow ) );
    this.gui.repaint ();
    updateConverted ();
  }


  /**
   * TODO
   * 
   * @param a
   * @param b
   * @param cfg
   * @return Productions for 2 nonterminals
   */
  private ArrayList < Production > getProductionsForNonterminals (
      NonterminalSymbol a, NonterminalSymbol b, CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getNonterminalSymbol ().equals ( a )
          && p.getProductionWord ().size () > 0
          && p.getProductionWord ().get ( 0 ).equals ( b ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  /**
   * TODO
   * 
   * @param cfg
   * @return Lala
   */
  @SuppressWarnings ( "unused" )
  private CFG eliminateEntityProductions ( CFG cfg )
  {
    CFG newCFG = new DefaultCFG ( cfg.getNonterminalSymbolSet (), cfg
        .getTerminalSymbolSet (), cfg.getStartSymbol () );
    for ( Production p : cfg.getProduction () )
    {
      newCFG.addProduction ( p );
    }
    ArrayList < Production > entityProductions = getEntityProductions ( cfg );
    for ( Production p : entityProductions )
    {
      NonterminalSymbol a = p.getNonterminalSymbol ();
      NonterminalSymbol b = ( NonterminalSymbol ) p.getProductionWord ().get (
          0 );
      for ( Production p2 : newCFG.getProductionForNonTerminal ( b ) )
      {
        newCFG.addProduction ( new DefaultProduction ( a, p2
            .getProductionWord () ) );
      }
      newCFG.getProduction ().remove ( p );
    }
    if ( !getEntityProductions ( newCFG ).isEmpty () )
    {
      newCFG = eliminateEntityProductions ( newCFG );
    }
    return newCFG;
  }


  /**
   * TODO
   * 
   * @param cfg
   * @return EntityProductions
   */
  private ArrayList < Production > getEntityProductions ( CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getProductionWord ().size () == 1
          && p.getProductionWord ().get ( 0 ) instanceof NonterminalSymbol )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  /**
   * TODO
   * 
   * @param cfg
   * @return Lala
   */
  @SuppressWarnings ( "unused" )
  private CFG eliminateEpsilonProductions ( CFG cfg )
  {
    CFG newCFG = new DefaultCFG ( cfg.getNonterminalSymbolSet (), cfg
        .getTerminalSymbolSet (), cfg.getStartSymbol () );
    ArrayList < NonterminalSymbol > symbolsToEpsilon = new ArrayList < NonterminalSymbol > ();
    ArrayList < Production > productions = new ArrayList < Production > ();
    productions.addAll ( cfg.getProduction () );
    for ( Production p : getEpsilonProductions ( cfg ) )
    {
      symbolsToEpsilon.add ( p.getNonterminalSymbol () );
      productions.remove ( p );
    }
    for ( Production p : productions )
    {
      newCFG.addProduction ( p );
    }
    for ( NonterminalSymbol n : symbolsToEpsilon )
    {
      for ( Production p : cfg.getProduction () )
      {
        if ( p.getProductionWord ().get ().contains ( n ) )
        {
          ProductionWord word = new DefaultProductionWord ();
          ArrayList < ProductionWordMember > members = new ArrayList < ProductionWordMember > ();
          members.addAll ( p.getProductionWord ().get () );
          members.remove ( n );
          word.add ( members );
          newCFG.addProduction ( new DefaultProduction ( p
              .getNonterminalSymbol (), word ) );
        }
      }
    }

    return newCFG;
  }


  /**
   * TODO
   * 
   * @param cfg
   * @return EpsilonProductions
   */
  private ArrayList < Production > getEpsilonProductions ( CFG cfg )
  {
    ArrayList < Production > symbols = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getProductionWord ().equals ( new DefaultProductionWord () ) )
      {
        symbols.add ( p );
      }
    }
    return symbols;
  }


  /**
   * TODO
   * 
   * @param s
   * @param cfg
   * @return ArrayList
   */
  private ArrayList < Production > getDirectLeftRecursion (
      NonterminalSymbol s, CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProductionForNonTerminal ( s ) )
    {
      if ( p.getProductionWord ().size () > 0
          && p.getProductionWord ().get ( 0 ).equals ( s ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  /**
   * TODO
   * 
   * @param s
   * @param cfg
   * @throws NonterminalSymbolSetException
   */
  private void eliminateDirectLeftRecursion ( NonterminalSymbol s, CFG cfg )
      throws NonterminalSymbolSetException
  {
    ArrayList < Production > leftRecursions = getDirectLeftRecursion ( s, cfg );

    if ( !leftRecursions.isEmpty () )
    {
      DefaultNonterminalSymbol otherS = new DefaultNonterminalSymbol ( s
          .getName ()
          + "'" ); //$NON-NLS-1$
      cfg.getNonterminalSymbolSet ().add ( otherS );
      this.nonterminals.addElement ( otherS );
      ArrayList < Production > otherProductions = new ArrayList < Production > ();
      otherProductions.addAll ( cfg.getProductionForNonTerminal ( s ) );
      otherProductions.removeAll ( leftRecursions );
      for ( Production p : otherProductions )
      {
        p.getProductionWord ().add ( otherS );
      }
      for ( Production p : leftRecursions )
      {
        p.setNonterminalSymbol ( otherS );
        p.getProductionWord ().get ().remove ( 0 );
        p.getProductionWord ().add ( otherS );
      }
      this.modelConverted.addProduction ( new DefaultProduction ( otherS,
          new DefaultProductionWord () ), false );
    }
  }

}

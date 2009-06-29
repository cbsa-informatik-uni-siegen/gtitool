package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.rg.RG;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedAdapter;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.convert.ConvertContextFreeGrammar;
import de.unisiegen.gtitool.ui.convert.ConvertRegularGrammar;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.ConvertGrammarDialog.ConvertGrammarType;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
import de.unisiegen.gtitool.ui.model.GrammarConsoleTableModel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.popup.ProductionPopupMenu;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.ProductionsListChangedItem;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoItem;
import de.unisiegen.gtitool.ui.storage.Storage;
import de.unisiegen.gtitool.ui.swing.JGTIList;
import de.unisiegen.gtitool.ui.swing.JGTITable;
import de.unisiegen.gtitool.ui.swing.dnd.JGTITableModelRows;
import de.unisiegen.gtitool.ui.swing.dnd.JGTITableTransferHandler;


/**
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class GrammarPanel implements LogicClass < GrammarPanelForm >,
    EditorPanel

{

  /**
   * The {@link GrammarPanelForm}.
   */
  protected GrammarPanelForm gui;


  /**
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;


  /**
   * The {@link Grammar}.
   */
  protected Grammar grammar;


  /**
   * The name of this {@link GrammarPanel}.
   */
  private String name = null;


  /**
   * The {@link File} for this {@link GrammarPanel}.
   */
  private File file;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link RedoUndoHandler}
   */
  private RedoUndoHandler redoUndoHandler;


  /**
   * The {@link MainWindowForm}
   */
  protected MainWindowForm mainWindowForm;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The {@link GrammarConsoleTableModel} for the warning table.
   */
  private GrammarConsoleTableModel warningTableModel;


  /**
   * The {@link GrammarConsoleTableModel} for the error table.
   */
  private GrammarConsoleTableModel errorTableModel;


  /**
   * The {@link GrammarColumnModel}.
   */
  private GrammarColumnModel grammarColumnModel = new GrammarColumnModel ();


  /**
   * Allocates a new {@link GrammarPanel}
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param model The {@link DefaultGrammarModel}.
   * @param file The {@link File}
   */
  public GrammarPanel ( MainWindowForm mainWindowForm,
      DefaultGrammarModel model, File file )
  {
    this.mainWindowForm = mainWindowForm;
    this.model = model;
    this.file = file;
    this.gui = new GrammarPanelForm ( this );
    this.grammar = this.model.getGrammar ();

    this.redoUndoHandler = new RedoUndoHandler ( this.mainWindowForm );

    this.model.setRedoUndoHandler ( this.redoUndoHandler );

    initialize ();
    initializeSecondView ();

    setVisibleConsole ( this.mainWindowForm.getJCheckBoxMenuItemConsole ()
        .getState () );

    this.gui.jGTISplitPaneConsole.setDividerLocation ( 1.0 );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );

    this.gui.jGTITableGrammar.setDragEnabled ( true );
    this.gui.jGTITableGrammar
        .setTransferHandler ( new JGTITableTransferHandler (
            TransferHandler.MOVE )
        {

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = -1544518703030919808L;


          @Override
          protected boolean importTableModelRows ( JGTITable jGTITable,
              JGTITableModelRows rows, int targetIndex )
          {
            moveRows ( jGTITable, rows, targetIndex );
            return true;
          }
        } );

    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @Override
          public void colorChanged ()
          {
            GrammarPanel.this.gui.jGTITableGrammar.repaint ();
          }
        } );
  }


  /**
   * Add a new Error
   * 
   * @param grammarException The {@link GrammarException} containing the data
   */
  public final void addError ( GrammarException grammarException )
  {
    this.errorTableModel.addRow ( grammarException );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Add a new {@link Production}.
   * 
   * @param production the new Production to add.
   */
  public final void addProduction ( Production production )
  {
    this.model.addProduction ( production, true );
  }


  /**
   * Add a new warning.
   * 
   * @param grammarException The {@link GrammarException} containing the data.
   */
  public final void addWarning ( GrammarException grammarException )
  {
    this.warningTableModel.addRow ( grammarException );
  }


  /**
   * Clears the {@link Production} highlights.
   */
  private final void clearProductionHighlights ()
  {
    this.grammar.getStartSymbol ().setError ( false );

    for ( NonterminalSymbol current : this.grammar.getNonterminalSymbolSet () )
    {
      current.setError ( false );
    }

    for ( TerminalSymbol current : this.grammar.getTerminalSymbolSet () )
    {
      current.setError ( false );
    }

    for ( Production currentProduction : this.grammar.getProduction () )
    {
      currentProduction.setError ( false );
      currentProduction.getNonterminalSymbol ().setError ( false );
      for ( ProductionWordMember currentMember : currentProduction
          .getProductionWord () )
      {
        currentMember.setError ( false );
      }
    }
    this.gui.jGTITableGrammar.repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#clearValidationMessages()
   */
  public final void clearValidationMessages ()
  {
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "GrammarPanel.Error" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "GrammarPanel.Warning" ) ); //$NON-NLS-1$

    this.errorTableModel.clearData ();
    this.warningTableModel.clearData ();
  }


  /**
   * TODO
   * 
   * @param g
   * @return The string of the RDP
   */
  private String createRDP ( Grammar g )
  {
    StringBuilder result = new StringBuilder ();
    boolean first = true;
    for ( NonterminalSymbol A : g.getNonterminalSymbolSet () )
    {
      if ( !first )
      {
        result.append ( "\n\n" ); //$NON-NLS-1$
      }
      else
      {
        first = false;
      }
      result.append ( "void " ); //$NON-NLS-1$
      result.append ( A );
      result.append ( "() {\n" ); //$NON-NLS-1$
      for ( Production p : g.getProductionForNonTerminal ( A ) )
      {
        result.append ( "   case:{\n" ); //$NON-NLS-1$
        for ( ProductionWordMember m : p.getProductionWord () )
        {
          if ( m instanceof NonterminalSymbol )
          {
            result.append ( "      " ); //$NON-NLS-1$
            result.append ( m );
            result.append ( "();\n" ); //$NON-NLS-1$
          }
          else if ( m instanceof TerminalSymbol )
          {
            result.append ( "      match(\"" ); //$NON-NLS-1$
            result.append ( m );
            result.append ( "\");\n" ); //$NON-NLS-1$
          }
        }
        result.append ( "   }\n" ); //$NON-NLS-1$
      }
      result.append ( "}" ); //$NON-NLS-1$
    }
    return result.toString ();
  }


  /**
   * Let the listeners know that the modify status has changed.
   * 
   * @param forceModify True if the modify is forced, otherwise false.
   */
  public final void fireModifyStatusChanged ( boolean forceModify )
  {
    clearValidationMessages ();

    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    if ( forceModify )
    {
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( true );
      }
    }
    else
    {
      boolean newModifyStatus = isModified ();
      for ( ModifyStatusChangedListener current : listeners )
      {
        current.modifyStatusChanged ( newModifyStatus );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getConverter()
   */
  public Converter getConverter ()
  {
    if ( this.grammar instanceof RG )
    {
      return new ConvertRegularGrammar ( this.mainWindowForm, this.grammar );
    }
    return new ConvertContextFreeGrammar ( this.mainWindowForm, this.grammar );
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFile()
   */
  public final File getFile ()
  {
    return this.file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFileEnding()
   */
  public final String getFileEnding ()
  {
    return "." + this.grammar.getGrammarType ().getFileEnding (); //$NON-NLS-1$
  }


  /**
   * Returns the grammar.
   * 
   * @return The grammar.
   * @see #grammar
   */
  public final Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * Returns the {@link TableColumnModel} of the grammar.
   * 
   * @return The {@link TableColumnModel} of the grammar.
   */
  public TableColumnModel getGrammarTableColumnModel ()
  {
    return this.grammarColumnModel;
  }


  /**
   * Returns the {@link TableModel} of the grammar.
   * 
   * @return The {@link TableModel} of the grammar.
   */
  public TableModel getGrammarTableModel ()
  {
    return this.grammar;
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final GrammarPanelForm getGUI ()
  {
    return this.gui;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getJTabbedPaneConsole()
   */
  public final JTabbedPane getJTabbedPaneConsole ()
  {
    return this.gui.jGTITabbedPaneConsole;
  }


  /**
   * Returns the {@link MainWindow}.
   * 
   * @return the {@link MainWindow}.
   */
  public final MainWindow getMainWindow ()
  {
    return this.mainWindowForm.getLogic ();
  }


  /**
   * Returns the {@link DefaultModel}.
   * 
   * @return The {@link DefaultModel}.
   * @see #model
   */
  public final DefaultModel getModel ()
  {
    return this.model;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getName()
   */
  public final String getName ()
  {
    return this.file == null ? this.name : this.file.getName ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getPanel()
   */
  public final JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * Returns the parent frame.
   * 
   * @return the parent frame.
   */
  public final JFrame getParent ()
  {
    return this.mainWindowForm;
  }


  /**
   * Returns the redoUndoHandler.
   * 
   * @return The redoUndoHandler.
   * @see #redoUndoHandler
   */
  public final RedoUndoHandler getRedoUndoHandler ()
  {
    return this.redoUndoHandler;
  }


  /**
   * Handle add production button pressed.
   */
  public final void handleAddProduction ()
  {
    ProductionDialog dialog = new ProductionDialog ( getParent (), this.grammar
        .getNonterminalSymbolSet (), this.grammar.getTerminalSymbolSet (),
        this.model, null, this.redoUndoHandler );
    dialog.show ();
  }


  /**
   * Handles focus lost event on the console table.
   * 
   * @param event The {@link FocusEvent}.
   */
  public final void handleConsoleTableFocusLost (
      @SuppressWarnings ( "unused" ) FocusEvent event )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
    clearProductionHighlights ();
  }


  /**
   * Handles the mouse exited event on the console table.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleConsoleTableMouseExited (
      @SuppressWarnings ( "unused" ) MouseEvent event )
  {
    this.gui.jGTITableErrors.clearSelection ();
    this.gui.jGTITableWarnings.clearSelection ();
    clearProductionHighlights ();
  }


  /**
   * Handles {@link ListSelectionEvent}s on the console table.
   * 
   * @param event The {@link ListSelectionEvent}.
   */
  public final void handleConsoleTableValueChanged ( ListSelectionEvent event )
  {
    JTable table;
    if ( event.getSource () == this.gui.jGTITableErrors.getSelectionModel () )
    {
      table = this.gui.jGTITableErrors;
    }
    else if ( event.getSource () == this.gui.jGTITableWarnings
        .getSelectionModel () )
    {
      table = this.gui.jGTITableWarnings;
    }
    else
    {
      throw new IllegalArgumentException ( "wrong event source" ); //$NON-NLS-1$
    }

    this.gui.jGTITableGrammar.clearSelection ();
    clearProductionHighlights ();

    int index = table.getSelectedRow ();
    if ( index != -1 )
    {
      highlightProduction ( ( ( GrammarConsoleTableModel ) table.getModel () )
          .getProduction ( index ) );
      highlightNonterminalSymbol ( ( ( GrammarConsoleTableModel ) table
          .getModel () ).getNonterminalSymbol ( index ) );
      highlightProductionWord ( ( ( GrammarConsoleTableModel ) table
          .getModel () ).getProductionWordMember ( index ) );
      this.gui.jGTITableGrammar.repaint ();
    }
  }


  /**
   * TODO
   */
  public final void handleCreateRDP ()
  {
    TextWindow w = new TextWindow ( this.mainWindowForm,
        createRDP ( this.grammar ), false, null, getName () + "_RDP" ); //$NON-NLS-1$
    w.show ();
  }


  /**
   * Handle delete production button pressed.
   */
  public final void handleDeleteProduction ()
  {
    if ( this.gui.jGTITableGrammar.getRowCount () > 0 )
    {
      int [] rows = this.gui.jGTITableGrammar.getSelectedRows ();

      if ( rows.length == 0 )
      {
        return;
      }

      String message = null;
      if ( rows.length == 1 )
      {
        message = Messages.getString (
            "ProductionPopupMenu.DeleteProductionQuestion", //$NON-NLS-1$
            this.grammar.getProductionAt ( rows [ 0 ] ) );
      }
      else
      {
        message = Messages
            .getString ( "ProductionPopupMenu.DeleteProductionsQuestion" ); //$NON-NLS-1$
      }

      ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
          message, Messages
              .getString ( "ProductionPopupMenu.DeleteProductionTitle" ), true, //$NON-NLS-1$
          false, true, false, false );
      confirmDialog.show ();
      if ( confirmDialog.isConfirmed () )
      {
        ArrayList < Production > oldProductions = new ArrayList < Production > ();
        oldProductions.addAll ( this.grammar.getProduction () );

        int number = 0;
        for ( int index : rows )
        {
          this.model.removeProduction ( index - number );
          number++ ;
        }

        RedoUndoItem item = new ProductionsListChangedItem ( this.grammar,
            oldProductions );
        this.redoUndoHandler.addItem ( item );

        this.gui.repaint ();
      }
    }
  }


  /**
   * Handle edit production button pressed.
   */
  public final void handleEditProduction ()
  {

    if ( ( this.gui.jGTITableGrammar.getRowCount () > this.gui.jGTITableGrammar
        .getSelectedRow () )
        && ( this.gui.jGTITableGrammar.getSelectedRow () > -1 ) )
    {
      Production production = this.grammar
          .getProductionAt ( this.gui.jGTITableGrammar.getSelectedRow () );
      JFrame window = ( JFrame ) SwingUtilities.getWindowAncestor ( this.gui );
      ProductionDialog productionDialog = new ProductionDialog ( window,
          this.grammar.getNonterminalSymbolSet (), this.grammar
              .getTerminalSymbolSet (), this.model, production,
          this.redoUndoHandler );
      productionDialog.show ();
    }
  }


  /**
   * Opens {@link ConvertGrammarDialog} for elimination of entity productions
   */
  public final void handleEliminateEntityProductions ()
  {
    ConvertGrammarDialog converter = new ConvertGrammarDialog (
        this.mainWindowForm, this );
    converter.convert ( ConvertGrammarType.ELIMINATE_ENTITY_PRODUCTIONS );
  }


  /**
   * Opens {@link ConvertGrammarDialog} for elimination of epsilon productions
   */
  public final void handleEliminateEpsilonProductions ()
  {
    ConvertGrammarDialog converter = new ConvertGrammarDialog (
        this.mainWindowForm, this );
    converter.convert ( ConvertGrammarType.ELIMINATE_EPSILON_PRODUCTIONS );
  }


  /**
   * Opens {@link ConvertGrammarDialog} for elimination of LeftRecursion
   */
  public final void handleEliminateLeftRecursion ()
  {
    ConvertGrammarDialog converter = new ConvertGrammarDialog (
        this.mainWindowForm, this );
    converter.convert ( ConvertGrammarType.ELIMINATE_LEFT_RECURSION );
  }


  /**
   * Handles the {@link Exchange}.
   */
  public final void handleExchange ()
  {
    ExchangeDialog exchangeDialog = new ExchangeDialog ( this.mainWindowForm
        .getLogic (), this.model.getElement (), this.file );
    exchangeDialog.show ();
  }


  /**
   * Handles key events on the grammar table.
   * 
   * @param event The {@link KeyEvent}.
   */
  public final void handleGrammarTableKeyReleased ( KeyEvent event )
  {
    if ( event.getKeyCode () == KeyEvent.VK_DELETE )
    {
      handleDeleteProduction ();
    }
  }


  /**
   * Opens {@link ConvertGrammarDialog} for elimination of LeftRecursion
   */
  public final void handleLeftFactoring ()
  {
    ConvertGrammarDialog converter = new ConvertGrammarDialog (
        this.mainWindowForm, this );
    converter.convert ( ConvertGrammarType.LEFT_FACTORING );
  }


  /**
   * Handle redo button pressed
   */
  public final void handleRedo ()
  {
    this.redoUndoHandler.redo ();
    updateConfiguration ();
    this.gui.repaint ();
    fireModifyStatusChanged ( false );
  }


  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public final File handleSave ()
  {
    if ( this.file == null )
    {
      return handleSaveAs ();
    }
    try
    {
      Storage.getInstance ().store ( this.model, this.file );
    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public final File handleSaveAs ()
  {
    try
    {
      FileFilter fileFilter = new FileFilter ()
      {

        @Override
        public boolean accept ( File acceptedFile )
        {
          if ( acceptedFile.isDirectory () )
          {
            return true;
          }
          if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\." //$NON-NLS-1$
              + GrammarPanel.this.grammar.getGrammarType ().getFileEnding () ) )
          {
            return true;
          }
          return false;
        }


        @Override
        public String getDescription ()
        {
          return Messages.getString ( "NewDialog." //$NON-NLS-1$
              + GrammarPanel.this.grammar.getGrammarType ().toString () )
              + " (*." //$NON-NLS-1$
              + GrammarPanel.this.grammar.getGrammarType ().getFileEnding ()
              + ")"; //$NON-NLS-1$
        }
      };

      SaveDialog saveDialog = new SaveDialog ( this.mainWindowForm,
          PreferenceManager.getInstance ().getWorkingPath (), fileFilter,
          fileFilter );
      saveDialog.show ();

      if ( ( !saveDialog.isConfirmed () )
          || ( saveDialog.getSelectedFile () == null ) )
      {
        return null;
      }

      if ( saveDialog.getSelectedFile ().exists () )
      {
        ConfirmDialog confirmDialog = new ConfirmDialog ( this.mainWindowForm,
            Messages.getString (
                "MachinePanel.FileExists", saveDialog.getSelectedFile () //$NON-NLS-1$
                    .getName () ), Messages.getString ( "MachinePanel.Save" ), //$NON-NLS-1$
            true, false, true, false, false );
        confirmDialog.show ();
        if ( confirmDialog.isNotConfirmed () )
        {
          return null;
        }
      }

      String filename = saveDialog.getSelectedFile ().toString ().matches (
          ".+\\." + this.grammar.getGrammarType ().getFileEnding () ) ? saveDialog //$NON-NLS-1$
          .getSelectedFile ().toString ()
          : saveDialog.getSelectedFile ().toString ()
              + "." + this.grammar.getGrammarType ().getFileEnding (); //$NON-NLS-1$

      Storage.getInstance ().store ( this.model, new File ( filename ) );

      PreferenceManager.getInstance ().setWorkingPath (
          saveDialog.getCurrentDirectory ().getAbsolutePath () );
      this.file = new File ( filename );
    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "MachinePanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * Handle mouse button event for the JTable.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleTableMouseClickedEvent ( MouseEvent event )
  {
    if ( event.getButton () == MouseEvent.BUTTON2 )
    {
      return;
    }

    if ( event.getButton () == MouseEvent.BUTTON3 )
    {

      ArrayList < Production > productions = new ArrayList < Production > ();
      boolean multiRowChoosen = false;

      int [] rows = this.gui.jGTITableGrammar.getSelectedRows ();
      int rowIndex = this.gui.jGTITableGrammar.rowAtPoint ( event.getPoint () );

      if ( rows.length > 1 )
      {
        for ( int row : rows )
        {
          if ( row == rowIndex )
          {
            multiRowChoosen = true;
          }
        }
      }
      if ( !multiRowChoosen )
      {
        if ( rowIndex == -1 )
        {
          // Do nothing
        }
        else
        {

          // Give the user a visual clue which rowIndex he has clicked on
          this.gui.jGTITableGrammar
              .changeSelection ( rowIndex, 0, false, false );

          productions.add ( this.grammar.getProductionAt ( rowIndex ) );
        }
      }
      else
      {
        for ( int element : rows )
        {
          productions.add ( this.grammar.getProductionAt ( element ) );
        }
      }

      ProductionPopupMenu popupmenu = new ProductionPopupMenu ( this,
          this.model, productions );

      popupmenu.show ( ( Component ) event.getSource (), event.getX (), event
          .getY () );
    }
    else if ( event.getClickCount () == 2 )
    {

      int rowIndex = this.gui.jGTITableGrammar.rowAtPoint ( event.getPoint () );
      if ( rowIndex == -1 )
      {
        handleAddProduction ();
        return;
      }
      Production production = this.grammar.getProductionAt ( rowIndex );

      JFrame window = ( JFrame ) SwingUtilities.getWindowAncestor ( this.gui );
      ProductionDialog productionDialog = new ProductionDialog ( window,
          this.grammar.getNonterminalSymbolSet (), this.grammar
              .getTerminalSymbolSet (), this.model, production,
          this.redoUndoHandler );
      productionDialog.show ();
    }
  }


  /**
   * Handles the toolbar edit document event.
   */
  public final void handleToolbarEditDocument ()
  {
    TerminalDialog terminalDialog = new TerminalDialog ( this.mainWindowForm,
        this, this.grammar );
    terminalDialog.show ();

    updateConfiguration ();

    // Must be repainted because of the maybe changed start symbol.
    this.gui.jGTITableGrammar.repaint ();
  }


  /**
   * Handle undo button pressed
   */
  public final void handleUndo ()
  {
    this.redoUndoHandler.undo ();
    updateConfiguration ();
    this.gui.repaint ();
    fireModifyStatusChanged ( false );
  }


  /**
   * Highlight the affected error {@link NonterminalSymbol}.
   * 
   * @param nonterminalSymbol List with all {@link NonterminalSymbol} that is
   *          affected.
   */
  private final void highlightNonterminalSymbol (
      NonterminalSymbol nonterminalSymbol )
  {
    if ( nonterminalSymbol == null )
    {
      return;
    }

    nonterminalSymbol.setError ( true );
  }


  /**
   * Highlight the affected error {@link Production} list.
   * 
   * @param productionList List with all {@link Production} list that is
   *          affected.
   */
  private final void highlightProduction (
      ArrayList < Production > productionList )
  {
    if ( productionList == null )
    {
      return;
    }

    for ( Production currentProduction : productionList )
    {
      currentProduction.setError ( true );
      currentProduction.getNonterminalSymbol ().setError ( true );
      for ( ProductionWordMember currentMember : currentProduction
          .getProductionWord () )
      {
        currentMember.setError ( true );
      }
    }
  }


  /**
   * Highlight the affected error {@link ProductionWordMember}s.
   * 
   * @param productionWordMember List with all {@link ProductionWordMember}s
   *          that are affected.
   */
  private final void highlightProductionWord (
      ArrayList < ProductionWordMember > productionWordMember )
  {
    if ( productionWordMember == null )
    {
      return;
    }

    for ( ProductionWordMember currentMember : productionWordMember )
    {
      currentMember.setError ( true );
    }
  }


  /**
   * Initialize the used model
   */
  private final void initialize ()
  {
    this.gui.jGTITableGrammar.setModel ( this.grammar );
    this.gui.jGTITableGrammar.setColumnModel ( this.grammarColumnModel );
    this.gui.jGTITableGrammar.addAllowedDndSource ( this.gui.jGTITableGrammar );
    this.gui.jGTITableGrammar.getTableHeader ().setReorderingAllowed ( false );
    if ( this.grammar.getColumnCount () > 0 )
    {
      this.gui.jGTITableGrammar.getSelectionModel ().setSelectionInterval ( 0,
          0 );
    }

    // ModifyStatusChangedListener
    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      public void modifyStatusChanged ( boolean modified )
      {
        fireModifyStatusChanged ( modified );
      }
    };
    this.model
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );

    this.errorTableModel = new GrammarConsoleTableModel ();
    this.gui.jGTITableErrors.setModel ( this.errorTableModel );
    this.gui.jGTITableErrors.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableErrors.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableErrors
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableErrors.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleConsoleTableValueChanged ( event );
          }
        } );
    this.warningTableModel = new GrammarConsoleTableModel ();
    this.gui.jGTITableWarnings.setModel ( this.warningTableModel );
    this.gui.jGTITableWarnings.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableWarnings.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableWarnings
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableWarnings.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            handleConsoleTableValueChanged ( event );
          }
        } );

    updateConfiguration ();
  }


  /**
   * Initializes the second view .
   */
  private final void initializeSecondView ()
  {
    MouseListener mouseListener = new MouseAdapter ()
    {

      @Override
      public void mouseReleased ( MouseEvent event )
      {
        GrammarPanel.this.mainWindowForm.getLogic ()
            .handleSecondViewMouseReleased ( event );
      }
    };

    MouseMotionListener mouseMotionListener = new MouseMotionAdapter ()
    {

      @Override
      public void mouseDragged ( MouseEvent event )
      {
        GrammarPanel.this.mainWindowForm.getLogic ()
            .handleSecondViewMouseReleased ( event );
      }
    };

    this.gui.jGTITableGrammar.addMouseListener ( mouseListener );
    this.gui.jGTITableGrammar.addMouseMotionListener ( mouseMotionListener );
    this.gui.jGTITableGrammar.getTableHeader ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneGrammar.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneGrammar.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.jGTITabbedPaneConsole.addMouseListener ( mouseListener );

    this.gui.jGTITableErrors.addMouseListener ( mouseListener );
    this.gui.jGTITableErrors.getTableHeader ()
        .addMouseListener ( mouseListener );
    this.gui.jGTIScrollPaneErrors.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneErrors.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.jGTITableWarnings.addMouseListener ( mouseListener );
    this.gui.jGTITableWarnings.getTableHeader ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneWarnings.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneWarnings.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.styledNonterminalSymbolSetParserPanel
        .addMouseListener ( mouseListener );
    this.gui.styledStartNonterminalSymbolParserPanel
        .addMouseListener ( mouseListener );
    this.gui.styledTerminalSymbolSetParserPanel
        .addMouseListener ( mouseListener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( this.model.isModified () ) || ( this.file == null );
  }


  /**
   * Signals if this panel is redo able
   * 
   * @return true, if is redo able, false else
   */
  public final boolean isRedoAble ()
  {
    return this.redoUndoHandler.isRedoAble ();
  }


  /**
   * Signals if this panel is undo able
   * 
   * @return true, if is undo able, false else
   */
  public final boolean isUndoAble ()
  {
    return this.redoUndoHandler.isUndoAble ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "GrammarPanel.Error" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "GrammarPanel.Warning" ) ); //$NON-NLS-1$
    this.gui.jGTILabelNonterminalSymbols.setText ( Messages
        .getString ( "TerminalPanel.NonterminalSymbols" ) ); //$NON-NLS-1$
    this.gui.jGTILabelStartSymbol.setText ( Messages
        .getString ( "TerminalPanel.StartSymbol" ) ); //$NON-NLS-1$
    this.gui.jGTILabelTerminalSymbols.setText ( Messages
        .getString ( "TerminalPanel.TerminalSymbols" ) ); //$NON-NLS-1$
  }


  /**
   * Moves the rows.
   * 
   * @param jGTITable The {@link JGTIList} into which to import the rows.
   * @param rows The {@link JGTITableModelRows}.
   * @param targetIndex The target index.
   */
  protected final void moveRows (
      @SuppressWarnings ( "unused" ) JGTITable jGTITable,
      JGTITableModelRows rows, int targetIndex )
  {
    ArrayList < Production > oldProductions = new ArrayList < Production > ();
    oldProductions.addAll ( this.grammar.getProduction () );

    ArrayList < Production > productions = new ArrayList < Production > ();

    int [] indeces = rows.getRowIndices ();

    int newTargetIndex = targetIndex;

    if ( ( indeces.length > 0 ) && ( indeces [ 0 ] < targetIndex ) )
    {
      newTargetIndex++ ;
    }

    for ( int index : indeces )
    {
      productions.add ( this.grammar.getProductionAt ( index ) );

      if ( index < targetIndex )
      {
        newTargetIndex-- ;
      }
    }

    for ( int i = indeces.length - 1 ; i > -1 ; i-- )
    {
      this.grammar.getProduction ().remove ( indeces [ i ] );
    }

    newTargetIndex = Math.min ( newTargetIndex, this.grammar.getRowCount () );

    this.grammar.getProduction ().addAll ( newTargetIndex, productions );

    this.gui.jGTITableGrammar.getSelectionModel ().setSelectionInterval (
        newTargetIndex, newTargetIndex + indeces.length - 1 );

    fireModifyStatusChanged ( false );

    boolean changed = false;

    for ( int i = 0 ; i < oldProductions.size () ; i++ )
    {
      if ( !this.grammar.getProductionAt ( i )
          .equals ( oldProductions.get ( i ) ) )
      {
        changed = true;
        break;
      }
    }
    if ( changed )
    {
      ProductionsListChangedItem item = new ProductionsListChangedItem (
          this.grammar, oldProductions );
      this.redoUndoHandler.addItem ( item );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.model.resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#setName(java.lang.String)
   */
  public final void setName ( String name )
  {
    this.name = name;
  }


  /**
   * Sets the visibility of the console.
   * 
   * @param visible Visible or not visible.
   */
  public final void setVisibleConsole ( boolean visible )
  {
    if ( visible )
    {
      this.gui.jGTISplitPaneConsole
          .setRightComponent ( this.gui.jGTITabbedPaneConsole );
      this.gui.jGTISplitPaneConsole.setDividerSize ( 3 );
      this.gui.jGTISplitPaneConsole
          .setDividerLocation ( this.gui.jGTISplitPaneConsole.getHeight () - 203 );
    }
    else
    {
      this.gui.jGTISplitPaneConsole.setRightComponent ( null );
      this.gui.jGTISplitPaneConsole.setDividerSize ( 0 );
    }
  }


  /**
   * Update the grammar configuration.
   */
  private void updateConfiguration ()
  {
    this.gui.styledTerminalSymbolSetParserPanel.setText ( this.grammar
        .getTerminalSymbolSet () );

    this.gui.styledNonterminalSymbolSetParserPanel.setText ( this.grammar
        .getNonterminalSymbolSet () );

    this.gui.styledStartNonterminalSymbolParserPanel.setText ( this.grammar
        .getStartSymbol () );
  }
}

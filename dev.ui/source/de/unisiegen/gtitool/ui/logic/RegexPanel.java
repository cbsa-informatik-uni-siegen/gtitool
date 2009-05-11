package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.GraphSelectionModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.exceptions.RegexException;
import de.unisiegen.gtitool.core.exceptions.RegexParseException;
import de.unisiegen.gtitool.core.exceptions.RegexValidationException;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedAdapter;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.regex.DefaultRegex.RegexType;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultNodeView;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.latex.LatexExporter;
import de.unisiegen.gtitool.ui.logic.MainWindow.ButtonState;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.model.RegexConsoleTableModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.RegexPanelForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.RegexUndoItem;
import de.unisiegen.gtitool.ui.storage.Storage;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * @author Simon Meurer
 */
public final class RegexPanel implements LogicClass < RegexPanelForm >,
    EditorPanel
{

  /**
   * A {@link DocumentListener} for RegexTexts.
   */
  public class TextDocumentListener implements DocumentListener
  {

    /**
     * {@inheritDoc}
     * 
     * @see DocumentListener#changedUpdate(DocumentEvent)
     */
    public void changedUpdate ( @SuppressWarnings ( "unused" ) DocumentEvent e )
    {
      // Nothing to do here.
    }


    /**
     * {@inheritDoc}
     * 
     * @see DocumentListener#insertUpdate(DocumentEvent)
     */
    public void insertUpdate ( DocumentEvent arg0 )
    {
      try
      {
        getUndohistory ().push ( new RegexUndoItem ( getCurrentContent () ) );
        getRedohistory ().clear ();
        setCurrentContent ( arg0.getDocument ().getText ( 0,
            arg0.getDocument ().getLength () ) );
      }
      catch ( BadLocationException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
      updateRedoUndoButtons ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see DocumentListener#removeUpdate(DocumentEvent)
     */
    public void removeUpdate ( DocumentEvent arg0 )
    {
      try
      {
        getUndohistory ().push ( new RegexUndoItem ( getCurrentContent () ) );
        getRedohistory ().clear ();
        setCurrentContent ( arg0.getDocument ().getText ( 0,
            arg0.getDocument ().getLength () ) );
      }
      catch ( BadLocationException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
      updateRedoUndoButtons ();
    }

  }


  /**
   * The current Regex String.
   */
  private String currentContent = ""; //$NON-NLS-1$


  /**
   * Returns the currentContent.
   * 
   * @return The currentContent.
   * @see #currentContent
   */
  public String getCurrentContent ()
  {
    return this.currentContent;
  }


  /**
   * Sets the currentContent.
   * 
   * @param currentContent The currentContent to set.
   * @see #currentContent
   */
  public void setCurrentContent ( String currentContent )
  {
    this.currentContent = currentContent;
  }


  /**
   * The {@link TextDocumentListener}
   */
  private TextDocumentListener docListener = new TextDocumentListener ();


  /**
   * The {@link RegexConsoleTableModel} for the error table.
   */
  private RegexConsoleTableModel errorTableModel;


  /**
   * The {@link File} for this {@link RegexPanel}.
   */
  private File file;


  /**
   * The {@link RegexPanelForm}
   */
  private RegexPanelForm gui;


  /**
   * The {@link JGTIGraph} for this Panel
   */
  private JGTIGraph jGTIGraph;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link MainWindowForm}
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link DefaultRegexModel}.
   */
  private DefaultRegexModel model;


  /**
   * The name of this {@link GrammarPanel}.
   */
  private String name = null;


  /**
   * The redo history implemented as a {@link Stack}
   */
  private Stack < RegexUndoItem > redohistory = new Stack < RegexUndoItem > ();


  /**
   * The undo history implemented as a {@link Stack}
   */
  private Stack < RegexUndoItem > undohistory = new Stack < RegexUndoItem > ();


  /**
   * Returns the undohistory.
   * 
   * @return The undohistory.
   * @see #undohistory
   */
  public Stack < RegexUndoItem > getUndohistory ()
  {
    return this.undohistory;
  }


  /**
   * Returns the redohistory.
   * 
   * @return The redohistory.
   * @see #redohistory
   */
  public Stack < RegexUndoItem > getRedohistory ()
  {
    return this.redohistory;
  }


  /**
   * The {@link RegexConsoleTableModel} for the warning table.
   */
  private RegexConsoleTableModel warningTableModel;


  /**
   * Allocates a new {@link RegexPanel}
   * 
   * @param mainWindowForm The {@link MainWindowForm}
   * @param file The {@link File}
   * @param model The {@link DefaultRegexModel}
   */
  public RegexPanel ( MainWindowForm mainWindowForm, DefaultRegexModel model,
      File file )
  {
    this.mainWindowForm = mainWindowForm;
    this.file = file;
    this.model = model;
    this.gui = new RegexPanelForm ( this );
    if ( this.model.getRegex ().getRegexNode () != null )
    {
      this.gui.styledRegexParserPanel.setText ( this.model.getRegex ()
          .getRegexString () );
    }
    else
    {
      this.gui.styledRegexParserPanel.setText ( this.model
          .getInitialRegexString () );
    }
    if ( getRegex ().getRegexNode () != null )
    {
      getRegex ().getRegexNode ().setShowPositions (
          this.gui.jGTIPanelInfo.isVisible () );
    }
    initializeJGraph ();

    setVisibleConsole ( this.mainWindowForm.getJCheckBoxMenuItemConsole ()
        .getState () );

    this.gui.jGTISplitPaneConsole.setDividerLocation ( 0.5 );
    this.gui.styledRegexParserPanel.setAlphabet ( model.getRegex ()
        .getAlphabet () );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedRegexComment(java.awt.Color)
           */
          @Override
          public void colorChangedRegexComment (
              @SuppressWarnings ( "unused" ) Color newColor )
          {
            getGUI ().styledRegexParserPanel.repaint ();
          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedRegexSelectedNode(java.awt.Color)
           */
          @Override
          public void colorChangedRegexSelectedNode (
              @SuppressWarnings ( "unused" ) Color newColor )
          {
            getJGTIGraph ().repaint ();
          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedRegexSymbol(java.awt.Color)
           */
          @Override
          public void colorChangedRegexSymbol (
              @SuppressWarnings ( "unused" ) Color newColor )
          {
            getGUI ().repaint ();
          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedRegexMarkedNode(java.awt.Color)
           */
          @Override
          public void colorChangedRegexMarkedNode (
              @SuppressWarnings ( "unused" ) Color newColor )
          {
            getGUI ().repaint ();
          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedRegexPosition(java.awt.Color)
           */
          @Override
          public void colorChangedRegexPosition (
              @SuppressWarnings ( "unused" ) Color newColor )
          {
            getGUI ().repaint ();
          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedRegexToolTip(java.awt.Color)
           */
          @Override
          public void colorChangedRegexToolTip (
              @SuppressWarnings ( "unused" ) Color newColor )
          {
            getGUI ().repaint ();
          }


          /**
           * {@inheritDoc}
           * 
           * @see ColorChangedAdapter#colorChangedRegexToken(java.awt.Color)
           */
          @Override
          public void colorChangedRegexToken (
              @SuppressWarnings ( "unused" ) Color newColor )
          {
            getGUI ().repaint ();
          }
        } );

    this.gui.styledRegexParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < RegexNode > ()
        {

          /**
           * {@inheritDoc}
           * 
           * @see ParseableChangedListener#parseableChanged(de.unisiegen.gtitool.core.entities.Entity)
           */
          public void parseableChanged ( RegexNode newEntity )
          {
            changeRegex ( newEntity );
          }
        } );

    initialize ();
    if ( model.getRegex ().getRegexNode () != null )
    {
      this.currentContent = model.getRegex ().getRegexString ();
      changeRegex ( model.getRegex ().getRegexNode () );
    }
    this.gui.styledRegexParserPanel.getDocument ().addDocumentListener (
        this.docListener );
  }


  /**
   * Add a new Error
   * 
   * @param regexException The {@link GrammarException} containing the data
   */
  public final void addError ( RegexException regexException )
  {
    this.errorTableModel.addRow ( regexException );
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
   * Adds a {@link RegexUndoItem}
   * 
   * @param item The {@link RegexUndoItem}
   */
  public void addUndoItem ( RegexUndoItem item )
  {
    this.undohistory.add ( item );
  }


  /**
   * Add a new Error
   * 
   * @param regexException The {@link GrammarException} containing the data
   */
  public final void addWarning ( RegexException regexException )
  {
    this.warningTableModel.addRow ( regexException );
  }


  /**
   * Changes the Regex
   * 
   * @param newRegexNode The new {@link RegexNode}
   */
  public void changeRegex ( RegexNode newRegexNode )
  {
    if ( newRegexNode != null )
    {
      this.model.changeRegexNode ( newRegexNode,
          this.gui.styledRegexParserPanel.getText () );
      if ( !newRegexNode.isInCoreSyntax () )
      {
        getMainWindow ().addButtonState ( ButtonState.ENABLED_TO_CORE_SYNTAX );
      }
      else
      {
        getMainWindow ()
            .removeButtonState ( ButtonState.ENABLED_TO_CORE_SYNTAX );
      }
    }
    else
    {
      getMainWindow ().removeButtonState ( ButtonState.ENABLED_TO_CORE_SYNTAX );
    }
    if ( this.model.getRegex ().getRegexNode () != null )
    {
      this.model.getRegex ().getRegexNode ().setShowPositions (
          this.gui.jGTIPanelInfo.isVisible () );
    }
    initializeJGraph ();
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );
    this.model.createTree ();
    updateRegexNodeInfo ();
    fireModifyStatusChanged ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#clearValidationMessages()
   */
  public void clearValidationMessages ()
  {
    this.errorTableModel.clearData ();
    this.warningTableModel.clearData ();
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
   * @see EditorPanel#getConverter()
   */
  public Converter getConverter ()
  {
    return new ConvertRegexToMachineDialog ( this.mainWindowForm, this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFile()
   */
  public File getFile ()
  {
    return this.file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getFileEnding()
   */
  public String getFileEnding ()
  {
    return "." + RegexType.REGEX.getFileEnding (); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public RegexPanelForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the jGTIGraph.
   * 
   * @return The jGTIGraph.
   * @see #jGTIGraph
   */
  public JGTIGraph getJGTIGraph ()
  {
    return this.jGTIGraph;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getJTabbedPaneConsole()
   */
  public JTabbedPane getJTabbedPaneConsole ()
  {
    return this.gui.jGTITabbedPaneConsole;
  }


  /**
   * Returns the {@link MainWindow}.
   * 
   * @return The {@link MainWindow}.
   * @see #mainWindowForm
   */
  public final MainWindow getMainWindow ()
  {
    return this.mainWindowForm.getLogic ();
  }


  /**
   * Returns the mainWindowForm.
   * 
   * @return The mainWindowForm.
   * @see #mainWindowForm
   */
  public MainWindowForm getMainWindowForm ()
  {
    return this.mainWindowForm;
  }


  /**
   * Returns the {@link DefaultModel}.
   * 
   * @return The {@link DefaultModel}.
   * @see #model
   */
  public DefaultModel getModel ()
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
  public JPanel getPanel ()
  {
    return this.gui;
  }


  /**
   * Returns the regex.
   * 
   * @return The regex.
   */
  public DefaultRegex getRegex ()
  {
    return this.model.getRegex ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#handleExchange()
   */
  public void handleExchange ()
  {
    this.model.setActualRegexString ( this.gui.styledRegexParserPanel
        .getText () );
    ExchangeDialog exchangeDialog = new ExchangeDialog ( this.mainWindowForm
        .getLogic (), this.model.getElement (), this.file );
    exchangeDialog.show ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#handleRedo()
   */
  public void handleRedo ()
  {
    if ( isRedoAble () )
    {
      performRedo ();
      this.gui.repaint ();
      updateRedoUndoButtons ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#handleSave()
   */
  public File handleSave ()
  {
    if ( this.file == null )
    {
      return handleSaveAs ();
    }
    try
    {
      this.model.setActualRegexString ( this.gui.styledRegexParserPanel
          .getText () );
      Storage.getInstance ().store ( this.model, this.file );
    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "RegexPanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    this.model.setInitialRegexString ( this.gui.styledRegexParserPanel
        .getText () );
    return this.file;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#handleSaveAs()
   */
  public File handleSaveAs ()
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
              + RegexType.REGEX.getFileEnding () ) )
          {
            return true;
          }
          return false;
        }


        @Override
        public String getDescription ()
        {
          return Messages.getString ( "NewDialog." //$NON-NLS-1$
              + RegexType.REGEX.toString () ) + " (*." //$NON-NLS-1$
              + RegexType.REGEX.getFileEnding () + ")"; //$NON-NLS-1$
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
                "RegexPanel.FileExists", saveDialog.getSelectedFile () //$NON-NLS-1$
                    .getName () ), Messages.getString ( "RegexPanel.Save" ), //$NON-NLS-1$
            true, false, true, false, false );
        confirmDialog.show ();
        if ( confirmDialog.isNotConfirmed () )
        {
          return null;
        }
      }

      String filename = saveDialog.getSelectedFile ().toString ().matches (
          ".+\\." + RegexType.REGEX.getFileEnding () ) ? saveDialog //$NON-NLS-1$
          .getSelectedFile ().toString () : saveDialog.getSelectedFile ()
          .toString ()
          + "." + RegexType.REGEX.getFileEnding (); //$NON-NLS-1$

      this.model.setActualRegexString ( this.gui.styledRegexParserPanel
          .getText () );
      Storage.getInstance ().store ( this.model, new File ( filename ) );

      PreferenceManager.getInstance ().setWorkingPath (
          saveDialog.getCurrentDirectory ().getAbsolutePath () );
      this.file = new File ( filename );
    }
    catch ( StoreException e )
    {
      InfoDialog infoDialog = new InfoDialog ( this.mainWindowForm, e
          .getMessage (), Messages.getString ( "RegexPanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    this.model.setInitialRegexString ( this.gui.styledRegexParserPanel
        .getText () );
    return this.file;
  }


  /**
   * Handles the to Core Syntax
   */
  public void handleToCoreSyntaxButtonClicked ()
  {
    if ( this.errorTableModel.getRowCount () == 0 )
    {
      DefaultRegex newRegex = new DefaultRegex ( this.model.getRegex ()
          .getAlphabet () );
      newRegex.setRegexNode ( this.model.getRegex ().getRegexNode ()
          .toCoreSyntax ( true ), this.model.getRegex ().getRegexNode ()
          .toCoreSyntax ( true ).toPrettyString ().toString () );

      getMainWindow ().handleNew ( new DefaultRegexModel ( newRegex ) );
    }
  }


  /**
   * Handle the DFA button clicked
   * 
   * @param evt Unused
   */
  public void handleToDFAButton ( @SuppressWarnings ( "unused" ) ActionEvent evt )
  {
    if ( this.errorTableModel.getRowCount () == 0 )
    {
      ConvertRegexToMachineDialog converter = new ConvertRegexToMachineDialog (
          this.mainWindowForm, this );
      converter.convert ( RegexType.REGEX, MachineType.DFA, false );
    }
  }


  /**
   * Handles Click on the toLatexButton
   */
  public void handleToLatexButtonClicked ()
  {
    if ( this.errorTableModel.getRowCount () == 0 )
    {
      FileFilter ff = new FileFilter ()
      {

        @Override
        public boolean accept ( File acceptedFile )
        {
          if ( acceptedFile.isDirectory () )
          {
            return true;
          }
          if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\." //$NON-NLS-1$
              + "tex" ) ) //$NON-NLS-1$
          {
            return true;
          }
          return false;
        }


        @Override
        public String getDescription ()
        {
          return Messages.getString ( "Latex.FileDescription" ) //$NON-NLS-1$
              + " (*.tex)"; //$NON-NLS-1$
        }

      };
      SaveDialog sd = new SaveDialog ( getMainWindowForm (), PreferenceManager
          .getInstance ().getWorkingPath (), ff, ff );
      sd.show ();
      if ( sd.getSelectedFile () != null )
      {
        String filename = sd.getSelectedFile ().toString ().matches (
            ".+\\.tex" ) ? sd //$NON-NLS-1$
            .getSelectedFile ().toString () : sd.getSelectedFile ().toString ()
            + ".tex"; //$NON-NLS-1$
        LatexExporter.buildLatexFile ( this.model.toLatexString (), new File (
            filename ) );
      }
    }
  }


  /**
   * Handles the Click on the NFA Button
   * 
   * @param evt
   */
  public void handleToNFAButton ( @SuppressWarnings ( "unused" ) ActionEvent evt )
  {
    if ( this.errorTableModel.getRowCount () == 0 )
    {
      ConvertRegexToMachineDialog converter = new ConvertRegexToMachineDialog (
          this.mainWindowForm, this );
      converter.convert ( RegexType.REGEX, MachineType.ENFA, false );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#handleToolbarEditDocument()
   */
  public void handleToolbarEditDocument ()
  {

    AlphabetDialog alphabetDialog = new AlphabetDialog ( this.mainWindowForm,
        this, getRegex () );
    alphabetDialog.show ();
    initializeAlphabet ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#handleUndo()
   */
  public void handleUndo ()
  {
    if ( isUndoAble () )
    {
      performUndo ();
      this.gui.repaint ();
      updateRedoUndoButtons ();
    }
  }


  /**
   * Initialize
   */
  private void initialize ()
  {
    this.errorTableModel = new RegexConsoleTableModel ();
    this.warningTableModel = new RegexConsoleTableModel ();
    this.gui.jGTITableWarnings.setModel ( this.warningTableModel );
    this.gui.jGTITableWarnings.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableWarnings.getTableHeader ().setReorderingAllowed ( false );
    this.gui.jGTITableErrors.setModel ( this.errorTableModel );
    this.gui.jGTITableErrors.setColumnModel ( new ConsoleColumnModel () );
    this.gui.jGTITableErrors.getTableHeader ().setReorderingAllowed ( false );
    initializeAlphabet ();
    initializeSecondView ();
    this.gui.jGTITableWarnings
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableWarnings.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged (
              @SuppressWarnings ( "unused" ) ListSelectionEvent event )
          {
            // Nothing to do here
          }
        } );
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );
  }


  /**
   * Initializes the Alphabet
   */
  public void initializeAlphabet ()
  {
    this.gui.styledRegexParserPanel.parse ();
    this.gui.styledRegexAlphabetParserPanel.setText ( this.model.getRegex ()
        .getAlphabet ().toClassPrettyString () );
  }


  /**
   * Initializes the JGraph
   */
  public void initializeJGraph ()
  {
    this.model.initializeGraph ();
    this.jGTIGraph = this.model.getJGTIGraph ();
    this.jGTIGraph.setMoveable ( false );
    this.jGTIGraph.getSelectionModel ().setSelectionMode (
        GraphSelectionModel.SINGLE_GRAPH_SELECTION );
    this.jGTIGraph.addGraphSelectionListener ( new GraphSelectionListener ()
    {

      /**
       * @see org.jgraph.event.GraphSelectionListener#valueChanged(org.jgraph.event.GraphSelectionEvent)
       */
      public void valueChanged (
          @SuppressWarnings ( "unused" ) GraphSelectionEvent e )
      {
        updateRegexNodeInfo ();
      }
    } );
  }


  /**
   * Initializes the second view .
   */
  private final void initializeSecondView ()
  {
    MouseListener mouseListener = new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        RegexPanel.this.mainWindowForm.getLogic ()
            .handleSecondViewMouseReleased ( event );
      }
    };

    MouseMotionListener mouseMotionListener = new MouseMotionAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseDragged ( MouseEvent event )
      {
        RegexPanel.this.mainWindowForm.getLogic ()
            .handleSecondViewMouseReleased ( event );
      }
    };

    this.jGTIGraph.addMouseListener ( mouseListener );
    this.jGTIGraph.addMouseMotionListener ( mouseMotionListener );
    this.gui.jGTIScrollPaneGraph.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneGraph.getVerticalScrollBar ().addMouseListener (
        mouseListener );

    this.gui.styledRegexAlphabetParserPanel.addMouseListener ( mouseListener );
    this.gui.styledRegexParserPanel.addMouseListener ( mouseListener );

    this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jGTITextAreaFollowpos
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jGTITextAreaLastpos
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jGTITextAreaNullable
        .addMouseListener ( mouseListener );

    this.gui.regexNodeInfoPanel.jScrollPaneFirstpos.getHorizontalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jScrollPaneFirstpos.getVerticalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jScrollPaneFollowpos.getHorizontalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jScrollPaneFollowpos.getVerticalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jScrollPaneLastpos.getHorizontalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jScrollPaneLastpos.getVerticalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jScrollPaneNullable.getHorizontalScrollBar ()
        .addMouseListener ( mouseListener );
    this.gui.regexNodeInfoPanel.jScrollPaneNullable.getVerticalScrollBar ()
        .addMouseListener ( mouseListener );

    this.gui.jGTIScrollPaneNodeInfo.getHorizontalScrollBar ().addMouseListener (
        mouseListener );
    this.gui.jGTIScrollPaneNodeInfo.getVerticalScrollBar ().addMouseListener (
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
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public boolean isModified ()
  {
    if ( this.model.getInitialRegexString () == null )
    {
      return true;
    }
    return !this.model.getInitialRegexString ().equals (
        this.gui.styledRegexParserPanel.getText () )
        || this.model.getRegex ().getAlphabet ().isModified ()
        || this.file == null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#isRedoAble()
   */
  public boolean isRedoAble ()
  {
    return !this.redohistory.isEmpty ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#isUndoAble()
   */
  public boolean isUndoAble ()
  {
    return !this.undohistory.isEmpty ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 0, Messages
        .getString ( "RegexPanel.Error" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPaneConsole.setTitleAt ( 1, Messages
        .getString ( "RegexPanel.Warning" ) ); //$NON-NLS-1$
    this.gui.jGTILabelAlphabet.setText ( Messages
        .getString ( "RegexPanel.AlphabetTitle" ) ); //$NON-NLS-1$
    this.gui.jGTILabelGraph.setText ( Messages
        .getString ( "RegexPanel.GraphTitle" ) ); //$NON-NLS-1$
    this.gui.jGTILabelInfo.setText ( Messages
        .getString ( "RegexPanel.InformationTitle" ) ); //$NON-NLS-1$
    this.gui.jGTILabelRegex.setText ( Messages
        .getString ( "RegexPanel.RegexTitle" ) ); //$NON-NLS-1$
  }


  /**
   * Preforms the redo step
   */
  private void performRedo ()
  {
    try
    {
      Document document = this.gui.styledRegexParserPanel.getDocument ();
      document.removeDocumentListener ( this.docListener );
      RegexUndoItem item = this.redohistory.peek ();
      if ( item.getRegexString () != null )
      {
        this.undohistory.push ( new RegexUndoItem ( document.getText ( 0,
            document.getLength () ) ) );
        document.remove ( 0, document.getLength () );
        document.insertString ( 0, this.redohistory.pop ().getRegexString (),
            null );
      }
      else
      {
        this.undohistory
            .push ( new RegexUndoItem ( new DefaultRegexAlphabet (
                this.gui.styledRegexAlphabetParserPanel.getParsedObject ()
                    .get () ) ) );
        DefaultRegexAlphabet a = this.redohistory.pop ().getLastAlphabet ();
        performAlphabetChange ( getRegex ().getAlphabet (), a );
        this.gui.styledRegexAlphabetParserPanel.setText ( a );
      }
      document.addDocumentListener ( this.docListener );

    }
    catch ( BadLocationException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * Performs the undo step
   */
  private void performUndo ()
  {
    try
    {
      Document document = this.gui.styledRegexParserPanel.getDocument ();
      document.removeDocumentListener ( this.docListener );
      String doctext = document.getText ( 0, document.getLength () );
      RegexUndoItem item = this.undohistory.peek ();
      if ( item.getRegexString () != null )
      {
        String historytext = this.undohistory.pop ().getRegexString ();
        document.remove ( 0, document.getLength () );
        document.insertString ( 0, historytext, null );
        this.redohistory.add ( new RegexUndoItem ( doctext ) );
        this.currentContent = historytext;
      }
      else
      {
        DefaultRegexAlphabet a = this.undohistory.pop ().getLastAlphabet ();
        this.redohistory
            .add ( new RegexUndoItem ( new DefaultRegexAlphabet (
                this.gui.styledRegexAlphabetParserPanel.getParsedObject ()
                    .get () ) ) );
        performAlphabetChange ( getRegex ().getAlphabet (), a );
        this.gui.styledRegexAlphabetParserPanel.setText ( a );
      }
      document.addDocumentListener ( this.docListener );
    }
    catch ( BadLocationException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * Preforms the {@link Alphabet} change.
   * 
   * @param oldAlphabet The old {@link Alphabet}.
   * @param newAlphabet The new {@link Alphabet}.
   */
  public final void performAlphabetChange ( Alphabet oldAlphabet,
      Alphabet newAlphabet )
  {
    TreeSet < Symbol > symbolsToAdd = new TreeSet < Symbol > ();
    TreeSet < Symbol > symbolsToRemove = new TreeSet < Symbol > ();
    for ( Symbol current : newAlphabet )
    {
      if ( !oldAlphabet.contains ( current ) )
      {
        symbolsToAdd.add ( current );
      }
    }
    for ( Symbol current : oldAlphabet )
    {
      if ( !newAlphabet.contains ( current ) )
      {
        symbolsToRemove.add ( current );
      }
    }
    try
    {
      oldAlphabet.add ( symbolsToAdd );
      oldAlphabet.remove ( symbolsToRemove );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    fireModifyStatusChanged ( false );
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
  public void resetModify ()
  {
    this.model.setInitialRegexString ( this.gui.styledRegexParserPanel
        .getText () );
    this.model.getRegex ().getAlphabet ().resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#setName(java.lang.String)
   */
  public void setName ( String name )
  {
    this.name = name;
  }


  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#setVisibleConsole(boolean)
   */
  public void setVisibleConsole ( boolean visible )
  {
    if ( visible )
    {
      this.gui.jGTISplitPaneConsole
          .setRightComponent ( this.gui.jGTITabbedPaneConsole );
      this.gui.jGTISplitPaneConsole.setDividerSize ( 3 );
      this.gui.jGTISplitPaneConsole.setDividerLocation ( this.mainWindowForm
          .getHeight () - 322 );
    }
    else
    {
      this.gui.jGTISplitPaneConsole.setRightComponent ( null );
      this.gui.jGTISplitPaneConsole.setDividerSize ( 0 );
    }
  }


  /**
   * Updates the Redo and Undo button
   */
  public void updateRedoUndoButtons ()
  {
    if ( isUndoAble () )
    {
      this.mainWindowForm.getLogic ()
          .addButtonState ( ButtonState.ENABLED_UNDO );
    }
    else
    {
      this.mainWindowForm.getLogic ().removeButtonState (
          ButtonState.ENABLED_UNDO );
    }
    if ( isRedoAble () )
    {
      this.mainWindowForm.getLogic ()
          .addButtonState ( ButtonState.ENABLED_REDO );
    }
    else
    {
      this.mainWindowForm.getLogic ().removeButtonState (
          ButtonState.ENABLED_REDO );
    }
  }


  /**
   * Updates the RegexNode infos
   */
  protected void updateRegexNodeInfo ()
  {
    if ( this.jGTIGraph.getSelectionCell () instanceof DefaultNodeView )
    {
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setEnabled ( true );
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setEnabled ( true );
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setEnabled ( true );

      RegexNode node = ( ( DefaultNodeView ) this.jGTIGraph.getSelectionCell () )
          .getNode ();
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setText ( String
          .valueOf ( node.nullable () ) );
      String firstpos = "{"; //$NON-NLS-1$
      for ( LeafNode n : node.firstPos () )
      {
        firstpos += n.getPosition ();
        if ( node.firstPos ().indexOf ( n ) != node.firstPos ().size () - 1 )
        {
          firstpos += "; "; //$NON-NLS-1$
        }
      }
      firstpos += "}"; //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setText ( firstpos );
      String lastpos = "{"; //$NON-NLS-1$
      for ( LeafNode n : node.lastPos () )
      {
        lastpos += n.getPosition ();
        if ( node.lastPos ().indexOf ( n ) != node.lastPos ().size () - 1 )
        {
          lastpos += "; "; //$NON-NLS-1$
        }
      }
      lastpos += "}"; //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setText ( lastpos );
      String followpos = "{"; //$NON-NLS-1$
      if ( node instanceof LeafNode )
      {
        this.gui.regexNodeInfoPanel.jScrollPaneFollowpos.setVisible ( true );
        this.gui.regexNodeInfoPanel.jGTILabelFollowpos.setVisible ( true );
        LeafNode leaf = ( LeafNode ) node;
        boolean first = true;
        for ( Integer n : this.model.getRegex ().followPos (
            leaf.getPosition () ) )
        {
          if ( !first )
          {
            followpos += "; "; //$NON-NLS-1$
          }
          followpos += n;
          first = false;
        }
        followpos += "}"; //$NON-NLS-1$
        this.gui.regexNodeInfoPanel.jGTITextAreaFollowpos.setText ( followpos );
      }
      else
      {
        this.gui.regexNodeInfoPanel.jScrollPaneFollowpos.setVisible ( false );
        this.gui.regexNodeInfoPanel.jGTILabelFollowpos.setVisible ( false );
      }
    }
    else
    {
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaFollowpos.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setText ( "" ); //$NON-NLS-1$
      this.gui.regexNodeInfoPanel.jGTITextAreaNullable.setEnabled ( false );
      this.gui.regexNodeInfoPanel.jGTITextAreaFirstpos.setEnabled ( false );
      this.gui.regexNodeInfoPanel.jGTITextAreaLastpos.setEnabled ( false );
      this.gui.regexNodeInfoPanel.jScrollPaneFollowpos.setVisible ( false );
      this.gui.regexNodeInfoPanel.jGTILabelFollowpos.setVisible ( false );
    }
  }


  /**
   * Validates the Panel
   * 
   * @throws RegexValidationException When validation goes wrong
   */
  public void validate () throws RegexValidationException
  {
    ArrayList < RegexException > list = new ArrayList < RegexException > ();
    if ( this.gui.styledRegexParserPanel.parse () == null )
    {
      list.add ( new RegexParseException () );
    }
    try
    {
      getRegex ().validate ();
    }
    catch ( RegexValidationException exc )
    {
      list.addAll ( exc.getRegexException () );
    }
    if ( !list.isEmpty () )
    {
      throw new RegexValidationException ( list );
    }
  }

}

package de.unisiegen.gtitool.ui.logic;


import java.awt.event.ActionEvent;
import java.io.File;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.regex.Regex;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.exceptions.RegexException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.regex.DefaultRegex.RegexType;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.convert.Converter;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.latex.LatexExporter;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.ConsoleColumnModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.model.RegexConsoleTableModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.RegexPanelForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.redoundo.RegexChangedItem;
import de.unisiegen.gtitool.ui.storage.Storage;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * @author Simon Meurer
 */
public final class RegexPanel implements LogicClass < RegexPanelForm >,
    EditorPanel
{

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
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * The name of this {@link GrammarPanel}.
   */
  private String name = null;


  /**
   * The {@link RedoUndoHandler}
   */
  private RedoUndoHandler redoUndoHandler;


  /**
   * The {@link Regex}
   */
  private DefaultRegex regex;


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
    this.model.initializeGraph ();
    this.jGTIGraph = this.model.getJGTIGraph ();
    this.jGTIGraph.setEditable ( false );
    this.regex = model.getRegex ();
    this.gui = new RegexPanelForm ( this );

    this.redoUndoHandler = new RedoUndoHandler ( this.mainWindowForm );

    this.model.setRedoUndoHandler ( this.redoUndoHandler );
    setVisibleConsole ( this.mainWindowForm.getJCheckBoxMenuItemConsole ()
        .getState () );

    this.gui.jGTISplitPaneConsole.setDividerLocation ( 0.5 );
    if ( this.regex.getRegexNode () != null )
    {
      this.gui.jGTIButtonCoreSyntax.setEnabled ( !this.regex.getRegexNode ().isInCoreSyntax () );
      setRegexInitial ( this.regex.getRegexNode () );
    }

    this.gui.styledRegexParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < RegexNode > ()
        {

          public void parseableChanged ( RegexNode newEntity )
          {
            if ( newEntity != null )
            {
              changeRegex ( newEntity, true );
              getGUI ().jGTIButtonCoreSyntax.setEnabled ( !newEntity.isInCoreSyntax () );
            }
          }

        } );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );

    initialize ();
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
    this.model.addModifyStatusChangedListener ( listener );
  }


  /**
   * Changes the Regex
   * 
   * @param newRegexNode The new {@link RegexNode}
   */
  public void changeRegex ( RegexNode newRegexNode, boolean addRedoUndoItem )
  {
    RegexNode old = this.regex.getRegexNode ();
    if ( old != null && old.equals ( newRegexNode ) )
    {
      return;
    }
    if ( addRedoUndoItem )
    {
      this.regex.setRegexNode ( newRegexNode, true );
      this.redoUndoHandler.addItem ( new RegexChangedItem ( this, this.regex
          .getRegexNode (), old ) );

    }
    else
    {
      this.regex.changeRegexNode ( newRegexNode );
    }
    this.model.initializeGraph ();
    this.jGTIGraph = this.model.getJGTIGraph ();
    this.jGTIGraph.setEditable ( false );
    this.jGTIGraph.setEnabled ( false );
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );

    this.model.createTree ();
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#clearValidationMessages()
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getConverter()
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#getJTabbedPaneConsole()
   */
  public JTabbedPane getJTabbedPaneConsole ()
  {
    return this.gui.jGTITabbedPaneConsole;
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
   * @see #regex
   */
  public DefaultRegex getRegex ()
  {
    return this.regex;
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleExchange()
   */
  public void handleExchange ()
  {
    ExchangeDialog exchangeDialog = new ExchangeDialog ( this.mainWindowForm
        .getLogic (), this.model.getElement (), this.file );
    exchangeDialog.show ();
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleRedo()
   */
  public void handleRedo ()
  {
    this.redoUndoHandler.redo ();
    this.gui.repaint ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleSave()
   */
  public File handleSave ()
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
          .getMessage (), Messages.getString ( "RegexPanel.Save" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    resetModify ();
    fireModifyStatusChanged ( false );
    return this.file;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleSaveAs()
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
    return this.file;
  }


  /**
   * TODO
   * 
   * @param evt
   */
  public void handleToCoreSyntaxButtonClicked ( @SuppressWarnings ( "unused" )
  ActionEvent evt )
  {
    DefaultRegex newRegex = new DefaultRegex ( this.regex.getAlphabet (),
        this.regex.getRegexString () );
    newRegex.changeRegexNode ( this.regex.getRegexNode ().toCoreSyntax () );

    EditorPanel newEditorPanel = new RegexPanel ( this.mainWindowForm,
        new DefaultRegexModel ( newRegex ), null );
    TreeSet < String > nameList = new TreeSet < String > ();
    int count = 0;
    for ( EditorPanel current : this.mainWindowForm.getJGTIMainSplitPane ()
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( current.getFile () == null )
      {
        nameList.add ( current.getName () );
        count++ ;
      }
    }

    String newName = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
        + "." + RegexType.REGEX.getFileEnding (); //$NON-NLS-1$
    while ( nameList.contains ( newName ) )
    {
      count++ ;
      newName = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + "." + RegexType.REGEX.getFileEnding (); //$NON-NLS-1$
    }

    newEditorPanel.setName ( newName );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .addEditorPanel ( newEditorPanel );
    newEditorPanel
        .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .setSelectedEditorPanel ( newEditorPanel );
  }


  /**
   * TODO
   * 
   * @param evt
   */
  public void handleToLatexButtonClicked ( ActionEvent evt )
  {
    FileFilter ff = new FileFilter ()
    {

      @Override
      public boolean accept ( File acceptedFile )
      {
        if ( acceptedFile.isDirectory () )
        {
          return false;
        }
        if ( acceptedFile.getName ().toLowerCase ().matches ( ".+\\." //$NON-NLS-1$
            + "tex" ) )
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
      String filename = sd.getSelectedFile ().toString ().matches ( ".+\\.tex" ) ? sd //$NON-NLS-1$
          .getSelectedFile ().toString ()
          : sd.getSelectedFile ().toString () + ".tex"; //$NON-NLS-1$
      LatexExporter.buildLatexFile ( this.model.toLatexString (), new File (
          filename ) );
    }
  }


  public void handleToNFAButton ( ActionEvent evt )
  {
    ConvertRegexToMachineDialog converter = new ConvertRegexToMachineDialog (
        this.mainWindowForm, this );
    converter.convert ( RegexType.REGEX, MachineType.ENFA, false );

  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleToolbarEditDocument()
   */
  public void handleToolbarEditDocument ()
  {
    //TODO
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#handleUndo()
   */
  public void handleUndo ()
  {
    this.redoUndoHandler.undo ();
    this.gui.repaint ();
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
    this.gui.styledRegexAlphabetParserPanel
        .setText ( this.regex.getAlphabet () );
    this.gui.jGTITableWarnings
        .setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.gui.jGTITableWarnings.getSelectionModel ().addListSelectionListener (
        new ListSelectionListener ()
        {

          public void valueChanged ( ListSelectionEvent event )
          {
            // TODO
          }
        } );
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public boolean isModified ()
  {
    return this.model.isModified ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isRedoAble()
   */
  public boolean isRedoAble ()
  {
    return this.redoUndoHandler.isRedoAble ();
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#isUndoAble()
   */
  public boolean isUndoAble ()
  {
    return this.redoUndoHandler.isUndoAble ();
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
    //TODO
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.model.removeModifyStatusChangedListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public void resetModify ()
  {
    this.model.resetModify ();
  }


  /**
   * TODO
   * 
   * @param name
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#setName(java.lang.String)
   */
  public void setName ( String name )
  {
    this.name = name;
  }


  public void setRegexInitial ( RegexNode newRegexNode )
  {
    this.regex.changeRegexNode ( newRegexNode );
    this.gui.styledRegexParserPanel.setText ( newRegexNode.toPrettyString ()
        .toString () );
    this.model.initializeGraph ();
    this.jGTIGraph = this.model.getJGTIGraph ();
    this.jGTIGraph.setEditable ( false );
    this.jGTIGraph.setEnabled ( false );
    this.gui.jGTIScrollPaneGraph.setViewportView ( this.jGTIGraph );

    this.model.createTree ();
  }


  /**
   * TODO
   * 
   * @param visible
   * @see de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel#setVisibleConsole(boolean)
   */
  public void setVisibleConsole ( boolean visible )
  {
    this.gui.jGTIPanelConsole.setVisible ( visible );
  }

}

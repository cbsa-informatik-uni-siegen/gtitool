package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.GrammarColumnModel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;
import de.unisiegen.gtitool.ui.popup.ProductionPopupMenu;


/**
 * @author Benjamin Mies
 * @version $Id$
 */
public class GrammarPanel implements EditorPanel
{
  
  /**
   * The {@link GrammarPanelForm}.
   */
  private GrammarPanelForm gui;


  /**
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;


  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * The name of this {@link MachinePanel}.
   */
  private String name = null;


  /**
   * The {@link File} for this {@link MachinePanel}.
   */
  private File file;
  
  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link MainWindowForm}
   */
  private MainWindowForm parent;


  /**
   * Allocates a new {@link GrammarPanel}
   * 
   * @param parent The parent frame
   * @param model The {@link DefaultGrammarModel}.
   */
  public GrammarPanel ( MainWindowForm parent, DefaultGrammarModel model )
  {
    this.parent = parent;
    this.model = model;
    this.gui = new GrammarPanelForm ();
    this.gui.setGrammarPanel ( this );
    this.grammar = this.model.getGrammar ();
    this.gui.jGTITable.setModel ( model );
    this.gui.jGTITable.setColumnModel ( new GrammarColumnModel () );

  }

  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
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
    return "." + this.grammar.getGrammarType ().toLowerCase (); //$NON-NLS-1$
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.EditorPanel#getGui()
   */
  public EditorPanelForm getGui ()
  {
    return this.gui;
  }

  /**
   * {@inheritDoc}
   * 
   * @see EditorPanel#getName()
   */
  public String getName ()
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
   * Handles the {@link Exchange}.
   */
  public void handleExchange ()
  {
    // TODO implement me
  }

  /**
   * Handle redo button pressed
   */
  public void handleRedo ()
  {
    // TODO implement me
  }

  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public File handleSave ()
  {
    // TODO implement me
    return null;
  }

  /**
   * Handle save as operation
   * 
   * @return filename
   */
  public File handleSaveAs ()
  {
    // TODO implement me
    return null;
  }

  /**
   * Handle Toolbar Alphabet button action event
   */
  public void handleToolbarEditDocument ()
  {
    // TODO implement me
  }

  /**
   * Handle undo button pressed
   */
  public void handleUndo ()
  {
    // TODO implement me
  }

  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public boolean isModified ()
  {
    // TODO implement me
    return false;
  }

  /**
   * Signals if this panel is redo able
   * 
   * @return true, if is redo able, false else
   */
  public boolean isRedoAble ()
  {
    // TODO implement me
    return false;
  }

  /**
   * Signals if this panel is undo able
   * 
   * @return true, if is undo able, false else
   */
  public boolean isUndoAble ()
  {
    // TODO implement me
    return false;
  }

  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
    // TODO implement me
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void removeModifyStatusChangedListener (
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
    // TODO implement me
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
   * Handle mouse button event for the JTable.
   * 
   * @param event The {@link MouseEvent}.
   */
  public void handleTableMouseClickedEvent ( MouseEvent event )
  {
    if ( event.getButton () != MouseEvent.BUTTON3 )
      return;
    Production selectedProduction = null;
    int index = this.gui.jGTITable.rowAtPoint ( event.getPoint () );
    this.gui.jGTITable.getSelectionModel ()
        .setSelectionInterval ( index, index );

    if ( this.model.getRowCount () > 0 && index != -1 )
    {
      selectedProduction = this.model.getProductionAt ( index );
    }
    ProductionPopupMenu popupmenu = new ProductionPopupMenu ( this.gui,
        this.model, selectedProduction );
    popupmenu.show ( ( Component ) event.getSource (), event.getX (), event
        .getY () );
  }

  /**
   * Add a new {@link Production}.
   * 
   * @param production the new Production to add.
   */
  public void addProduction ( Production production )
  {
    this.model.addProduction ( production );
  }


  /**
   * Returns the parent frame.
   * 
   * @return the parent frame.
   */
  public JFrame getParent ()
  {
    return this.parent;
  }
}

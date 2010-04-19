package de.unisiegen.gtitool.ui.convert;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.StateMachine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.MachinePanel;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * base AbstractConvertGrammar
 */
public abstract class AbstractConvertGrammar implements Converter
{

  /**
   * The {@link Grammar}.
   */
  private final Grammar grammar;


  /**
   * The {@link MachineType}.
   */
  private MachineType machineType;


  /**
   * The {@link MainWindowForm}.
   */
  private final MainWindowForm mainWindowForm;


  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * Allocates a new {@link AbstractConvertGrammar}
   * 
   * @param mwf the {@link MainWindowForm}
   * @param grammar the {@link Grammar}
   */
  public AbstractConvertGrammar ( final MainWindowForm mwf,
      final Grammar grammar )
  {
    this.mainWindowForm = mwf;
    this.grammar = grammar;
  }


  /**
   * Allocates a new {@link AbstractConvertGrammar}
   * 
   * @param mainWindowForm the {@link MainWindowForm}
   * @param grammar the {@link Grammar}
   * @param alphabet the {@link Alphabet}
   */
  public AbstractConvertGrammar ( final MainWindowForm mainWindowForm,
      final Grammar grammar, final Alphabet alphabet )
  {
    this.mainWindowForm = mainWindowForm;
    this.grammar = grammar;
    this.alphabet = alphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.Converter#convert(de.unisiegen.gtitool.core.entities.InputEntity.EntityType,
   *      de.unisiegen.gtitool.core.entities.InputEntity.EntityType, boolean,
   *      boolean)
   */
  public void convert (
      @SuppressWarnings ( "unused" ) EntityType fromEntityType,
      EntityType toEntityType, @SuppressWarnings ( "unused" ) boolean complete,
      @SuppressWarnings ( "unused" ) boolean cb )
  {
    if ( ! ( toEntityType instanceof MachineType ) )
      throw new IllegalArgumentException ( "unsopported to entity type: " //$NON-NLS-1$
          + toEntityType );

    this.machineType = ( MachineType ) toEntityType;
    createMachine ();
    addPanelToView ();
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * set a new alphabet
   * 
   * @param alphabet the {@link Alphabet}
   */
  protected final void setAlphabet ( final Alphabet alphabet )
  {
    this.alphabet = alphabet;
  }


  /**
   * Returns the {@link Grammar}.
   * 
   * @return The {@link Grammar}.
   * @see #grammar
   */
  public final Grammar getGrammar ()
  {
    return this.grammar;
  }


  /**
   * Returns the newPanel.
   * 
   * @return The newPanel.
   */
  public abstract MachinePanel getNewPanel ();


  /**
   * sets the {@link MachinePanel}
   * 
   * @param panel the {@link MachinePanel}
   */
  protected abstract void setNewPanel ( final MachinePanel panel );


  /**
   * retrieves the {@link MachineType}
   * 
   * @return the {@link MachineType}
   */
  protected MachineType getMachineType ()
  {
    return this.machineType;
  }


  /**
   * retrieves the {@link MainWindowForm}
   * 
   * @return the {@link MainWindowForm}
   */
  protected MainWindowForm getMainWindowForm ()
  {
    return this.mainWindowForm;
  }


  /**
   * Create a new {@link StateMachine}.
   */
  protected abstract void createMachine ();


  /**
   * create a new {@link MachinePanel}
   * 
   * @param machine the {@link Machine}
   */
  protected abstract void createMachinePanel ( Machine machine );


  /**
   * Add the new {@link MachinePanel} to the {@link MainWindowForm}.
   */
  protected final void addPanelToView ()
  {
    TreeSet < String > nameList = new TreeSet < String > ();
    int count = 0;
    for ( EditorPanel current : this.mainWindowForm.getJGTIMainSplitPane ()
        .getJGTIEditorPanelTabbedPane () )
      if ( current.getFile () == null )
      {
        nameList.add ( current.getName () );
        count++ ;
      }

    String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
        + "." + this.machineType.toString ().toLowerCase (); //$NON-NLS-1$
    while ( nameList.contains ( name ) )
    {
      count++ ;
      name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + this.machineType.toString ();
    }

    getNewPanel ().setName ( name );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .addEditorPanel ( getNewPanel () );
    getNewPanel ().addModifyStatusChangedListener (
        this.mainWindowForm.getLogic ().getModifyStatusChangedListener () );
    this.mainWindowForm.getJGTIMainSplitPane ().getJGTIEditorPanelTabbedPane ()
        .setSelectedEditorPanel ( getNewPanel () );
  }
}

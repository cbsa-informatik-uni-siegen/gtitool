package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.grammars.rg.DefaultRG;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.machines.nfa.DefaultNFA;
import de.unisiegen.gtitool.core.machines.pda.DefaultPDA;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;


/**
 * The{@link NewDialog}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class NewDialog
{

  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private MainWindowForm mainWindowForm;


  /**
   * The {@link NewDialogChoice}.
   */
  private NewDialogChoice newDialogChoice;


  /**
   * The {@link NewDialogGrammarChoice}.
   */
  private NewDialogGrammarChoice grammarChoice;


  /**
   * The {@link NewDialogMachineChoice}.
   */
  private NewDialogMachineChoice machineChoice;


  /**
   * The {@link NewDialogAlphabet}.
   */
  private NewDialogAlphabet newDialogAlphabet;


  /**
   * The {@link NewDialogTerminal}
   */
  private NewDialogTerminal newDialogTerminal;


  /**
   * The {@link GridBagConstraints} to add the body panel.
   */
  private GridBagConstraints gridBagConstraints;


  /**
   * The new created panel.
   */
  private EditorPanel newPanel;


  /**
   * Creates a new {@link NewDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   */
  public NewDialog ( MainWindowForm parent )
  {
    this.mainWindowForm = parent;
    initialize ();
  }


  /**
   * Getter for the new created editor panel
   * 
   * @return The {@link EditorPanel}
   */
  public final EditorPanel getEditorPanel ()
  {
    return this.newPanel;
  }


  /**
   * Getter for the gui of this logic class
   * 
   * @return The {@link JDialog}
   */
  public final JDialog getGui ()
  {
    return this.gui;
  }


  /**
   * Handle previous button pressed for the {@link NewDialogAlphabet}
   */
  public final void handleAlphabetPrevious ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.MACHINE ) )
    {
      this.machineChoice.getGui ().setVisible ( true );
    }
    else
    {
      this.grammarChoice.getGui ().setVisible ( true );
    }
    this.newDialogAlphabet.getGui ().setVisible ( false );
    this.newDialogTerminal.getGui ().setVisible ( false );

  }


  /**
   * Handle the cancel event.
   */
  public final void handleCancel ()
  {
    this.gui.dispose ();
  }


  /**
   * Handle finish button pressed for the {@link NewDialogAlphabet}.
   */
  public final void handleFinish ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.MACHINE ) )
    {
      if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.DFA ) )
      {
        this.newPanel = new MachinePanel ( this.mainWindowForm,
            new DefaultMachineModel ( new DefaultDFA ( this.newDialogAlphabet
                .getAlphabet (), this.newDialogAlphabet.getPushDownAlphabet (),
                this.newDialogAlphabet.getUsePushDownAlphabet () ) ), null );
        this.gui.dispose ();
      }
      else if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.NFA ) )
      {
        this.newPanel = new MachinePanel ( this.mainWindowForm,
            new DefaultMachineModel ( new DefaultNFA ( this.newDialogAlphabet
                .getAlphabet (), this.newDialogAlphabet.getPushDownAlphabet (),
                this.newDialogAlphabet.getUsePushDownAlphabet () ) ), null );
        this.gui.dispose ();
      }
      else if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.ENFA ) )
      {
        this.newPanel = new MachinePanel ( this.mainWindowForm,
            new DefaultMachineModel ( new DefaultENFA ( this.newDialogAlphabet
                .getAlphabet (), this.newDialogAlphabet.getPushDownAlphabet (),
                this.newDialogAlphabet.getUsePushDownAlphabet () ) ), null );
        this.gui.dispose ();
      }
      else if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.PDA ) )
      {
        this.newPanel = new MachinePanel ( this.mainWindowForm,
            new DefaultMachineModel ( new DefaultPDA ( this.newDialogAlphabet
                .getAlphabet (), this.newDialogAlphabet.getPushDownAlphabet (),
                this.newDialogAlphabet.getUsePushDownAlphabet () ) ), null );
        this.gui.dispose ();
      }
    }
    else
    {
      if ( this.grammarChoice.getUserChoice ().equals (
          NewDialogGrammarChoice.Choice.CONTEXT_FREE ) )
      {
        this.newPanel = new GrammarPanel ( this.mainWindowForm,
            new DefaultGrammarModel ( new DefaultCFG ( this.newDialogTerminal
                .getNonterminalSymbolSet (), this.newDialogTerminal
                .geTerminalSymbolSet (), this.newDialogTerminal
                .getStartSymbol () ) ), null );
        this.gui.dispose ();
      }
      else if ( this.grammarChoice.getUserChoice ().equals (
          NewDialogGrammarChoice.Choice.REGULAR ) )
      {
        this.newPanel = new GrammarPanel ( this.mainWindowForm,
            new DefaultGrammarModel ( new DefaultRG ( this.newDialogTerminal
                .getNonterminalSymbolSet (), this.newDialogTerminal
                .geTerminalSymbolSet (), this.newDialogTerminal
                .getStartSymbol () ) ), null );
        this.gui.dispose ();
      }
    }
  }


  /**
   * Handle next button pressed for the {@link NewDialogGrammarChoice}.
   */
  public final void handleNextGrammarChoice ()
  {
    this.newDialogTerminal.getGui ().setVisible ( true );
    this.grammarChoice.getGui ().setVisible ( false );
  }


  /**
   * Handle next button pressed for the {@link NewDialogMachineChoice}.
   */
  public final void handleNextMachineChoice ()
  {
    this.newDialogAlphabet.getGui ().setVisible ( true );
    this.machineChoice.getGui ().setVisible ( false );
  }


  /**
   * Handle next button pressed for the {@link NewDialogChoice}.
   */
  public final void handleNextNewDialogChoice ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.MACHINE ) )
    {
      this.machineChoice.getGui ().setVisible ( true );
    }
    else
    {
      this.grammarChoice.getGui ().setVisible ( true );
    }
    this.newDialogChoice.getGui ().setVisible ( false );

  }


  /**
   * Handle previous button pressed for the {@link NewDialogGrammarChoice}.
   */
  public final void handlePreviousGrammarChoice ()
  {
    this.newDialogChoice.getGui ().setVisible ( true );
    this.grammarChoice.getGui ().setVisible ( false );
  }


  /**
   * Handle previous button pressed for the {@link NewDialogMachineChoice}.
   */
  public final void handlePreviousMachineChoice ()
  {
    this.newDialogChoice.getGui ().setVisible ( true );
    this.machineChoice.getGui ().setVisible ( false );

  }


  /**
   * Initialize all components.
   */
  private final void initialize ()
  {
    this.gui = new NewDialogForm ( this.mainWindowForm, true );
    this.gui.setLogic ( this );
    this.newDialogChoice = new NewDialogChoice ( this );
    this.machineChoice = new NewDialogMachineChoice ( this );
    this.grammarChoice = new NewDialogGrammarChoice ( this );
    this.newDialogAlphabet = new NewDialogAlphabet ( this );
    this.newDialogTerminal = new NewDialogTerminal ( this );

    this.gridBagConstraints = new GridBagConstraints ();
    this.gridBagConstraints.gridx = 0;
    this.gridBagConstraints.gridy = 0;
    this.gridBagConstraints.weightx = 1;
    this.gridBagConstraints.weighty = 1;
    this.gridBagConstraints.fill = GridBagConstraints.BOTH;

    this.gui.jGTIPanelBody.add ( this.newDialogChoice.getGui (),
        this.gridBagConstraints );
    this.gui.jGTIPanelBody.add ( this.grammarChoice.getGui (),
        this.gridBagConstraints );
    this.grammarChoice.getGui ().setVisible ( false );
    this.gui.jGTIPanelBody.add ( this.machineChoice.getGui (),
        this.gridBagConstraints );
    this.machineChoice.getGui ().setVisible ( false );
    this.gui.jGTIPanelBody.add ( this.newDialogAlphabet.getGui (),
        this.gridBagConstraints );
    this.newDialogAlphabet.getGui ().setVisible ( false );
    this.gui.jGTIPanelBody.add ( this.newDialogTerminal.getGui (),
        this.gridBagConstraints );
    this.newDialogTerminal.getGui ().setVisible ( false );
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public final void show ()
  {
    int x = this.mainWindowForm.getBounds ().x
        + ( this.mainWindowForm.getWidth () / 2 ) - ( this.gui.getWidth () / 2 );
    int y = this.mainWindowForm.getBounds ().y
        + ( this.mainWindowForm.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}

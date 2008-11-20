package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.Grammar.GrammarType;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.core.grammars.rg.DefaultRG;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.dfa.DefaultDFA;
import de.unisiegen.gtitool.core.machines.enfa.DefaultENFA;
import de.unisiegen.gtitool.core.machines.nfa.DefaultNFA;
import de.unisiegen.gtitool.core.machines.pda.DefaultPDA;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.core.regex.DefaultRegex.RegexType;
import de.unisiegen.gtitool.ui.logic.NewDialogMachineChoice.Choice;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultRegexModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The{@link NewDialog}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class NewDialog implements LogicClass < NewDialogForm >
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
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final NewDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Returns the {@link Machine} choice.
   * 
   * @return The {@link Machine} choice.
   */
  public final Choice getMachineChoice ()
  {
    return this.machineChoice.getUserChoice ();
  }


  /**
   * Returns the newDialogChoice.
   * 
   * @return The newDialogChoice.
   * @see #newDialogChoice
   */
  public NewDialogChoice getNewDialogChoice ()
  {
    return this.newDialogChoice;
  }


  /**
   * Handle previous button pressed for the {@link NewDialogAlphabet}
   */
  public final void handleAlphabetPrevious ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.MACHINE ) )
    {
      this.machineChoice.getGUI ().setVisible ( true );
    }
    else if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.GRAMMAR ) )
    {
      this.grammarChoice.getGUI ().setVisible ( true );
    }
    else
    {
      this.newDialogChoice.getGUI ().setVisible ( true );
    }
    this.newDialogAlphabet.getGUI ().setVisible ( false );
    this.newDialogTerminal.getGUI ().setVisible ( false );

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

        PreferenceManager.getInstance ().setLastChoosenEntityType (
            MachineType.DFA );

        this.gui.dispose ();
      }
      else if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.NFA ) )
      {
        this.newPanel = new MachinePanel ( this.mainWindowForm,
            new DefaultMachineModel ( new DefaultNFA ( this.newDialogAlphabet
                .getAlphabet (), this.newDialogAlphabet.getPushDownAlphabet (),
                this.newDialogAlphabet.getUsePushDownAlphabet () ) ), null );

        PreferenceManager.getInstance ().setLastChoosenEntityType (
            MachineType.NFA );

        this.gui.dispose ();
      }
      else if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.ENFA ) )
      {
        this.newPanel = new MachinePanel ( this.mainWindowForm,
            new DefaultMachineModel ( new DefaultENFA ( this.newDialogAlphabet
                .getAlphabet (), this.newDialogAlphabet.getPushDownAlphabet (),
                this.newDialogAlphabet.getUsePushDownAlphabet () ) ), null );

        PreferenceManager.getInstance ().setLastChoosenEntityType (
            MachineType.ENFA );

        this.gui.dispose ();
      }
      else if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.PDA ) )
      {
        this.newPanel = new MachinePanel ( this.mainWindowForm,
            new DefaultMachineModel ( new DefaultPDA ( this.newDialogAlphabet
                .getAlphabet (), this.newDialogAlphabet.getPushDownAlphabet (),
                this.newDialogAlphabet.getUsePushDownAlphabet () ) ), null );

        PreferenceManager.getInstance ().setLastChoosenEntityType (
            MachineType.PDA );

        this.gui.dispose ();
      }
    }
    else if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.GRAMMAR ) )
    {
      if ( this.grammarChoice.getUserChoice ().equals (
          NewDialogGrammarChoice.Choice.CONTEXT_FREE ) )
      {
        this.newPanel = new GrammarPanel ( this.mainWindowForm,
            new DefaultGrammarModel ( new DefaultCFG ( this.newDialogTerminal
                .getNonterminalSymbolSet (), this.newDialogTerminal
                .geTerminalSymbolSet (), this.newDialogTerminal
                .getStartSymbol () ) ), null );

        PreferenceManager.getInstance ().setLastChoosenEntityType (
            GrammarType.CFG );

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

        PreferenceManager.getInstance ().setLastChoosenEntityType (
            GrammarType.RG );

        this.gui.dispose ();
      }
    }
    else
    {
      this.newPanel = new RegexPanel ( this.mainWindowForm,
          new DefaultRegexModel ( new DefaultRegex ( this.newDialogAlphabet
              .getRegexAlphabet () ) ), null ); //$NON-NLS-1$

      PreferenceManager.getInstance ().setLastChoosenEntityType (
          RegexType.REGEX );
      this.gui.dispose ();
    }
  }


  /**
   * Handle next button pressed for the {@link NewDialogGrammarChoice}.
   */
  public final void handleNextGrammarChoice ()
  {
    this.newDialogTerminal.getGUI ().setVisible ( true );
    this.grammarChoice.getGUI ().setVisible ( false );
  }


  /**
   * Handle next button pressed for the {@link NewDialogMachineChoice}.
   */
  public final void handleNextMachineChoice ()
  {
    this.newDialogAlphabet.getGUI ().setVisible ( true );
    this.machineChoice.getGUI ().setVisible ( false );

    this.newDialogAlphabet.setButtonStatus ();
  }


  /**
   * Handle next button pressed for the {@link NewDialogChoice}.
   */
  public final void handleNextNewDialogChoice ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.MACHINE ) )
    {
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .setVisible ( true );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelPushDown
          .setVisible ( true );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelInput
          .setVisible ( true );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledRegexAlphabetParserPanelInput
          .setVisible ( false );

      try
      {
        this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelInput
            .setText ( new DefaultAlphabet ( new DefaultSymbol ( "0" ),
                new DefaultSymbol ( "1" ) ) );
      }
      catch ( AlphabetException exc )
      {
        exc.printStackTrace ();
      }
      this.machineChoice.getGUI ().setVisible ( true );
    }
    else if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.GRAMMAR ) )
    {
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .setVisible ( true );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelPushDown
          .setVisible ( true );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelInput
          .setVisible ( true );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledRegexAlphabetParserPanelInput
          .setVisible ( false );

      try
      {
        this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelInput
            .setText ( new DefaultAlphabet ( new DefaultSymbol ( "0" ),
                new DefaultSymbol ( "1" ) ) );
      }
      catch ( AlphabetException exc )
      {
        exc.printStackTrace ();
      }
      this.grammarChoice.getGUI ().setVisible ( true );
    }
    else
    {
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .setVisible ( false );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelPushDown
          .setVisible ( false );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledAlphabetParserPanelInput
          .setVisible ( false );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledRegexAlphabetParserPanelInput
          .setVisible ( true );
      this.newDialogAlphabet.getGUI ().alphabetPanelForm.styledRegexAlphabetParserPanelInput
          .setText ( "[A-Z],[a-z],[0-9]" );
      this.newDialogAlphabet.getGUI ().setVisible ( true );
    }
    this.newDialogChoice.getGUI ().setVisible ( false );

  }


  /**
   * Handle previous button pressed for the {@link NewDialogGrammarChoice}.
   */
  public final void handlePreviousGrammarChoice ()
  {
    this.newDialogChoice.getGUI ().setVisible ( true );
    this.grammarChoice.getGUI ().setVisible ( false );
  }


  /**
   * Handle previous button pressed for the {@link NewDialogMachineChoice}.
   */
  public final void handlePreviousMachineChoice ()
  {
    this.newDialogChoice.getGUI ().setVisible ( true );
    this.machineChoice.getGUI ().setVisible ( false );

  }


  /**
   * Initialize all components.
   */
  private final void initialize ()
  {
    this.gui = new NewDialogForm ( this, this.mainWindowForm );
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

    this.gui.jGTIPanelBody.add ( this.newDialogChoice.getGUI (),
        this.gridBagConstraints );
    this.gui.jGTIPanelBody.add ( this.grammarChoice.getGUI (),
        this.gridBagConstraints );

    this.gui.jGTIPanelBody.add ( this.machineChoice.getGUI (),
        this.gridBagConstraints );
    this.gui.jGTIPanelBody.add ( this.newDialogAlphabet.getGUI (),
        this.gridBagConstraints );
    this.gui.jGTIPanelBody.add ( this.newDialogTerminal.getGUI (),
        this.gridBagConstraints );

    this.grammarChoice.getGUI ().setVisible ( false );
    this.machineChoice.getGUI ().setVisible ( false );
    this.newDialogAlphabet.getGUI ().setVisible ( false );
    this.newDialogTerminal.getGUI ().setVisible ( false );

    EntityType entityType = PreferenceManager.getInstance ()
        .getLastChoosenEntityType ();

    if ( entityType.equals ( MachineType.DFA ) )
    {
      this.newDialogChoice.getGUI ().jGTIRadioButtonMachine.setSelected ( true );
      this.machineChoice.getGUI ().jGTIRadioButtonDFA.setSelected ( true );
    }
    else if ( entityType.equals ( MachineType.NFA ) )
    {
      this.newDialogChoice.getGUI ().jGTIRadioButtonMachine.setSelected ( true );
      this.machineChoice.getGUI ().jGTIRadioButtonNFA.setSelected ( true );
    }
    else if ( entityType.equals ( MachineType.ENFA ) )
    {
      this.newDialogChoice.getGUI ().jGTIRadioButtonMachine.setSelected ( true );
      this.machineChoice.getGUI ().jGTIRadioButtonENFA.setSelected ( true );
    }
    else if ( entityType.equals ( MachineType.PDA ) )
    {
      this.newDialogChoice.getGUI ().jGTIRadioButtonMachine.setSelected ( true );
      this.machineChoice.getGUI ().jGTIRadioButtonPDA.setSelected ( true );
    }
    else if ( entityType.equals ( GrammarType.RG ) )
    {
      this.newDialogChoice.getGUI ().jGTIRadioButtonGrammar.setSelected ( true );
      this.grammarChoice.getGUI ().jGTIRadioButtonRegularGrammar
          .setSelected ( true );
    }
    else if ( entityType.equals ( GrammarType.CFG ) )
    {
      this.newDialogChoice.getGUI ().jGTIRadioButtonGrammar.setSelected ( true );
      this.grammarChoice.getGUI ().jGTIRadioButtonContextFreeGrammar
          .setSelected ( true );
    }
    else if ( entityType.equals ( RegexType.REGEX ) )
    {
      this.newDialogChoice.getGUI ().jGTIRadioButtonRegex.setSelected ( true );
    }
    else
    {
      throw new RuntimeException ( "unsupported entity type" ); //$NON-NLS-1$
    }
  }


  /**
   * Shows the {@link NewDialog}.
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

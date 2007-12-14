package de.unisiegen.gtitool.ui.logic;


import java.awt.GridBagConstraints;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;


/**
 * The <code>NewDialogg</code>.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class NewDialog
{

  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link Alphabet} for the new file
   */
  private Alphabet alphabet;


  /**
   * The {@link NewDialogChoice}
   */
  private NewDialogChoice newDialogChoice;


  /**
   * The {@link NewDialogGrammarChoice}
   */
  private NewDialogGrammarChoice grammarChoice;


  /**
   * The {@link NewDialogMachineChoice}
   */
  private NewDialogMachineChoice machineChoice;


  /**
   * The {@link NewDialogAlphabet}
   */
  private NewDialogAlphabet newDialogAlphabet;


  /**
   * The {@link GridBagConstraints} to add the body panel
   */
  private GridBagConstraints gridBagConstraints;


  /**
   * The new created Panel
   */
  private EditorPanel newPanel;


  /**
   * The ending of the new file name
   */
  private String fileEnding;


  /**
   * Creates a new <code>NewDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public NewDialog ( JFrame pParent )
  {
    this.parent = pParent;
    initialize ();
  }


  /**
   * Get the alphabet for the new file
   * 
   * @return the {@link Alphabet} for the new file
   */
  public Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Getter for the new created editor panel
   * 
   * @return The {@link EditorPanel}
   */
  public EditorPanel getEditorPanel ()
  {
    return this.newPanel;
  }


  /**
   * Get the file ending for the new file
   * 
   * @return file ending for the new file
   */
  public String getFileEnding ()
  {
    return this.fileEnding;
  }


  /**
   * Getter for the gui of this logic class
   * 
   * @return The {@link JDialog}
   */
  public JDialog getGui ()
  {
    return this.gui;
  }


  /**
   * Handle previous button pressed for the {@link NewDialogAlphabet}
   */
  public void handleAlphabetPrevious ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.Machine ) )
    {
      this.machineChoice.getGui ().setVisible ( true );
    }
    else
    {
      this.grammarChoice.getGui ().setVisible ( true );
    }
    this.newDialogAlphabet.getGui ().setVisible ( false );

  }


  /**
   * Handle finish button pressed for the {@link NewDialogAlphabet}
   */
  public void handleFinish ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.Machine ) )
    {
      if ( this.machineChoice.getUserChoice ().equals (
          NewDialogMachineChoice.Choice.DFA ) )
      {
        this.newPanel = new MachinePanel ( this.parent,
            new DefaultMachineModel ( new DFA ( this.newDialogAlphabet
                .getAlphabet () ) ), null );
        this.fileEnding = ".dfa"; //$NON-NLS-1$
        this.gui.dispose ();
      }
    }
    else
    {
      //TODO Implement me (Grammar)
    }

  }


  /**
   * Handle next button pressed for the {@link NewDialogGrammarChoice}
   */
  public void handleNextGrammarChoice ()
  {
    this.newDialogAlphabet.getGui ().setVisible ( true );
    this.grammarChoice.getGui ().setVisible ( false );

  }


  /**
   * Handle next button pressed for the {@link NewDialogMachineChoice}
   */
  public void handleNextMachineChoice ()
  {
    this.newDialogAlphabet.getGui ().setVisible ( true );
    this.machineChoice.getGui ().setVisible ( false );

  }


  /**
   * Handle next button pressed for the {@link NewDialogChoice}
   */
  public void handleNextNewDialogChoice ()
  {
    if ( this.newDialogChoice.getUserChoice ().equals (
        NewDialogChoice.Choice.Machine ) )
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
   * Handle previous button pressed for the {@link NewDialogGrammarChoice}
   */
  public void handlePreviousGrammarChoice ()
  {
    this.newDialogChoice.getGui ().setVisible ( true );
    this.grammarChoice.getGui ().setVisible ( false );

  }


  /**
   * Handle previous button pressed for the {@link NewDialogMachineChoice}
   */
  public void handlePreviousMachineChoice ()
  {
    this.newDialogChoice.getGui ().setVisible ( true );
    this.machineChoice.getGui ().setVisible ( false );

  }


  /**
   * Initialize all components
   */
  private void initialize ()
  {
    this.gui = new NewDialogForm ( this.parent, true );
    this.gui.setLogic ( this );
    this.newDialogChoice = new NewDialogChoice ( this );
    this.machineChoice = new NewDialogMachineChoice ( this );
    this.grammarChoice = new NewDialogGrammarChoice ( this );
    this.newDialogAlphabet = new NewDialogAlphabet ( this );

    this.gridBagConstraints = new GridBagConstraints ();
    this.gridBagConstraints.gridx = 0;
    this.gridBagConstraints.gridy = 0;
    this.gridBagConstraints.weightx = 1;
    this.gridBagConstraints.weighty = 1;
    this.gridBagConstraints.fill = GridBagConstraints.BOTH;

    this.gui.bodyPanel.add ( this.newDialogChoice.getGui (),
        this.gridBagConstraints );
    this.gui.bodyPanel.add ( this.grammarChoice.getGui (),
        this.gridBagConstraints );
    this.grammarChoice.getGui ().setVisible ( false );
    this.gui.bodyPanel.add ( this.machineChoice.getGui (),
        this.gridBagConstraints );
    this.machineChoice.getGui ().setVisible ( false );
    this.gui.bodyPanel.add ( this.newDialogAlphabet.getGui (),
        this.gridBagConstraints );
    this.newDialogAlphabet.getGui ().setVisible ( false );
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}

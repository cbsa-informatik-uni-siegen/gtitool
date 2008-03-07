package de.unisiegen.gtitool.ui.logic;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.ui.netbeans.NewDialogAlphabetForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogTerminalForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The panel used to enter the {@link Alphabet} for the new file.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class NewDialogTerminal
{

  /**
   * The {@link NewDialogAlphabetForm}
   */
  private NewDialogTerminalForm gui;


  /**
   * The parent Dialog containing this panel
   */
  private NewDialog parent;


  /**
   * Allocate a new {@link NewDialogTerminal}.
   * 
   * @param parent The dialog containing this panel.
   */
  public NewDialogTerminal ( NewDialog parent )
  {
    this.parent = parent;
    this.gui = new NewDialogTerminalForm ();
    this.gui.setLogic ( this );

    this.gui.terminalPanelForm.setNonterminalSymbolSet ( PreferenceManager.getInstance ().getNonterminalSymbolSetItem ().getStandardNonterminalSymbolSet () );
    this.gui.terminalPanelForm.setTerminalSymbolSet ( PreferenceManager.getInstance ().getTerminalSymbolSetItem ().getStandardTerminalSymbolSet ());
    
    
    this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
    .addNonterminalSymbolSetChangedListener ( new NonterminalSymbolSetChangedListener()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void nonterminalSymbolSetChanged (
          @SuppressWarnings("unused")
          NonterminalSymbolSet newNonterminalSymbolSet )
      {
        setButtonStatus ();
      }
    } );
this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
    .addTerminalSymbolSetChangedListener ( new TerminalSymbolSetChangedListener()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void terminalSymbolSetChanged ( @SuppressWarnings("unused")
      TerminalSymbolSet newTerminalSymbolSet )
      {
        setButtonStatus ();
      }
    } );
  }




  /**
   * Getter for the gui of this logic class.
   * 
   * @return The {@link NewDialogAlphabetForm}.
   */
  public final NewDialogTerminalForm getGui ()
  {
    return this.gui;
  }



  /**
   * Handle the cancel button pressed event.
   */
  
  public final void handleCancel ()
  {
    this.parent.getGui ().dispose ();
  }


  /**
   * Handle the finish button pressed event.
   */
  public final void handleFinish ()
  {
    this.parent.handleFinish ();

  }


  /**
   * Handle the previous button pressed event.
   */
  public final void handlePrevious ()
  {
    this.parent.handleAlphabetPrevious ();
  }
  
  /**
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return the {@link NonterminalSymbolSet}.
   */
  public NonterminalSymbolSet getNonterminalSymbolSet(){
    return this.gui.terminalPanelForm.getNonterminalSymbolSet ();
  }
  
  /**
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return the {@link TerminalSymbolSet}
   */
  public TerminalSymbolSet geTerminalSymbolSet(){
    return this.gui.terminalPanelForm.getTerminalSymbolSet ();
  }
  
  /**
   * Sets the status of the buttons.
   */
  private final void setButtonStatus ()
  {
    if ( ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .getNonterminalSymbolSet () == null )
        || ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
            .getTerminalSymbolSet ()== null ) )
    {
      this.gui.jGTIButtonFinished.setEnabled ( false );
    }
    else
    {
      this.gui.jGTIButtonFinished.setEnabled ( true );
    }
  }
}

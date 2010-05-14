package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.ParsingTable;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.BaseGameDialogForm;


/**
 * Abstract logic class for BaseGameDialog
 * 
 * @author Christian Uhrhan
 */
public abstract class AbstractBaseGameDialog implements
    LogicClass < BaseGameDialogForm >
{

  /**
   * Defines the type of our game
   */
  public enum GameType
  {
    /**
     * The user should find the {@link ParsingTable} entry where only one item
     * is recorded
     */
    GUESS_SINGLE_ENTRY,

    /**
     * The user should find the {@link ParsingTable} entry where multiple items
     * are recoreded
     */
    GUESS_MULTI_ENTRY;
  }


  /**
   * The {@link CFG}
   */
  private DefaultCFG cfg;


  /**
   * The {@link GameType}
   */
  private GameType gameType;


  /**
   * The {@link BaseGameDialogForm}
   */
  private BaseGameDialogForm gui;


  /**
   * Allocates a new {@link AbstractBaseGameDialog}
   * 
   * @param parent The {@link JFrame}
   * @param cfg The {@link CFG}
   * @param gameType The {@link GameType}
   */
  public AbstractBaseGameDialog ( final JFrame parent, final CFG cfg,
      final GameType gameType )
  {
    // setup grammar
    this.cfg = ( DefaultCFG ) cfg;
    this.cfg.getTerminalSymbolSet ().addIfNonexistent (
        DefaultTerminalSymbol.EndMarker );

    // setup game type
    this.gameType = gameType;

    // setup gui
    this.gui = new BaseGameDialogForm ( parent, this );
    int x = parent.getBounds ().x + ( parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = parent.getBounds ().y + ( parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
  }


  /**
   * Returns the {@link GameType}
   * 
   * @return The {@link GameType}
   */
  protected GameType getGameType ()
  {
    return this.gameType;
  }
  
  
  /**
   * 
   * Returns the {@link DefaultCFG}
   *
   * @return The {@link DefaultCFG}
   */
  protected DefaultCFG getGrammar()
  {
    return this.cfg;
  }


  /**
   * Shows the dialog
   */
  public void show ()
  {
    this.gui.setVisible ( true );
  }


  /**
   * closes the dialog
   */
  public void handleOk ()
  {
    this.gui.dispose ();
  }


  /**
   * handles the uncover of a parsing table cell
   * 
   * @param evt The {@link MouseEvent}
   */
  public abstract void handleUncover ( final MouseEvent evt );


  /**
   * implements the logic to handle the 'show all' button
   */
  protected abstract void handleShowAll ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public BaseGameDialogForm getGUI ()
  {
    return this.gui;
  }
}

package de.unisiegen.gtitool.ui.logic;


import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;
import de.unisiegen.gtitool.ui.netbeans.BaseGameDialogForm;
import de.unisiegen.gtitool.ui.netbeans.FollowSetDialogForm;


/**
 * Abstract logic class for BaseGameDialog
 * 
 * @author Christian Uhrhan
 */
public abstract class AbstractBaseGameDialog
{

  /**
   * Which Button is pressed
   */
  private enum Action
  {
    /**
     * start button
     */
    START,

    /**
     * previous button
     */
    PREVIOUS,

    /**
     * next button
     */
    NEXT,

    /**
     * stop button
     */
    STOP;
  }


  /**
   * The {@link CFG}
   */
  private DefaultCFG cfg;


  /**
   * The {@link BaseGameDialogForm}
   */
  private BaseGameDialogForm gui;


  /**
   * 
   * Allocates a new {@link AbstractBaseGameDialog}
   *
   * @param parent The {@link JFrame}
   * @param cfg The {@link CFG}
   */
  public AbstractBaseGameDialog ( final JFrame parent, final CFG cfg )
  {
    // setup grammar
    this.cfg = ( DefaultCFG ) cfg;

    // setup gui
    //TODO: this.gui = new CreateParsingTableGameDialogForm ( parent, this );
    int x = parent.getBounds ().x + ( parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = parent.getBounds ().y + ( parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
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
   * 
   * implements the logic to handle a double click
   * on a table cell
   *
   */
  protected abstract void onHandleUncover();
  
  /**
   * handles the uncover of a parsing table cell
   * 
   * @param evt The {@link MouseEvent}
   */
  public void handleUncover ( final MouseEvent evt )
  {
    if ( evt.getClickCount () >= 2 )
      onHandleUncover();
  }
  
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

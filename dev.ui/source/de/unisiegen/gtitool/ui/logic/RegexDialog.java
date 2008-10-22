package de.unisiegen.gtitool.ui.logic;


import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.RegexDialogForm;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * TODO
 */
public class RegexDialog implements LogicClass < RegexDialogForm >
{

  /**
   * TODO
   */
  private RegexDialogForm gui;


  private RegexPanel panel;


  /**
   * TODO
   * 
   * @param parent
   */
  public RegexDialog ( RegexPanel panel )
  {
    this.panel = panel;
    this.gui = new RegexDialogForm ( this, panel.getMainWindowForm () );

    this.gui.styledRegexParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < RegexNode > ()
        {

          public void parseableChanged ( RegexNode newEntity )
          {
            if ( newEntity == null )
            {
              setEnabled ( false );
            }
            else
            {
              setEnabled ( true );
            }
          }

        } );
    
    this.gui.styledRegexParserPanel.setAlphabet ( this.panel.getRegex ()
        .getAlphabet () );
    this.gui.styledRegexParserPanel.setText ( this.panel.getRegex ()
        .getRegexString () );
    this.gui.styledAlphabetParserPanel.setText ( this.panel.getRegex ()
        .getAlphabet () );

    this.gui.setVisible ( true );
  }


  /**
   * Enables or Disables the Ok-Button
   */
  public void setEnabled ( boolean enabled )
  {
    this.gui.jGTIButtonOk.setEnabled ( enabled );
  }


  /**
   * TODO
   * 
   * @return the gui
   * @see de.unisiegen.gtitool.ui.logic.interfaces.LogicClass#getGUI()
   */
  public RegexDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * TODO
   */
  public void handleCancel ()
  {
    this.gui.dispose ();
  }


  /**
   * TODO
   */
  public void handleOk ()
  {
    this.panel
        .changeRegex ( this.gui.styledRegexParserPanel.getParsedObject () );
    this.panel.getRegex ().setRegexString ( this.gui.styledRegexParserPanel.getText () );
    this.gui.dispose ();
  }

}

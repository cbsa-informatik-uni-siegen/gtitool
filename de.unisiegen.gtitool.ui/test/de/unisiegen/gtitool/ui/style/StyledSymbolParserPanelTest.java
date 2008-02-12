package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.listener.SymbolChangedListener;


/**
 * The test class of the {@link StyledSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledSymbolParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
@SuppressWarnings (
{ "all" } )
public class StyledSymbolParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "SymbolPanelTest" );
    StyledSymbolParserPanel styledSymbolParserPanel = new StyledSymbolParserPanel ();
    styledSymbolParserPanel
        .addSymbolChangedListener ( new SymbolChangedListener ()
        {

          public void symbolChanged ( Symbol symbol )
          {
            if ( symbol != null )
            {
              System.out.println ( symbol );
            }
          }
        } );
    jFrame.add ( styledSymbolParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}

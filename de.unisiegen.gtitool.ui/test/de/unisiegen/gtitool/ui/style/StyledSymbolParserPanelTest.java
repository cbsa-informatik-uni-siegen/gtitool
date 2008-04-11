package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.listener.SymbolChangedListener;


/**
 * The test class of the {@link StyledSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledSymbolParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class StyledSymbolParserPanelTest
{

  /**
   * The main methos.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "SymbolPanelTest" ); //$NON-NLS-1$
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
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}

/*******************************************************************************
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Tiny Look and Feel * * (C) Copyright 2003 - 2007 Hans Bickel * * For
 * licensing information and credits, please refer to the * comment in file
 * de.muntjak.tinylookandfeel.TinyLookAndFeel * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package de.muntjak.tinylookandfeel;


import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopPaneUI;


/**
 * TinyDesktopPaneUI
 * 
 * @version 1.0
 * @author Hans Bickel
 */
@SuppressWarnings (
{ "all" } )
public class TinyDesktopPaneUI extends BasicDesktopPaneUI
{

  public static ComponentUI createUI ( JComponent c )
  {
    return new TinyDesktopPaneUI ();
  }


  public void installUI ( JComponent c )
  {
    super.installUI ( c );
  }
}

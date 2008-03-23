/*******************************************************************************
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Tiny Look and Feel * * (C) Copyright 2003 - 2007 Hans Bickel * * For
 * licensing information and credits, please refer to the * comment in file
 * de.muntjak.tinylookandfeel.TinyLookAndFeel * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package de.muntjak.tinylookandfeel;


import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalLabelUI;


/**
 * TinyLabelUI
 * 
 * @version 1.0
 * @author Hans Bickel
 */
@SuppressWarnings (
{ "all" } )
public class TinyLabelUI extends MetalLabelUI
{

  protected static TinyLabelUI sharedInstance = new TinyLabelUI ();


  public static ComponentUI createUI ( JComponent c )
  {
    return sharedInstance;
  }
}

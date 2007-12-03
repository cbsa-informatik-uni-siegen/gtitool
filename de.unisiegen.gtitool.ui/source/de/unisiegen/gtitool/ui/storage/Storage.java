package de.unisiegen.gtitool.ui.storage;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Storage</code> class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Storage
{

  /**
   * The single instance of the <code>Storage<code>
   */
  private static Storage singleStorage;


  /**
   * The name of a supported {@link Charset}.
   */
  private static final String CHARSET_NAME = "UTF8"; //$NON-NLS-1$


  /**
   * Returns the single instance of the <code>Storage</code>.
   * 
   * @return The single instance of the <code>Storage</code>.
   */
  public final static Storage getInstance ()
  {
    if ( singleStorage == null )
    {
      singleStorage = new Storage ();
    }
    return singleStorage;
  }


  /**
   * The used
   */
  private BufferedWriter writer;


  /**
   * Allocates a new <code>Storage</code>.
   */
  private Storage ()
  {
    // Do nothing
  }


  /**
   * Returns the {@link Element}.
   * 
   * @param pNode The input {@link Node}.
   * @return The {@link Element}.
   */
  private final Element getElement ( Node pNode )
  {
    Element newElement = new Element ( pNode.getNodeName () );
    if ( pNode.getAttributes () != null )
    {
      for ( int i = 0 ; i < pNode.getAttributes ().getLength () ; i++ )
      {
        Node current = pNode.getAttributes ().item ( i );
        if ( current.getNodeType () == Node.ATTRIBUTE_NODE )
        {
          newElement.addAttribute ( new Attribute ( current.getNodeName (),
              current.getNodeValue () ) );
        }
      }
    }
    if ( pNode.getChildNodes () != null )
    {
      for ( int i = 0 ; i < pNode.getChildNodes ().getLength () ; i++ )
      {
        Node current = pNode.getChildNodes ().item ( i );
        if ( current.getNodeType () == Node.ELEMENT_NODE )
        {
          newElement.addElement ( getElement ( current ) );
        }
      }
    }
    return newElement;
  }


  /**
   * Loads the {@link Storable} from the given file name.
   * 
   * @param pFileName The file name.
   * @return The {@link Storable} from the given file name.
   */
  public final Storable load ( String pFileName )
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
      DocumentBuilder builder = factory.newDocumentBuilder ();
      Document document = builder.parse ( new File ( pFileName ) );
      Element element = getElement ( document.getDocumentElement () );
      if ( element.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
      {
        return new Alphabet ( element );
      }
      if ( element.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
      {
        return new Symbol ( element );
      }
    }
    catch ( SAXException exc )
    {
      exc.printStackTrace ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
    }
    catch ( ParserConfigurationException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TransformerFactoryConfigurationError exc )
    {
      exc.printStackTrace ();
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
    return null;
  }


  /**
   * Writes a string to the {@link BufferedWriter}.
   * 
   * @param pText The text which should be written to the {@link BufferedWriter}.
   */
  private final void print ( String pText )
  {
    try
    {
      this.writer.write ( pText );
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Writes a new line to the {@link BufferedWriter}.
   */
  private final void println ()
  {
    try
    {
      this.writer.newLine ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Writes a string and a new line to the {@link BufferedWriter}.
   * 
   * @param pText The text which should be written to the {@link BufferedWriter}.
   */
  private final void println ( String pText )
  {
    try
    {
      this.writer.write ( pText );
      this.writer.newLine ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Stores the given {@link Element} with the given indent.
   * 
   * @param pElement The {@link Element} to store.
   * @param pIndent The used indent.
   */
  private final void store ( Element pElement, int pIndent )
  {
    StringBuilder indent = new StringBuilder ();
    for ( int i = 0 ; i < pIndent ; i++ )
    {
      indent.append ( " " ); //$NON-NLS-1$
    }
    print ( indent.toString () );
    print ( "<" ); //$NON-NLS-1$
    print ( pElement.getName () );
    print ( " " ); //$NON-NLS-1$
    for ( int i = 0 ; i < pElement.getAttribute ().size () ; i++ )
    {
      if ( i > 0 )
      {
        print ( " " ); //$NON-NLS-1$
      }
      print ( pElement.getAttribute ( i ).getName () );
      print ( "=" ); //$NON-NLS-1$
      print ( "\"" ); //$NON-NLS-1$
      print ( pElement.getAttribute ( i ).getValue () );
      print ( "\"" ); //$NON-NLS-1$
    }
    if ( pElement.getElement ().size () == 0 )
    {
      print ( "/>" ); //$NON-NLS-1$
    }
    else
    {
      println ( ">" ); //$NON-NLS-1$
      for ( int i = 0 ; i < pElement.getElement ().size () ; i++ )
      {
        store ( pElement.getElement ( i ), pIndent + 2 );
      }
      print ( indent.toString () );
      print ( "</" ); //$NON-NLS-1$
      print ( pElement.getName () );
      print ( ">" ); //$NON-NLS-1$
    }
    if ( pIndent != 0 )
    {
      println ();
    }
  }


  /**
   * Stores the given {@link Storable} to the given file name.
   * 
   * @param pStorable The {@link Storable} to store.
   * @param pFileName The used file name.
   */
  public final void store ( Storable pStorable, String pFileName )
  {
    try
    {
      this.writer = new BufferedWriter ( new OutputStreamWriter (
          new FileOutputStream ( pFileName ), CHARSET_NAME ) );
    }
    catch ( UnsupportedEncodingException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    catch ( FileNotFoundException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return;
    }
    println ( "<?xml version='1.0' encoding='utf-8'?>" ); //$NON-NLS-1$
    println ();
    store ( pStorable.getElement (), 0 );
    try
    {
      this.writer.close ();
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}

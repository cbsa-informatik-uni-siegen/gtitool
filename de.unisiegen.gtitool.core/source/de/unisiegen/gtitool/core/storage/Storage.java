package de.unisiegen.gtitool.core.storage;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


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
   * @param node The input {@link Node}.
   * @return The {@link Element}.
   */
  private final Element getElement ( Node node )
  {
    Element newElement = new Element ( node.getNodeName () );
    if ( node.getAttributes () != null )
    {
      for ( int i = 0 ; i < node.getAttributes ().getLength () ; i++ )
      {
        Node current = node.getAttributes ().item ( i );
        if ( current.getNodeType () == Node.ATTRIBUTE_NODE )
        {
          newElement.addAttribute ( new Attribute ( current.getNodeName (),
              current.getNodeValue () ) );
        }
      }
    }
    if ( node.getChildNodes () != null )
    {
      for ( int i = 0 ; i < node.getChildNodes ().getLength () ; i++ )
      {
        Node current = node.getChildNodes ().item ( i );
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
   * @param file The {@link File}.
   * @param fileClass The {@link File} class.
   * @return The {@link Storable} from the given file name.
   * @throws StoreException If the file could not be loaded.
   */
  public final Storable load ( File file, Class < ? extends Storable > fileClass )
      throws StoreException
  {
    try
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
      DocumentBuilder builder = factory.newDocumentBuilder ();
      Document document = builder.parse ( file );
      Element element = getElement ( document.getDocumentElement () );
      Constructor < ? extends Storable > constructor = fileClass
          .getConstructor ( new Class []
          { Element.class } );
      return constructor.newInstance ( new Object []
      { element } );

    }
    catch ( ParserConfigurationException exc )
    {
      throw new StoreException ( Messages.getString ( "StoreException.Parse" ) ); //$NON-NLS-1$
    }
    catch ( SAXException exc )
    {
      throw new StoreException ( Messages.getString ( "StoreException.Parse" ) ); //$NON-NLS-1$
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      throw new StoreException ( Messages.getString ( "StoreException.Readed" ) ); //$NON-NLS-1$
    }
    catch ( Exception exc )
    {
      Throwable exception;
      if ( exc instanceof InvocationTargetException )
      {
        exception = exc.getCause ();
      }
      else
      {
        exception = exc;
      }

      if ( exception instanceof StoreException )
      {
        throw ( StoreException ) exception;
      }
      if ( exception instanceof AlphabetException )
      {
        throw new StoreException ( ( ( AlphabetException ) exception )
            .getDescription () );
      }
      if ( exception instanceof SymbolException )
      {
        throw new StoreException ( ( ( SymbolException ) exception )
            .getDescription () );
      }
      if ( exception instanceof StateException )
      {
        throw new StoreException ( ( ( StateException ) exception )
            .getDescription () );
      }
      if ( exception instanceof TransitionException )
      {
        throw new StoreException ( ( ( TransitionException ) exception )
            .getDescription () );
      }
      throw new StoreException ( Messages.getString ( "StoreException.Readed" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Writes a string to the {@link BufferedWriter}.
   * 
   * @param text The text which should be written to the {@link BufferedWriter}.
   * @throws IOException If an I/O error occurs.
   */
  private final void print ( String text ) throws IOException
  {
    this.writer.write ( text );
  }


  /**
   * Writes a new line to the {@link BufferedWriter}.
   * 
   * @throws IOException If an I/O error occurs.
   */
  private final void println () throws IOException
  {
    this.writer.newLine ();
  }


  /**
   * Writes a string and a new line to the {@link BufferedWriter}.
   * 
   * @param text The text which should be written to the {@link BufferedWriter}.
   * @throws IOException If an I/O error occurs.
   */
  private final void println ( String text ) throws IOException
  {
    this.writer.write ( text );
    this.writer.newLine ();
  }


  /**
   * Stores the given {@link Element} with the given indent.
   * 
   * @param element The {@link Element} to store.
   * @param indent The used indent.
   * @throws IOException If an I/O error occurs.
   */
  private final void store ( Element element, int indent ) throws IOException
  {
    StringBuilder indentText = new StringBuilder ();
    for ( int i = 0 ; i < indent ; i++ )
    {
      indentText.append ( " " ); //$NON-NLS-1$
    }
    print ( indentText.toString () );
    print ( "<" ); //$NON-NLS-1$
    print ( element.getName () );
    for ( Attribute current : element.getAttribute () )
    {
      print ( " " ); //$NON-NLS-1$
      print ( current.getName () );
      print ( "=" ); //$NON-NLS-1$
      print ( "\"" ); //$NON-NLS-1$
      print ( current.getValue () );
      print ( "\"" ); //$NON-NLS-1$
    }
    if ( element.getElement ().size () == 0 )
    {
      print ( "/>" ); //$NON-NLS-1$
    }
    else
    {
      println ( ">" ); //$NON-NLS-1$
      for ( Element current : element.getElement () )
      {
        store ( current, indent + 2 );
      }
      print ( indentText.toString () );
      print ( "</" ); //$NON-NLS-1$
      print ( element.getName () );
      print ( ">" ); //$NON-NLS-1$
    }
    if ( indent != 0 )
    {
      println ();
    }
  }


  /**
   * Stores the given {@link Storable} to the given file name.
   * 
   * @param storable The {@link Storable} to store.
   * @param file The used {@link File}.
   * @throws StoreException If the file could not be loaded.
   */
  public final void store ( Storable storable, File file )
      throws StoreException
  {
    try
    {
      this.writer = new BufferedWriter ( new OutputStreamWriter (
          new FileOutputStream ( file ), CHARSET_NAME ) );
      println ( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" ); //$NON-NLS-1$
      println ();
      store ( storable.getElement (), 0 );
      this.writer.close ();
    }
    catch ( Exception exc )
    {
      throw new StoreException ( Messages.getString ( "StoreException.Store" ) ); //$NON-NLS-1$
    }
  }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeo.boilerpipe;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author jabed hasan
 */
public class Boilerpipe {
    
    
    public static String DefaultExtractor(String word) throws MalformedURLException, IOException, SAXException, BoilerpipeProcessingException{
        String domain = "http://en.wikipedia.org/wiki/";
        domain += word ;
        URL url;
        url = new URL(domain); 
        final InputSource is = HTMLFetcher.fetch(url).toInputSource();
        final BoilerpipeSAXInput in = new BoilerpipeSAXInput(is);
        final TextDocument doc = in.getTextDocument();
        // You have the choice between different Extractors
    
        return ""+DefaultExtractor.INSTANCE.getText(doc);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeo.OpenNLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import org.xml.sax.InputSource;

import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.parser.chunking.Parser;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.parser.Parse;

/**
 *
 * @author Jabed hasan
 */
public class OpenNLP {
    public static String RESOURCES = "/Volumes/Bangali/MyRnD/EnrichingExistingOntology/resources/";
    
    public static String[] SentenceDetect(String paragraph) throws InvalidFormatException,IOException {
		
		//always start with a model, a model is learned from training data
		InputStream is = new FileInputStream( OpenNLP.RESOURCES+"en-sent.bin" );
		
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		
		String sentences[] = sdetector.sentDetect(paragraph);
		is.close();
		return sentences;
	}
	
	public static void Tokenize(String paragraph) throws InvalidFormatException, IOException {
		InputStream is = new FileInputStream( OpenNLP.RESOURCES+"en-token.bin" );
		TokenizerModel model = new TokenizerModel(is);
	 
		Tokenizer tokenizer = new TokenizerME(model);
	 
		String tokens[] = tokenizer.tokenize(paragraph);
	 
		for (String a : tokens)
			System.out.println(a);
	 
		is.close();
	}
	
	public static void findName() throws IOException {
		InputStream is = new FileInputStream( OpenNLP.RESOURCES+"en-ner-person.bin" );
	
		TokenNameFinderModel model = new TokenNameFinderModel(is);
		is.close();
	
		NameFinderME nameFinder = new NameFinderME(model);
	
		String []sentence = new String[]{
			    "Mike",
			    "Smith",
			    "javed",
			    "is",
			    "a",
			    "good",
			    "person",
			    "Jack"
			    };
	
			Span nameSpans[] = nameFinder.find(sentence);
	
			for(Span s: nameSpans){
				System.out.println(s.getType());	
			}			
	}
	
	public static String POSTag(String input) throws IOException {
		POSModel model = new POSModelLoader()	
			.load(new File( OpenNLP.RESOURCES+"en-pos-maxent.bin" ));
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(model);
		
		ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));
		POSSample sample = null;
		perfMon.start();
		String line;
		while ((line = lineStream.read()) != null) {
	 
			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
					.tokenize(line);
			String[] tags = tagger.tag(whitespaceTokenizerLine);
	 
			 sample = new POSSample(whitespaceTokenizerLine, tags);
			System.out.println(sample.toString());
			
			
	        		
			perfMon.incrementCounter();
		}
		perfMon.stopAndPrintFinalResult();
		
		return sample.toString();
	}
	
	public static void chunk() throws IOException {
		POSModel model = new POSModelLoader()
				.load(new File( OpenNLP.RESOURCES+"en-pos-maxent.bin" ));
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(model);
		String input = "A landform is a natural feature of the Earth's surface."+
                "Landforms together make up a given terrain , and their arrangement"+
		           " on the landscape or the study of same is known as topography.";
		ObjectStream<String> lineStream = new PlainTextByLineStream(
				new StringReader(input));
		perfMon.start();
		String line;
		String whitespaceTokenizerLine[] = null;
		String[] tags = null;
		while ((line = lineStream.read()) != null) {
			whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
					.tokenize(line);
			tags = tagger.tag(whitespaceTokenizerLine);
	
			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			System.out.println(sample.toString());
				perfMon.incrementCounter();
		}
		perfMon.stopAndPrintFinalResult();
		// chunker
		InputStream is = new FileInputStream( OpenNLP.RESOURCES+"en-chunker.bin" );
		ChunkerModel cModel = new ChunkerModel(is);
		ChunkerME chunkerME = new ChunkerME(cModel);
		String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);
		for (String s : result)
			System.out.println(s);
		Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
		for (Span s : span)
			System.out.println(s.toString());
	}
	
	public static void Parse() throws InvalidFormatException, IOException {
		
		InputStream is = new FileInputStream( OpenNLP.RESOURCES+"en-parser-chunking.bin" );
	 
		ParserModel model = new ParserModel(is);
	 
		Parser parser = (Parser) ParserFactory.create(model);
		String sentence = "A landform is a natural feature of the Earth's surface."+
                "Landforms together make up a given terrain , and their arrangement"+
		           " on the landscape or the study of same is known as topography.";
		Parse topParses[] =  ParserTool.parseLine(sentence, parser, 1);
	 
		for (Parse p : topParses)
			p.show();
	 
		is.close();
	 
		/*
		 * (TOP (S (NP (NN Programcreek) ) (VP (VBZ is) (NP (DT a) (ADJP (RB
		 * very) (JJ huge) (CC and) (JJ useful) ) ) ) (. website.) ) )
		 */
	}
	
	
	public static String[] extractNouns(String sentenceWithTags) {
	    // Split String into array of Strings whenever there is a tag that starts with "._NN" 
	    // followed by zero, one or two more letters (like "_NNP", "_NNPS", or "_NNS") 
	    String[] nouns = sentenceWithTags.split("_NN\\w?\\w?\\b");
	    // remove all but last word (which is the noun) in every String in the array 
	    for(int index = 0; index < nouns.length; index++) {
	        nouns[index] = nouns[index].substring(nouns[index].lastIndexOf(" ") + 1)
	        // Remove all non-word characters from extracted Nouns 
	        .replaceAll("[^\\p{L}\\p{Nd}]", "");
	    } 
	    return nouns;
	}
	
	public static String extractOriginal(String sentenceWithTags) {
	    return sentenceWithTags.replaceAll("_([A-Z]*)\\b", "");
	}
}

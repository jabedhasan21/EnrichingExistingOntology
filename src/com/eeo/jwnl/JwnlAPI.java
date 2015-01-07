/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeo.jwnl;

import com.eeo.model.ParentConcept;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.data.list.PointerTargetTree;
import net.didion.jwnl.dictionary.Dictionary;

/**
 *
 * @author jabed hasan
 */
public class JwnlAPI {
    
    public static String propsFile =  "/Users/ammaryeahyea/NetBeansProjects/EnrichingExistingOntology/config/file_properties.xml";
    private IndexWord indexWord;
    private String parentConceptHead;
    private ArrayList<ParentConcept> parentConcepts;
    
    public static ArrayList<ParentConcept> initializeJWNL(String parentConceptHead) {
        ArrayList<ParentConcept> tempParentConcepts = new ArrayList<ParentConcept>();
        try {
	    // initialize JWNL (this must be done before JWNL can be used)
	    JWNL.initialize(new FileInputStream(propsFile));
            tempParentConcepts =  new JwnlAPI(parentConceptHead).go();
	} catch (FileNotFoundException | JWNLException ex) {
            ex.printStackTrace();
	    System.exit(-1);
	}
        
        return tempParentConcepts;
    }
    
    public JwnlAPI(String parentConceptHead) throws JWNLException{
        this.parentConcepts = new ArrayList<ParentConcept>();
        
        this.parentConceptHead = parentConceptHead;
       // indexWord = Dictionary.getInstance().getIndexWord(POS.NOUN, parentConceptHead);
        indexWord = Dictionary.getInstance().lookupIndexWord(POS.NOUN, parentConceptHead);
    }
    
    public ArrayList<ParentConcept> go() throws JWNLException {
        
        Synset[] senses = (Synset[]) indexWord.getSenses();
        for (Synset sense : senses) {
           // System.out.println(" gloss : "+sense.getGloss());
            
            
            Word[] words = sense.getWords();
            for (Word word : words) {
              /*System.out.println("Lemma : "+word.getLemma());
                System.out.println("POS Key : "+word.getPOS().getKey());
                System.out.println("POS Label : "+word.getPOS().getLabel());
                System.out.println("Index : "+word.getIndex());
                System.out.println("Synset : "+word.getSynset());
                */
                if(word.getLemma().equals(this.parentConceptHead)){
                    
                   // System.out.println(" Offset : "+sense.);
                    
                    if(sense.getOffset() == 6568978){
                        System.out.println(" Offset : "+sense.getOffset());
                        System.out.println("Gloss : "+sense.getGloss());
                        //System.out.println("LexFileName : "+sense.getLexFileName());
                        //System.out.println("LexFileId : "+sense.getLexFileId());
                        PointerTargetNodeList hypernyms = PointerUtils.getInstance().getDirectHypernyms(sense);
                        System.out.println("Direct hypernyms of \"" + word.getLemma() + "\":");
                        hypernyms.print();
                        
                 
                        
                    }
                    
                    
                    ParentConcept concept = new ParentConcept(word.getLemma(), sense.getGloss(), word.getPOS().getLabel());
                    this.parentConcepts.add(concept);
                }
                
            }
            //System.out.println("==========================");
        }
        return this.parentConcepts;
        //demonstrateListOperation(indexWord);
	//demonstrateTreeOperation(indexWord);
    }
  
    private void demonstrateListOperation(IndexWord word) throws JWNLException {
        // Get all of the hypernyms (parents) of the first sense of <var>word</var>
        PointerTargetNodeList hypernyms = PointerUtils.getInstance().getDirectHypernyms(word.getSense(1));
        System.out.println("Direct hypernyms of \"" + word.getLemma() + "\":");
        hypernyms.print();
    }
    
    private void demonstrateTreeOperation(IndexWord word) throws JWNLException {
        // Get all the hyponyms (children) of the first sense of <var>word</var>
        PointerTargetTree hyponyms = PointerUtils.getInstance().getHyponymTree(word.getSense(1));
        System.out.println("Hyponyms of \"" + word.getLemma() + "\":");
        hyponyms.print();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeo.jaws;

/**
 *
 * @author Jabed hasan
 */
import java.util.ArrayList;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;

public class JawsAPI {
    public WordNetDatabase database;
    public ArrayList<Synset> nouneSynsetsList  = new ArrayList<Synset>();
    public  ArrayList<Synset> varbSynsetsList  = new ArrayList<Synset>();
    public ArrayList<Synset> adjectiveSynsetsList  = new ArrayList<Synset>();
    public ArrayList<Synset> adverbSynsetsList  = new ArrayList<Synset>();
    public ArrayList<Synset> adjectiveSatelliteSynsetsList  = new ArrayList<Synset>();
    public String words = "";
    public static  String resultAPI = "";
    
    public void setData(String word){
		
        Synset[] synsets = database.getSynsets(word);

        if( synsets.length > 0 ){

                for (int i = 0; i < synsets.length; i++)
                {
                        SynsetType synsetType = synsets[i].getType();

                        if( synsetType == SynsetType.NOUN ){
                                setNounSynset(synsets[i]);
                        }
                        else if (synsetType == SynsetType.VERB) {
                                setVarbSynset(synsets[i]);
                        }
                        else if (synsetType == SynsetType.ADJECTIVE) {
                                setAdjectiveSynset(synsets[i]);
                        }
                        else if (synsetType == SynsetType.ADVERB) {
                                setAdverbSynset(synsets[i]);
                        }
                        else {
                                setAdjectiveSatelliteSynset(synsets[i]);
                        }
                }
        }
        else{
            JawsAPI.resultAPI += "Nathing Found.\n Here we will Apply SAM(Semi-automatic mapping)";
        }
    }
    
    public void showData(ArrayList<Synset> synsetsList,String word){
		
        for(Synset synset : synsetsList){
                String[] wordForms = synset.getWordForms();
                JawsAPI.resultAPI += "\n"; 
                for (int j = 0; j < wordForms.length; j++) {
                   JawsAPI.resultAPI += (j > 0 ? ", " : "") + wordForms[j]+"";
                }

                //Definition
                 JawsAPI.resultAPI += ": " + synset.getDefinition()+"\n";

                //Examples
                String[] useExamples = synset.getUsageExamples();
                int exmplCountr = 1;

                if(useExamples.length > 0){

                         JawsAPI.resultAPI += "Examples :=> \n";

                        for(String exmpl:useExamples){
                                 JawsAPI.resultAPI += exmplCountr+" .) "+exmpl;
                                exmplCountr++;
                        }
                }
                //Antonim
                WordSense[] wordSenses = synset.getAntonyms(word);

                for(int a = 0; a<wordSenses.length; a++ ){
                         JawsAPI.resultAPI += "\n Antonyms : => "+wordSenses[a].getWordForm();
                }

        }	
    }
    
    public void showData(String word){
		
        JawsAPI.resultAPI = "";

        if(nouneSynsetsList.size() > 0){
            JawsAPI.resultAPI += "\n========= Noun :=> \n";
            showData(nouneSynsetsList,word);
        }
        
        if(varbSynsetsList.size() > 0 ){
            JawsAPI.resultAPI += "\n========= Varb :=> \n";
            showData(varbSynsetsList,word);
        }
        
        if(adjectiveSynsetsList.size() > 0 ){
            JawsAPI.resultAPI += "\n========= Adjective :=> \n";
            showData(adjectiveSynsetsList,word);
        }

        if(adverbSynsetsList.size() > 0) {
            JawsAPI.resultAPI += "\n========= Adverb :=>\n ";
            showData(adverbSynsetsList,word);
        }
        
        if(adjectiveSatelliteSynsetsList.size() > 0 ){
            JawsAPI.resultAPI +=  "\n========= AdjectiveSatellite :=> \n";
            showData(adjectiveSatelliteSynsetsList,word);
        }

        if(word.equals(""))
                JawsAPI.resultAPI += "Input Properly ";

        if(JawsAPI.resultAPI.equals("") && (!word.equals("")))
               JawsAPI.resultAPI += "Nathing Found.\n Here we will Apply SAM(Semi-automatic mapping)";

    } 
        
    public void setNounSynset(Synset synset){
		
	nouneSynsetsList.add(synset);
    }
	
    public void setVarbSynset(Synset synset){

                    varbSynsetsList.add(synset);
    }

    public void setAdjectiveSynset(Synset synset){

            adjectiveSatelliteSynsetsList.add(synset);
    }

    public void setAdverbSynset(Synset synset){

            adverbSynsetsList.add(synset);
    }

    public void setAdjectiveSatelliteSynset(Synset synset){

            adjectiveSatelliteSynsetsList.add(synset);
    }
    
    public static String getResult(String words) {
        JawsAPI jawsAPI = new JawsAPI();
        jawsAPI.database = DbConnection.getDbConnection();

        jawsAPI.words = words; // input Should be take Here

        jawsAPI.setData(jawsAPI.words);
        jawsAPI.showData(jawsAPI.words);
        return jawsAPI.resultAPI;
    }
		
}
    

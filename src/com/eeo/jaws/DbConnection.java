/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeo.jaws;
/**
 *
 * @author jabed hasan
 */

import edu.smu.tspell.wordnet.WordNetDatabase;

public class DbConnection {
    public static WordNetDatabase getDbConnection(){
		System.setProperty("wordnet.database.dir", "/Users/ammaryeahyea/NetBeansProjects/EnrichingExistingOntology/WordNetDB/");
		return WordNetDatabase.getFileInstance();
    }
}

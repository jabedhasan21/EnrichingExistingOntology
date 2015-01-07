/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeo.model;

/**
 *
 * @author jabed hasan
 */
public class ParentConcept {
    public String lemma;
    public String gloss;
    public String label = "";
    public String summary = "";
    public String pos;
    public Long id;
    
    public ParentConcept(String lemma,String gloss,String pos,Long id){
        this.lemma = lemma;
        this.gloss = gloss;
        this.pos = pos;
        this.id = id;
    }
}

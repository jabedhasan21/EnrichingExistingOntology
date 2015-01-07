/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eeo.model;

/**
 *
 * @author ammaryeahyea
 */
public class NewConcept {
    public String currentWikiCategoryHead;
    public String alternativeDefinition;
    public String parentConceptHead;
    public NewConcept(String currentWikiCategoryHead,String alternativeDefinition,String parentConceptHead){
        this.currentWikiCategoryHead = currentWikiCategoryHead;
        this.alternativeDefinition = alternativeDefinition;
        this.parentConceptHead = parentConceptHead;
    }
}

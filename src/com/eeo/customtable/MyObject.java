package com.eeo.customtable;

import java.beans.PropertyChangeSupport;

public class MyObject {

	 private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

     private MyObjectManager manager;

     private String value;

     private boolean selected;

     public MyObject(String value) {
         this.value = value;
     }

     public PropertyChangeSupport getPropertyChangeSupport() {
         return propertyChangeSupport;
     }

     public String getValue() {
         return value;
     }

     public void setValue(String value) {
         this.value = value;
         propertyChangeSupport.firePropertyChange("value", null, value);
     }

     public MyObjectManager getManager() {
         return manager;
     }

     public void setManager(MyObjectManager manager) {
         this.manager = manager;
         propertyChangeSupport.firePropertyChange("manager", null, manager);
     }

     public boolean isSelected() {
         return selected;
     }

     public void setSelected(boolean selected) {
         if (this.selected != selected) {
             this.selected = selected;
             if (selected) {
                 manager.setAsSelected(this);
             }
             propertyChangeSupport.firePropertyChange("selected", !selected, selected);
         }
     }
}

package com.eeo.customtable;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


public class MyObjectManager {

    public PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private List<MyObject> objects = new ArrayList<MyObject>();

    public void addObject(MyObject object) {
        objects.add(object);
        object.setManager(this);
        propertyChangeSupport.firePropertyChange("objects", null, object);
    }

    public List<MyObject> getObjects() {
        return objects;
    }

    public void setAsSelected(MyObject myObject) {
        for (MyObject o : objects) {
            o.setSelected(myObject == o);
        }
    }
}

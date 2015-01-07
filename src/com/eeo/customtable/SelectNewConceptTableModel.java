package com.eeo.customtable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.table.AbstractTableModel;

public class SelectNewConceptTableModel extends AbstractTableModel implements PropertyChangeListener {

	private final MyObjectManager manager;

    public SelectNewConceptTableModel(MyObjectManager manager) {
        super();
        this.manager = manager;
        manager.propertyChangeSupport.addPropertyChangeListener(this);
        for (MyObject object : manager.getObjects()) {
            object.getPropertyChangeSupport().addPropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == manager) {
            // OK, not the cleanest thing, just to get the gist of it.
            if (evt.getPropertyName().equals("objects")) {
                ((MyObject) evt.getNewValue()).getPropertyChangeSupport().addPropertyChangeListener(this);
            }
            fireTableDataChanged();
        } else if (evt.getSource() instanceof MyObject) {
            int index = manager.getObjects().indexOf(evt.getSource());
            fireTableRowsUpdated(index, index);
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return manager.getObjects().size();
    }

    public MyObject getValueAt(int row) {
        return manager.getObjects().get(row);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return getValueAt(rowIndex).getValue();
        case 1:
            return getValueAt(rowIndex).isSelected();
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            getValueAt(rowIndex).setSelected(Boolean.TRUE.equals(value));
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
        case 0:
            return String.class;
        case 1:
            return Boolean.class;
        }
        return Object.class;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
        case 0:
            return "Suggested Concept";
        case 1:
            return "Selected";
        }
        return null;
    }
}

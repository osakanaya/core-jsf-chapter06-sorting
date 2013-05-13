package com.corejsf;

import java.util.Arrays;
import java.util.Comparator;

import javax.faces.model.DataModel;

public class SortFilterModel<E> extends DataModel<E> {
	private DataModel<E> model;
	private Integer[] rows;

	public SortFilterModel() {
		setWrappedData(null);
	}
	
	public SortFilterModel(E[] names) {
		setWrappedData(names);
	}
	
	public SortFilterModel(DataModel<E> model) {
		this.model = model;
		initializeRows();
	}
	
	private void initializeRows() {
		int rowCnt = model.getRowCount();
		if (rowCnt != -1) {
			rows = new Integer[rowCnt];
			for(int i = 0; i < rowCnt; i++) {
				rows[i] = i;
			}
		}
	}
	
	@Override
	public void setRowIndex(int rowIndex) {
		if (0 <= rowIndex && rowIndex < rows.length) {
			model.setRowIndex(rows[rowIndex]);
		} else {
			model.setRowIndex(rowIndex);
		}
	}

	@Override
	public boolean isRowAvailable() {
		return model.isRowAvailable();
	}

	@Override
	public int getRowCount() {
		return model.getRowCount();
	}

	@Override
	public E getRowData() {
		return model.getRowData();
	}

	@Override
	public int getRowIndex() {
		return model.getRowIndex();
	}

	@Override
	public Object getWrappedData() {
		return model.getWrappedData();
	}

	@Override
	public void setWrappedData(Object data) {
		model.setWrappedData(data);
		initializeRows();
	}

	public void sortBy(final Comparator<E> dataComp) {
		Comparator<Integer> rowComp = new Comparator<Integer>() {
			public int compare(Integer r1, Integer r2) {
				E e1 = getData(r1);
				E e2 = getData(r2);
				return dataComp.compare(e1, e2);
			}
		};
		
		Arrays.sort(rows, rowComp);
	}
	
	private E getData(int row) {
		int originalIndex = model.getRowIndex();
		model.setRowIndex(row);
		E thisRowData = model.getRowData();
		model.setRowIndex(originalIndex);
		
		return thisRowData;
	}
}

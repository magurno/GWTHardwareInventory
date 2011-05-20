package com.google.gwt.gwthardwareinventory.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GWTHardwareInventory implements EntryPoint {

  private VerticalPanel mainPanel = new VerticalPanel();
  private FlexTable categoriesFlexTable = new FlexTable();
  private HorizontalPanel addPanel = new HorizontalPanel();
  private TextBox newNameCategoryTextBox = new TextBox();
  private Button addCategoryButton = new Button("Add");
  private Label lastUpdatedLabel = new Label();
  private ArrayList<String> categories = new ArrayList<String>();

  /**
   * Entry point method.
   */
  public void onModuleLoad() {
 	  // Create table for category data.
	    categoriesFlexTable.setText(0, 0, "Name Category");
	    categoriesFlexTable.setText(0, 1, "Delete");
	    // Add styles to elements in the stock list table.
	    categoriesFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
	    categoriesFlexTable.addStyleName("watchList");
	    categoriesFlexTable.getCellFormatter().addStyleName(0, 1, "watchListRemoveColumn");
	    // Assemble Add Category panel.
	    addPanel.add(newNameCategoryTextBox);
	    addPanel.add(addCategoryButton);
	    addPanel.addStyleName("addPanel");
	    // Assemble Main panel.
	    mainPanel.add(categoriesFlexTable);
	    mainPanel.add(addPanel);
	    mainPanel.add(lastUpdatedLabel);
	    // Associate the Main panel with the HTML host page.
	    RootPanel.get("categoryList").add(mainPanel);
	    // Move cursor focus to the input box.
	    newNameCategoryTextBox.setFocus(true);
	    // Listen for mouse events on the Add button.
	    addCategoryButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        addCategory();
	      }
	    });
	    // Listen for keyboard events in the input box.
	    newNameCategoryTextBox.addKeyPressHandler(new KeyPressHandler() {
	      public void onKeyPress(KeyPressEvent event) {
	        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
	          addCategory();
	        }
	      }
	    });

	  }

	  /**
	   * Add category to FlexTable. Executed when the user clicks the addCategoryButton or
	   * presses enter in the newNameCategoryTextBox.
	   */
	  private void addCategory() {
		   final String nameCategory = newNameCategoryTextBox.getText().toUpperCase().trim();
		    newNameCategoryTextBox.setFocus(true);

		    // Category name must be between 1 and 10 chars that are numbers, letters, or dots.
		    if (!nameCategory.matches("^[0-9A-Z\\.]{1,10}$")) {
		      Window.alert("'" + nameCategory + "' is not a valid symbol.");
		      newNameCategoryTextBox.selectAll();
		      return;
		    }

		    newNameCategoryTextBox.setText("");
		    // Don't add the category if it's already in the table.
		    if (categories.contains(nameCategory))
		      return;
		    // Add the category to the table.
		    int row = categoriesFlexTable.getRowCount();
		    categories.add(nameCategory);
		    categoriesFlexTable.setText(row, 0, nameCategory);
		    categoriesFlexTable.getCellFormatter().addStyleName(row, 1, "watchListRemoveColumn");
		    // Add a button to remove this stock from the table.
		    Button removeCategoryButton = new Button("x");
		    removeCategoryButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        int removedIndex = categories.indexOf(nameCategory);
		        categories.remove(removedIndex);        
		        categoriesFlexTable.removeRow(removedIndex + 1);
		      }
		    });
		    categoriesFlexTable.setWidget(row, 3, removeCategoryButton);
	  }
  }
package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.ImmutableList
import com.vaadin.Application
import com.vaadin.ui.Label
import com.vaadin.ui.Table
import com.vaadin.ui.Window

/**
 * User: rafalmag
 * Date: 17.12.12
 * Time: 18:27
 */
class VaadinHW extends Application {

    @Override
    void init() {
        Model model = new Model(ImmutableList.of(new Person("A"), new Person("B")), new ModelValidator());

        // Main window is the primary browser window
        final Window main = new Window("Hello window")
        setMainWindow(main)

        // "Hello world" text is added to window as a Label component
        main.addComponent(new Label("Hello World!" + model.toString()))

        Table table = initTable()
        main.addComponent(table)
    }

    Table initTable() {
        Table table = new Table("Table with Cell Styles");
        table.addStyleName("checkerboard");

// Add some columns in the table. In this example, the property
// IDs of the container are integers so we can determine the
// column number easily.

//        table.addContainerProperty("0", String.class, null, "", null, null);
        for (int i = 0; i < 8; i++)
            table.addContainerProperty("" + (i + 1), String.class, null,
                    String.valueOf((char) (65 + i)), null, null);

// Add some items in the table.
        table.addItem(["R", "N", "B", "Q", "K", "B", "N", "R"].toArray(), new Integer(0));
        table.addItem(["P", "P", "P", "P", "P", "P", "P", "P"].toArray(), new Integer(1));
        for (int i = 2; i < 6; i++)
            table.addItem(["", "", "", "", "", "", "", ""].toArray(), new Integer(i));
        table.addItem(["P", "P", "P", "P", "P", "P", "P", "P"].toArray(), new Integer(6));
        table.addItem(["R", "N", "B", "Q", "K", "B", "N", "R"].toArray(), new Integer(7));

        table.setRowHeaderMode(Table.ROW_HEADER_MODE_ID)
        table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_ID)
        table.setPageLength(8);

// Set cell style generator
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            public String getStyle(Object itemId, Object propertyId) {
                // Row style setting, not relevant in this example.
                if (propertyId == null)
                    return "green"; // Will not actually be visible

                int row = ((Integer) itemId).intValue();
                int col = Integer.parseInt((String) propertyId);

                // The first column.
                if (col == 0)
                    return "rowheader";

                // Other cells.
                if ((row + col) % 2 == 0)
                    return "black";
                else
                    return "white";
            }
        })
        table
    }
}

package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.ImmutableList
import com.vaadin.Application
import com.vaadin.data.Item
import com.vaadin.data.util.IndexedContainer
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

    static Table initTable() {
        Table table = new Table("Table with Cell Styles");
        table.addStyleName("checkerboard");


        IndexedContainer container = initContainer()

        table.setContainerDataSource(container)

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

    static IndexedContainer initContainer() {
        IndexedContainer container = new IndexedContainer();
// Add some columns in the table. In this example, the property
// IDs of the container are integers so we can determine the
// column number easily.

        def properties = (1..8).step(1).collect { "" + it }
        for (int i = 0; i < 8; i++) {
            String property = properties[i]
            String value = String.valueOf((char) (65 + i))
            container.addContainerProperty(property, property.getClass(), value)
        }

// Add some items in the table.
        addItems(container, properties, new Integer(0), ["R", "N", "B", "Q", "K", "B", "N", "R"])
        addItems(container, properties, new Integer(1), ["P", "P", "P", "P", "P", "P", "P", "P"])
        for (int i = 2; i < 6; i++) {
            addItems(container, properties, new Integer(i), ["", "", "", "", "", "", "", ""])
        }
        addItems(container, properties, new Integer(6), ["P", "P", "P", "P", "P", "P", "P", "P"])
        addItems(container, properties, new Integer(7), ["R", "N", "B", "Q", "K", "B", "N", "R"])
        container
    }

    static void addItems(IndexedContainer container, List<String> properties, int itemId, ArrayList<String> values) {
        Item item = container.addItem(itemId);
        [properties, values].transpose().collectEntries {
            it
        }.each {
            item.getItemProperty(it.key).setValue(it.value)
        }
    }
}

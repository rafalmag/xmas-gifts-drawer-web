package pl.rafalmag.xmasgiftsdrawer.gui

import com.vaadin.Application
import com.vaadin.data.Item
import com.vaadin.data.util.IndexedContainer
import com.vaadin.ui.Table
import com.vaadin.ui.Window
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.ModelValidator
import pl.rafalmag.xmasgiftsdrawer.Person

class MainWindow extends Application {

    final Model model = new Model([new Person("A"), new Person("B")], new ModelValidator())

    @Override
    void init() {
        setTheme("mytheme")
        def mainWindow = new Window("Hello window")
        setMainWindow(mainWindow)

        mainWindow.addComponent(initTable())
    }

    def Table initTable() {
        Table table = new Table("Table with Model");
        table.addStyleName("model")

        IndexedContainer container = initContainer(model)
        table.setContainerDataSource(container)
        table.setRowHeaderMode(Table.ROW_HEADER_MODE_ID)
        table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_ID)
        table.setPageLength(model.getPersons().size())
        table
    }

    static IndexedContainer initContainer(Model model) {
        IndexedContainer container = new IndexedContainer();

        model.getPersons().each {
            container.addContainerProperty(it, it.getClass(), it.name)
        }

        model.getPersons().each { giver ->
            Item giverItem = container.addItem(giver)
            model.getPersons().each { getter ->
                giverItem.getItemProperty(getter).setValue(model.canGive(giver, getter))
            }
        }
        container
    }

}

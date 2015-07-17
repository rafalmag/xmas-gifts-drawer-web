package pl.rafalmag.xmasgiftsdrawer.gui

import com.vaadin.annotations.Theme
import com.vaadin.data.Item
import com.vaadin.data.util.IndexedContainer
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Table
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import pl.rafalmag.xmasgiftsdrawer.Model
import pl.rafalmag.xmasgiftsdrawer.ModelValidator
import pl.rafalmag.xmasgiftsdrawer.Person

@Theme("mytheme")
class MainWindow extends UI {

    final Model model = new Model([new Person("A"), new Person("B")], new ModelValidator())

    @Override
    void init(VaadinRequest request) {
        println "init"
        VerticalLayout view = new VerticalLayout();
        view.addComponent(initTable())
        setContent(view);
    }

    def Table initTable() {
        Table table = new Table("Table with Model")
        table.addStyleName("model")

        IndexedContainer container = initContainer(model)
        table.setContainerDataSource(container)
        table.setRowHeaderMode(Table.ROW_HEADER_MODE_ID)
        table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_ID)
        table.setPageLength(model.getPersons().size())
        table
    }

    static IndexedContainer initContainer(Model model) {
        IndexedContainer container = new IndexedContainer()

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

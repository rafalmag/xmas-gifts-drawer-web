package pl.rafalmag.xmasgiftsdrawer

import com.google.common.collect.ImmutableList
import com.vaadin.Application
import com.vaadin.ui.Label
import com.vaadin.ui.Window

/**
 * User: rafalmag
 * Date: 17.12.12
 * Time: 18:27
 */
class VaadinHW extends Application{

    @Override
    void init() {
        Model model = new Model(ImmutableList.of(new Person("A"),new Person("B")),new ModelValidator());

        // Main window is the primary browser window
        final Window main = new Window("Hello window")
        setMainWindow(main)

        // "Hello world" text is added to window as a Label component
        main.addComponent(new Label("Hello World!"+model.toString()))
    }
}

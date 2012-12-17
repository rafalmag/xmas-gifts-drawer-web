//
//import com.google.common.collect.ImmutableList;
//import com.vaadin.Application;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.Window;
//import pl.rafalmag.xmasgiftsdrawer.Model;
//import pl.rafalmag.xmasgiftsdrawer.ModelValidator;
//import pl.rafalmag.xmasgiftsdrawer.Person;
//
//@SuppressWarnings("serial")
//public class HelloWorld extends Application {
//
//    /**
//     * Init is invoked on application load (when a user accesses the application
//     * for the first time).
//     */
//    @Override
//    public void init() {
//
//        Model model = new Model(ImmutableList.of(new Person("A"),new Person("B")),new ModelValidator());
//
//        // Main window is the primary browser window
//        final Window main = new Window("Hello window");
//        setMainWindow(main);
//
//        // "Hello world" text is added to window as a Label component
//        main.addComponent(new Label("Hello World!"+model.toString()));
//    }
//
//}
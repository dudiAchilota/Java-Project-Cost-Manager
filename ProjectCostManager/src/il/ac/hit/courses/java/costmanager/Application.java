package il.ac.hit.courses.java.costmanager;

import il.ac.hit.courses.java.costmanager.model.*;
import il.ac.hit.courses.java.costmanager.view.IView;
import il.ac.hit.courses.java.costmanager.view.View;
import il.ac.hit.courses.java.costmanager.viewModel.IViewModel;
import il.ac.hit.courses.java.costmanager.viewModel.ViewModel;

/**
 * Running the application cost manager
 */
public class Application {
    /**
     * main to application
     *
     * @param args
     */
    public static void main(String args[]) {
        /*
         * Implement the MVVM architecture
         */
        String message = "";

        //creating the application components
        IView view = new View();
        IModel model = null;
        try {
            model = new DerbyDBModel();
        } catch (CostManagerException e) {
            message = e.getMessage();
        }

        // If you have a problem building tables send a message
        view.showMessage(message, "main");

        IViewModel vm = new ViewModel();

        //connecting components with each other
        view.setViewModel(vm);
        vm.setView(view);
        vm.setModel(model);

    }
}


package oreillyclock.commandsandhandlers;

import static java.util.concurrent.TimeUnit.SECONDS;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.MessageDialog;

public class HelloHandler {

    @Execute
    public void execute(final UISynchronize display) {

        Job job = new Job("About to say hello") {

            @Override
            protected IStatus run(IProgressMonitor progressMonitor) {

                try {
                    progressMonitor.beginTask("Preparing", 5000);
                    for (int i = 0; i < 5; i++) {
                        SECONDS.sleep(1);
                        progressMonitor.worked(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    progressMonitor.done();
                }

                // w/o display.asyncExec() it throws exception since dialog should be created in UI Thread!
                display.asyncExec(() -> MessageDialog.openInformation(null, "Hello", "World"));

                return Status.OK_STATUS;
            }
        };

        job.schedule();
    }
}
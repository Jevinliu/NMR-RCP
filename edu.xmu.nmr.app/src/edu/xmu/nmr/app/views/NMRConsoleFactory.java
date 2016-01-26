package edu.xmu.nmr.app.views;

import java.io.PrintStream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class NMRConsoleFactory implements IConsoleFactory {
    
    int counter = 1;
    
    private static MessageConsole console = new MessageConsole("", null);
    
    public void closeConsole() {
        final IConsoleManager manager = ConsolePlugin.getDefault()
                .getConsoleManager();
        if (console != null) {
            manager.removeConsoles(new IConsole[] { console });
        }
    }
    
    public MessageConsole getConsole() {
        return console;
    }
    
    public void showConsole() {
        if (console != null) {
            final IConsoleManager manager = ConsolePlugin.getDefault()
                    .getConsoleManager();
            final IConsole[] existing = manager.getConsoles();
            boolean exists = false;
            for (IConsole element : existing) {
                if (console == element) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                manager.addConsoles(new IConsole[] { console });
            }
            manager.showConsoleView(console);
            console.clearConsole();
            final MessageConsoleStream stream = console.newMessageStream();
            System.setOut(new PrintStream(stream));
            final MessageConsoleStream stream2 = console.newMessageStream();
            final Color redColor = new Color(null, 255, 0, 0);
            stream2.setColor(redColor);
            System.setErr(new PrintStream(stream2));
        }
    }
    
    @Override public void openConsole() {
        // TODO Auto-generated method stub
         showConsole();
    }
    
}

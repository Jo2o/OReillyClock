package oreillyclock;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ClockWidget extends Canvas {

    public ClockWidget(Composite parent, int style) {
        super(parent, style);

        addPaintListener(this::drawClock);

        Runnable redraw = () -> {
            while (!this.isDisposed()) { // extends Resources have to be disposed
                // clock.redraw(); // this generates exception
                this.getDisplay().asyncExec(this::redraw); // running on UI thread
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
        new Thread(redraw, "TickTock").start();
    }

    private void drawClock(PaintEvent paintEvent) {
        paintEvent.gc.drawArc(paintEvent.x, paintEvent.y, paintEvent.width - 1, paintEvent.height - 1, 0, 360);
        drawSecondHand(paintEvent);
    }

    private void drawSecondHand(PaintEvent paintEvent) {
        int seconds = LocalTime.now().getSecond();
        int arc = (15 - seconds) * 6 % 360;
        Color blue = paintEvent.display.getSystemColor(SWT.COLOR_BLUE);
        paintEvent.gc.setBackground(blue);
        paintEvent.gc.fillArc(paintEvent.x, paintEvent.y, paintEvent.width - 1, paintEvent.height - 1, arc - 1, 2);
    }

    @Override
    public Point computeSize(int w, int h, boolean changed) {
        int size;
        if (w == SWT.DEFAULT) {
            size = h;
        } else if (h == SWT.DEFAULT) {
            size = w;
        } else {
            size = Math.min(w, h);
        }
        if (size == SWT.DEFAULT) {
            size = 50;
        }
        return new Point(size, size);
    }
}

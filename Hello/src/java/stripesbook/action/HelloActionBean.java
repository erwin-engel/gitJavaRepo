/***
 * Excerpted from "Stripes: and Java Web Development is Fun Again",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
***/

package stripesbook.action;

import java.util.Date;
import java.util.Random;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class HelloActionBean implements ActionBean {/* (1) */
    private ActionBeanContext ctx;
    public ActionBeanContext getContext() { return ctx; }
    public void setContext(ActionBeanContext ctx) { this.ctx = ctx; }

    private Date date;/* (2) */
    public Date getDate() {
        return date;
    }
    @DefaultHandler
    public Resolution currentDate() {/* (3) */
        date = new Date();
        return new ForwardResolution(VIEW);
    }
    public Resolution randomDate() {
        long max = System.currentTimeMillis();
        long random = new Random().nextLong() % max;
        date = new Date(random);
        return new ForwardResolution(VIEW);
    }
    private static final String VIEW = "/WEB-INF/jsp/hello.jsp";
}


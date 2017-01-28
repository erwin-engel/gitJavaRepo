/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reken.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import reken.domein.Reken;

/**
 *
 * @author erwin
 */
public class RekenActionBean extends BaseActionBean implements ActionBean {
    private static final String REKENFORMVIEW = "/WEB-INF/jsp/rekenform.jsp";
    private static final String SOMVIEW = "/WEB-INF/jsp/som.jsp";
    private Reken reken = null;
   
    public void setReken(Reken reken){
        this.reken = reken;
    }
    
    public Reken getReken() {
        if (reken != null) {
            return reken;
        } else {
            reken = new Reken();
            return reken;
        }
    }
    
    @DefaultHandler
    public Resolution toonRekenForm() {
        return new ForwardResolution(REKENFORMVIEW);
    }
    
    public Resolution optellen() {
        return new ForwardResolution(SOMVIEW);
    }
}

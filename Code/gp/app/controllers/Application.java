package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void group() {
        List<GPM> olderGPM = GPM.find(
            "order by GR desc"
        ).from(0).fetch(250);
        render(olderGPM);
    }
    
    public static void groupplus() {
        List<GPM> olderGPM = GPM.find(
            "order by sum_plus desc"
        ).from(0).fetch(250);
        render(olderGPM);
    }
    
    public static void groupcomment() {
        List<GPM> olderGPM = GPM.find(
            "order by sum_comment desc"
        ).from(0).fetch(250);
        render(olderGPM);
    }
    
    public static void groupreshared() {
        List<GPM> olderGPM = GPM.find(
            "order by sum_reshared desc"
        ).from(0).fetch(250);
        render(olderGPM);
    }
}
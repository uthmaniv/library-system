package org.uthmaniv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.println("Testing log 4j for the first time");
        logger.info("We all good uthman");
        System.out.println("completed");
    }
}
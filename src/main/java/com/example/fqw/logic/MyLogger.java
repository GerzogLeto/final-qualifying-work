package com.example.fqw.logic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {

   public static Logger logger = LoggerFactory.getLogger(MyLogger.class);

   public static void defineTrouble (ErrorType errorType, Object[] arr){

      switch (errorType){
         case GOTO_WRONG_POSITION -> {
            Long truckId = (Long)arr[0];
            Long commandId = (Long)arr[1];
            String message = (String)arr[2];
            MyLogger.logger.warn("\n#####################" +
                    "\nГрузовик c ИД:  " + truckId +
                    "\nне может выполнить комманду GOTO с ИД: " + commandId +
                    "\n" + message +
                    "\n#####################");
            break;

         }

         case LOAD_WRONG_POS_TRUCK -> {
            Long truckId = (Long)arr[0];
            Long commandId = (Long)arr[1];
            Long freightId = (Long)arr[2];
            MyLogger.logger.warn("\n#####################" +
                    "\nГрузовик c ИД:  " + truckId +
                    "\nне может выполнить комманду LOAD с ИД: " + commandId +
                    "\nГрузовик не в месте загрузки груза с ИД: " + freightId +
                    "\n#####################");
            break;

         }

         case UNLOAD_POS_TRUCK -> {
            Long truckId = (Long)arr[0];
            Long commandId = (Long)arr[1];
            Long freightId = (Long)arr[2];
            MyLogger.logger.warn("\n#####################" +
                    "\nГрузовик c ИД:  " + truckId +
                    "\nне может выполнить комманду UNLOAD с ИД: " + commandId +
                    "\nГрузовик не в месте разгрузки груза с ИД: " + freightId +
                    "\n#####################");
            break;

         }
      }
   }
}

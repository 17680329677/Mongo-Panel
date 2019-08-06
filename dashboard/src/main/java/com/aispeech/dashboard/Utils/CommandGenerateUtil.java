package com.aispeech.dashboard.Utils;

import com.aispeech.dashboard.Model.Command;

/**
 * @Author: hezhe.du
 * @Date: 2019/6/10 0010 16:06
 */

public class CommandGenerateUtil {

    public static String commandGenerate(Command command) {
        if ("find".equals(command.getOperate())) {
            return findCommand(command);
        } else if ("count".equals(command.getOperate())) {
            return countCommand(command);
        } else {
            return null;
        }
    }

    private static String findCommand(Command command) {
        String findCommand;
        if (command.isDiagnostic()) {
            findCommand = "function(){return db." + command.getCollection() + ".find().explain('executionStats');}";
        } else {
            findCommand = "function(){var a=[]; db." + command.getCollection() + ".find(" + command.getFilter() + ")." +
                    "limit(" + command.getLimit() + ").skip(" + command.getSkip() + ").pretty()." +
                    "forEach(function(i){a.push(i)}); return a;}";
        }
        return findCommand;
    }

    private static String countCommand(Command command) {
        String countCommand = "function(){return db." + command.getCollection() + ".count(" + command.getFilter() + ");}";
        return countCommand;
    }
}

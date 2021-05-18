package com.adpro.remind.core.help;

public class Help implements HelpMessage {
    private static Help instance = new Help();

    private Help() {}

    public static Help getInstance() {
        return instance;
    }

    @Override
    public String help() {
        return "--ReMind Bot Command List--\n" +
                "\n" +
                "-help untuk menampilkan list ini\n" +
                "-help reminder untuk menampilkan list command untuk fitur reminder\n" +
                "-help schedule untuk menampilkan list command untuk fitur schedule\n" +
                "-help todo untuk menampilkan list command untuk fitur todo\n";
    }

    public String help(String fitur) {
        HelpMessage helpMessage;
        if (fitur.equals("reminder")) helpMessage = HelpReminder.getInstance();
        else if (fitur.equals("schedule")) helpMessage = HelpSchedule.getInstance();
        else if (fitur.equals("todo")) helpMessage = HelpToDoList.getInstance();
        else helpMessage = ErrorHelp.getInstance();
        return helpMessage.help();
    }
}

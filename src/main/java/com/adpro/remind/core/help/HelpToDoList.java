package com.adpro.remind.core.help;

public class HelpToDoList implements HelpMessage {
    private static HelpToDoList instance = new HelpToDoList();

    private HelpToDoList() {}

    public static HelpToDoList getInstance() {
        return instance;
    }
    @Override
    public String help() {
        return "#Fitur ToDoList\n" +
                "\n" +
                "-list ADD [NAMA_LIST]\n" +
                "-list ADDITEM [ID_LIST] [NAMA_ITEM]\n" +
                "-list DELETE [ID_LIST] [NOMOR_ITEM]\n" +
                "-list DELETE [ID_LIST]\n" +
                "-list SHOW [ID_LIST]\n" +
                "-list SHOW ALL\n" +
                "\n" +
                "Notes:\n" +
                "1. Untuk melihat ID suatu List, gunakan -list SHOW ALL\n"+
                "2. Untuk melihat nomor suatu item pada list, gunakan -list SHOW [ID_LIST]\n";
    }
}

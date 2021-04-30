package com.adpro.remind.core.help;

public class HelpSchedule implements HelpMessage {
    private static HelpSchedule instance = new HelpSchedule();

    private HelpSchedule() {}

    public static HelpSchedule getInstance() {
        return instance;
    }
    @Override
    public String help() {
        return "#Fitur Schedule\n" +
                "\n" +
                "-schedule ADD [NAMA_SCHEDULE] [HARI] [JAM] [DESC]\n" +
                "-schedule DELETE [ID_SCHEDULE]\n" +
                "-schedule UPDATE [ID_SCHEDULE] [HARI_BARU] [JAM_BARU]\n" +
                "-schedule UPDATEDESC [ID_SCHEDULE] [DESC_BARU]\n" +
                "-schedule SHOW [HARI]\n" +
                "-schedule SHOW ALL\n" +
                "\n" +
                "Notes:\n" +
                "1. Format HARI: Senin, Selasa, dsb.\n" +
                "2. Format JAM: HH:MM\n" +
                "4. Untuk melihat ID suatu Schedule, gunakan -schedule SHOW ALL atau -schedule SHOW [HARI]\n";
    }
}

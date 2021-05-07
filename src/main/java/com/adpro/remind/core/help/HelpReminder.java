package com.adpro.remind.core.help;

public class HelpReminder implements HelpMessage {
    private static HelpReminder instance = new HelpReminder();

    private HelpReminder() {}

    public static HelpReminder getInstance() {
        return instance;
    }
    @Override
    public String help() {
        return "#Fitur Reminder\n" +
                "\n" +
                "-reminder ADD [NAMA_REMINDER] [TANGGAL] [JAM]\n" +
                "-reminder DELETE [ID_REMINDER]\n" +
                "-reminder UPDATE [ID_REMINDER] [TANGGAL_BARU] [JAM_BARU]\n" +
                "-reminder SHOW ALL\n" +
                "-reminder SHOW [TANGGAL]\n" +
                "-reminder DETAIL [ID_REMINDER]\n" +
                "-reminder SET [ID_REMINDER] [WAKTU_REMINDER]\n" +
                "\n" +
                "Notes:\n" +
                "1. Format TANGGAL: DD/MM/YYYY\n" +
                "2. Format JAM: HH:MM\n" +
                "3. Format WAKTU: X Hari atau X Jam\n" +
                "4. Untuk melihat ID suatu Reminder, gunakan -reminder SHOW ALL atau -reminder SHOW [TANGGAL]\n";
    }
}

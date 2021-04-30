package com.adpro.remind.core.help;

public class ErrorHelp implements HelpMessage {
    private static ErrorHelp instance = new ErrorHelp();

    private ErrorHelp() {}

    public static ErrorHelp getInstance() {
        return instance;
    }

    @Override
    public String help() {
        return "Fitur ini tidak tersedia. Silahkan masukkan command lain atau coba -help untuk menampilkan semua fitur.";
    }
}

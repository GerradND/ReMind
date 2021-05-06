package com.adpro.remind.controller.schedule;

import com.adpro.remind.controller.OpsCommand;

public class ScheduleCommand {
    private OpsCommand opsCommand;
    private String outputMessage;

    public ScheduleCommand(String[] components, String ops){
        opsCommand = null;
        if (ops.equals("add")){
            opsCommand = new AddSchedule(components);
        } else if (ops.equals("update")) {
            opsCommand = new UpdateSchedule(components);
        } else if (ops.equals("delete")) {
            opsCommand = new DeleteSchedule(components);
        } else if (ops.equals("show")) {
            opsCommand = new ShowSchedule(components);
        }
        this.outputMessage = opsCommand.execute();
    }

    public String getOutputMessage() {
        return outputMessage;
    }

}

package com.adpro.remind.repository;

import com.adpro.remind.command.Command;

public interface CommandRepository {
    Command getCommand(String commandName);
    void addCommand(String commandName, Command command);
}

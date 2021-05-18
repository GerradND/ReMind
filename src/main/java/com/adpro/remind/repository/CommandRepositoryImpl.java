package com.adpro.remind.repository;


import com.adpro.remind.command.Command;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CommandRepositoryImpl implements CommandRepository {
    static final Map<String, Command> feature = new HashMap<>();

    @Override
    public Command getCommand(String commandName) {
        return feature.get(commandName);
    }

    @Override
    public void addCommand(String commandName, Command command) {
        feature.put(commandName, command);
    }
}
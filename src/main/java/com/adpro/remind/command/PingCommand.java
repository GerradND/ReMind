package com.adpro.remind.command;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Message;

@NoArgsConstructor
public class PingCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        long time = System.currentTimeMillis();
        message.getChannel().sendMessage("Pong!")/* => RestAction<Message> */
                .queue(response /* => Message */ -> {
                    response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                });
    }
}

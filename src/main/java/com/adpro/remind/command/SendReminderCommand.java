package com.adpro.remind.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import javax.security.auth.login.LoginException;

public class SendReminderCommand implements Command {
    @Override
    public void getOutputMessage(Message message, String[] inputContent) {
        EmbedBuilder eb1 = new EmbedBuilder();
        eb1.setTitle("Pertama - langsung");
        System.out.println("Pertama");
        message.getChannel().sendMessage(eb1.build()).queue();

//        String textChannelId = message.getChannel().getId();
//        String guildId = message.getGuild().getId();
//
//        System.out.println("id text channel = " + textChannelId);
//        System.out.println("id guild = " + guildId);

//        String userId = message.getAuthor().getId();

//        System.out.println("id private = " + userId);

        String token = "ODM2MDg3NDY0NDUyMDMwNDY0.YIY5IQ.nLEk_v3_ArzDmuFCKLF8ChImhN0";
        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(token)
                    .build();
            jda.awaitReady();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        TextChannel textChannel = jda.getTextChannelById(textChannelId);
//        System.out.println(textChannelId);
//
//        EmbedBuilder eb2 = new EmbedBuilder();
//        eb2.setTitle("Kedua");
//        System.out.println("Kedua");
//        textChannel.sendMessage(eb2.build()).queue();

        String guildId = "819813694871175179";
        String textChannelId = "840079805693034496";
        TextChannel textChannel = jda.getTextChannelById(textChannelId);

        EmbedBuilder eb3 = new EmbedBuilder();
        eb3.setTitle("Ketiga - text");
        System.out.println("Ketiga");
        textChannel.sendMessage(eb3.build()).queue();

        String userId = "545157230606024708";
        User user = jda.getUserById(userId);

        EmbedBuilder eb4 = new EmbedBuilder();
        eb4.setTitle("Keempat - private");
        System.out.println("Keempat");
        user.openPrivateChannel().queue((privateChannel) ->
        {
            privateChannel.sendMessage("tes kirim private message").queue();
        });
        PrivateChannel privateChannel = user.openPrivateChannel().
    }
}

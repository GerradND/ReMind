package com.adpro.remind.service;

import com.adpro.remind.model.Guild;
import com.adpro.remind.repository.GuildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class GuildServiceImplTest {

    @Mock
    private GuildRepository guildRepository;

    @InjectMocks
    private GuildServiceImpl guildService;

    private Guild guild;

    @BeforeEach
    public void setup(){
        guild = new Guild();
        guild.setIdGuild("test");
    }

    @Test
    public void testServiceCreateGuildSuccess() {
        verify(guildRepository).save(guild);
        lenient().when(guildRepository.findByIdGuild(guild.getIdGuild())).thenReturn(guild);
        guildService.createGuild(guild.getIdGuild());
    }
}
